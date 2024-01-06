package com.pichs.xwidget.radiobutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.roundview.XRoundButton;

public class XRadioRoundButton extends XRoundButton implements XRadioButton {

    private final XRadioItemHelper mRadioItemHelper;

    public XRadioRoundButton(Context context) {
        this(context, null);
    }

    public XRadioRoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRadioRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadioItemHelper = new XRadioItemHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
    }

    @Override
    public boolean isIgnoreRadioGroup() {
        if (mRadioItemHelper == null) return false;
        return mRadioItemHelper.isIgnoreRadioGroup();
    }

    @Override
    public void setIgnoreRadioGroup(boolean isIgnoreRadioGroup) {
        if (mRadioItemHelper == null) return;
        mRadioItemHelper.setIgnoreRadioGroup(isIgnoreRadioGroup);
    }


    @Override
    public void setChecked(boolean checked) {
        if (isChecked() == checked) {
            return;
        }
        super.setChecked(checked);
        if (mRadioItemHelper != null) {
            mRadioItemHelper.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        return super.isChecked();
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (!isCheckStateFollowParent()) {
            super.setOnClickListener(l);
        }
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (mRadioItemHelper != null) mRadioItemHelper.setOnCheckedChangeListener(listener);
    }

    @Override
    public boolean isCheckStateFollowParent() {
        return mRadioItemHelper != null && mRadioItemHelper.isCheckStateFollowParent();
    }

    @Override
    public boolean isCheckedByClickEnable() {
        return mRadioItemHelper != null && mRadioItemHelper.isCheckedByClickEnable();
    }

}
