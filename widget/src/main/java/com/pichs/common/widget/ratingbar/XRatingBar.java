package com.pichs.common.widget.ratingbar;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

import com.pichs.common.widget.R;
import com.pichs.common.widget.utils.XDisplayHelper;

/**
 * 评分条，star 控件。简单易用，效果不错。
 */
public class XRatingBar extends View {

    /**
     * 表示条中填充的重力。
     */
    public enum Gravity {
        /**
         * 左重力是默认的：条将从左到右填充。
         */
        Left(0),
        /**
         * 右重力：条将从右到左填充。
         */
        Right(1);

        final int id;

        Gravity(int id) {
            this.id = id;
        }

        static Gravity fromId(int id) {
            for (Gravity f : values()) {
                if (f.id == id) return f;
            }
            return Left;
        }
    }

    private @ColorInt
    int borderColor;
    private @ColorInt
    int starSelectedColor;
    private final @ColorInt
    int backgroundColor = Color.TRANSPARENT;
    private @ColorInt
    int starNormalColor;
    private @ColorInt
    int pressedBorderColor;
    private @ColorInt
    int pressedFillColor;
    private final @ColorInt
    int pressedBackgroundColor = Color.TRANSPARENT;
    private @ColorInt
    int pressedStarBackgroundColor;
    private int starCount;
    private int starsSeparation;
    private int desiredStarSize;
    private int maxStarSize;
    private float starStep;
    private float progress;
    private boolean isIndicator;
    private Gravity ratingGravity;
    private int starBorderWidth;
    private int starCornerRadius;
    private boolean borderVisible;

    // Internal variables
    private float currentStarSize;
    private float defaultStarSize;
    private Paint paintStarOutline;
    private Paint paintStarBorder;
    private Paint paintStarFill;
    private Paint paintStarBackground;
    private CornerPathEffect cornerPathEffect;
    private Path starPath;
    private ValueAnimator ratingAnimator;
    private OnRatingBarChangeListener ratingListener;
    private OnClickListener clickListener;
    private boolean touchInProgress;
    private float[] starVertex;
    private RectF starsDrawingSpace;
    private RectF starsTouchSpace;
    private Canvas internalCanvas;
    private Bitmap internalBitmap;

    public XRatingBar(Context context) {
        super(context);
        initView();
    }

