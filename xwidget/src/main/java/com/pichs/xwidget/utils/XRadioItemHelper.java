package com.pichs.xwidget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.R;
import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.radiobutton.XRadioButtonCheckable;

import java.lang.ref.WeakReference;

public class XRadioItemHelper implements View.OnClickListener, XRadioButtonCheckable {

    private XRadioItemHelper() {
    }

    private WeakReference<View> mOwner;

    public XRadioItemHelper(Context context, AttributeSet attr, int defStyleAttr, View owner) {
        init(context, attr, defStyleAttr, owner);
    }

    public void init(Context context, AttributeSet attr, int defStyleAttr, View owner) {
        mOwner = new WeakReference<>(owner);
        TypedArray ta = context.obtainStyledAttributes(attr, R.styleable.XRadioItem, defStyleAttr, 0);
        isIgnoreRadioGroup = ta.getBoolean(R.styleable.XRadioItem_xp_ignore_radio_group, false);
        ta.recycle();
    }

    private boolean isRadioButtonMode = false;
    private boolean isIgnoreRadioGroup = false;

    @Override
    public boolean isIgnoreRadioGroup() {
        return isIgnoreRadioGroup;
    }

    @Override
    public void setIgnoreRadioGroup(boolean isIgnoreRadioGroup) {
        this.isIgnoreRadioGroup = isIgnoreRadioGroup;
        if (isIgnoreRadioGroup) {
            setRadioButtonMode(false);
        }
    }

    @Override
    public boolean isRadioButtonMode() {
        return isRadioButtonMode;
    }

    @Override
    public void setRadioButtonMode(boolean isRadioButtonMode) {
        if (isIgnoreRadioGroup) {
            return;
        }
        this.isRadioButtonMode = isRadioButtonMode;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChangeListener != null && !isIgnoreRadioGroup) {
            mChangeListener.onCheckedChanged(mOwner.get(), checked);
        }
    }

    @Override
    public boolean isChecked() {
        if (mOwner.get() instanceof XRadioButtonCheckable) {
            return ((XRadioButtonCheckable) mOwner.get()).isChecked();
        }
        return false;
    }

    @Override
    public void toggle() {
        if (mOwner.get() instanceof XRadioButtonCheckable) {
            ((XRadioButtonCheckable) mOwner.get()).toggle();
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        if (mOwner != null && mOwner.get() != null) {
            mOwner.get().setOnClickListener(listener);
        }
    }

    private OnCheckedChangeListener mChangeListener;

    /**
     * 选中时间监听
     *
     * @param listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override
    public void onClick(View v) {
        toggle();
    }

}
