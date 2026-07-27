package com.pichs.xwidget.shinebutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

import com.pichs.xwidget.shinebutton.interpolator.Ease;
import com.pichs.xwidget.shinebutton.interpolator.EasingInterpolator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * ShineView — 点击发散光芒动画效果视图。
 * <p>
 * 该 View 作为 overlay 添加到 Window 的 DecorView 上，
 * 在首次 onDraw 时自动启动动画，动画结束后自动从父容器中移除。
 */
public class ShineView extends View {

    private static final int DEFAULT_STROKE_WIDTH_LARGE = 20;
    private static final int DEFAULT_STROKE_WIDTH_SMALL = 10;
    private static final float DEFAULT_DISTANCE_OFFSET = 0.2f;
    private static final float MIN_ARC_SWEEP = 0.1f;
    private static final float CLICK_ANIM_MAX = 1.1f;
    private static final int CENTER_STROKE_MIN_OFFSET = 8;

    private ShineAnimator mShineAnimator;
    private ValueAnimator mClickAnimator;

    private WeakReference<ShineButton> mShineButton;
    private Paint mPaint;
    private Paint mPaint2;
    private Paint mPaintSmall;

    private int mColorCount;

    private final List<Integer> mFlashingColors = new ArrayList<>();

    private int mShineCount;
    private float mSmallOffsetAngle;
    private float mTurnAngle;
    private long mAnimDuration;
    private long mClickAnimDuration;
    private float mShineDistanceMultiple;
    private int mSmallShineColor;
    private int mBigShineColor;

    private int mShineSize;

    private boolean mAllowRandomColor;
    private boolean mEnableFlashing;

    private final RectF mRectF = new RectF();
    private final RectF mSmallRectF = new RectF();

    private final Random mRandom = new Random();
    private int mCenterAnimX;
    private int mCenterAnimY;
    private int mBtnWidth;
    private int mBtnHeight;

    private float mValue;
    private float mClickValue;
    private volatile boolean mIsAnimating;
    private float mDistanceOffset = DEFAULT_DISTANCE_OFFSET;

    // ---------- 循环外预计算的缓存值 ----------
    private float mCachedArcStep;
    private float mCachedValueMinusOneTimesTurnAngle;
    private float mCachedDistanceFactor;
    private float mCachedSmallDistanceFactor;
    private float mCachedHalfColorCount;

    public ShineView(Context context) {
        super(context);
    }

