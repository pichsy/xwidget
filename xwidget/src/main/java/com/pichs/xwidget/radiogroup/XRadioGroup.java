package com.pichs.xwidget.radiogroup;

import android.view.View;

public interface XRadioGroup {
    void select(int position);

    void select(View child);

    void setOnRadioCheckedListener(OnRadioCheckedListener onCheckedChangeListener);
}
