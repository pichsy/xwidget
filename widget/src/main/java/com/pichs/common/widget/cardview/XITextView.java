package com.pichs.common.widget.cardview;

/**
 * 字体颜色选择器
 */
public interface XITextView {

    /**
     * 设置字体颜色--普通状态
     */
    void setNormalTextColor(int color);

    /**
     * 文本按压 颜色
     */
    void setPressedTextColor(int color);

    /**
     * 文本选中 颜色
     */
    void setCheckedTextColor(int color);

    /**
     * 文本 活跃 颜色
     */
    void setActivatedTextColor(int color);

    /**
     * 文本 不可用 颜色
     */
    void setUnEnabledTextColor(int color);

    /**
     * 清除textView的状态颜色
     */
    void clearTextStateColor();

    /**
     * 是否忽略全局字体
     */
    void setIgnoreGlobalTypeface(boolean isIgnoreGlobalTypeface);
}