    public ShineView(Context context, final ShineButton shineButton, ShineParams shineParams) {
        super(context);
        initShineParams(shineParams, shineButton);
        mShineButton = new WeakReference<>(shineButton);
        mShineAnimator = new ShineAnimator(mAnimDuration, mShineDistanceMultiple, mClickAnimDuration);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBigShineColor);
        mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH_LARGE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.WHITE);
        mPaint2.setStrokeWidth(DEFAULT_STROKE_WIDTH_LARGE);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);

        mPaintSmall = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSmall.setColor(mSmallShineColor);
        mPaintSmall.setStrokeWidth(DEFAULT_STROKE_WIDTH_SMALL);
        mPaintSmall.setStyle(Paint.Style.STROKE);
        mPaintSmall.setStrokeCap(Paint.Cap.ROUND);

        mClickAnimator = ValueAnimator.ofFloat(0f, CLICK_ANIM_MAX);
        mClickAnimator.setDuration(mClickAnimDuration);
        mClickAnimator.setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
        mClickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mClickValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mClickAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mClickValue = 0;
                invalidate();
            }
        });

        // 预先计算不随动画变化的值
        precomputeStaticValues();
    }

    public ShineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 启动动画。由 ShineButton 在需要展示光芒效果时调用。
     */
    public void showAnimation(ShineButton shineButton) {
        if (shineButton == null) {
            return;
        }
        mBtnWidth = shineButton.getWidth();
        mBtnHeight = shineButton.getHeight();
        int[] location = new int[2];
        shineButton.getLocationInWindow(location);
        mCenterAnimX = location[0] + mBtnWidth / 2;
        mCenterAnimY = location[1] + mBtnHeight / 2;

        if (shineButton.getWindow() != null) {
            View decor = shineButton.getWindow().getDecorView();
            mCenterAnimX = mCenterAnimX - decor.getPaddingLeft();
            mCenterAnimY = mCenterAnimY - decor.getPaddingTop();
        }

        // 清除旧的 listener，避免累积导致性能退化
        mShineAnimator.removeAllUpdateListeners();
        mShineAnimator.removeAllListeners();

        mShineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mValue = (float) valueAnimator.getAnimatedValue();
                float inverseFactor = mShineDistanceMultiple - mValue;
                if (mShineSize > 0) {
                    mPaint.setStrokeWidth(mShineSize * inverseFactor);
                    mPaintSmall.setStrokeWidth((mShineSize * 2f / 3f) * inverseFactor);
                } else {
                    mPaint.setStrokeWidth((mBtnWidth / 2f) * inverseFactor);
                    mPaintSmall.setStrokeWidth((mBtnWidth / 3f) * inverseFactor);
                }

                float distanceFactor = mBtnWidth / (3f - mShineDistanceMultiple) * mValue;
                float halfHeight = mBtnHeight / (3f - mShineDistanceMultiple) * mValue;
                mRectF.set(
                        mCenterAnimX - distanceFactor,
                        mCenterAnimY - halfHeight,
                        mCenterAnimX + distanceFactor,
                        mCenterAnimY + halfHeight
                );

                float smallDistanceFactor = mBtnWidth / ((3f - mShineDistanceMultiple) + mDistanceOffset) * mValue;
                float smallHalfHeight = mBtnHeight / ((3f - mShineDistanceMultiple) + mDistanceOffset) * mValue;
                mSmallRectF.set(
                        mCenterAnimX - smallDistanceFactor,
                        mCenterAnimY - smallHalfHeight,
                        mCenterAnimX + smallDistanceFactor,
                        mCenterAnimY + smallHalfHeight
                );

                // 更新缓存的计算值
                updateCachedAnimationValues();
                invalidate();
            }
        });

        mShineAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mRectF.set(0, 0, 0, 0);
                mSmallRectF.set(0, 0, 0, 0);
                invalidate();
                // 动画结束后从父容器移除自身
                ShineButton button = mShineButton != null ? mShineButton.get() : null;
                if (button != null) {
                    button.removeView(ShineView.this);
                } else if (getParent() instanceof android.view.ViewGroup) {
                    ((android.view.ViewGroup) getParent()).removeView(ShineView.this);
                }
            }
        });

        mShineAnimator.start();
        mClickAnimator.start();
    }

    /**
     * 预计算不随动画帧变化的值，避免在 onDraw 循环中重复计算。
     */
    private void precomputeStaticValues() {
        mCachedArcStep = mShineCount > 0 ? 360f / mShineCount : 0f;
        mCachedHalfColorCount = mColorCount / 2f;
    }

    /**
     * 更新每帧随 mValue 变化的值。
     */
    private void updateCachedAnimationValues() {
        mCachedValueMinusOneTimesTurnAngle = (mValue - 1f) * mTurnAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制大光芒弧线
        for (int i = 0; i < mShineCount; i++) {
            if (mEnableFlashing && mColorCount > 0) {
                // 闪烁模式：每帧随机颜色
                mPaint.setColor(mFlashingColors.get(mRandom.nextInt(mColorCount)));
            } else if (mAllowRandomColor) {
                // 位置索引颜色模式
                int colorIndex = Math.abs(mColorCount / 2 - i);
                if (colorIndex >= mColorCount) {
                    colorIndex = mColorCount - 1;
                }
                mPaint.setColor(mFlashingColors.get(colorIndex));
            }
            float startAngle = mCachedArcStep * i + 1f + mCachedValueMinusOneTimesTurnAngle;
            canvas.drawArc(mRectF, startAngle, MIN_ARC_SWEEP, false, mPaint);
        }

        // 绘制小光芒弧线
        for (int i = 0; i < mShineCount; i++) {
            if (mEnableFlashing && mColorCount > 0) {
                mPaintSmall.setColor(mFlashingColors.get(mRandom.nextInt(mColorCount)));
            } else if (mAllowRandomColor) {
                int colorIndex = Math.abs(mColorCount / 2 - i);
                if (colorIndex >= mColorCount) {
                    colorIndex = mColorCount - 1;
                }
                mPaintSmall.setColor(mFlashingColors.get(colorIndex));
            }
            float startAngle = mCachedArcStep * i + 1f - mSmallOffsetAngle + mCachedValueMinusOneTimesTurnAngle;
            canvas.drawArc(mSmallRectF, startAngle, MIN_ARC_SWEEP, false, mPaintSmall);
        }

        // 绘制中心点脉冲
        float clickStrokeWidth = mBtnWidth * mClickValue * (mShineDistanceMultiple - mDistanceOffset);
        mPaint.setStrokeWidth(clickStrokeWidth);
        if (mClickValue != 0) {
            float innerStrokeWidth = clickStrokeWidth - CENTER_STROKE_MIN_OFFSET;
            mPaint2.setStrokeWidth(Math.max(0, innerStrokeWidth));
        } else {
            mPaint2.setStrokeWidth(0);
        }
        canvas.drawPoint(mCenterAnimX, mCenterAnimY, mPaint);
        canvas.drawPoint(mCenterAnimX, mCenterAnimY, mPaint2);

        // 首次绘制时启动动画（确保 layout 完成后尺寸正确）
        if (mShineAnimator != null && !mIsAnimating) {
            ShineButton button = mShineButton != null ? mShineButton.get() : null;
            if (button != null) {
                mIsAnimating = true;
                showAnimation(button);
            }
            // 如果 button 已被回收，不设置 mIsAnimating，
            // 下次 onDraw 还会重试；如果持续为 null 也不会死循环，
            // 因为 View 已无引用，GC 会一并回收
        }
    }

    // ==================== ShineParams ====================

    /**
     * 光芒动画参数配置。
     * <p>
     * 使用 Builder 模式构建，或直接通过 setter 方法逐项配置。
     */
    public static class ShineParams {

        private final List<Integer> flashingColors = new ArrayList<>();
        private boolean allowRandomColor;
        private long animDuration = 1500;
        private int bigShineColor;
        private long clickAnimDuration = 200;
        private boolean enableFlashing;
        private int shineCount = 7;
        private float shineTurnAngle = 20;
        private float shineDistanceMultiple = 1.5f;
        private float smallShineOffsetAngle = 20;
        private int smallShineColor;
        private int shineSize;

        public ShineParams() {
        }

        // ---------- Getters ----------

        public List<Integer> getFlashingColors() {
            return Collections.unmodifiableList(flashingColors);
        }

        public boolean isAllowRandomColor() {
            return allowRandomColor;
        }

        public long getAnimDuration() {
            return animDuration;
        }

        public int getBigShineColor() {
            return bigShineColor;
        }

        public long getClickAnimDuration() {
            return clickAnimDuration;
        }

        public boolean isEnableFlashing() {
            return enableFlashing;
        }

        public int getShineCount() {
            return shineCount;
        }

        public float getShineTurnAngle() {
            return shineTurnAngle;
        }

        public float getShineDistanceMultiple() {
            return shineDistanceMultiple;
        }

        public float getSmallShineOffsetAngle() {
            return smallShineOffsetAngle;
        }

        public int getSmallShineColor() {
            return smallShineColor;
        }

        public int getShineSize() {
            return shineSize;
        }

        // ---------- Setters with validation ----------

        public ShineParams setFlashingColors(@ColorInt int... colors) {
            flashingColors.clear();
            for (int color : colors) {
                flashingColors.add(color);
            }
            return this;
        }

        public ShineParams setFlashingColors(List<Integer> colors) {
            flashingColors.clear();
            if (colors != null) {
                flashingColors.addAll(colors);
            }
            return this;
        }

        public ShineParams setAllowRandomColor(boolean allowRandomColor) {
            this.allowRandomColor = allowRandomColor;
            return this;
        }

        public ShineParams setAnimDuration(@IntRange(from = 0) long animDuration) {
            this.animDuration = Math.max(0, animDuration);
            return this;
        }

        public ShineParams setBigShineColor(@ColorInt int bigShineColor) {
            this.bigShineColor = bigShineColor;
            return this;
        }

        public ShineParams setClickAnimDuration(@IntRange(from = 0) long clickAnimDuration) {
            this.clickAnimDuration = Math.max(0, clickAnimDuration);
            return this;
        }

        public ShineParams setEnableFlashing(boolean enableFlashing) {
            this.enableFlashing = enableFlashing;
            return this;
        }

        public ShineParams setShineCount(@IntRange(from = 1) int shineCount) {
            this.shineCount = Math.max(1, shineCount);
            return this;
        }

        public ShineParams setShineTurnAngle(float shineTurnAngle) {
            this.shineTurnAngle = shineTurnAngle;
            return this;
        }

        public ShineParams setShineDistanceMultiple(
                @FloatRange(from = 1.0) float shineDistanceMultiple) {
            this.shineDistanceMultiple = Math.max(1.0f, shineDistanceMultiple);
            return this;
        }

        public ShineParams setSmallShineOffsetAngle(float smallShineOffsetAngle) {
            this.smallShineOffsetAngle = smallShineOffsetAngle;
            return this;
        }

        public ShineParams setSmallShineColor(@ColorInt int smallShineColor) {
            this.smallShineColor = smallShineColor;
            return this;
        }

        public ShineParams setShineSize(@IntRange(from = 0) int shineSize) {
            this.shineSize = Math.max(0, shineSize);
            return this;
        }
    }

    // ==================== 内部初始化 ====================

    private void initShineParams(ShineParams shineParams, ShineButton shineButton) {
        if (shineParams == null) {
            shineParams = new ShineParams();
        }

        mShineCount = shineParams.shineCount;
        mTurnAngle = shineParams.shineTurnAngle;
        mSmallOffsetAngle = shineParams.smallShineOffsetAngle;
        mEnableFlashing = shineParams.enableFlashing;
        mAllowRandomColor = shineParams.allowRandomColor;
        mShineDistanceMultiple = shineParams.shineDistanceMultiple;
        mAnimDuration = shineParams.animDuration;
        mClickAnimDuration = shineParams.clickAnimDuration;
        mSmallShineColor = shineParams.smallShineColor;
        mBigShineColor = shineParams.bigShineColor;
        mShineSize = shineParams.shineSize;

        // 防御性拷贝：复制一份颜色列表，避免外部修改影响内部状态
        mFlashingColors.clear();
        if (shineParams.flashingColors != null && !shineParams.flashingColors.isEmpty()) {
            mFlashingColors.addAll(shineParams.flashingColors);
        }

        // 默认颜色列表
        if (mFlashingColors.isEmpty()) {
            Collections.addAll(mFlashingColors,
                    Color.parseColor("#FFFF99"),
                    Color.parseColor("#FFCCCC"),
                    Color.parseColor("#996699"),
                    Color.parseColor("#FF6666"),
                    Color.parseColor("#FFFF66"),
                    Color.parseColor("#F44336"),
                    Color.parseColor("#666666"),
                    Color.parseColor("#CCCC00"),
                    Color.parseColor("#999933")
            );
        }

        mColorCount = mFlashingColors.size();
    }
}
