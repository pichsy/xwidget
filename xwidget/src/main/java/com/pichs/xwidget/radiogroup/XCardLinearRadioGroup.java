package com.pichs.xwidget.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.cardview.XCardLinearLayout;

public class XCardLinearRadioGroup extends XCardLinearLayout implements XRadioGroup {

    private final XRadioGroupHelper mRadioGroupHelper;


    @Override
    public void setOnRadioCheckedListener(OnRadioCheckedListener onRadioCheckedListener) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.setOnRadioCheckedListener(onRadioCheckedListener);
        }
    }
    public XCardLinearRadioGroup(Context context) {
        this(context, null);
    }

    public XCardLinearRadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCardLinearRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    public View getSelectedView() {
        if (mRadioGroupHelper != null) {
            return mRadioGroupHelper.getSelectedView();
        }
        return null;
    }

    @Override
    public int getSelectedPosition() {
        if (mRadioGroupHelper != null) {
            return mRadioGroupHelper.getSelectedPosition();
        }
        return -1;
    }
}
