package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XITextView;

import java.lang.ref.WeakReference;

/**
 * 字体颜色选择器帮助类
 */
public class XTextViewHelper implements XITextView {

    private final WeakReference<TextView> mOwner;
    private int normalColor;
    private int pressedColor;
    private int checkedColor;
    private int unEnabledColor;
    private int activatedColor;

    public XTextViewHelper(Context context, AttributeSet attrs, int defAttr, TextView owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XTextViewHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, TextView owner) {
        mOwner = new WeakReference<>(owner);
        init(context, attrs, defAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defAttr, int defStyleRes) {
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XITextView, defAttr, defStyleRes);
            normalColor = ta.getColor(R.styleable.XITextView_android_textColor, Color.LTGRAY);
            pressedColor = ta.getColor(R.styleable.XITextView_xp_pressedTextColor, Color.LTGRAY);
            checkedColor = ta.getColor(R.styleable.XITextView_xp_checkedTextColor, Color.LTGRAY);
            unEnabledColor = ta.getColor(R.styleable.XITextView_xp_unEnabledTextColor, Color.LTGRAY);
            activatedColor = ta.getColor(R.styleable.XITextView_xp_activatedTextColor, Color.LTGRAY);
            ta.recycle();
            setSelector();
        }
    }

    private void setSelector() {
        if (mOwner.get() != null) {
            if (pressedColor == 0 && checkedColor == 0 && unEnabledColor == 0 && activatedColor == 0) {
                mOwner.get().setTextColor(normalColor);
            } else {
                XGradientHelper.ColorStateListBuilder builder = new XGradientHelper.ColorStateListBuilder();
                if (pressedColor != 0) {
                    builder.addPressedColor(pressedColor);
                }
                if (unEnabledColor != 0) {
                    builder.addPressedColor(unEnabledColor);
                }
                if (activatedColor != 0) {
                    builder.addPressedColor(activatedColor);
                }
                if (checkedColor != 0) {
                    builder.addPressedColor(checkedColor);
                }
                mOwner.get().setTextColor(builder.build());
            }
        }
    }

    @Override
    public void setNormalTextColor(int color) {
        normalColor = color;
        setSelector();
    }

    @Override
    public void setPressedTextColor(int color) {
        pressedColor = color;
        setSelector();
    }

    @Override
    public void setCheckedTextColor(int color) {
        checkedColor = color;
        setSelector();
    }

    @Override
    public void setActivatedTextColor(int color) {
        activatedColor = color;
        setSelector();
    }

    @Override
    public void setUnEnabledTextColor(int color) {
        unEnabledColor = color;
        setSelector();
    }

    /**
     * 清除所有字体状态的颜色，设置为普通色
     */
    @Override
    public void clearTextStateColor() {
        pressedColor = 0;
        unEnabledColor = 0;
        checkedColor = 0;
        activatedColor = 0;
        setSelector();
    }
}
