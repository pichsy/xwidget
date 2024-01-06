package com.pichs.xwidget.radiogroup;

import android.view.View;

public interface OnRadioCheckedListener {
    void onCheckedChanged(XRadioGroup group, View checkedView, boolean isChecked, int position);
}
