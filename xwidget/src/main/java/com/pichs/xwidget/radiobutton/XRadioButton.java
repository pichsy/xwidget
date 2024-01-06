package com.pichs.xwidget.radiobutton;

import android.view.View;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;

/**
 * 单选框接口
 */
public interface XRadioButton {

    /**
     * 是否忽略RadioGroup
     */
    boolean isIgnoreRadioGroup();

    /**
     * 设置是否忽略RadioGroup
     *
     * @param isIgnoreRadioGroup 是否忽略RadioGroup
     */
    void setIgnoreRadioGroup(boolean isIgnoreRadioGroup);

    /**
     * 选中状态改变
     *
     * @param checked 是否选中
     */
    void setChecked(boolean checked);

    /**
     * @return 选中状态
     */
    boolean isChecked();

    /**
     * 切换选中状态
     */
    void toggle();

    /**
     * 选中时间监听
     *
     * @param listener listener
     */
    void setOnClickListener(View.OnClickListener listener);

    /**
     * 选中时间监听
     *
     * @param listener listener
     */
    default void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
    }

    /**
     * 是否跟随父类的选中状态
     *
     * @return 是否跟随父类的选中状态
     */
    boolean isCheckStateFollowParent();

    /**
     * 是否可以监听
     */
    boolean isCheckedByClickEnable();

}
