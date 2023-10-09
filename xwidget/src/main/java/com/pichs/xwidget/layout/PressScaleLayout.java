package com.pichs.xwidget.layout;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.pichs.common.widget.R;
import com.pichs.xwidget.interpolator.AnimationProperty;
import com.pichs.xwidget.interpolator.XInterpolators;

/**
 * 按下缩放的布局
 * 缩放子控件，不缩放自己，可解决部分图片不对称时的按压场景
 * clickable默认为false，设置点击事件后才会生效动画
 */
public class PressScaleLayout extends FrameLayout {

    private ValueAnimator valueAnim;
    private float currentScaleX = 1f;
    private float currentScaleY = 1f;
    private View childView;
    private float pressedScale = 0.9f;
    private int pressedScaleAnimDuration = 80;

    public PressScaleLayout(Context context) {
        this(context, null);
    }

    public PressScaleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PressScaleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

        childView = getChildAt(0);
        valueAnim = new ValueAnimator();
        valueAnim.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            currentScaleX = value;
            currentScaleY = value;
            if (childView != null) {
                childView.setScaleX(currentScaleX);
                childView.setScaleY(currentScaleY);
            }
        });
    }

    private void init(Context context, AttributeSet attrs, int defAttr) {
        // 获取属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PressScaleLayout, defAttr, 0);
        pressedScale = ta.getFloat(R.styleable.PressScaleLayout_xp_pressedScale, 0.9f);
        pressedScaleAnimDuration = ta.getInteger(R.styleable.PressScaleLayout_xp_pressedScaleAnimDuration, pressedScaleAnimDuration);
        ta.recycle();
    }

    public void setPressedScale(float pressedScale) {
        this.pressedScale = pressedScale;
    }

    public float getPressedScale() {
        return pressedScale;
    }

    public void setPressedScaleAnimDuration(int pressedScaleAnimDuration) {
        this.pressedScaleAnimDuration = pressedScaleAnimDuration;
    }

    public int getPressedScaleAnimDuration() {
        return pressedScaleAnimDuration;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        doPressAnimation(pressed);
    }

    private void doPressAnimation(boolean pressed) {
        if (pressed) {
            cancelAnimator();
            if (childView == null) {
                childView = getChildAt(0);
            }
            shrinkViewAnim();
        } else {
            enlargeViewAnim(() -> {

            });
        }
    }

    // 缩小动画
    public void shrinkViewAnim() {
        if (childView != null) {
            valueAnim.setValues(
                    PropertyValuesHolder.ofFloat(
                            AnimationProperty.scaleX, currentScaleX, pressedScale
                    ),
                    PropertyValuesHolder.ofFloat(
                            AnimationProperty.scaleY, currentScaleY, pressedScale
                    )
            );
            valueAnim.setDuration(pressedScaleAnimDuration);
            valueAnim.setRepeatCount(0);
            valueAnim.setInterpolator(XInterpolators.linear);
            valueAnim.start();
        }
    }

    // 放大动画
    public void enlargeViewAnim(Runnable onEnd) {
        if (childView != null) {
            valueAnim.setValues(
                    PropertyValuesHolder.ofFloat(
                            AnimationProperty.scaleX, currentScaleX, 1f
                    ),
                    PropertyValuesHolder.ofFloat(
                            AnimationProperty.scaleY, currentScaleY, 1f
                    )
            );
            valueAnim.setDuration(pressedScaleAnimDuration);
            valueAnim.setRepeatCount(0);
            valueAnim.setInterpolator(XInterpolators.linear);
            valueAnim.start();
            postDelayed(onEnd, pressedScaleAnimDuration + 50);
        }
    }

    public void cancelAnimator() {
        if (valueAnim != null) {
            valueAnim.cancel();
        }
    }
}