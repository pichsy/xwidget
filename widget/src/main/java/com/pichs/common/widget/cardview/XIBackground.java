package com.pichs.common.widget.cardview;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.utils.XBackgroundHelper;

/**
 * 背景接口
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
     * 设置正常背景渐变色开始颜色
     * @param startColor startColor
     */
    void setBackgroundGradientStartColor(int startColor);

    /**
     * 设置正常背景渐变色结束颜色
     * @param endColor endColor
     */
    void setBackgroundGradientEndColor(int endColor);

    /**
     * 按压背景效果
     */
    void setPressedBackground(Drawable pressedBackground);

    // 渐变色
    void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置按压背景渐变色开始颜色
     * @param startColor startColor
     */
    void setPressedBackgroundGradientStartColor(int startColor);

    /**
     * 设置按压背景渐变色结束颜色
     * @param endColor endColor
     */
    void setPressedBackgroundGradientEndColor(int endColor);

    /**
     * 不可用背景效果
     */
    void setDisabledBackground(Drawable disabledBackground);

    // 渐变色
    void setDisabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置不可用背景渐变色开始颜色
     * @param startColor startColor
     */
    void setDisabledBackgroundGradientStartColor(int startColor);

    /**
     * 设置不可用背景渐变色结束颜色
     * @param endColor endColor
     */
    void setDisabledBackgroundGradientEndColor(int endColor);

    /**
     * 选中背景效果
     */
    void setCheckedBackground(Drawable checkedBackground);

    // 渐变色
    void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置选中用背景渐变色开始颜色
     * @param startColor startColor
     */
    void setCheckedBackgroundGradientStartColor(int startColor);

    /**
     * 设置选中用背景渐变色结束颜色
     * @param endColor endColor
     */
    void setCheckedBackgroundGradientEndColor(int endColor);

    /**
     * activated=true效果
     */
    void setActivatedBackground(Drawable activateBackground);

    // 渐变色
    void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置选中用背景渐变色开始颜色
     * @param startColor startColor
     */
    void setActivatedBackgroundGradientStartColor(int startColor);

    /**
     * 设置选中用背景渐变色结束颜色
     * @param endColor endColor
     */
    void setActivatedBackgroundGradientEndColor(int endColor);

    /**
     * 动态改变背景系列颜色值
     *
     * @param color
     */
    void setNormalBackgroundColor(@ColorInt int color);

    void setPressedBackgroundColor(@ColorInt int color);

    void setActivatedBackgroundColor(@ColorInt int color);

    void setCheckedBackgroundColor(@ColorInt int color);

    void setDisabledBackgroundColor(@ColorInt int color);

    XBackgroundHelper clearBackgrounds();
}
