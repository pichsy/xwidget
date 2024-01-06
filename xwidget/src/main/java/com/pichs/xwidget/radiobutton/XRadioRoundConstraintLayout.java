package com.pichs.xwidget.radiobutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.Nullable;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.roundview.XRoundConstraintLayout;

public class XRadioRoundConstraintLayout extends XRoundConstraintLayout implements XRadioButton {

    private final XRadioItemHelper mRadioItemHelper;

    public XRadioRoundConstraintLayout(Context context) {
        this(context, null);
    }

    public XRadioRoundConstraintLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRadioRoundConstraintLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadioItemHelper = new XRadioItemHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Checkable owner) {
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
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (mRadioItemHelper != null) mRadioItemHelper.onViewRemoved(view);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (mRadioItemHelper != null) mRadioItemHelper.onViewAdded(view);
    }

    @Override
    public boolean isCheckedByClickEnable() {
        return mRadioItemHelper != null && mRadioItemHelper.isCheckedByClickEnable();
    }

}
