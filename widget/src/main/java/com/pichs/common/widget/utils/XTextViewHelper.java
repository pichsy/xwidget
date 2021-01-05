package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
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
    private int normalColor = Color.DKGRAY;
    private int pressedColor = 0;
    private int checkedColor = 0;
    private int unEnabledColor = 0;
    private int activatedColor = 0;
    // 是否忽略全局字体更换
    private boolean isIgnoreGlobalTypeface = false;
    // 初始化时的字体类型。
    private Typeface mInitTypeface = Typeface.DEFAULT;
    // 初始化时的字体样式。
    private int mInitTypefaceStyle = Typeface.NORMAL;

    public XTextViewHelper(Context context, AttributeSet attrs, int defAttr, TextView owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XTextViewHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, TextView owner) {
        mOwner = new WeakReference<>(owner);
        init(context, attrs, defAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defAttr, int defStyleRes) {
        if (mOwner != null && mOwner.get() != null) {
            mInitTypeface = mOwner.get().getTypeface() == null ? Typeface.DEFAULT : mOwner.get().getTypeface();
            mInitTypefaceStyle = mInitTypeface.getStyle();
        }
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XITextView, defAttr, defStyleRes);
            normalColor = ta.getColor(R.styleable.XITextView_android_textColor, Color.DKGRAY);
            pressedColor = ta.getColor(R.styleable.XITextView_xp_pressedTextColor, 0);
            checkedColor = ta.getColor(R.styleable.XITextView_xp_checkedTextColor, 0);
            unEnabledColor = ta.getColor(R.styleable.XITextView_xp_unEnabledTextColor, 0);
            activatedColor = ta.getColor(R.styleable.XITextView_xp_activatedTextColor, 0);
            isIgnoreGlobalTypeface = ta.getBoolean(R.styleable.XITextView_xp_ignoreGlobalTypeface, false);
            ta.recycle();
            setSelector();
        }
        setTypeface();
    }

    private void setTypeface() {
        if (mOwner.get() != null) {
            if (isIgnoreGlobalTypeface) {
                XTypefaceHelper.removeObserver(mOwner.get());
                mOwner.get().setTypeface(mInitTypeface, mInitTypefaceStyle);
                return;
            }
            XTypefaceHelper.observer(mOwner.get(), (typeface, typefaceStyle) -> {
                int style = typefaceStyle;
                if (typefaceStyle == XTypefaceHelper.NONE) {
                    style = mInitTypefaceStyle;
                }
                mOwner.get().setTypeface(typeface, style);
            });
        }
    }

    /**
     * 忽略移除或者添加
     *
     * @param isIgnoreGlobalTypeface 是否忽略全局的字体变化监听
     */
    @Override
    public void setIgnoreGlobalTypeface(boolean isIgnoreGlobalTypeface) {
        this.isIgnoreGlobalTypeface = isIgnoreGlobalTypeface;
        setTypeface();
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
                builder.setUnSateColor(normalColor);
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
