package com.pichs.xwidget.roundview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.XIAlpha;
import com.pichs.xwidget.utils.XAlphaHelper;
import com.pichs.xwidget.utils.XBackgroundHelper;

import java.util.TreeSet;

/**
 * 提供为图片添加圆角、边框、剪裁到圆形。
 * shown image in radius view,
 * the oval is supported
 */
@SuppressLint("AppCompatCustomView")
public class XRoundImageView extends ImageView implements XIAlpha {

    public static final String TAG = "RoundedImageView";
    private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private XAlphaHelper xAlphaHelper;

    public static final float DEFAULT_RADIUS = 0f;
    public static final float DEFAULT_BORDER_WIDTH = 0f;
    private static final ScaleType[] SCALE_TYPES = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    private final float[] mCornerRadii =
            new float[]{DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS};

    private Drawable mBackgroundDrawable;
    private ColorStateList mBorderColor =
            ColorStateList.valueOf(XRoundImageDrawable.DEFAULT_BORDER_COLOR);
    private float mBorderWidth = DEFAULT_BORDER_WIDTH;
    private ColorFilter mColorFilter = null;
    private boolean mColorMod = false;
    private Drawable mDrawable;
    private boolean mHasColorFilter = false;
    private boolean mIsOval = false;
    private int mResource;
    private int mBackgroundResource;
    private ScaleType mScaleType;
    private Shader.TileMode mTileModeX = Shader.TileMode.CLAMP;
    private Shader.TileMode mTileModeY = Shader.TileMode.CLAMP;
    private PorterDuff.Mode mMode;


    public XRoundImageView(Context context) {
        this(context,null);
    }

