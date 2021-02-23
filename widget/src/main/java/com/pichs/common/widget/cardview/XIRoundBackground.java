package com.pichs.common.widget.cardview;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.utils.XRoundBackgroundHelper;

/**
 *
 */
public interface XIRoundBackground {

    /**
     * 全部圆角
     *
     * @param radius radius
     */
    void setRadius(int radius);

    /**
     * 四个圆角,单独的
     *
     * @param topLeftRadius topLeftRadius
     */
    void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius);

    /**
     * @param borderWidth borderWidth 边框宽度
     * @param borderColor borderColor 边框颜色
     */
    void setBorder(@ColorInt int borderColor, int borderWidth);

    /**
     * 按压时边框颜色
     *
     * @param pressedBorderColor pressedBorderColor
     */
    void setPressedBorderColor(@ColorInt int pressedBorderColor);

    /**
     * 不可用边框颜色
     *
     * @param unEnabledBorderColor unEnabledBorderColor
     */
    void setUnEnabledBorderColor(@ColorInt int unEnabledBorderColor);

    /**
     * 活跃边框颜色
     *
     * @param activatedBorderColor activatedBorderColor
     */
    void setActivatedBorderColor(@ColorInt int activatedBorderColor);

    /**
     * 选中边框颜色
     *
     * @param checkedBorderColor checkedBorderColor
     */
    void setCheckedBorderColor(@ColorInt int checkedBorderColor);


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


    XRoundBackgroundHelper clearBackgrounds();
}
