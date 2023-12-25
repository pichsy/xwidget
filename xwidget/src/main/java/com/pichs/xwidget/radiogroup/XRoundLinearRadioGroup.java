package com.pichs.xwidget.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.roundview.XRoundConstraintLayout;
import com.pichs.xwidget.roundview.XRoundLinearLayout;
import com.pichs.xwidget.utils.XRadioGroupHelper;

public class XRoundLinearRadioGroup extends XRoundLinearLayout {

    private final XRadioGroupHelper mRadioGroupHelper;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    public XRoundLinearRadioGroup(Context context) {
        this(context, null);
    }

    public XRoundLinearRadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRoundLinearRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
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
}