    public XRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XRoundImageView, defStyle, 0);
        String heightStr = attrs.getAttributeValue(ANDROID_NAMESPACE, "layout_height");
        String widthStr = attrs.getAttributeValue(ANDROID_NAMESPACE, "layout_width");

        int height = -2, width = -2;
        if (!String.valueOf(ViewGroup.LayoutParams.MATCH_PARENT).equals(heightStr) &&
                !String.valueOf(ViewGroup.LayoutParams.WRAP_CONTENT).equals(heightStr)) {
            height = a.getDimensionPixelSize(R.styleable.XRoundImageView_android_layout_height, -2);
        }
        if (!String.valueOf(ViewGroup.LayoutParams.MATCH_PARENT).equals(widthStr) &&
                !String.valueOf(ViewGroup.LayoutParams.WRAP_CONTENT).equals(widthStr)) {
            width = a.getDimensionPixelSize(R.styleable.XRoundImageView_android_layout_width, -2);
        }
        int index = a.getInt(R.styleable.XRoundImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            // default scaletype to FIT_CENTER
            setScaleType(ScaleType.CENTER_CROP);
        }

        float cornerRadiusOverride =
                a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_radius, -1);

        int tlpx = a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_radiusTopLeft, 0);
        int trpx = a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_radiusTopRight, 0);
        int brpx = a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_radiusBottomRight, 0);
        int blpx = a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_radiusBottomLeft, 0);

        int minRadius = (int) getMinNotZeroFloat(tlpx, trpx, brpx, blpx, width, height);
        tlpx = Math.min(tlpx, minRadius);
        trpx = Math.min(trpx, minRadius);
        brpx = Math.min(brpx, minRadius);
        blpx = Math.min(blpx, minRadius);
        mCornerRadii[XRoundImageDrawable.Corner.TOP_LEFT] = tlpx;
        mCornerRadii[XRoundImageDrawable.Corner.TOP_RIGHT] = trpx;
        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_RIGHT] = brpx;
        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_LEFT] = blpx;

        boolean any = false;
        for (float cornerRadius : mCornerRadii) {
            if (cornerRadius > 0) {
                any = true;
                break;
            }
        }

        if (!any) {
            if (cornerRadiusOverride < 0) {
                cornerRadiusOverride = DEFAULT_RADIUS;
            }
            for (int i = 0, len = mCornerRadii.length; i < len; i++) {
                mCornerRadii[i] = cornerRadiusOverride;
            }
        }

        mBorderWidth = a.getDimensionPixelSize(R.styleable.XRoundImageView_xp_borderWidth, -1);
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER_WIDTH;
        }

        mBorderColor = a.getColorStateList(R.styleable.XRoundImageView_xp_borderColor);
        if (mBorderColor == null) {
            mBorderColor = ColorStateList.valueOf(XRoundImageDrawable.DEFAULT_BORDER_COLOR);
        }

        mIsOval = a.getBoolean(R.styleable.XRoundImageView_xp_oval, false);

        int colorFilter = a.getColor(R.styleable.XRoundImageView_xp_colorFilter, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT);
        int colorFilterMode = a.getInt(R.styleable.XRoundImageView_xp_colorFilterMode, 1);
        if (colorFilter == XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT) {
            clearColorFilter();
        } else {
            mMode = getColorFilterMode(colorFilterMode);
            setColorFilter(colorFilter, mMode);
        }
        setTileModeX(Shader.TileMode.CLAMP);
        setTileModeY(Shader.TileMode.CLAMP);

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(true);
        super.setBackgroundDrawable(mBackgroundDrawable);
        a.recycle();

        xAlphaHelper = new XAlphaHelper(context, attrs, 0, this);

    }

    @Override
    public void setNormalAlpha(float alpha) {
        xAlphaHelper.setNormalAlpha(alpha);
    }

    @Override
    public void setAlphaOnPressed(float alpha) {
        xAlphaHelper.setAlphaOnPressed(alpha);
    }

    @Override
    public void setAlphaOnDisabled(float alpha) {
        xAlphaHelper.setAlphaOnDisabled(alpha);
    }

    @Override
    public void setNormalScale(float scaleRate) {
        xAlphaHelper.setNormalScale(scaleRate);
    }

    @Override
    public void setScaleOnPressed(float scaleRate) {
        xAlphaHelper.setScaleOnPressed(scaleRate);
    }

    @Override
    public void setScaleOnDisabled(float scaleRate) {
        xAlphaHelper.setScaleOnDisabled(scaleRate);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        xAlphaHelper.onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        xAlphaHelper.onEnabledChanged(this, enabled);
    }

    public void setColorFilterOverride(int color) {
        if (getCurrentMode() != null) {
            setColorFilter(color, getCurrentMode());
        } else {
            setColorFilter(color);
        }
    }

    public PorterDuff.Mode getCurrentMode() {
        return mMode;
    }

    private PorterDuff.Mode getColorFilterMode(int mode) {
        switch (mode) {
            case 0:
                return PorterDuff.Mode.SRC;
            case 1:
                return PorterDuff.Mode.SRC_ATOP;
            case 2:
                return PorterDuff.Mode.SRC_IN;
            case 3:
                return PorterDuff.Mode.SRC_OUT;
            case 4:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.MULTIPLY;
            case 6:
                return PorterDuff.Mode.DST;
            case 7:
                return PorterDuff.Mode.DST_ATOP;
            case 8:
                return PorterDuff.Mode.DST_IN;
            case 9:
                return PorterDuff.Mode.DST_OUT;
            case 10:
                return PorterDuff.Mode.DST_OVER;
            case 11:
                return PorterDuff.Mode.CLEAR;
            case 12:
                return PorterDuff.Mode.XOR;
            case 13:
                return PorterDuff.Mode.SCREEN;
            case 14:
                return PorterDuff.Mode.DARKEN;
            case 15:
                return PorterDuff.Mode.LIGHTEN;
            case 16:
                return PorterDuff.Mode.ADD;
            case 17:
                return PorterDuff.Mode.OVERLAY;
        }
        return PorterDuff.Mode.SRC_ATOP;
    }

    private float getMinNotZeroFloat(float topLeft, float topRight, float bottomLeft, float bottomRight, float width, float height) {
        // 获取角度中大于0的最小值
        TreeSet<Float> radiusSet = new TreeSet<>();
        radiusSet.add(Math.max(topLeft, 0f));
        radiusSet.add(Math.max(topRight, 0f));
        radiusSet.add(Math.max(bottomRight, 0f));
        radiusSet.add(Math.max(bottomLeft, 0f));
        radiusSet.add(Math.max(height, 0f));
        radiusSet.add(Math.max(width, 0f));
        radiusSet.remove(0f);
        if (!radiusSet.isEmpty()) {
            // 取值不能超过宽或高最短边的一半
            return radiusSet.first();
        }
        return 0f;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) return;

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mResource = 0;
        mDrawable = XRoundImageDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mResource = 0;
        mDrawable = XRoundImageDrawable.fromBitmap(bm);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        if (mResource != resId) {
            mResource = resId;
            mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(mDrawable);
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = getResources();
        if (rsrc == null) {
            return null;
        }

        Drawable d = null;

        if (mResource != 0) {
            try {
                d = rsrc.getDrawable(mResource);
            } catch (Exception e) {
                mResource = 0;
            }
        }
        return XRoundImageDrawable.fromDrawable(d);
    }

    /**
     * @hide
     */
    @Override
    public void setBackground(Drawable background) {
//        setBackgroundDrawable(background);
    }

    /**
     * @hide
     */
    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
