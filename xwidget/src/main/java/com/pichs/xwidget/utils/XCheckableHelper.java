package com.pichs.xwidget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.pichs.xwidget.R;

public class XCheckableHelper {

    public static void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        if (context == null) {
            return;
        }
        if (null != attrs || defStyleAttr != 0) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XICheckable, defStyleAttr, defStyleRes);
            boolean isChecked = typedArray.getBoolean(R.styleable.XICheckable_android_checked, false);
            boolean isActivated = typedArray.getBoolean(R.styleable.XICheckable_xp_activated, false);
            typedArray.recycle();
            if (isChecked && owner instanceof Checkable) {
                ((Checkable) owner).setChecked(true);
            }
            if (isActivated) {
                owner.setActivated(true);
            }
        }
    }
}
