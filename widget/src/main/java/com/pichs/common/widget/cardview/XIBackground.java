package com.pichs.common.widget.cardview;

import android.graphics.drawable.Drawable;

import com.pichs.common.widget.utils.XBackgroundHelper;

/**
 *
 */
public interface XIBackground {

    /**
     * 正常的背景色，渐变会代替它
     *
     * @param drawable
     */
    void setNormalBackground(Drawable drawable);

    /**
     * 设置背景正常渐变色背景
     *
     * @param starColor   开始颜色
     * @param endColor    结束颜色
     * @param orientation 0 横向（默认）， 1 竖向
     */
    void setBackgroundGradient(int starColor, int endColor, @GradientOrientation int orientation);


    /**
     * 按压背景效果
     */
    void setPressedBackground(Drawable pressedBackground);

    // 渐变色
    void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 不可用背景效果
     */
    void setUnEnabledBackground(Drawable unEnabledBackground);

    // 渐变色
    void setUnEnabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 选中背景效果
     */
    void setCheckedBackground(Drawable checkedBackground);

    // 渐变色
    void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * activated=true效果
     */
    void setActivatedBackground(Drawable activateBackground);

    // 渐变色
    void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);


    XBackgroundHelper clearBackgrounds();
}