//        if (mBackgroundResource != resId) {
//            mBackgroundResource = resId;
//            mBackgroundDrawable = resolveBackgroundResource();
//            setBackgroundDrawable(mBackgroundDrawable);
//        }
    }

    /**
     * @hide
     */
    @Override
    public void setBackgroundColor(int color) {
//        mBackgroundDrawable = new ColorDrawable(color);
//        setBackgroundDrawable(mBackgroundDrawable);
    }

    /**
     * @hide
     */
    private Drawable resolveBackgroundResource() {
        Resources rsrc = getResources();
        if (rsrc == null) {
            return null;
        }

        Drawable d = null;

        if (mBackgroundResource != 0) {
            try {
                d = rsrc.getDrawable(mBackgroundResource);
            } catch (Exception e) {
                mBackgroundResource = 0;
            }
        }
        return XRoundImageDrawable.fromDrawable(d);
    }

    private void updateDrawableAttrs() {
        updateAttrs(mDrawable, mScaleType);
    }

    private void updateBackgroundDrawableAttrs(boolean convert) {
        if (convert) {
            mBackgroundDrawable = XRoundImageDrawable.fromDrawable(mBackgroundDrawable);
        }
        updateAttrs(mBackgroundDrawable, ScaleType.FIT_XY);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (mColorFilter != cf) {
            mColorFilter = cf;
            mHasColorFilter = true;
            mColorMod = true;
            applyColorMod();
            invalidate();
        }
    }

    private void applyColorMod() {
        if (mDrawable != null && mColorMod) {
            mDrawable = mDrawable.mutate();
            if (mHasColorFilter) {
                mDrawable.setColorFilter(mColorFilter);
            }
        }
    }

    private void updateAttrs(Drawable drawable, ScaleType scaleType) {
        if (drawable == null) {
            return;
        }

        if (drawable instanceof XRoundImageDrawable) {
            ((XRoundImageDrawable) drawable)
                    .setScaleType(scaleType)
                    .setBorderWidth(mBorderWidth)
                    .setBorderColor(mBorderColor)
                    .setOval(mIsOval)
                    .setTileModeX(mTileModeX)
                    .setTileModeY(mTileModeY);
            if (null != mCornerRadii) {
                ((XRoundImageDrawable) drawable).setCornerRadius(
                        mCornerRadii[XRoundImageDrawable.Corner.TOP_LEFT],
                        mCornerRadii[XRoundImageDrawable.Corner.TOP_RIGHT],
                        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_RIGHT],
                        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_LEFT]);
            }

            applyColorMod();
        } else if (drawable instanceof LayerDrawable) {
            // loop through layers to and set drawable attrs
            LayerDrawable ld = ((LayerDrawable) drawable);
            for (int i = 0, layers = ld.getNumberOfLayers(); i < layers; i++) {
                updateAttrs(ld.getDrawable(i), scaleType);
            }
        }
    }

    /**
     * @hide
     */
    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
