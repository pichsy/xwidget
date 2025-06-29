package com.pichs.xwidget.roundview;

import static com.pichs.xwidget.utils.XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView.ScaleType;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.Throwable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.TreeSet;

public class XRoundImageDrawable extends Drawable {

    @IntDef({Corner.TOP_LEFT, Corner.TOP_RIGHT, Corner.BOTTOM_LEFT, Corner.BOTTOM_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Corner {
        int TOP_LEFT = 0;
        int TOP_RIGHT = 1;
        int BOTTOM_RIGHT = 2;
        int BOTTOM_LEFT = 3;
    }


    public static final String TAG = "XRoundImageDrawable";
    public static final int DEFAULT_BORDER_COLOR = DEFAULT_COLOR_TRANSPARENT;

    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();
    private final RectF mBitmapRect = new RectF();
    private final Bitmap mBitmap;
    private final Paint mBitmapPaint;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private final RectF mBorderRect = new RectF();
    private final Paint mBorderPaint;
    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mSquareCornersRect = new RectF();

    private Shader.TileMode mTileModeX = Shader.TileMode.CLAMP;
    private Shader.TileMode mTileModeY = Shader.TileMode.CLAMP;
    private boolean mRebuildShader = true;

    private float mCornerRadius = 0f;
    // [ topLeft, topRight, bottomLeft, bottomRight ]
    private final boolean[] mCornersRounded = new boolean[]{true, true, true, true};

    private boolean mOval = false;
    private float mBorderWidth = 0;
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
    private ScaleType mScaleType = ScaleType.FIT_CENTER;

    public XRoundImageDrawable(Bitmap bitmap) {
        mBitmap = bitmap;

        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();
        mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    public static XRoundImageDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new XRoundImageDrawable(bitmap);
        } else {
            return null;
        }
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof XRoundImageDrawable) {
                // just return if it's already a XRoundImageDrawable
                return drawable;
            } else if (drawable instanceof LayerDrawable) {
                ConstantState cs = drawable.mutate().getConstantState();
                LayerDrawable ld = (LayerDrawable) (cs != null ? cs.newDrawable() : drawable);

                int num = ld.getNumberOfLayers();

                // loop through layers to and change to XRoundImageDrawables if possible
                for (int i = 0; i < num; i++) {
                    Drawable d = ld.getDrawable(i);
                    ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d));
                }
                return ld;
            }

            // try to get a bitmap from the drawable and
            Bitmap bm = drawableToBitmap(drawable);
            if (bm != null) {
                return new XRoundImageDrawable(bm);
            }
        }
        return drawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Throwable e) {
            e.printStackTrace();
            bitmap = null;
        }

        return bitmap;
    }

    public Bitmap getSourceBitmap() {
        return mBitmap;
    }

    @Override
    public boolean isStateful() {
        return mBorderColor.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int newColor = mBorderColor.getColorForState(state, 0);
        if (mBorderPaint.getColor() != newColor) {
            mBorderPaint.setColor(newColor);
            return true;
        } else {
            return super.onStateChange(state);
        }
    }

    private void updateShaderMatrix() {
        float scale;
        float dx;
        float dy;

        switch (mScaleType) {
            case CENTER:
                mBorderRect.set(mBounds);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);

                mShaderMatrix.reset();
                mShaderMatrix.setTranslate((int) ((mBorderRect.width() - mBitmapWidth) * 0.5f + 0.5f),
                        (int) ((mBorderRect.height() - mBitmapHeight) * 0.5f + 0.5f));
                break;

            case CENTER_CROP:
                mBorderRect.set(mBounds);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);

                mShaderMatrix.reset();

                dx = 0;
                dy = 0;

                if (mBitmapWidth * mBorderRect.height() > mBorderRect.width() * mBitmapHeight) {
                    scale = mBorderRect.height() / (float) mBitmapHeight;
                    dx = (mBorderRect.width() - mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = mBorderRect.width() / (float) mBitmapWidth;
                    dy = (mBorderRect.height() - mBitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth / 2,
                        (int) (dy + 0.5f) + mBorderWidth / 2);
                break;

            case CENTER_INSIDE:
                mShaderMatrix.reset();

                if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min(mBounds.width() / (float) mBitmapWidth,
                            mBounds.height() / (float) mBitmapHeight);
                }

                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            default:
            case FIT_CENTER:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_END:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_START:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_XY:
                mBorderRect.set(mBounds);
                mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.reset();
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
        }

        mDrawableRect.set(mBorderRect);
        mRebuildShader = true;
    }

    @Override
    protected void onBoundsChange(@NonNull Rect bounds) {
        super.onBoundsChange(bounds);

        mBounds.set(bounds);

        updateShaderMatrix();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mRebuildShader) {
            BitmapShader bitmapShader = new BitmapShader(mBitmap, mTileModeX, mTileModeY);
            if (mTileModeX == Shader.TileMode.CLAMP && mTileModeY == Shader.TileMode.CLAMP) {
                bitmapShader.setLocalMatrix(mShaderMatrix);
            }
            mBitmapPaint.setShader(bitmapShader);
            mRebuildShader = false;
        }

        if (mOval) {
            if (mBorderWidth > 0) {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
                canvas.drawOval(mBorderRect, mBorderPaint);
            } else {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
            }
        } else {
            if (any(mCornersRounded)) {
                float radius = mCornerRadius;
                if (mBorderWidth > 0) {
                    canvas.drawRoundRect(mDrawableRect, radius, radius, mBitmapPaint);
                    canvas.drawRoundRect(mBorderRect, radius, radius, mBorderPaint);
                    redrawBitmapForSquareCorners(canvas);
                    redrawBorderForSquareCorners(canvas);
                } else {
                    canvas.drawRoundRect(mDrawableRect, radius, radius, mBitmapPaint);
                    redrawBitmapForSquareCorners(canvas);
                }
            } else {
                canvas.drawRect(mDrawableRect, mBitmapPaint);
                if (mBorderWidth > 0) {
                    canvas.drawRect(mBorderRect, mBorderPaint);
                }
            }
        }
    }

    private void redrawBitmapForSquareCorners(Canvas canvas) {
        if (all(mCornersRounded)) {
            // no square corners
            return;
        }

        if (mCornerRadius == 0) {
            return; // no round corners
        }

        float left = mDrawableRect.left;
        float top = mDrawableRect.top;
        float right = left + mDrawableRect.width();
        float bottom = top + mDrawableRect.height();
        float radius = mCornerRadius;

        if (!mCornersRounded[Corner.TOP_LEFT]) {
            mSquareCornersRect.set(left, top, left + radius, top + radius);
            canvas.drawRect(mSquareCornersRect, mBitmapPaint);
        }

        if (!mCornersRounded[Corner.TOP_RIGHT]) {
            mSquareCornersRect.set(right - radius, top, right, radius);
            canvas.drawRect(mSquareCornersRect, mBitmapPaint);
        }

        if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
            mSquareCornersRect.set(right - radius, bottom - radius, right, bottom);
            canvas.drawRect(mSquareCornersRect, mBitmapPaint);
        }

        if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
            mSquareCornersRect.set(left, bottom - radius, left + radius, bottom);
            canvas.drawRect(mSquareCornersRect, mBitmapPaint);
        }
    }

    private void redrawBorderForSquareCorners(Canvas canvas) {
        if (all(mCornersRounded)) {
            // no square corners
            return;
        }

        if (mCornerRadius == 0) {
            return; // no round corners
        }

        float left = mDrawableRect.left;
        float top = mDrawableRect.top;
        float right = left + mDrawableRect.width();
        float bottom = top + mDrawableRect.height();
        float radius = mCornerRadius;
        float offset = mBorderWidth / 2;

        if (!mCornersRounded[Corner.TOP_LEFT]) {
            canvas.drawLine(left - offset, top, left + radius, top, mBorderPaint);
            canvas.drawLine(left, top - offset, left, top + radius, mBorderPaint);
        }

        if (!mCornersRounded[Corner.TOP_RIGHT]) {
            canvas.drawLine(right - radius - offset, top, right, top, mBorderPaint);
            canvas.drawLine(right, top - offset, right, top + radius, mBorderPaint);
        }

        if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
            canvas.drawLine(right - radius - offset, bottom, right + offset, bottom, mBorderPaint);
            canvas.drawLine(right, bottom - radius, right, bottom, mBorderPaint);
        }

        if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
            canvas.drawLine(left - offset, bottom, left + radius, bottom, mBorderPaint);
            canvas.drawLine(left, bottom - radius, left, bottom, mBorderPaint);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getAlpha() {
        return mBitmapPaint.getAlpha();
    }

    @Override
    public void setAlpha(int alpha) {
        mBitmapPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public ColorFilter getColorFilter() {
        return mBitmapPaint.getColorFilter();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mBitmapPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mBitmapPaint.setDither(dither);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mBitmapPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    /**
     * @return the corner radius.
     */
    public float getCornerRadius() {
        return mCornerRadius;
    }

    /**
     * @param corner the specific corner to get radius of.
     * @return the corner radius of the specified corner.
     */
    public float getCornerRadius(@Corner int corner) {
        return mCornersRounded[corner] ? mCornerRadius : 0f;
    }

    /**
     * Sets all corners to the specified radius.
     *
     * @param radius the radius.
     * @return the {@link XRoundImageDrawable} for chaining.
     */
    public XRoundImageDrawable setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
        return this;
    }

    /**
     * Sets the corner radii of all the corners.
     *
     * @param topLeft     top left corner radius.
     * @param topRight    top right corner radius
     * @param bottomRight bototm right corner radius.
     * @param bottomLeft  bottom left corner radius.
     * @return the {@link XRoundImageDrawable} for chaining.
     */
    public XRoundImageDrawable setCornerRadius(float topLeft, float topRight, float bottomRight,
                                               float bottomLeft) {
        TreeSet<Float> radiusSet = new TreeSet<>();
        if (topLeft > 0) {
            radiusSet.add(topLeft);
            mCornersRounded[Corner.TOP_LEFT] = true;
        } else {
            mCornersRounded[Corner.TOP_LEFT] = false;
        }
        if (topRight > 0) {
            radiusSet.add(topRight);
            mCornersRounded[Corner.TOP_RIGHT] = true;
        } else {
            mCornersRounded[Corner.TOP_RIGHT] = false;
        }
        if (bottomRight > 0) {
            radiusSet.add(bottomRight);
            mCornersRounded[Corner.BOTTOM_RIGHT] = true;
        } else {
            mCornersRounded[Corner.BOTTOM_RIGHT] = false;
        }
        if (bottomLeft > 0) {
            radiusSet.add(bottomLeft);
            mCornersRounded[Corner.BOTTOM_LEFT] = true;
        } else {
            mCornersRounded[Corner.BOTTOM_LEFT] = false;
        }
        if (radiusSet.size() > 0) {
            mCornerRadius = radiusSet.first();
        } else {
            mCornerRadius = 0f;
        }
        return this;
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public XRoundImageDrawable setBorderWidth(float width) {
        mBorderWidth = width;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        return this;
    }

    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public XRoundImageDrawable setBorderColor(@ColorInt int color) {
        return setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public XRoundImageDrawable setBorderColor(ColorStateList colors) {
        mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        return this;
    }

    public boolean isOval() {
        return mOval;
    }

    public XRoundImageDrawable setOval(boolean oval) {
        mOval = oval;
        return this;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public XRoundImageDrawable setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ScaleType.FIT_CENTER;
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType;
            updateShaderMatrix();
        }
        return this;
    }

    public Shader.TileMode getTileModeX() {
        return mTileModeX;
    }

    public XRoundImageDrawable setTileModeX(Shader.TileMode tileModeX) {
        if (mTileModeX != tileModeX) {
            mTileModeX = tileModeX;
            mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public Shader.TileMode getTileModeY() {
        return mTileModeY;
    }

    public XRoundImageDrawable setTileModeY(Shader.TileMode tileModeY) {
        if (mTileModeY != tileModeY) {
            mTileModeY = tileModeY;
            mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    private static boolean only(int index, boolean[] booleans) {
        for (int i = 0, len = booleans.length; i < len; i++) {
            if (booleans[i] != (i == index)) {
                return false;
            }
        }
        return true;
    }

    private static boolean any(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    private static boolean all(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return false;
            }
        }
        return true;
    }

    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }
}