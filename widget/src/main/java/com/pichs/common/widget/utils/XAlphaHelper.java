package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XIAlpha;

import java.lang.ref.WeakReference;

public class XAlphaHelper implements XIAlpha {

    private final WeakReference<View> mOwner;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean isChangeAlphaOnPress = true;

    /**
     * 设置是否要在 disabled 时改变透明度
     */
    private boolean isChangeAlphaOnDisable = true;

    private float mNormalAlpha = 1f;
    private float mPressedAlpha = .7f;
    private float mDisabledAlpha = .7f;


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
            ta.recycle();
            if (mPressedAlpha == 1f) {
                isChangeAlphaOnPress = false;
            }
            if (mDisabledAlpha == 1f) {
                isChangeAlphaOnDisable = false;
            }
        }

    }


    public void onPressedChanged(View current, boolean pressed) {
        View target = mOwner.get();
        if (target == null) {
            return;
        }
        if (current.isEnabled()) {
            target.setAlpha(isChangeAlphaOnPress && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
        } else {
            if (isChangeAlphaOnDisable) {
                target.setAlpha(mDisabledAlpha);
            }
        }
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
    }


    @Override
    public void setChangeAlphaOnPressed(boolean isChangeAlphaOnPressed) {
        isChangeAlphaOnPress = isChangeAlphaOnPressed;
    }

    @Override
    public void setChangeAlphaOnDisabled(boolean isChangeAlphaOnDisabled) {
        isChangeAlphaOnDisable = isChangeAlphaOnDisabled;
        View target = mOwner.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }
}
