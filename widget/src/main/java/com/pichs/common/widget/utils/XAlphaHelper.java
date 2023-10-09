package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XIAlpha;
import com.pichs.common.widget.interpolator.XInterpolators;
import com.pichs.common.widget.view.IPressedStateHelper;
import com.pichs.common.widget.view.OnPressedStateListener;

import java.lang.ref.WeakReference;

public class XAlphaHelper implements XIAlpha, IPressedStateHelper {

    private final WeakReference<View> mOwner;

    @Override
    public void setOnPressedStateListener(OnPressedStateListener listener) {
        this.mOnPressedStateListener = listener;
    }

    private OnPressedStateListener mOnPressedStateListener;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean isChangeAlphaOnPress = false;

    /**
     * 设置是否要在 disabled 时改变透明度
     */
    private boolean isChangeAlphaOnDisable = false;

    /**
     * 设置是否要在 press 时改变大小
     */
    private boolean isChangeScaleOnPress = false;

    /**
     * 设置是否要在 disabled 时改变大小
     */
    private boolean isChangeScaleOnDisable = false;

    private float mNormalAlpha = 1f;
    private float mPressedAlpha = 1f;
    private float mDisabledAlpha = 1f;

    private float mNormalScale = 1f;
    private float mPressedScale = 1f;
    private float mDisabledScale = 1f;

    public XAlphaHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XAlphaHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, View owner) {
        mOwner = new WeakReference<>(owner);
        init(context, attrs, defAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defAttr, int defStyleRes) {
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XIAlpha, defAttr, defStyleRes);
            mNormalAlpha = ta.getFloat(R.styleable.XIAlpha_android_alpha, 1f);
            mPressedAlpha = ta.getFloat(R.styleable.XIAlpha_xp_pressedAlpha, 1f);
            mDisabledAlpha = ta.getFloat(R.styleable.XIAlpha_xp_disabledAlpha, 1f);
            mPressedScale = ta.getFloat(R.styleable.XIAlpha_xp_pressedScale, 1f);
            mDisabledScale = ta.getFloat(R.styleable.XIAlpha_xp_disabledScale, 1f);
            ta.recycle();
            if (mPressedAlpha != mNormalAlpha) {
                isChangeAlphaOnPress = true;
            }
            if (mDisabledAlpha != mNormalAlpha) {
                isChangeAlphaOnDisable = true;
            }
            if (mNormalScale != mPressedScale) {
                isChangeScaleOnPress = true;
            }
            if (mNormalScale != mDisabledScale) {
                isChangeScaleOnDisable = true;
            }
        }
    }


    public void onPressedChanged(View current, boolean pressed) {
        View target = mOwner.get();
        if (target == null) {
            return;
        }
        if (target.isEnabled()) {
            target.setAlpha(isChangeAlphaOnPress && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
            if (isChangeScaleOnPress) {
                if (pressed) {
                    target.animate()
                            .scaleX(mPressedScale)
                            .scaleY(mPressedScale)
                            .setDuration(80)
                            .setInterpolator(XInterpolators.linear)
                            .start();
                } else {
                    target.animate()
                            .scaleX(mNormalScale)
                            .scaleY(mNormalScale)
                            .setDuration(80)
                            .setInterpolator(XInterpolators.linear)
                            .start();
                }
            }
            if (mOnPressedStateListener != null) {
                mOnPressedStateListener.onPressedStatusChanged(pressed);
            }
        } /*else {
//            if (isChangeAlphaOnDisable) {
//                target.setAlpha(mDisabledAlpha);
//            }
        }*/
    }

    /**
     * 在 {@link View#setEnabled(boolean)} 中调用，通知 helper 更新
     *
     * @param current the view to be handled, maybe not  equal to target view
     * @param enabled {@link View#setEnabled(boolean)} 中接收到的参数
     */
    public void onEnabledChanged(View current, boolean enabled) {
        View target = mOwner.get();
        if (target == null) {
            return;
        }
        float alphaForIsEnable;
        if (isChangeAlphaOnDisable) {
            alphaForIsEnable = enabled ? mNormalAlpha : mDisabledAlpha;
        } else {
            alphaForIsEnable = mNormalAlpha;
        }
        if (current != target && target.isEnabled() != enabled) {
            target.setEnabled(enabled);
        }
        target.setAlpha(alphaForIsEnable);
        if (enabled) {
            target.animate()
                    .scaleX(mNormalScale)
                    .scaleY(mNormalScale)
                    .setDuration(150)
                    .setInterpolator(XInterpolators.linear)
                    .start();
        } else {
            if (isChangeScaleOnDisable) {
                target.animate()
                        .scaleX(mDisabledScale)
                        .scaleY(mDisabledScale)
                        .setDuration(150)
                        .setInterpolator(XInterpolators.linear)
                        .start();
            }
        }
    }

    @Override
    public void setNormalAlpha(float alpha) {
        this.mNormalAlpha = alpha;
        isChangeAlphaOnPress = mPressedAlpha != mNormalAlpha;
        isChangeAlphaOnDisable = mDisabledAlpha != mNormalAlpha;
        View target = mOwner.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }

    @Override
    public void setAlphaOnPressed(float alpha) {
        this.mPressedAlpha = alpha;
        isChangeAlphaOnPress = mPressedAlpha != mNormalAlpha;
    }

    @Override
    public void setAlphaOnDisabled(float alpha) {
        this.mDisabledAlpha = alpha;
        isChangeAlphaOnDisable = mDisabledAlpha != mNormalAlpha;
        View target = mOwner.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }

    @Override
    public void setNormalScale(float scaleRate) {
        this.mNormalScale = scaleRate;
        isChangeScaleOnPress = mNormalScale != mPressedScale;
        isChangeScaleOnDisable = mNormalScale != mDisabledScale;
        View target = mOwner.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }

    @Override
    public void setScaleOnPressed(float scaleRate) {
        this.mPressedScale = scaleRate;
        isChangeScaleOnPress = mNormalScale != scaleRate;
    }

    @Override
    public void setScaleOnDisabled(float scaleRate) {
        this.mDisabledScale = scaleRate;
        isChangeScaleOnDisable = mDisabledScale != scaleRate;
        View target = mOwner.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }
}
