package com.pichs.xwidget.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.pichs.xwidget.radiobutton.XRadioButton;
import com.pichs.xwidget.radiogroup.OnRadioCheckedListener;
import com.pichs.xwidget.radiogroup.XRadioGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class XRadioGroupHelper implements XRadioGroup, View.OnClickListener {
    private XRadioGroupHelper() {
    }

    private WeakReference<ViewGroup> mOwner;
    private final ArrayList<XRadioButton> mXCheckBoxList = new ArrayList<>();
    private final HashMap<XRadioButton, ArrayList<XRadioButton>> mXCheckBoxMap = new HashMap<>();

    private OnRadioCheckedListener mOnRadioCheckedListener = null;

    private View mCheckedView = null;

    @Override
    public void setOnRadioCheckedListener(OnRadioCheckedListener onCheckedChangeListener) {
        mOnRadioCheckedListener = onCheckedChangeListener;
    }

    public XRadioGroupHelper(Context context, AttributeSet attrs, int defStyleAttr, ViewGroup owner) {
        mOwner = new WeakReference<>(owner);
    }

    public void onViewAdded(View child) {
        if (child instanceof XRadioButton) {
            XRadioButton radioButton = (XRadioButton) child;
            if (!mXCheckBoxList.contains(radioButton)) {
                if (!radioButton.isIgnoreRadioGroup()) {
                    mXCheckBoxList.add(radioButton);
                    if (radioButton.isChecked()) {
                        if (mCheckedView != null) {
                            radioButton.setChecked(false);
                        } else {
                            mCheckedView = child;
                        }
                    }
                    radioButton.setOnClickListener(this);
                }
            }
        }
    }

    public void onViewRemoved(View child) {
        if (child instanceof XRadioButton) {
            XRadioButton radioButton = (XRadioButton) child;
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


    public void onCheckedChanged(View view, boolean isChecked) {
        if (isChecked && mCheckedView == view) {
            return;
        }
        mCheckedView = view;
        if (isChecked) {
            for (XRadioButton child : mXCheckBoxList) {
                if (child != view) {
                    child.setChecked(false);
                }
            }
        }
        if (mOnRadioCheckedListener != null) {
            mOnRadioCheckedListener.onCheckedChanged((XRadioGroup) mOwner.get(), view, isChecked, indexOf(view));
        }
    }

    private int indexOf(XRadioButton child) {
        return mXCheckBoxList.indexOf(child);
    }

    private int indexOf(View child) {
        if (child instanceof XRadioButton) {
            return mXCheckBoxList.indexOf(child);
        }
        return -1;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof XRadioButton) {
            XRadioButton xCheckBox = (XRadioButton) v;
            xCheckBox.setChecked(true);
            onCheckedChanged(v, xCheckBox.isChecked());
        }
    }

    @Override
    public void select(int position) {
        if (position < 0 || position >= mXCheckBoxList.size()) {
            return;
        }
        XRadioButton item = mXCheckBoxList.get(position);
        if (item != null) {
            onClick((View) item);
        }
    }

    @Override
    public void select(View child) {
        if (mXCheckBoxList.isEmpty()) {
            return;
        }
        if (child == null) {
            return;
        }
        if (child instanceof XRadioButton && mXCheckBoxList.contains(child)) {
            onClick(child);
        }
    }
}