//        mBackgroundDrawable = background;
//        updateBackgroundDrawableAttrs(true);
//        //noinspection deprecation
//        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    /**
     * @return the largest corner radius.
     */
    public float getCornerRadius() {
        float maxRadius = 0;
        for (float r : mCornerRadii) {
            maxRadius = Math.max(r, maxRadius);
        }
        return maxRadius;
    }

    /**
     * Set the corner radii of all corners in px.
     *
     * @param radius the radius to set.
     */
    public void setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
    }

    /**
     * Set the corner radii of each corner individually. Currently only one unique nonzero value is
     * supported.
     *
     * @param topLeft     radius of the top left corner in px.
     * @param topRight    radius of the top right corner in px.
     * @param bottomRight radius of the bottom right corner in px.
     * @param bottomLeft  radius of the bottom left corner in px.
     */
    public void setCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        if (mCornerRadii[XRoundImageDrawable.Corner.TOP_LEFT] == topLeft
                && mCornerRadii[XRoundImageDrawable.Corner.TOP_RIGHT] == topRight
                && mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_RIGHT] == bottomRight
                && mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_LEFT] == bottomLeft) {
            return;
        }

        float minRadius = getMinNotZeroFloat(topLeft, topRight, bottomLeft, bottomRight, getWidth(), getHeight());

        topLeft = Math.min(topLeft, minRadius);
        topRight = Math.min(topRight, minRadius);
        bottomLeft = Math.min(bottomLeft, minRadius);
        bottomRight = Math.min(bottomRight, minRadius);
        mCornerRadii[XRoundImageDrawable.Corner.TOP_LEFT] = topLeft;
        mCornerRadii[XRoundImageDrawable.Corner.TOP_RIGHT] = topRight;
        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_LEFT] = bottomLeft;
        mCornerRadii[XRoundImageDrawable.Corner.BOTTOM_RIGHT] = bottomRight;

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(@DimenRes int resId) {
        setBorderWidth(getResources().getDimension(resId));
    }

    public void setBorderWidth(float width) {
        if (mBorderWidth == width) {
            return;
        }
        mBorderWidth = width;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    @ColorInt
    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public void setBorderColor(@ColorInt int color) {
        setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public void setBorderColor(ColorStateList colors) {
        if (mBorderColor.equals(colors)) {
            return;
        }

        mBorderColor = (colors != null) ?
                colors : ColorStateList.valueOf(XRoundImageDrawable.DEFAULT_BORDER_COLOR);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        if (mBorderWidth > 0) {
            invalidate();
        }
    }

    /**
     * Return true if this view should be oval and always set corner radii to half the height or
     * width.
     *
     * @return if this {@link XRoundImageView} is set to oval.
     */
    public boolean isOval() {
        return mIsOval;
    }

    /**
     * Set if the drawable should ignore the corner radii set and always round the source to
     * exactly half the height or width.
     *
     * @param oval if this {@link XRoundImageView} should be oval.
     */
    public void setOval(boolean oval) {
        mIsOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public Shader.TileMode getTileModeX() {
        return mTileModeX;
    }

    public void setTileModeX(Shader.TileMode tileModeX) {
        if (this.mTileModeX == tileModeX) {
            return;
        }

        this.mTileModeX = tileModeX;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public Shader.TileMode getTileModeY() {
        return mTileModeY;
    }

    public void setTileModeY(Shader.TileMode tileModeY) {
        if (this.mTileModeY == tileModeY) {
            return;
        }

        this.mTileModeY = tileModeY;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }
}


