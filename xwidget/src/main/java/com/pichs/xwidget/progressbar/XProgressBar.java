package com.pichs.xwidget.progressbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.core.view.ViewCompat;

import com.pichs.xwidget.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 一个进度条控件，通过颜色变化显示进度，支持环形和矩形两种形式，主要特性如下：
 * <ol>
 * <li>支持在进度条中以文字形式显示进度，支持修改文字的颜色和大小。</li>
 * <li>可以通过 xml 属性修改进度背景色，当前进度颜色，进度条尺寸。</li>
 * <li>支持限制进度的最大值。</li>
 * </ol>
 * <p>
 * XProgressBar
 */
public class XProgressBar extends View {

    public final static int TYPE_RECT = 0;
    public final static int TYPE_CIRCLE = 1;
    public final static int TYPE_ROUND_RECT = 2;

    @IntDef({
            TYPE_RECT,
            TYPE_CIRCLE,
            TYPE_ROUND_RECT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public final static int TOTAL_DURATION = 500;
    public final static int DEFAULT_PROGRESS_COLOR = Color.BLUE;
    public final static int DEFAULT_BACKGROUND_COLOR = Color.GRAY;
    public final static int DEFAULT_TEXT_SIZE = 20;
    public final static int DEFAULT_TEXT_COLOR = Color.BLACK;
    private final static int PENDING_VALUE_NOT_SET = -1;
    /*circle_progress member*/
    public static int DEFAULT_STROKE_WIDTH = dp2px(40);
    private XProgressBarTextGenerator mXProgressBarTextGenerator;
    /*rect_progress member*/
    RectF mBgRect;
    RectF mProgressRect;
    /*common member*/
    private int mWidth;
    private int mHeight;
    @Type
    private int mType;
    private int mProgressColor;
    private int mBackgroundColor;
    private int mMaxValue;
    private int mValue;
    private int mPendingValue;
    private long mAnimationStartTime;
    private int mAnimationDistance;
    private int mAnimationDuration;
    private int mTextSize;
    private int mTextColor;
    private boolean mRoundCap;
    private Paint mBackgroundPaint = new Paint();
    private Paint mPaint = new Paint();
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mArcOval = new RectF();
    private String mText = "";
    private int mStrokeWidth;
    private int mCircleRadius;
    private Point mCenterPoint;

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public XProgressBar(Context context) {
        super(context);
        setup(context, null);
    }

    public XProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public XProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    public void setup(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XProgressBar);
        mType = array.getInt(R.styleable.XProgressBar_xp_progressType, TYPE_RECT);
        mProgressColor = array.getColor(R.styleable.XProgressBar_xp_progressColor, DEFAULT_PROGRESS_COLOR);
        mBackgroundColor = array.getColor(R.styleable.XProgressBar_xp_backgroundColor, DEFAULT_BACKGROUND_COLOR);

        mMaxValue = array.getInt(R.styleable.XProgressBar_xp_maxValue, 100);
        mValue = array.getInt(R.styleable.XProgressBar_xp_value, 0);

        mRoundCap = array.getBoolean(R.styleable.XProgressBar_xp_strokeRoundCap, false);

        mTextSize = DEFAULT_TEXT_SIZE;
        if (array.hasValue(R.styleable.XProgressBar_xp_textSize)) {
            mTextSize = array.getDimensionPixelSize(R.styleable.XProgressBar_xp_textSize, DEFAULT_TEXT_SIZE);
        }
        mTextColor = DEFAULT_TEXT_COLOR;
        if (array.hasValue(R.styleable.XProgressBar_xp_textColor)) {
            mTextColor = array.getColor(R.styleable.XProgressBar_xp_textColor, DEFAULT_TEXT_COLOR);
        }

        if (mType == TYPE_CIRCLE) {
            mStrokeWidth = array.getDimensionPixelSize(R.styleable.XProgressBar_xp_strokeWidth, DEFAULT_STROKE_WIDTH);
        }
        array.recycle();
        configPaint(mTextColor, mTextSize, mRoundCap);

        setProgress(mValue);
    }

    private void configShape() {
        if (mType == TYPE_RECT || mType == TYPE_ROUND_RECT) {
            mBgRect = new RectF(getPaddingLeft(), getPaddingTop(), mWidth + getPaddingLeft(), mHeight + getPaddingTop());
            mProgressRect = new RectF();
        } else {
            mCircleRadius = (Math.min(mWidth, mHeight) - mStrokeWidth) / 2;
            mCenterPoint = new Point(mWidth / 2, mHeight / 2);
        }
    }

    private void configPaint(int textColor, int textSize, boolean isRoundCap) {
        mPaint.setColor(mProgressColor);
        mBackgroundPaint.setColor(mBackgroundColor);
        if (mType == TYPE_RECT || mType == TYPE_ROUND_RECT) {
            mPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setAntiAlias(true);
            if (isRoundCap) {
                mPaint.setStrokeCap(Paint.Cap.ROUND);
            }
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setStrokeWidth(mStrokeWidth);
            mBackgroundPaint.setAntiAlias(true);
        }
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setType(@Type int type) {
        mType = type;
        configPaint(mTextColor, mTextSize, mRoundCap);
        invalidate();
    }

    public void setBarColor(int backgroundColor, int progressColor) {
        mBackgroundColor = backgroundColor;
        mProgressColor = progressColor;
        mBackgroundPaint.setColor(mBackgroundColor);
        mPaint.setColor(mProgressColor);
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(mBackgroundColor);
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mPaint.setColor(mProgressColor);
        invalidate();
    }

    /**
     * 设置进度文案的文字大小
     *
     * @see #setTextColor(int)
     * @see #setXProgressBarTextGenerator(XProgressBarTextGenerator)
     */
    public void setTextSize(int textSize) {
        mTextPaint.setTextSize(textSize);
        invalidate();
    }

    /**
     * 设置进度文案的文字颜色
     *
     * @see #setTextSize(int)
     * @see #setXProgressBarTextGenerator(XProgressBarTextGenerator)
     */
    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
        invalidate();
    }

    /**
     * 设置环形进度条的两端是否有圆形的线帽，类型为{@link #TYPE_CIRCLE}时生效
     */
    public void setStrokeRoundCap(boolean isRoundCap) {
        mPaint.setStrokeCap(isRoundCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        invalidate();
    }

    /**
     * 通过 {@link XProgressBarTextGenerator} 设置进度文案
     */
    public void setXProgressBarTextGenerator(XProgressBarTextGenerator XProgressBarTextGenerator) {
        mXProgressBarTextGenerator = XProgressBarTextGenerator;
    }

    public XProgressBarTextGenerator getXProgressBarTextGenerator() {
        return mXProgressBarTextGenerator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPendingValue != PENDING_VALUE_NOT_SET) {
            long elapsed = System.currentTimeMillis() - mAnimationStartTime;
            if (elapsed >= mAnimationDuration) {
                mValue = mPendingValue;
                mPendingValue = PENDING_VALUE_NOT_SET;
            } else {
                mValue = (int) (mPendingValue - (1f - ((float) elapsed / mAnimationDuration)) * mAnimationDistance);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        if (mXProgressBarTextGenerator != null) {
            mText = mXProgressBarTextGenerator.generateText(this, mValue, mMaxValue);
        }
        if (((mType == TYPE_RECT || mType == TYPE_ROUND_RECT) && mBgRect == null) ||
                (mType == TYPE_CIRCLE && mCenterPoint == null)) {
            // npe protect, sometimes measure may not be called by parent.
            configShape();
        }
        if (mType == TYPE_RECT) {
            drawRect(canvas);
        } else if (mType == TYPE_ROUND_RECT) {
            drawRoundRect(canvas);
        } else {
            drawCircle(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        configShape();
        setMeasuredDimension(mWidth, mHeight);
    }

    private void drawRect(Canvas canvas) {
        canvas.drawRect(mBgRect, mBackgroundPaint);
        mProgressRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + parseValueToWidth(), getPaddingTop() + mHeight);
        canvas.drawRect(mProgressRect, mPaint);
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mBgRect.top + (mBgRect.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mBgRect.centerX(), baseline, mTextPaint);
        }
    }

    private void drawRoundRect(Canvas canvas) {
        float round = mHeight / 2f;
        canvas.drawRoundRect(mBgRect, round, round, mBackgroundPaint);
        mProgressRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + parseValueToWidth(), getPaddingTop() + mHeight);
        canvas.drawRoundRect(mProgressRect, round, round, mPaint);
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mBgRect.top + (mBgRect.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mBgRect.centerX(), baseline, mTextPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, mBackgroundPaint);
        mArcOval.left = mCenterPoint.x - mCircleRadius;
        mArcOval.right = mCenterPoint.x + mCircleRadius;
        mArcOval.top = mCenterPoint.y - mCircleRadius;
        mArcOval.bottom = mCenterPoint.y + mCircleRadius;
        if (mValue > 0) {
            canvas.drawArc(mArcOval, 270, 360f * mValue / mMaxValue, false, mPaint);
        }
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mArcOval.top + (mArcOval.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mCenterPoint.x, baseline, mTextPaint);
        }
    }

    private int parseValueToWidth() {
        if (mMaxValue == 0) return 0;
        return mWidth * mValue / mMaxValue;
    }

    public int getProgress() {
        return mValue;
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    public void setProgress(int progress, boolean animated) {
        if (progress > mMaxValue || progress < 0) {
            return;
        }

        if ((mPendingValue == PENDING_VALUE_NOT_SET && mValue == progress) ||
                (mPendingValue != PENDING_VALUE_NOT_SET && mPendingValue == progress)) {
            return;
        }

        if (!animated) {
            mPendingValue = PENDING_VALUE_NOT_SET;
            mValue = progress;
            invalidate();
        } else {
            mAnimationDuration = Math.abs((int) (TOTAL_DURATION * (mValue - progress) / (float) mMaxValue));
            mAnimationStartTime = System.currentTimeMillis();
            mAnimationDistance = progress - mValue;
            mPendingValue = progress;
            invalidate();
        }
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        if (maxValue == 0) return;
        mMaxValue = maxValue;
    }

    public interface XProgressBarTextGenerator {
        /**
         * 设置进度文案, {@link XProgressBar} 会在进度更新时调用该方法获取要显示的文案
         *
         * @param value    当前进度值
         * @param maxValue 最大进度值
         * @return 进度文案
         */
        String generateText(XProgressBar progressBar, int value, int maxValue);
    }
}
