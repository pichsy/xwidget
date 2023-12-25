package com.pichs.xwidget.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.cardview.XCardConstraintLayout;
import com.pichs.xwidget.cardview.XCardLinearLayout;
import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.utils.XRadioGroupHelper;

public class XCardLinearRadioGroup extends XCardLinearLayout {

    private final XRadioGroupHelper mRadioGroupHelper;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        if (mRadioGroupHelper != null) {
            mRadioGroupHelper.setOnCheckedChangeListener(onCheckedChangeListener);
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
}
