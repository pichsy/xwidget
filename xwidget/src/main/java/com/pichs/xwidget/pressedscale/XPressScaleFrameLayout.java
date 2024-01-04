package com.pichs.xwidget.pressedscale;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.pichs.xwidget.R;
import com.pichs.xwidget.interpolator.AnimationProperty;
import com.pichs.xwidget.interpolator.XInterpolators;
import com.pichs.xwidget.utils.XIdsHelper;

import java.util.HashSet;

/**
 * 按下缩放的布局
 * 缩放子控件，不缩放自己，可解决部分图片不对称时的按压场景
 * clickable默认为false，设置点击事件后才会生效动画
 */
public class XPressScaleFrameLayout extends FrameLayout {

    private final ValueAnimator valueAnim;
    private float currentScaleX = 1f;
    private float currentScaleY = 1f;
    //    private View childView;
    private float pressedScale = 0.9f;
    private int pressedScaleAnimDuration = 80;

    private final HashSet<View> mScaleChildren = new HashSet<>();
    private boolean isJustFirstAnimation = true;
    private final HashSet<Integer> mScaleChildIds = new HashSet<>();

    public XPressScaleFrameLayout(Context context) {
        this(context, null);
    }

    public XPressScaleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XPressScaleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

//        childView = getChildAt(0);
        valueAnim = new ValueAnimator();
        valueAnim.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            currentScaleX = value;
            currentScaleY = value;
            if (mScaleChildren.size() > 0) {
                for (View child : mScaleChildren) {
                    child.setScaleX(currentScaleX);
                    child.setScaleY(currentScaleY);
                }
            }
        });
    }

    private void init(Context context, AttributeSet attrs, int defAttr) {
        // 获取属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XPressScaleLinearLayout, defAttr, 0);
        pressedScale = ta.getFloat(R.styleable.XPressScaleLinearLayout_xp_pressedScale, 0.9f);
        pressedScaleAnimDuration = ta.getInteger(R.styleable.XPressScaleLinearLayout_xp_pressedScaleAnimDuration, pressedScaleAnimDuration);
        String ids = ta.getString(R.styleable.XPressScaleLinearLayout_xp_pressedScaleChildIds);
        isJustFirstAnimation = ta.getBoolean(R.styleable.XPressScaleLinearLayout_xp_pressedScaleJustFirst, false);
        ta.recycle();
        mScaleChildIds.clear();
        if (ids != null && ids.length() > 0) {
            String[] idArray = ids.split("[,，]");
            if (idArray.length == 0) {
                return;
            }
            for (String idStr : idArray) {
                try {
                    int id = XIdsHelper.getIdByName(context, idStr.trim());
                    if (id != 0) {
                        mScaleChildIds.add(id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d("XPressScaleLinearLayout", "init: getChildCount():" + getChildCount());

    }


    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (!mScaleChildIds.isEmpty()) {
            if (mScaleChildIds.contains(view.getId())) {
                mScaleChildren.add(view);
            }
        } else if (isJustFirstAnimation) {
            if (mScaleChildren.isEmpty()) {
                mScaleChildren.add(view);
            }
        } else {
            mScaleChildren.add(view);
        }
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        mScaleChildren.remove(view);
    }

    /**
     * 全量更新id列表
     *
     * @param views view
     */
    public void setPressedScaleChildViewIds(int... views) {
        if (getChildCount() == 0) {
            return;
        }
        mScaleChildIds.clear();
        for (int view : views) {
            mScaleChildIds.add(view);
        }
        // 处理 mScaleChildren 列表
        mScaleChildren.clear();
        if (!mScaleChildIds.isEmpty()) {
            for (int id : mScaleChildIds) {
                View child = findViewById(id);
                if (child != null) {
                    mScaleChildren.add(child);
                }
            }
        }

        // 如果默认第一个，如果被全部移除后，则会默认第一个
        if (mScaleChildIds.isEmpty() && isJustFirstAnimation) {
            View childAt = getChildAt(0);
            if (childAt != null) {
                mScaleChildren.add(childAt);
            }
        } else if (mScaleChildIds.isEmpty()) {
            // 应当添加全部控件到列表
            for (int i = 0; i < getChildCount(); i++) {
                mScaleChildren.add(getChildAt(i));
            }
        }
    }

    public HashSet<Integer> getPressedScaleChildViewIds() {
        return mScaleChildIds;
    }


    public HashSet<View> getPressedScaleChildView() {
        return mScaleChildren;
    }

    public void setJustFirstAnimation(boolean justFirstAnimation) {
        isJustFirstAnimation = justFirstAnimation;
        if (getChildCount() == 0) {
            return;
        }

        if (!mScaleChildIds.isEmpty()) {
            return;
        }

        if (isJustFirstAnimation) {
            View childAt = getChildAt(0);
            if (childAt != null) {
                mScaleChildren.clear();
                mScaleChildren.add(childAt);
            }
        } else {
            // 添加全部控件到列表
            mScaleChildren.clear();
            for (int i = 0; i < getChildCount(); i++) {
                mScaleChildren.add(getChildAt(i));
            }
        }
    }

    public boolean isJustFirstAnimation() {
        return isJustFirstAnimation;
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
            shrinkViewAnim();
        } else {
            enlargeViewAnim(() -> {

            });
        }
    }

    // 缩小动画
    public void shrinkViewAnim() {
        if (mScaleChildren.size() != 0) {
            valueAnim.setValues(PropertyValuesHolder.ofFloat(AnimationProperty.scaleX, currentScaleX, pressedScale), PropertyValuesHolder.ofFloat(AnimationProperty.scaleY, currentScaleY, pressedScale));
            valueAnim.setDuration(pressedScaleAnimDuration);
            valueAnim.setRepeatCount(0);
            valueAnim.setInterpolator(XInterpolators.linear);
            valueAnim.start();
        }
    }

    // 放大动画
    public void enlargeViewAnim(Runnable onEnd) {
        if (mScaleChildren.size() != 0) {
            valueAnim.setValues(PropertyValuesHolder.ofFloat(AnimationProperty.scaleX, currentScaleX, 1f), PropertyValuesHolder.ofFloat(AnimationProperty.scaleY, currentScaleY, 1f));
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