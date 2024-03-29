package com.pichs.xwidget.cardview;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

import com.pichs.xwidget.roundview.XCubeSidesHeight;
import com.pichs.xwidget.utils.XRoundBackgroundHelper;

/**
 * Round系列背景接口
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
     * 边框颜色
     *
     * @param borderColor 颜色 borderColor
     */
    void setBorderColor(@ColorInt int borderColor);

    /**
     * 边框宽度
     *
     * @param borderWidth borderWidth
     */
    void setBorderWidth(int borderWidth);

    /**
     * 按压时边框颜色
     *
     * @param pressedBorderColor pressedBorderColor
     */
    void setPressedBorderColor(@ColorInt int pressedBorderColor);

    /**
     * 不可用边框颜色
     *
     * @param disabledBorderColor disabledBorderColor
     */
    void setDisabledBorderColor(@ColorInt int disabledBorderColor);

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
     * 设置正常背景渐变色开始颜色
     *
     * @param startColor startColor
     */
    void setBackgroundGradientStartColor(int startColor);

    /**
     * 设置正常背景渐变色结束颜色
     *
     * @param endColor endColor
     */
    void setBackgroundGradientEndColor(int endColor);

    /**
     * 按压背景效果
     */
    void setPressedBackground(Drawable pressedBackground);

    /**
     * 按压背景效果
     * 渐变色
     *
     * @param startColor  开始颜色
     * @param endColor    结束颜色
     * @param orientation 0 横向（默认）， 1 竖向
     */
    void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置按压背景渐变色开始颜色
     *
     * @param startColor startColor
     */
    void setPressedBackgroundGradientStartColor(int startColor);

    /**
     * 设置按压背景渐变色结束颜色
     *
     * @param endColor endColor
     */
    void setPressedBackgroundGradientEndColor(int endColor);

    /**
     * 不可用背景效果
     */
    void setDisabledBackground(Drawable disabledBackground);

    /**
     * 不可用背景效果
     * 渐变色
     *
     * @param startColor  开始颜色
     * @param endColor    结束颜色
     * @param orientation 0 横向（默认）， 1 竖向
     */
    void setDisabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置不可用背景渐变色开始颜色
     *
     * @param startColor startColor
     */
    void setDisabledBackgroundGradientStartColor(int startColor);

    /**
     * 设置不可用背景渐变色结束颜色
     *
     * @param endColor endColor
     */
    void setDisabledBackgroundGradientEndColor(int endColor);

    /**
     * 选中背景效果
     */
    void setCheckedBackground(Drawable checkedBackground);

    /**
     * 选中背景效果
     * 渐变色
     *
     * @param startColor  开始颜色
     * @param endColor    结束颜色
     * @param orientation 0 横向（默认）， 1 竖向
     */
    void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置选中用背景渐变色开始颜色
     *
     * @param startColor startColor
     */
    void setCheckedBackgroundGradientStartColor(int startColor);

    /**
     * 设置选中用背景渐变色结束颜色
     *
     * @param endColor endColor
     */
    void setCheckedBackgroundGradientEndColor(int endColor);

    /**
     * activated=true效果
     */
    void setActivatedBackground(Drawable activateBackground);

    /**
     * activated=true效果
     * 渐变色
     *
     * @param startColor  开始颜色
     * @param endColor    结束颜色
     * @param orientation 0 横向（默认）， 1 竖向
     */
    void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation);

    /**
     * 设置选中用背景渐变色开始颜色
     *
     * @param startColor startColor
     */
    void setActivatedBackgroundGradientStartColor(int startColor);

    /**
     * 设置选中用背景渐变色结束颜色
     *
     * @param endColor endColor
     */
    void setActivatedBackgroundGradientEndColor(int endColor);

    /**
     * 设置普通背景色
     *
     * @param color 颜色值数组
     */
    void setNormalBackgroundColor(@ColorInt int color);

    /**
     * 设置正常背景渐变色开始颜色
     *
     * @param color setPressedBackgroundColor
     */
    void setPressedBackgroundColor(@ColorInt int color);

    /**
     * 设置正常背景渐变色结束颜色
     *
     * @param color setActivatedBackgroundColor
     */
    void setActivatedBackgroundColor(@ColorInt int color);

    /**
     * 设置正常背景渐变色结束颜色
     *
     * @param color setCheckedBackgroundColor
     */
    void setCheckedBackgroundColor(@ColorInt int color);

    /**
     * 设置正常背景渐变色结束颜色
     *
     * @param color setDisabledBackgroundColor
     */
    void setDisabledBackgroundColor(@ColorInt int color);


    /**
     * 动态改变背景系列颜色值
     *
     * @param colors 颜色值数组
     */
    void setBackgroundGradientColors(@ColorInt int[] colors, @GradientOrientation int orientation);

    /**
     * 动态改变背景系列颜色值
     *
     * @return 颜色值数组
     */
    @GradientOrientation
    int getBackgroundGradientOrientation();

    /**
     * 动态改变背景系列颜色值
     *
     * @param colors      颜色值数组
     * @param orientation 渐变方向
     */
    void setPressedBackgroundGradientColors(@ColorInt int[] colors, @GradientOrientation int orientation);

    /**
     * 动态改变背景系列颜色值
     *
     * @return 颜色值数组
     */
    @GradientOrientation
    int getPressedBackgroundGradientOrientation();

    /**
     * 动态改变背景系列颜色值
     *
     * @param colors      颜色值数组
     * @param orientation 渐变方向
     */
    void setActivatedBackgroundGradientColors(@ColorInt int[] colors, @GradientOrientation int orientation);

    /**
     * 动态改变背景系列颜色值
     *
     * @return 颜色值数组
     */
    @GradientOrientation
    int getActivatedBackgroundGradientOrientation();

    /**
     * 动态改变背景系列颜色值
     *
     * @param colors      颜色值数组
     * @param orientation 渐变方向
     */
    void setCheckedBackgroundGradientColors(@ColorInt int[] colors, @GradientOrientation int orientation);

    /**
     * 动态改变背景系列颜色值
     *
     * @return 颜色值数组
     */
    @GradientOrientation
    int getCheckedBackgroundGradientOrientation();

    /**
     * 动态改变背景系列颜色值
     *
     * @param colors      颜色值数组
     * @param orientation 渐变方向
     */
    void setDisabledBackgroundGradientColors(@ColorInt int[] colors, @GradientOrientation int orientation);

    @GradientOrientation
    int getDisabledBackgroundGradientOrientation();

    /**
     * Deprecated this,  please use {@link #setCubeSidesGradientColors}
     *
     * @param colors @Deprecated
     */
    @Deprecated
    void setCubeFrontGradientColors(@ColorInt int... colors);

    /**
     * setCubeSidesGradientColors 多色随便加
     *
     * @param colors @Deprecated
     */
    void setCubeSidesGradientColors(@ColorInt int... colors);


    /**
     * 不设置就是NONE 设置为任何数都会生效
     *
     * @param left  default={@link XCubeSidesHeight#NONE}
     * @param back  default={@link XCubeSidesHeight#NONE}
     * @param right default={@link XCubeSidesHeight#NONE}
     * @param front default={@link XCubeSidesHeight#NONE}
     */
    void setCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front);

    /**
     * setCubeSidesBorderColor
     *
     * @param color int
     */
    void setCubeSidesBorderColor(@ColorInt int color);

    /**
     * setCubeSidesBorderWidth
     *
     * @param width int
     */
    void setCubeSidesBorderWidth(int width);

    /**
     * setPressedCubeSidesGradientColors
     *
     * @param colors int
     */
    void setPressedCubeSidesGradientColors(@ColorInt int... colors);

    /**
     * setPressedCubeSidesHeight
     *
     * @param left  int size px
     * @param back  int size px
     * @param right int size px
     * @param front int size px
     */
    void setPressedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front);

    /**
     * setActivatedCubeSidesGradientColors
     *
     * @param colors int
     */
    void setDisabledCubeSidesGradientColors(@ColorInt int... colors);

    /**
     * setDisabledCubeSidesHeight
     *
     * @param left  int size px
     * @param back  int size px
     * @param right int size px
     * @param front int size px
     */
    void setDisabledCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front);

    /**
     * setActivatedCubeSidesGradientColors
     *
     * @param colors int
     */
    void setCheckedCubeSidesGradientColors(@ColorInt int... colors);

    /**
     * setCheckedCubeSidesHeight
     *
     * @param left  int size px
     * @param back  int size px
     * @param right int size px
     * @param front int size px
     */
    void setCheckedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front);

    /**
     * setActivatedCubeSidesGradientColors
     *
     * @param colors int
     */
    void setActivatedCubeSidesGradientColors(@ColorInt int... colors);

    /**
     * setActivatedCubeSidesHeight
     *
     * @param left  int size px
     * @param back  int size px
     * @param right int size px
     * @param front int size px
     */
    void setActivatedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front);

    /**
     * clearBackgrounds
     *
     * @return XRoundBackgroundHelper
     */
    XRoundBackgroundHelper clearBackgrounds();
}
