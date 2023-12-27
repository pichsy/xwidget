package com.pichs.xwidget.radiogroup;

import android.view.View;

/**
 * 单选框组接口
 */
public interface XRadioGroup {
    /**
     * 选中某个位置
     *
     * @param position 位置
     */
    void select(int position);

    /**
     * 选中某个view
     *
     * @param child view
     */
    void select(View child);

    /**
     * 选中监听
     *
     * @param onCheckedChangeListener onCheckedChangeListener
     */
    void setOnRadioCheckedListener(OnRadioCheckedListener onCheckedChangeListener);
}
