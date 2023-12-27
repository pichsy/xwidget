package com.pichs.xwidget.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.roundview.XRoundConstraintLayout;
import com.pichs.xwidget.utils.XRadioGroupHelper;

public class XRoundConstraintRadioGroup extends XRoundConstraintLayout implements XRadioGroup {

    private final XRadioGroupHelper mRadioGroupHelper;


    @Override
    public void setOnRadioCheckedListener(OnRadioCheckedListener onRadioCheckedListener) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.setOnRadioCheckedListener(onRadioCheckedListener);
        }
    }
    public XRoundConstraintRadioGroup(Context context) {
        this(context, null);
    }

    public XRoundConstraintRadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRoundConstraintRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadioGroupHelper = new XRadioGroupHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.onViewAdded(view);
        }
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.onViewRemoved(view);
        }
    }

    @Override
    public void select(int position) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.select(position);
        }
    }

    @Override
    public void select(View child) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.select(child);
        }
    }
}
