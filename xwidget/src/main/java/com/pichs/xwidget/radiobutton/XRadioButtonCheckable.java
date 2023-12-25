package com.pichs.xwidget.radiobutton;

import android.view.View;

/**
 * 单选框接口
 */
public interface XRadioButtonCheckable {

    /**
     * 是否忽略RadioGroup
     */
    boolean isIgnoreRadioGroup();

    /**
     * 设置是否忽略RadioGroup
     */
    void setIgnoreRadioGroup(boolean isIgnoreRadioGroup);

    /**
     * 是否是单选模式
     */
    boolean isRadioButtonMode();

    /**
     * 设置是否是单选模式
     */
    void setRadioButtonMode(boolean isRadioButtonMode);

    /**
     * 选中状态改变
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
}