    public XRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs);
        initView();
    }

    public XRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(attrs);
        initView();
    }

    /**
     * 初始化画笔和默认值。
     */
    private void initView() {
        starPath = new Path();
        cornerPathEffect = new CornerPathEffect(starCornerRadius);

        paintStarOutline = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paintStarOutline.setStyle(Paint.Style.FILL_AND_STROKE);
        paintStarOutline.setAntiAlias(true);
        paintStarOutline.setDither(true);
        paintStarOutline.setStrokeJoin(Paint.Join.ROUND);
        paintStarOutline.setStrokeCap(Paint.Cap.ROUND);
        paintStarOutline.setColor(Color.BLACK);
        paintStarOutline.setPathEffect(cornerPathEffect);

        paintStarBorder = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paintStarBorder.setStyle(Paint.Style.STROKE);
        paintStarBorder.setStrokeJoin(Paint.Join.ROUND);
        paintStarBorder.setStrokeCap(Paint.Cap.ROUND);
        paintStarBorder.setStrokeWidth(starBorderWidth);
        paintStarBorder.setPathEffect(cornerPathEffect);

        paintStarBackground = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paintStarBackground.setStyle(Paint.Style.FILL_AND_STROKE);
        paintStarBackground.setAntiAlias(true);
        paintStarBackground.setDither(true);
        paintStarBackground.setStrokeJoin(Paint.Join.ROUND);
        paintStarBackground.setStrokeCap(Paint.Cap.ROUND);

        paintStarFill = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paintStarFill.setStyle(Paint.Style.FILL_AND_STROKE);
        paintStarFill.setAntiAlias(true);
        paintStarFill.setDither(true);
        paintStarFill.setStrokeJoin(Paint.Join.ROUND);
        paintStarFill.setStrokeCap(Paint.Cap.ROUND);

        defaultStarSize = applyDimension(COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
    }

    /**
     * 解析XML中定义的属性。
     */
    private void parseAttrs(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.XRatingBar);

        borderColor = arr.getColor(R.styleable.XRatingBar_xp_borderColor, getResources().getColor(R.color.srb_golden_stars));
        starSelectedColor = arr.getColor(R.styleable.XRatingBar_xp_starSelectedColor, borderColor);
        starNormalColor = arr.getColor(R.styleable.XRatingBar_xp_starNormalColor, Color.TRANSPARENT);
        starBorderWidth = arr.getDimensionPixelSize(R.styleable.XRatingBar_xp_borderWidth, XDisplayHelper.dp2px(getContext(), 1f));

        pressedBorderColor = arr.getColor(R.styleable.XRatingBar_xp_pressedBorderColor, borderColor);
        pressedFillColor = arr.getColor(R.styleable.XRatingBar_xp_pressedStarSelectedColor, starSelectedColor);
        pressedStarBackgroundColor = arr.getColor(R.styleable.XRatingBar_xp_pressedStarNormalColor, starNormalColor);

        starCount = arr.getInteger(R.styleable.XRatingBar_xp_starCount, 5);
        // 限制最低是1颗星
        if (starCount < 1) {
            starCount = 1;
        }
        starsSeparation = arr.getDimensionPixelSize(R.styleable.XRatingBar_xp_starsSeparation, XDisplayHelper.dp2px(getContext(), 4f));
        desiredStarSize = arr.getDimensionPixelSize(R.styleable.XRatingBar_xp_starSize, XDisplayHelper.dp2px(getContext(), 30f));
        maxStarSize = arr.getDimensionPixelSize(R.styleable.XRatingBar_xp_maxStarSize, desiredStarSize);
        starStep = arr.getFloat(R.styleable.XRatingBar_xp_starStep, 0.1f);
        starCornerRadius = arr.getDimensionPixelSize(R.styleable.XRatingBar_xp_starCornerRadius, XDisplayHelper.dp2px(getContext(), 15f));

        progress = normalizeRating(arr.getFloat(R.styleable.XRatingBar_xp_starProgress, 0f));
        isIndicator = arr.getBoolean(R.styleable.XRatingBar_xp_isIndicator, false);
        ratingGravity = Gravity.fromId(arr.getInt(R.styleable.XRatingBar_xp_ratingGravity, Gravity.Left.id));

        borderVisible = starBorderWidth > 0 && borderColor != 0;
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            if (desiredStarSize != Integer.MAX_VALUE) {
                // user specified a specific star size, so there is a desired width
                int desiredWidth = calculateTotalWidth(desiredStarSize, starCount, starsSeparation, true);
                width = Math.min(desiredWidth, widthSize);
            } else if (maxStarSize != Integer.MAX_VALUE) {
                // user specified a max star size, so there is a desired width
                int desiredWidth = calculateTotalWidth(maxStarSize, starCount, starsSeparation, true);
                width = Math.min(desiredWidth, widthSize);
            } else {
                // using defaults
                int desiredWidth = calculateTotalWidth(defaultStarSize, starCount, starsSeparation, true);
                width = Math.min(desiredWidth, widthSize);
            }
        } else {
            //Be whatever you want
            if (desiredStarSize != Integer.MAX_VALUE) {
                // user specified a specific star size, so there is a desired width
                width = calculateTotalWidth(desiredStarSize, starCount, starsSeparation, true);
            } else if (maxStarSize != Integer.MAX_VALUE) {
                // user specified a max star size, so there is a desired width
                width = calculateTotalWidth(maxStarSize, starCount, starsSeparation, true);
            } else {
                // using defaults
                width = calculateTotalWidth(defaultStarSize, starCount, starsSeparation, true);
            }
        }

        float tentativeStarSize = (width - getPaddingLeft() - getPaddingRight() - starsSeparation * (starCount - 1f)) / starCount;

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            if (desiredStarSize != Integer.MAX_VALUE) {
                // user specified a specific star size, so there is a desired width
                int desiredHeight = calculateTotalHeight(desiredStarSize, starCount, starsSeparation, true);
                height = Math.min(desiredHeight, heightSize);
            } else if (maxStarSize != Integer.MAX_VALUE) {
                // user specified a max star size, so there is a desired width
                int desiredHeight = calculateTotalHeight(maxStarSize, starCount, starsSeparation, true);
                height = Math.min(desiredHeight, heightSize);
            } else {
                // using defaults
                int desiredHeight = calculateTotalHeight(tentativeStarSize, starCount, starsSeparation, true);
                height = Math.min(desiredHeight, heightSize);
            }
        } else {
            //Be whatever you want
            if (desiredStarSize != Integer.MAX_VALUE) {
                // user specified a specific star size, so there is a desired width
                height = calculateTotalHeight(desiredStarSize, starCount, starsSeparation, true);
            } else if (maxStarSize != Integer.MAX_VALUE) {
                // user specified a max star size, so there is a desired width
                height = calculateTotalHeight(maxStarSize, starCount, starsSeparation, true);
            } else {
                // using defaults
                height = calculateTotalHeight(tentativeStarSize, starCount, starsSeparation, true);
            }
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width = getWidth();
        int height = getHeight();
        if (desiredStarSize == Integer.MAX_VALUE) {
            currentStarSize = calculateBestStarSize(width, height);
        } else {
            currentStarSize = desiredStarSize;
        }
        performStarSizeAssociatedCalculations(width, height);
    }

    /**
     * 根据选择的宽度和高度计算最大可能的星星大小。
     * 如果存在maxStarSize，则会考虑它，并且星星大小不会大于此值。
     *
     * @param width
     * @param height
     */
    private float calculateBestStarSize(int width, int height) {
        if (maxStarSize != Integer.MAX_VALUE) {
            float desiredTotalWidth = calculateTotalWidth(maxStarSize, starCount, starsSeparation, true);
            float desiredTotalHeight = calculateTotalHeight(maxStarSize, starCount, starsSeparation, true);
            if (desiredTotalWidth >= width || desiredTotalHeight >= height) {
                // we need to shrink the size of the stars
                float sizeBasedOnWidth = (width - getPaddingLeft() - getPaddingRight() - starsSeparation * (starCount - 1)) / starCount;
                float sizeBasedOnHeight = height - getPaddingTop() - getPaddingBottom();
                return Math.min(sizeBasedOnWidth, sizeBasedOnHeight);
            } else {
                return maxStarSize;
            }
        } else {
            // expand the most we can
            float sizeBasedOnWidth = (width - getPaddingLeft() - getPaddingRight() - starsSeparation * (starCount - 1f)) / starCount;
            float sizeBasedOnHeight = height - getPaddingTop() - getPaddingBottom();
            return Math.min(sizeBasedOnWidth, sizeBasedOnHeight);
        }
    }

    /**
     * 根据给定的星星大小计算总宽度。
     *
     * @param width
     * @param height
     */
    private void performStarSizeAssociatedCalculations(int width, int height) {
        float totalStarsWidth = calculateTotalWidth(currentStarSize, starCount, starsSeparation, false);
        float totalStarsHeight = calculateTotalHeight(currentStarSize, starCount, starsSeparation, false);
        float startingX = ((width - getPaddingLeft() - getPaddingRight()) / 2f) - totalStarsWidth / 2 + getPaddingLeft();
        float startingY = ((height - getPaddingTop() - getPaddingBottom()) / 2f) - totalStarsHeight / 2 + getPaddingTop();
        starsDrawingSpace = new RectF(startingX, startingY, startingX + totalStarsWidth, startingY + totalStarsHeight);
        float aux = starsDrawingSpace.width() * 0.05f;
        starsTouchSpace = new RectF(starsDrawingSpace.left - aux, starsDrawingSpace.top, starsDrawingSpace.right + aux, starsDrawingSpace.bottom);

        float bottomFromMargin = currentStarSize * 0.2f;
        float triangleSide = currentStarSize * 0.35f;
        float half = currentStarSize * 0.5f;
        float tipVerticalMargin = currentStarSize * 0.05f;
        float tipHorizontalMargin = currentStarSize * 0.03f;
        float innerUpHorizontalMargin = currentStarSize * 0.38f;
        float innerBottomHorizontalMargin = currentStarSize * 0.32f;
        float innerBottomVerticalMargin = currentStarSize * 0.6f;
        float innerCenterVerticalMargin = currentStarSize * 0.27f;

        starVertex = new float[]{tipHorizontalMargin, innerUpHorizontalMargin, // top left
                tipHorizontalMargin + triangleSide, innerUpHorizontalMargin, half, tipVerticalMargin, // top tip
                currentStarSize - tipHorizontalMargin - triangleSide, innerUpHorizontalMargin, currentStarSize - tipHorizontalMargin, innerUpHorizontalMargin, // top right
                currentStarSize - innerBottomHorizontalMargin, innerBottomVerticalMargin, currentStarSize - bottomFromMargin, currentStarSize - tipVerticalMargin, // bottom right
                half, currentStarSize - innerCenterVerticalMargin, bottomFromMargin, currentStarSize - tipVerticalMargin, // bottom left
                innerBottomHorizontalMargin, innerBottomVerticalMargin};
    }

    /**
     * 根据几个参数计算总宽度
     */
    private int calculateTotalWidth(float starSize, int starCount, float starsSeparation, boolean padding) {
        return Math.round(starSize * starCount + starsSeparation * (starCount - 1)) + (padding ? getPaddingLeft() + getPaddingRight() : 0);
    }

    /**
     * 根据几个参数计算总高度
     */
    private int calculateTotalHeight(float starSize, int numberOfStars, float starsSeparation, boolean padding) {
        return Math.round(starSize) + (padding ? getPaddingTop() + getPaddingBottom() : 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        generateInternalCanvas(w, h);
    }

    /**
     * 生成内部画布，星星将在其上绘制。
     */
    private void generateInternalCanvas(int w, int h) {
        if (internalBitmap != null) {
            // avoid leaking memory after losing the reference
            internalBitmap.recycle();
        }

        if (w > 0 && h > 0) {
            // if width == 0 or height == 0 we don't need internal bitmap, cause view won't be drawn anyway.
            internalBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            internalBitmap.eraseColor(Color.TRANSPARENT);
            internalCanvas = new Canvas(internalBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        if (width == 0 || height == 0) {
            // don't draw view with width or height equal zero.
            return;
        }

        // clean internal canvas
        internalCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // choose colors
        setupColorsInPaint();

        // draw stars
        if (ratingGravity == Gravity.Left) {
            drawFromLeftToRight(internalCanvas);
        } else {
            drawFromRightToLeft(internalCanvas);
        }

        // draw view background color
        if (touchInProgress) {
            canvas.drawColor(pressedBackgroundColor);
        } else {
            canvas.drawColor(backgroundColor);
        }

        // draw internal bitmap to definite canvas
        canvas.drawBitmap(internalBitmap, 0, 0, null);
    }

    /**
     * 根据当前状态设置不同的颜色
     */
    private void setupColorsInPaint() {
        if (touchInProgress) {
            paintStarBorder.setColor(pressedBorderColor);
            paintStarFill.setColor(pressedFillColor);
            if (pressedFillColor != Color.TRANSPARENT) {
                paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            } else {
                paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            paintStarBackground.setColor(pressedStarBackgroundColor);
            if (pressedStarBackgroundColor != Color.TRANSPARENT) {
                paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            } else {
                paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
        } else {
            paintStarBorder.setColor(borderColor);
            paintStarFill.setColor(starSelectedColor);
            if (starSelectedColor != Color.TRANSPARENT) {
                paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            } else {
                paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            paintStarBackground.setColor(starNormalColor);
            if (starNormalColor != Color.TRANSPARENT) {
                paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            } else {
                paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
        }
    }

    /**
     * 从左到右绘制视图
     */
    private void drawFromLeftToRight(Canvas internalCanvas) {
        float remainingTotalRating = progress;
        float startingX = starsDrawingSpace.left;
        float startingY = starsDrawingSpace.top;
        for (int i = 0; i < starCount; i++) {
            if (remainingTotalRating >= 1) {
                drawStar(internalCanvas, startingX, startingY, 1f, Gravity.Left);
                remainingTotalRating -= 1;
            } else {
                drawStar(internalCanvas, startingX, startingY, remainingTotalRating, Gravity.Left);
                remainingTotalRating = 0;
            }
            startingX += starsSeparation + currentStarSize;
        }
    }

    /**
     * Draws the view when gravity is Right
     *
     * @param internalCanvas
     */
    private void drawFromRightToLeft(Canvas internalCanvas) {
        float remainingTotalRating = progress;
        float startingX = starsDrawingSpace.right - currentStarSize;
        float startingY = starsDrawingSpace.top;
        for (int i = 0; i < starCount; i++) {
            if (remainingTotalRating >= 1) {
                drawStar(internalCanvas, startingX, startingY, 1f, Gravity.Right);
                remainingTotalRating -= 1;
            } else {
                drawStar(internalCanvas, startingX, startingY, remainingTotalRating, Gravity.Right);
                remainingTotalRating = 0;
            }
            startingX -= starsSeparation + currentStarSize;
        }
    }

    /**
     * 画星星
     *
     * @param canvas  canvas to draw the star in
     * @param x       left of the star
     * @param y       top of the star
     * @param filled  between 0 and 1
     * @param gravity Left or Right
     */
    private void drawStar(Canvas canvas, float x, float y, float filled, Gravity gravity) {
        // calculate fill in pixels
        float fill = currentStarSize * filled;

        // prepare path for star
        starPath.reset();
        starPath.moveTo(x + starVertex[0], y + starVertex[1]);
        for (int i = 2; i < starVertex.length; i = i + 2) {
            starPath.lineTo(x + starVertex[i], y + starVertex[i + 1]);
        }
        starPath.close();

        // draw star outline
        canvas.drawPath(starPath, paintStarOutline);

        // Note: below, currentStarSize*0.02f is a minor correction so the user won't see a vertical black line in between the fill and empty color
        if (gravity == Gravity.Left) {
            // color star fill
            canvas.drawRect(x, y, x + fill + currentStarSize * 0.02f, y + currentStarSize, paintStarFill);
            // draw star background
            canvas.drawRect(x + fill, y, x + currentStarSize, y + currentStarSize, paintStarBackground);
        } else {
            // color star fill
            canvas.drawRect(x + currentStarSize - (fill + currentStarSize * 0.02f), y, x + currentStarSize, y + currentStarSize, paintStarFill);
            // draw star background
            canvas.drawRect(x, y, x + currentStarSize - fill, y + currentStarSize, paintStarBackground);
        }

        // draw star border on top
        if (borderVisible) {
            canvas.drawPath(starPath, paintStarBorder);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isIndicator || (ratingAnimator != null && ratingAnimator.isRunning())) {
            return false;
        }

        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // check if action is performed on stars
                if (starsTouchSpace.contains(event.getX(), event.getY())) {
                    touchInProgress = true;
                    setNewRatingFromTouch(event.getX(), event.getY());
                } else {
                    if (touchInProgress && ratingListener != null) {
                        ratingListener.onRatingChanged(this, progress, true);
                    }
                    touchInProgress = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                setNewRatingFromTouch(event.getX(), event.getY());
                if (clickListener != null) {
                    clickListener.onClick(this);
                }
            case MotionEvent.ACTION_CANCEL:
                if (ratingListener != null) {
                    ratingListener.onRatingChanged(this, progress, true);
                }
                touchInProgress = false;
                break;

        }

        invalidate();
        return true;
    }

    /**
     * Assigns a rating to the touch event.
     */
    private void setNewRatingFromTouch(float x, float y) {
        // normalize x to inside starsDrawinSpace
        if (ratingGravity != Gravity.Left) {
            x = getWidth() - x;
        }

        // we know that touch was inside starsTouchSpace, but it might be outside starsDrawingSpace
        if (x < starsDrawingSpace.left) {
            progress = 0;
            return;
        } else if (x > starsDrawingSpace.right) {
            progress = starCount;
            return;
        }

        x = x - starsDrawingSpace.left;
        // reduce the width to allow the user reach the top and bottom values of rating (0 and numberOfStars)
        progress = (float) starCount / starsDrawingSpace.width() * x;

        // correct rating in case step size is present
        float mod = progress % starStep;
        if (mod < starStep / 4) {
            progress = progress - mod;
            progress = Math.max(0, progress);
        } else {
            progress = progress - mod + starStep;
            progress = Math.min(starCount, progress);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.rating = getProgress();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.rating);
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private float rating = 0.0f;

        protected SavedState(Parcel source) {
            super(source);
            rating = source.readFloat();
        }

        @TargetApi(Build.VERSION_CODES.N)
        protected SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
        }

        protected SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(rating);
        }
    }

    /* ----------- GETTERS AND SETTERS ----------- */

    public float getProgress() {
        return progress;
    }

    /**
     * 设置进度：规则：
     * 1.如果progress<0，则progress=0
     * 2.如果progress>starCount，则progress=starCount
     * 3.如果progress>0&&progress<starCount，则progress=progress
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress < 0) {
            this.progress = 0;
        } else if (progress > starCount) {
            this.progress = starCount;
        }
        invalidate();
        if (ratingListener != null && (ratingAnimator == null || !ratingAnimator.isRunning())) {
            ratingListener.onRatingChanged(this, progress, false);
        }
    }

    public float getStarStep() {
        return starStep;
    }

    /**
     * Sets step size of rating.
     * Throws IllegalArgumentException if provided value is less or equal than zero.
     *
     * @param starStep step size
     */
    public void setStarStep(float starStep) {
        this.starStep = starStep;
        if (starStep < 0.1) {
            this.starStep = 0.1f;
        } else if (starStep > starCount) {
            this.starStep = starCount;
        }
        // request redraw of the view
        invalidate();
    }

    public boolean isIndicator() {
        return isIndicator;
    }

    /**
     * Sets indicator property.
     * If provided value is true, touch events will be deactivated, and thus user interaction will be deactivated.
     *
     * @param indicator true if indicator mode should be activated, false otherwise
     */
    public void setIndicator(boolean indicator) {
        isIndicator = indicator;
        touchInProgress = false;
    }

    /**
     * Returns max star size in the requested dimension.
     *
     * @return value in pixels
     */
    public float getMaxStarSize() {
        return maxStarSize;
    }

    /**
     * 设置星星的最大尺寸
     */
    public void setMaxStarSize(int maxStarSize) {
        this.maxStarSize = maxStarSize;
        if (currentStarSize > maxStarSize) {
            // force re-calculating the layout dimension
            requestLayout();
            generateInternalCanvas(getWidth(), getHeight());
            // request redraw of the view
            invalidate();
        }
    }

    /**
     * Return star size in pixels.
     *
     * @return value in pixels
     */
    public float getStarSize() {
        return currentStarSize;
    }


    /**
     * Sets exact star size in pixels.
     *
     * @param starSize value in pixels
     */
    public void setStarSize(int starSize) {
        this.desiredStarSize = starSize;
        if (desiredStarSize > maxStarSize) {
            desiredStarSize = maxStarSize;
        } else if (desiredStarSize < 0) {
            desiredStarSize = 0;
        }
        // force re-calculating the layout dimension
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        // request redraw of the view
        invalidate();
    }


    /**
     * Returns stars separation in pixels.
     *
     * @return value in pixels
     */
    public float getStarsSeparation() {
        return starsSeparation;
    }

    /**
     * Sets separation between stars in pixels.
     *
     * @param starsSeparation value in pixels
     */
    public void setStarsSeparation(int starsSeparation) {
        this.starsSeparation = starsSeparation;
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        invalidate();
    }

    public int getStarCount() {
        return starCount;
    }

    /**
     * 设置星星数量
     *
     * @param count 设置星星的数量
     */
    public void setStarCount(@IntRange(from = 1, to = Integer.MAX_VALUE) int count) {
        this.starCount = count;
        if (count < 1) {
            this.starCount = 1;
        }
        if (this.progress > this.starCount) {
            this.progress = this.starCount;
        }
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        invalidate();
    }

    /**
     * Returns star border width in pixels.
     *
     * @return value in pixels
     */
    public float getStarBorderWidth() {
        return starBorderWidth;
    }


    /**
     * Sets border width of stars in pixels.
     * Throws IllegalArgumentException if provided value is less or equal than zero.
     *
     * @param starBorderWidth width in pixels
     */
    public void setStarBorderWidth(int starBorderWidth) {
        this.starBorderWidth = Math.max(starBorderWidth, 0);
        borderVisible = starBorderWidth > 0 && borderColor != Color.TRANSPARENT;
        paintStarBorder.setStrokeWidth(starBorderWidth);
        invalidate();
    }

    /**
     * Returns start corner radius in pixels,
     *
     * @return value in pixels
     */
    public float getStarCornerRadius() {
        return starCornerRadius;
    }


    /**
     * Sets radius of star corner in pixels.
     * Throws IllegalArgumentException if provided value is less than zero.
     *
     * @param starCornerRadius radius in pixels
     */
    public void setStarCornerRadius(int starCornerRadius) {
        this.starCornerRadius = Math.max(starCornerRadius, 0);
        cornerPathEffect = new CornerPathEffect(starCornerRadius);
        paintStarBorder.setPathEffect(cornerPathEffect);
        paintStarOutline.setPathEffect(cornerPathEffect);
        // request redraw of the view
        invalidate();
    }

    public @ColorInt
    int getBorderColor() {
        return borderColor;
    }

    /**
     * Sets border color of stars in normal state.
     *
     * @param borderColor color
     */
    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        borderVisible = starBorderWidth > 0 && borderColor != Color.TRANSPARENT;
        // request redraw of the view
        invalidate();
    }

    public @ColorInt
    int getStarSelectedColor() {
        return starSelectedColor;
    }

    /**
     * Sets fill color of stars in normal state.
     *
     * @param starSelectedColor color
     */
    public void setStarSelectedColor(@ColorInt int starSelectedColor) {
        this.starSelectedColor = starSelectedColor;
        invalidate();
    }

    /**
     * Returns background color of stars in normal state.
     *
     * @return color
     */
    public @ColorInt
    int getStarNormalColor() {
        return starNormalColor;
    }

    /**
     * Sets background color of stars in normal state.
     *
     * @param starNormalColor color
     */
    public void setStarNormalColor(@ColorInt int starNormalColor) {
        this.starNormalColor = starNormalColor;
        // request redraw of the view
        invalidate();
    }

    public @ColorInt
    int getPressedBorderColor() {
        return pressedBorderColor;
    }

    /**
     * Sets border color of stars in pressed state.
     *
     * @param pressedBorderColor
     */
    public void setPressedBorderColor(@ColorInt int pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
        // request redraw of the view
        invalidate();
    }

    public @ColorInt
    int getPressedFillColor() {
        return pressedFillColor;
    }

    /**
     * Sets fill color of stars in pressed state.
     *
     * @param pressedFillColor color
     */
    public void setPressedFillColor(@ColorInt int pressedFillColor) {
        this.pressedFillColor = pressedFillColor;
        // request redraw of the view
        invalidate();
    }

    public @ColorInt
    int getPressedStarBackgroundColor() {
        return pressedStarBackgroundColor;
    }

    /**
     * Sets background color of stars in pressed state.
     *
     * @param pressedStarBackgroundColor color
     */
    public void setPressedStarBackgroundColor(@ColorInt int pressedStarBackgroundColor) {
        this.pressedStarBackgroundColor = pressedStarBackgroundColor;
        invalidate();
    }

    public Gravity getRatingGravity() {
        return ratingGravity;
    }

    /**
     * Sets gravity of fill.
     *
     * @param ratingGravity gravity
     */
    public void setRatingGravity(Gravity ratingGravity) {
        this.ratingGravity = ratingGravity;
        invalidate();
    }


    /**
     * Sets rating with animation.
     *
     * @param builder animation builder
     */
    private void animateRating(AnimationBuilder builder) {
        builder.ratingTarget = normalizeRating(builder.ratingTarget);
        ratingAnimator = ValueAnimator.ofFloat(0, builder.ratingTarget);
        ratingAnimator.setDuration(builder.duration);
        ratingAnimator.setRepeatCount(builder.repeatCount);
        ratingAnimator.setRepeatMode(builder.repeatMode);
        ratingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) (animation.getAnimatedValue());
                setProgress(value);
            }
        });

        if (builder.interpolator != null) {
            ratingAnimator.setInterpolator(builder.interpolator);
        }
        if (builder.animatorListener != null) {
            ratingAnimator.addListener(builder.animatorListener);
        }
        ratingAnimator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (ratingListener != null) {
                    ratingListener.onRatingChanged(XRatingBar.this, progress, false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                if (ratingListener != null) {
                    ratingListener.onRatingChanged(XRatingBar.this, progress, false);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                if (ratingListener != null) {
                    ratingListener.onRatingChanged(XRatingBar.this, progress, false);
                }
            }
        });
        ratingAnimator.start();
    }

    /**
     * Returns a new AnimationBuilder.
     *
     * @return animation builder
     */
    public AnimationBuilder getAnimationBuilder() {
        return new AnimationBuilder(this);
    }

    /**
     * Normalizes rating passed by argument between 0 and numberOfStars.
     *
     * @param rating rating to normalize
     * @return normalized rating
     */
    private float normalizeRating(float rating) {
        if (rating < 0) {
            Log.w("XRatingBar", String.format("Assigned rating is less than 0 (%f < 0), I will set it to exactly 0", rating));
            return 0;
        } else if (rating > starCount) {
            Log.w("XRatingBar", String.format("Assigned rating is greater than numberOfStars (%f > %d), I will set it to exactly numberOfStars", rating, starCount));
            return starCount;
        } else {
            return rating;
        }
    }

    /**
     * Sets OnClickListener.
     *
     * @param listener The listener to be notified on click events.
     */
    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * Sets OnRatingBarChangeListener.
     *
     * @param listener The listener to be notified of rating changes.
     */
    public void setOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
        this.ratingListener = listener;
    }

    public interface OnRatingBarChangeListener {

        /**
         * 通知评级已更改。客户端可以使用fromUser参数来区分用户启动的更改和以编程方式发生的更改。
         * 当用户通过触摸来完成评级时，不会连续调用此方法，只会调用一次。
         *
         * @param XRatingBar 评级已更改的评级栏。
         * @param rating     当前评级。这将在范围内 0..starCount.
         * @param fromUser   如果评级更改是由用户的触摸手势或箭头键/水平轨道球运动发起的，则为true。
         *                   如果评级更改是由编程方式发起的，则为false。
         */
        void onRatingChanged(XRatingBar XRatingBar, float rating, boolean fromUser);

    }

    /**
     * - Target rating: numberOfStars
     * - Animation: Bounce
     * - Duration: 2s
     * 动画构建器
     * 提供良好的默认值：
     * - 目标评级：numberOfStars
     * - 动画：反弹
     * - 持续时间：2s
     */
    public static class AnimationBuilder {
        private XRatingBar ratingBar;
        private long duration;
        private Interpolator interpolator;
        private float ratingTarget;
        private int repeatCount;
        private int repeatMode;
        private AnimatorListener animatorListener;

        private AnimationBuilder(XRatingBar ratingBar) {
            this.ratingBar = ratingBar;
            this.duration = 2000;
            this.interpolator = new BounceInterpolator();
            this.ratingTarget = ratingBar.getStarCount();
            this.repeatCount = 1;
            this.repeatMode = ValueAnimator.REVERSE;
        }

        /**
         * Sets duration of animation.
         *
         * @param duration
         * @return
         */
        public AnimationBuilder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets interpolator for animation.
         *
         * @param interpolator
         * @return
         */
        public AnimationBuilder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        /**
         * Sets rating after animation has ended.
         *
         * @param ratingTarget
         * @return
         */
        public AnimationBuilder setRatingTarget(float ratingTarget) {
            this.ratingTarget = ratingTarget;
            return this;
        }

        /**
         * Sets repeat count for animation.
         *
         * @param repeatCount must be a positive value or ValueAnimator.INFINITE
         * @return
         */
        public AnimationBuilder setRepeatCount(int repeatCount) {
            this.repeatCount = repeatCount;
            return this;
        }

        /**
         * Sets repeat mode for animation.
         *
         * @param repeatMode must be ValueAnimator.RESTART or ValueAnimator.REVERSE
         * @return
         */
        public AnimationBuilder setRepeatMode(int repeatMode) {
            this.repeatMode = repeatMode;
            return this;
        }

        /**
         * Sets AnimatorListener.
         *
         * @param animatorListener
         * @return
         */
        public AnimationBuilder setAnimatorListener(AnimatorListener animatorListener) {
            this.animatorListener = animatorListener;
            return this;
        }

        /**
         * Starts animation.
         */
        public void start() {
            ratingBar.animateRating(this);
        }
    }
}
