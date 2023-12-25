package com.pichs.xwidget.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.radiobutton.XRadioButtonCheckable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;

public class XRadioGroupHelper implements View.OnClickListener {
    private XRadioGroupHelper() {
    }

    private WeakReference<ViewGroup> mOwner;
    private final ArrayList<XRadioButtonCheckable> mXCheckBoxList = new ArrayList<>();
    private OnCheckedChangeListener mOnCheckedChangeListener = null;

    private View mCheckedView = null;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public XRadioGroupHelper(Context context, AttributeSet attrs, int defStyleAttr, ViewGroup owner) {
        mOwner = new WeakReference<>(owner);
//        if (getChildCount() == 0) {
//            return;
//        }
//        for (int i = 0; i < getChildCount(); i++) {
//            if (getChildAt(i) instanceof XRadioButtonCheckable) {
//                XRadioButtonCheckable child = (XRadioButtonCheckable) getChildAt(i);
//                child.setRadioButtonMode(true);
//                mXCheckBoxList.add(child);
//                child.setOnClickListener(this);
//            }
//        }
    }

    public void onViewAdded(View child) {
        if (child instanceof XRadioButtonCheckable) {
            XRadioButtonCheckable radioButton = (XRadioButtonCheckable) child;
            if (!mXCheckBoxList.contains(radioButton)) {
                if (!radioButton.isIgnoreRadioGroup()) {
                    radioButton.setRadioButtonMode(true);
                    mXCheckBoxList.add(radioButton);
                    radioButton.setOnClickListener(this);
                }
            }
        }
    }

    public void onViewRemoved(View child) {
        if (child instanceof XRadioButtonCheckable) {
            XRadioButtonCheckable radioButton = (XRadioButtonCheckable) child;
            if (mXCheckBoxList.contains(radioButton)) {
                if (!radioButton.isIgnoreRadioGroup()) {
                    mXCheckBoxList.remove(radioButton);
                    radioButton.setOnClickListener(null);
                }
            }
        }
    }

    public int getChildCount() {
        if (mOwner == null || mOwner.get() == null) {
            return 0;
        }
        return mOwner.get().getChildCount();
    }

    public View getChildAt(int index) {
        if (mOwner == null || mOwner.get() == null) {
            return null;
        }
        return mOwner.get().getChildAt(index);
    }


    public <T extends View> void onCheckedChanged(T view, boolean isChecked) {
        if (isChecked && mCheckedView == view) {
            return;
        }
        // 如果是取消选中，必须要有一个选中的
//        if (!isChecked && mCheckedView == view) {
//            if (view instanceof XRadioButtonCheckable) {
//                ((XRadioButtonCheckable) view).setChecked(true);
//            }
//            return;
//        }
        mCheckedView = view;
        if (isChecked) {
            for (XRadioButtonCheckable child : mXCheckBoxList) {
                if (child != view) {
                    child.setChecked(false);
                }
            }
        }
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(view, isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof XRadioButtonCheckable) {
            XRadioButtonCheckable xCheckBox = (XRadioButtonCheckable) v;
            xCheckBox.setChecked(true);
            onCheckedChanged(v, xCheckBox.isChecked());
        }
    }

    public void select(int index) {
        if (index < 0 || index >= mXCheckBoxList.size()) {
            return;
        }
        XRadioButtonCheckable item = mXCheckBoxList.get(index);
        if (item != null) {
            onClick((View) item);
        }
    }

    public void select(View child) {
        if (mXCheckBoxList.isEmpty()) {
            return;
        }
        if (child == null) {
            return;
        }
        if (child instanceof XRadioButtonCheckable && mXCheckBoxList.contains(child)) {
            onClick(child);
        }
    }
}
