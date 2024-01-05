package com.pichs.xwidget.utils;

import static com.pichs.xwidget.utils.XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XIRoundBackground;
import com.pichs.xwidget.roundview.XCubeSidesHeight;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 圆角背景帮助类
 */
public class XRoundBackgroundHelper implements XIRoundBackground {

    private final WeakReference<View> mOwner;
    private Drawable background;
    private Drawable pressedBackground;
    private Drawable checkedBackground;
    private Drawable disabledBackground;
    private Drawable activatedBackground;
    // 立体属性
    private Drawable cubeSidesBackground;
    private Drawable pressedCubeSidesBackground;
    private Drawable activatedCubeSidesBackground;
    private Drawable checkedCubeSidesBackground;
    private Drawable disabledCubeSidesBackground;

    // 原始设置的background drawable
    private Drawable backgroundTmp;
    private Drawable pressedBackgroundTmp;
    private Drawable checkedBackgroundTmp;
    private Drawable disabledBackgroundTmp;
    private Drawable activatedBackgroundTmp;

    /**
     * 圆角
     */
    private int radius = 0;
    private int topLeftRadius = 0;
    private int topRightRadius = 0;
    private int bottomLeftRadius = 0;
    private int bottomRightRadius = 0;
    @ColorInt
    private int borderColor = DEFAULT_COLOR_TRANSPARENT;
    @ColorInt
    private int pressedBorderColor = DEFAULT_COLOR_TRANSPARENT;
    @ColorInt
    private int checkedBorderColor = DEFAULT_COLOR_TRANSPARENT;
    @ColorInt
    private int activatedBorderColor = DEFAULT_COLOR_TRANSPARENT;
    @ColorInt
    private int disabledBorderColor = DEFAULT_COLOR_TRANSPARENT;

    private int borderWidth = 0;

    private int bgStartColor = DEFAULT_COLOR_TRANSPARENT;
    private int bgEndColor = DEFAULT_COLOR_TRANSPARENT;
    @GradientOrientation
    private int bgColorOrientation = GradientOrientation.HORIZONTAL;
    private int pressedBgStartColor = DEFAULT_COLOR_TRANSPARENT;
    private int pressedBgEndColor = DEFAULT_COLOR_TRANSPARENT;
    @GradientOrientation
    private int pressedBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int checkedBgStartColor = DEFAULT_COLOR_TRANSPARENT;
    private int checkedBgEndColor = DEFAULT_COLOR_TRANSPARENT;
    @GradientOrientation
    private int checkedBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int disabledBgStartColor = DEFAULT_COLOR_TRANSPARENT;
    private int disabledBgEndColor = DEFAULT_COLOR_TRANSPARENT;
    @GradientOrientation
    private int disabledBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int activatedBgStartColor = DEFAULT_COLOR_TRANSPARENT;
    private int activatedBgEndColor = DEFAULT_COLOR_TRANSPARENT;
    @GradientOrientation
    private int activatedBgColorOrientation = GradientOrientation.HORIZONTAL;

    // 渐变色，列表，支持多色渐变
    private int[] backgroundColors;
    private int[] activatedBackgroundColors;
    private int[] pressedBackgroundColors;
    private int[] checkedBackgroundColors;
    private int[] disabledBackgroundColors;


    // 颜色数组
    private int[] cubeSidesColors;
    private int[] pressedCubeSidesColors;
    private int[] activatedCubeSidesColors;
    private int[] disabledCubeSidesColors;
    private int[] checkedCubeSidesColors;

    // 边框颜色,宽度
    @ColorInt
    // cube- border是通用，不提供单独某个状态设置，基本没人这么用，遇到了，建议xml代码创建
    private int cubeSidesBorderColor = DEFAULT_COLOR_TRANSPARENT;
    private int cubeSidesBorderWidth;
    // 立体按钮默认值 设置为 NONE
    private int cubeFrontHeight = XCubeSidesHeight.NONE;
    private int cubeRightHeight = XCubeSidesHeight.NONE;
    private int cubeLeftHeight = XCubeSidesHeight.NONE;
    private int cubeBackHeight = XCubeSidesHeight.NONE;

    private int pressedCubeFrontHeight = XCubeSidesHeight.NONE;
    private int pressedCubeBackHeight = XCubeSidesHeight.NONE;
    private int pressedCubeLeftHeight = XCubeSidesHeight.NONE;
    private int pressedCubeRightHeight = XCubeSidesHeight.NONE;

    private int activatedCubeFrontHeight = XCubeSidesHeight.NONE;
    private int activatedCubeLeftHeight = XCubeSidesHeight.NONE;
    private int activatedCubeBackHeight = XCubeSidesHeight.NONE;
    private int activatedCubeRightHeight = XCubeSidesHeight.NONE;

    private int checkedCubeFrontHeight = XCubeSidesHeight.NONE;
    private int checkedCubeBackHeight = XCubeSidesHeight.NONE;
    private int checkedCubeLeftHeight = XCubeSidesHeight.NONE;
    private int checkedCubeRightHeight = XCubeSidesHeight.NONE;

    private int disabledCubeFrontHeight = XCubeSidesHeight.NONE;
    private int disabledCubeLeftHeight = XCubeSidesHeight.NONE;
    private int disabledCubeBackHeight = XCubeSidesHeight.NONE;
    private int disabledCubeRightHeight = XCubeSidesHeight.NONE;

    public XRoundBackgroundHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XRoundBackgroundHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, View owner) {
        mOwner = new WeakReference<>(owner);
        init(context, attrs, defAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defAttr, int defStyleRes) {
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XIRoundBackground, defAttr, defStyleRes);
            // 圆角和边框
            radius = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_radius, 0);
            topLeftRadius = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_radiusTopLeft, 0);
            topRightRadius = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_radiusTopRight, 0);
            bottomLeftRadius = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_radiusBottomLeft, 0);
            bottomRightRadius = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_radiusBottomRight, 0);
            borderWidth = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_borderWidth, 0);
            // 边框颜色
            borderColor = ta.getColor(R.styleable.XIRoundBackground_xp_borderColor, borderColor);
            pressedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBorderColor, pressedBorderColor);
            checkedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBorderColor, checkedBorderColor);
            disabledBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBorderColor, disabledBorderColor);
            activatedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBorderColor, activatedBorderColor);
            // 常色
            backgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_android_background);
            pressedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_pressedBackground);
            checkedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_checkedBackground);
            disabledBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_disabledBackground);
            activatedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_activatedBackground);
            // 渐变
            bgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientStartColor, bgStartColor);
            bgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientEndColor, bgEndColor);
            bgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL);
            pressedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundStartColor, pressedBgStartColor);
            pressedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundEndColor, pressedBgEndColor);
            pressedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_pressedBackgroundOrientation, bgColorOrientation);
            checkedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundStartColor, checkedBgStartColor);
            checkedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundEndColor, checkedBgEndColor);
            checkedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_checkedBackgroundOrientation, bgColorOrientation);
            disabledBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBackgroundStartColor, disabledBgStartColor);
            disabledBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBackgroundEndColor, disabledBgEndColor);
            disabledBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_disabledBackgroundOrientation, bgColorOrientation);
            activatedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundStartColor, activatedBgStartColor);
            activatedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundEndColor, activatedBgEndColor);
            activatedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_activatedBackgroundOrientation, bgColorOrientation);

            String backgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_backgroundGradientColors);
            String pressedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_pressedBackgroundGradientColors);
            String activatedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_activatedBackgroundGradientColors);
            String checkedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_checkedBackgroundGradientColors);
            String disabledBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_disabledBackgroundGradientColors);

            String cubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_cubeSidesGradientColors);
//          // 优先使用新属性，xp_cubeSidesGradientColors, 没有这个属性，才会使用xp_cubeFrontBorderColor
            if (cubeSidesColorString == null) {
                cubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_cubeFrontGradientColors);
            }
            cubeSidesBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_cubeSidesBorderColor, cubeSidesBorderColor);
            cubeSidesBorderWidth = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_cubeSidesBorderWidth, 0);

            cubeLeftHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_cubeLeftHeight, XCubeSidesHeight.NONE);
            cubeBackHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_cubeBackHeight, XCubeSidesHeight.NONE);
            cubeRightHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_cubeRightHeight, XCubeSidesHeight.NONE);
            cubeFrontHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_cubeFrontHeight, XCubeSidesHeight.NONE);

            String pressedCubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_pressedCubeSidesGradientColors);
            pressedCubeFrontHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_pressedCubeFrontHeight, XCubeSidesHeight.NONE);
            pressedCubeBackHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_pressedCubeBackHeight, XCubeSidesHeight.NONE);
            pressedCubeLeftHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_pressedCubeLeftHeight, XCubeSidesHeight.NONE);
            pressedCubeRightHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_pressedCubeRightHeight, XCubeSidesHeight.NONE);

            String activatedCubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_activatedCubeSidesGradientColors);
            activatedCubeFrontHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_activatedCubeFrontHeight, XCubeSidesHeight.NONE);
            activatedCubeBackHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_activatedCubeBackHeight, XCubeSidesHeight.NONE);
            activatedCubeLeftHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_activatedCubeLeftHeight, XCubeSidesHeight.NONE);
            activatedCubeRightHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_activatedCubeRightHeight, XCubeSidesHeight.NONE);

            String disabledCubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_disabledCubeSidesGradientColors);
            disabledCubeFrontHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_disabledCubeFrontHeight, XCubeSidesHeight.NONE);
            disabledCubeBackHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_disabledCubeBackHeight, XCubeSidesHeight.NONE);
            disabledCubeLeftHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_disabledCubeLeftHeight, XCubeSidesHeight.NONE);
            disabledCubeRightHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_disabledCubeRightHeight, XCubeSidesHeight.NONE);

            String checkedCubeSidesColorString = ta.getString(R.styleable.XIRoundBackground_xp_checkedCubeSidesGradientColors);
            checkedCubeFrontHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_checkedCubeFrontHeight, XCubeSidesHeight.NONE);
            checkedCubeBackHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_checkedCubeBackHeight, XCubeSidesHeight.NONE);
            checkedCubeLeftHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_checkedCubeLeftHeight, XCubeSidesHeight.NONE);
            checkedCubeRightHeight = ta.getDimensionPixelSize(R.styleable.XIRoundBackground_xp_checkedCubeRightHeight, XCubeSidesHeight.NONE);

            ta.recycle();

            backgroundColors = dealWithColors(backgroundColorString);

            pressedBackgroundColors = dealWithColors(pressedBackgroundColorString);
            activatedBackgroundColors = dealWithColors(activatedBackgroundColorString);
            checkedBackgroundColors = dealWithColors(checkedBackgroundColorString);
            disabledBackgroundColors = dealWithColors(disabledBackgroundColorString);

            // 立体属性
            cubeSidesColors = dealWithColors(cubeSidesColorString);
            // 立方体侧面的背景色
            cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);

            // 立体属性
            pressedCubeSidesColors = dealWithColors(pressedCubeSidesColorString);
            // 立体属性
            disabledCubeSidesColors = dealWithColors(disabledCubeSidesColorString);
            // 立体属性
            checkedCubeSidesColors = dealWithColors(checkedCubeSidesColorString);
            // 立体属性
            activatedCubeSidesColors = dealWithColors(activatedCubeSidesColorString);

            Drawable finalBgDrawable = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    borderColor, borderWidth,
                    backgroundTmp,
                    bgStartColor,
                    bgEndColor,
                    backgroundColors,
                    bgColorOrientation
            );

            background = getFinalLayerDrawable(
                    finalBgDrawable, cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
            );

            Drawable finalPressedBgDrawable = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                    pressedBackgroundTmp,
                    pressedBgStartColor,
                    pressedBgEndColor,
                    pressedBackgroundColors,
                    pressedBgColorOrientation
            );

            if (isCubeSidesEnabled() && isPressedCubeSidesEnabled()) {
                if (pressedCubeSidesColors == null || pressedCubeSidesColors.length == 0) {
                    pressedCubeSidesColors = cubeSidesColors;
                }
                if (finalPressedBgDrawable == null) {
                    finalPressedBgDrawable = finalBgDrawable;
                }
            }

            pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);

            pressedBackground = getFinalLayerDrawable(
                    finalPressedBgDrawable, pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
            );

            Drawable finalCheckedBgDrawable = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                    checkedBackgroundTmp,
                    checkedBgStartColor,
                    checkedBgEndColor,
                    checkedBackgroundColors,
                    checkedBgColorOrientation
            );

            if (isCubeSidesEnabled() && isCheckedCubeSidesEnabled()) {
                if (checkedCubeSidesColors == null || checkedCubeSidesColors.length == 0) {
                    checkedCubeSidesColors = cubeSidesColors;
                }
                if (finalCheckedBgDrawable == null) {
                    finalCheckedBgDrawable = finalBgDrawable;
                }
            }
            checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);

            checkedBackground = getFinalLayerDrawable(
                    finalCheckedBgDrawable, checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
            );

            Drawable finalDisabledBgDrawable = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                    disabledBackgroundTmp,
                    disabledBgStartColor,
                    disabledBgEndColor,
                    disabledBackgroundColors,
                    disabledBgColorOrientation
            );

            if (isCubeSidesEnabled() && isDisabledCubeSidesEnabled()) {
                if (disabledCubeSidesColors == null || disabledCubeSidesColors.length == 0) {
                    disabledCubeSidesColors = cubeSidesColors;
                }
                if (finalDisabledBgDrawable == null) {
                    finalDisabledBgDrawable = finalBgDrawable;
                }
            }

            disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);

            disabledBackground = getFinalLayerDrawable(
                    finalDisabledBgDrawable, disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
            );

            Drawable finalActivatedBgDrawable = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                    activatedBackgroundTmp,
                    activatedBgStartColor,
                    activatedBgEndColor,
                    activatedBackgroundColors,
                    activatedBgColorOrientation
            );

            if (isCubeSidesEnabled() && isActivatedCubeSidesEnabled()) {
                if (activatedCubeSidesColors == null || activatedCubeSidesColors.length == 0) {
                    activatedCubeSidesColors = cubeSidesColors;
                }
                if (finalActivatedBgDrawable == null) {
                    finalActivatedBgDrawable = finalBgDrawable;
                }
            }

            activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);

            activatedBackground = getFinalLayerDrawable(
                    finalActivatedBgDrawable, activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
            );
            setBackgroundSelector();
        }
    }

    private int[] dealWithColors(String backgroundColorString) {
        if (!TextUtils.isEmpty(backgroundColorString) && !TextUtils.isEmpty(backgroundColorString.trim())) {
            String[] cors = backgroundColorString.trim().split("[,，]");
            if (cors.length > 0) {
                ArrayList<Integer> colors = new ArrayList<>();
                for (String cor : cors) {
                    try {
                        if (cor.trim().isEmpty()) {
                            continue;
                        }
                        if (!XColorHelper.isColorStringFormat(cor.trim())) {
                            continue;
                        }
                        int color = XColorHelper.parseColor(cor.trim());
                        colors.add(color);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
                return XColorHelper.toIntArray(colors);
            }
        }
        return null;
    }

    /**
     * 只要有一个设置了，就激活立方体,立体效果
     *
     * @return isCubeSidesEnabled
     */
    private boolean isCubeSidesEnabled() {
        return cubeSidesColors != null &&
                cubeSidesColors.length != 0 &&
                (cubeFrontHeight != XCubeSidesHeight.NONE ||
                        cubeBackHeight != XCubeSidesHeight.NONE ||
                        cubeLeftHeight != XCubeSidesHeight.NONE ||
                        cubeRightHeight != XCubeSidesHeight.NONE
                );
    }


    /**
     * 只要有一个设置了，就激活
     *
     * @return isPressedCubeSidesEnabled
     */
    private boolean isPressedCubeSidesEnabled() {
        return pressedCubeBackHeight != XCubeSidesHeight.NONE ||
                pressedCubeFrontHeight != XCubeSidesHeight.NONE ||
                pressedCubeLeftHeight != XCubeSidesHeight.NONE ||
                pressedCubeRightHeight != XCubeSidesHeight.NONE;
    }

    /**
     * 只要有一个设置了，就激活
     *
     * @return isDisabledCubeSidesEnabled
     */
    private boolean isDisabledCubeSidesEnabled() {
        return disabledCubeBackHeight != XCubeSidesHeight.NONE ||
                disabledCubeFrontHeight != XCubeSidesHeight.NONE ||
                disabledCubeLeftHeight != XCubeSidesHeight.NONE ||
                disabledCubeRightHeight != XCubeSidesHeight.NONE;
    }

    /**
     * 只要有一个设置了，就激活
     *
     * @return isCheckedCubeSidesEnabled
     */
    private boolean isCheckedCubeSidesEnabled() {
        return checkedCubeBackHeight != XCubeSidesHeight.NONE ||
                checkedCubeFrontHeight != XCubeSidesHeight.NONE ||
                checkedCubeLeftHeight != XCubeSidesHeight.NONE ||
                checkedCubeRightHeight != XCubeSidesHeight.NONE;
    }

    /**
     * 只要有一个设置了，就激活
     *
     * @return isActivatedCubeSidesEnabled
     */
    private boolean isActivatedCubeSidesEnabled() {
        return activatedCubeBackHeight != XCubeSidesHeight.NONE ||
                activatedCubeFrontHeight != XCubeSidesHeight.NONE ||
                activatedCubeLeftHeight != XCubeSidesHeight.NONE ||
                activatedCubeRightHeight != XCubeSidesHeight.NONE;
    }


    private Drawable getFinalLayerDrawable(Drawable finalDrawable, Drawable cubeDrawable, int cubeLeftHeight, int cubeBackHeight, int cubeRightHeight, int cubeFrontHeight) {
        if (finalDrawable == null) return null;
        if (cubeDrawable == null || (cubeFrontHeight == XCubeSidesHeight.NONE && cubeLeftHeight == XCubeSidesHeight.NONE && cubeRightHeight == XCubeSidesHeight.NONE && cubeBackHeight == XCubeSidesHeight.NONE)) {
            return finalDrawable;
        }
        return XGradientHelper.getLayerDrawable(
                finalDrawable,
                cubeDrawable,
                cubeLeftHeight == XCubeSidesHeight.NONE ? 0 : cubeLeftHeight,
                cubeBackHeight == XCubeSidesHeight.NONE ? 0 : cubeBackHeight,
                cubeRightHeight == XCubeSidesHeight.NONE ? 0 : cubeRightHeight,
                cubeFrontHeight == XCubeSidesHeight.NONE ? 0 : cubeFrontHeight
        );
    }

//    @Deprecated
//    private Drawable getFinalLayerDrawable(Drawable finalDrawable, Drawable cubeDrawable, int cubeFrontHeight) {
//        if (finalDrawable == null) return null;
//        if (cubeDrawable == null || cubeFrontHeight == 0) {
//            return finalDrawable;
//        }
//        return XGradientHelper.getLayerDrawable(finalDrawable, cubeDrawable, cubeFrontHeight);
//    }

    private Drawable getGradientDrawable(int radius, int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius, int[] gradientColors, int borderColor, int borderWidth, @GradientOrientation int orientation) {
        if (gradientColors == null || gradientColors.length == 0) {
            return null;
        }
        XGradientHelper.GradientDrawableBuilder builder = new XGradientHelper.GradientDrawableBuilder();
        builder.setRadius(radius)
                .setTopLeftRadius(topLeftRadius)
                .setTopRightRadius(topRightRadius)
                .setBottomLeftRadius(bottomLeftRadius)
                .setBottomRightRadius(bottomRightRadius)
                .setStrokeWidth(borderWidth)
                .setStrokeColor(borderColor);
        if (gradientColors.length > 1) {
            GradientDrawable.Orientation ot;
            if (orientation == GradientOrientation.VERTICAL) {
                ot = GradientDrawable.Orientation.TOP_BOTTOM;
            } else if (orientation == GradientOrientation.TL_BR) {
                ot = GradientDrawable.Orientation.TL_BR;
            } else if (orientation == GradientOrientation.BL_TR) {
                ot = GradientDrawable.Orientation.BL_TR;
            } else {
                ot = GradientDrawable.Orientation.LEFT_RIGHT;
            }
            builder.setOrientation(ot);
            builder.setGradientColors(gradientColors);
        } else {
            builder.setFillColor(gradientColors[0]);
        }
        return builder.build();
    }

    private Drawable getFinalDrawable(int radius, int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius, int borderColor, int borderWidth, Drawable bg, int startColor, int endColor, int[] gradientColors, @GradientOrientation int orientation) {
        if (bg != null && !(bg instanceof ColorDrawable) && !(bg instanceof GradientDrawable)) {
            return bg;
        }
        XGradientHelper.GradientDrawableBuilder builder = new XGradientHelper.GradientDrawableBuilder();
        builder.setRadius(radius)
                .setTopLeftRadius(topLeftRadius)
                .setTopRightRadius(topRightRadius)
                .setBottomLeftRadius(bottomLeftRadius)
                .setBottomRightRadius(bottomRightRadius)
                .setStrokeWidth(borderWidth)
                .setStrokeColor(borderColor);
        if (startColor != DEFAULT_COLOR_TRANSPARENT && endColor != DEFAULT_COLOR_TRANSPARENT) {
            GradientDrawable.Orientation ot;
            if (orientation == GradientOrientation.VERTICAL) {
                ot = GradientDrawable.Orientation.TOP_BOTTOM;
            } else if (orientation == GradientOrientation.TL_BR) {
                ot = GradientDrawable.Orientation.TL_BR;
            } else if (orientation == GradientOrientation.BL_TR) {
                ot = GradientDrawable.Orientation.BL_TR;
            } else {
                ot = GradientDrawable.Orientation.LEFT_RIGHT;
            }
            builder.setOrientation(ot);
            builder.setGradientColors(new int[]{startColor, endColor});
            return builder.build();
        }
        if (gradientColors != null && gradientColors.length > 1) {
            GradientDrawable.Orientation ot;
            if (orientation == GradientOrientation.VERTICAL) {
                ot = GradientDrawable.Orientation.TOP_BOTTOM;
            } else if (orientation == GradientOrientation.TL_BR) {
                ot = GradientDrawable.Orientation.TL_BR;
            } else if (orientation == GradientOrientation.BL_TR) {
                ot = GradientDrawable.Orientation.BL_TR;
            } else {
                ot = GradientDrawable.Orientation.LEFT_RIGHT;
            }
            builder.setOrientation(ot);
            builder.setGradientColors(gradientColors);
            return builder.build();
        }

        if (bg instanceof ColorDrawable) {
            // 如果背景色是颜色，则提出颜色，使用builder
            int color = ((ColorDrawable) bg).getColor();
            builder.setFillColor(color);
            return builder.build();
        }
        if (startColor != DEFAULT_COLOR_TRANSPARENT) {
            builder.setFillColor(startColor);
            return builder.build();
        }
        if (endColor != DEFAULT_COLOR_TRANSPARENT) {
            builder.setFillColor(endColor);
            return builder.build();
        }
        // 优先级最低
        if (gradientColors != null && gradientColors.length == 1 & gradientColors[0] != DEFAULT_COLOR_TRANSPARENT) {
            builder.setFillColor(gradientColors[0]);
            return builder.build();
        }
        return bg;
    }

    private void setBackgroundSelector() {
        if (pressedBackground == null && disabledBackground == null && checkedBackground == null && activatedBackground == null) {
            if (mOwner.get() != null) {
                mOwner.get().setBackground(background);
            }
        } else {
            if (mOwner.get() != null) {
                XGradientHelper.StateListDrawableBuilder builder = new XGradientHelper.StateListDrawableBuilder();
                if (pressedBackground != null) {
                    builder.addPressedState(pressedBackground);
                }
                if (disabledBackground != null) {
                    builder.addDisabledState(disabledBackground);
                }
                if (checkedBackground != null) {
                    builder.addCheckedState(checkedBackground);
                }
                if (activatedBackground != null) {
                    builder.addActivatedState(activatedBackground);
                }
                builder.setUnState(background);
                mOwner.get().setBackground(builder.build());
            }
        }
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
        cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation
                ), cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );

        pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );

        checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
        cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation
                ), cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );

        pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );

        checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );


        disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );


        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBorderColor(int pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledBorderColor(int disabledBorderColor) {
        this.disabledBorderColor = disabledBorderColor;

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setActivatedBorderColor(int activatedBorderColor) {
        this.activatedBorderColor = activatedBorderColor;
        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBorderColor(int checkedBorderColor) {
        this.checkedBorderColor = checkedBorderColor;
        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        setBackgroundSelector();
    }


    @Override
    public void setNormalBackground(Drawable drawable) {
        this.backgroundTmp = drawable;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, @GradientOrientation int orientation) {
        this.bgStartColor = starColor;
        this.bgEndColor = endColor;
        this.bgColorOrientation = orientation;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setBackgroundGradientStartColor(int startColor) {
        setBackgroundGradient(startColor, bgEndColor, bgColorOrientation);
    }

    @Override
    public void setBackgroundGradientEndColor(int endColor) {
        setBackgroundGradient(bgStartColor, endColor, bgColorOrientation);
    }

    @Override
    public void setPressedBackground(Drawable drawable) {
        this.pressedBackgroundTmp = drawable;

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.pressedBgStartColor = startColor;
        this.pressedBgEndColor = endColor;
        this.pressedBgColorOrientation = orientation;
        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackgroundGradientStartColor(int startColor) {
        setPressedBackgroundGradient(startColor, pressedBgEndColor, pressedBgColorOrientation);
    }

    @Override
    public void setPressedBackgroundGradientEndColor(int endColor) {
        setPressedBackgroundGradient(pressedBgStartColor, endColor, pressedBgColorOrientation);
    }

    @Override
    public void setDisabledBackground(Drawable drawable) {
        this.disabledBackgroundTmp = drawable;
        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setDisabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.disabledBgStartColor = startColor;
        this.disabledBgEndColor = endColor;
        this.disabledBgColorOrientation = orientation;

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setDisabledBackgroundGradientStartColor(int startColor) {
        setDisabledBackgroundGradient(startColor, disabledBgEndColor, disabledBgColorOrientation);
    }

    @Override
    public void setDisabledBackgroundGradientEndColor(int endColor) {
        setDisabledBackgroundGradient(disabledBgStartColor, endColor, disabledBgColorOrientation);
    }

    @Override
    public void setCheckedBackground(Drawable drawable) {
        this.checkedBackgroundTmp = drawable;
        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.checkedBgEndColor = endColor;
        this.checkedBgStartColor = startColor;
        this.checkedBgColorOrientation = orientation;

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackgroundGradientStartColor(int startColor) {
        setCheckedBackgroundGradient(startColor, checkedBgEndColor, checkedBgColorOrientation);
    }

    @Override
    public void setCheckedBackgroundGradientEndColor(int endColor) {
        setCheckedBackgroundGradient(checkedBgStartColor, endColor, checkedBgColorOrientation);
    }

    @Override
    public void setActivatedBackground(Drawable drawable) {
        this.activatedBackgroundTmp = drawable;
        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.activatedBgStartColor = startColor;
        this.activatedBgEndColor = endColor;
        this.activatedBgColorOrientation = orientation;
        this.activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackgroundGradientStartColor(int startColor) {
        setActivatedBackgroundGradient(startColor, this.activatedBgEndColor, this.activatedBgColorOrientation);
    }

    @Override
    public void setActivatedBackgroundGradientEndColor(int endColor) {
        setActivatedBackgroundGradient(activatedBgStartColor, endColor, this.activatedBgColorOrientation);
    }

    @Override
    public void setNormalBackgroundColor(int color) {
        setNormalBackground(new ColorDrawable(color));
    }

    @Override
    public void setPressedBackgroundColor(int color) {
        setPressedBackground(new ColorDrawable(color));
    }

    @Override
    public void setActivatedBackgroundColor(int color) {
        setActivatedBackground(new ColorDrawable(color));
    }

    @Override
    public void setCheckedBackgroundColor(int color) {
        setCheckedBackground(new ColorDrawable(color));
    }

    @Override
    public void setDisabledBackgroundColor(int color) {
        setDisabledBackground(new ColorDrawable(color));
    }

    @Override
    public void setBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        this.bgColorOrientation = orientation;
        this.backgroundColors = colors;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getBackgroundGradientOrientation() {
        return bgColorOrientation;
    }

    @Override
    public void setPressedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        this.pressedBgColorOrientation = orientation;
        this.pressedBackgroundColors = colors;
        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getPressedBackgroundGradientOrientation() {
        return pressedBgColorOrientation;
    }

    @Override
    public void setActivatedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        this.activatedBgColorOrientation = orientation;
        this.activatedBackgroundColors = colors;
        this.activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getActivatedBackgroundGradientOrientation() {
        return activatedBgColorOrientation;
    }

    @Override
    public void setCheckedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        this.checkedBgColorOrientation = orientation;
        this.checkedBackgroundColors = colors;
        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getCheckedBackgroundGradientOrientation() {
        return checkedBgColorOrientation;
    }

    @Override
    public void setDisabledBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        this.disabledBgColorOrientation = orientation;
        this.disabledBackgroundColors = colors;

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getDisabledBackgroundGradientOrientation() {
        return disabledBgColorOrientation;
    }

    /**
     * 请使用： {@link #setCubeSidesGradientColors(int...)}
     *
     * @param colors @Deprecated
     */
    @Deprecated
    @Override
    public void setCubeFrontGradientColors(int... colors) {
        setCubeSidesGradientColors(colors);
    }

    @Override
    public void setCubeSidesGradientColors(int... colors) {
        this.cubeSidesColors = colors;
        cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front) {
        if (cubeFrontHeight == front && cubeLeftHeight == left && cubeRightHeight == right && cubeBackHeight == back) {
            return;
        }
        this.cubeFrontHeight = front;
        this.cubeBackHeight = back;
        this.cubeLeftHeight = left;
        this.cubeRightHeight = right;
        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setCubeSidesBorderColor(@ColorInt int color) {
        this.cubeSidesBorderColor = color;
        // 立体属性
        cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);
        // 立体属性
        pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);
        // 立体属性
        disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);
        // 立体属性
        checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);
        // 立体属性
        activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);

        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setCubeSidesBorderWidth(int width) {
        this.cubeSidesBorderWidth = width;
        // 立体属性
        cubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                cubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, bgColorOrientation);
        // 立体属性
        pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);
        // 立体属性
        disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);
        // 立体属性
        checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);
        // 立体属性
        activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);

        background = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        borderColor, borderWidth,
                        backgroundTmp,
                        bgStartColor,
                        bgEndColor,
                        backgroundColors,
                        bgColorOrientation),
                cubeSidesBackground, cubeLeftHeight, cubeBackHeight, cubeRightHeight, cubeFrontHeight
        );

        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );

        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );

        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );

        setBackgroundSelector();
    }

    @Override
    public void setPressedCubeSidesGradientColors(@ColorInt int... colors) {
        this.pressedCubeSidesColors = colors;
        pressedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, pressedBgColorOrientation);
        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front) {
        if (this.pressedCubeFrontHeight == front && pressedCubeBackHeight == back && pressedCubeLeftHeight == left && pressedCubeRightHeight == right) {
            return;
        }
        this.pressedCubeFrontHeight = front;
        this.pressedCubeBackHeight = back;
        this.pressedCubeLeftHeight = left;
        this.pressedCubeRightHeight = right;
        pressedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        pressedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : pressedBorderColor, borderWidth,
                        pressedBackgroundTmp,
                        pressedBgStartColor,
                        pressedBgEndColor,
                        pressedBackgroundColors,
                        pressedBgColorOrientation),
                pressedCubeSidesBackground, pressedCubeLeftHeight, pressedCubeBackHeight, pressedCubeRightHeight, pressedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledCubeSidesGradientColors(@ColorInt int... colors) {
        this.disabledCubeSidesColors = colors;
        // 立体属性
        disabledCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, disabledBgColorOrientation);
        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front) {
        if (this.disabledCubeFrontHeight == front && disabledCubeBackHeight == back && disabledCubeLeftHeight == left && disabledCubeRightHeight == right) {
            return;
        }
        this.disabledCubeFrontHeight = front;
        this.disabledCubeBackHeight = back;
        this.disabledCubeLeftHeight = left;
        this.disabledCubeRightHeight = right;
        disabledBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        disabledBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : disabledBorderColor, borderWidth,
                        disabledBackgroundTmp,
                        disabledBgStartColor,
                        disabledBgEndColor,
                        disabledBackgroundColors,
                        disabledBgColorOrientation),
                disabledCubeSidesBackground, disabledCubeLeftHeight, disabledCubeBackHeight, disabledCubeRightHeight, disabledCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedCubeSidesGradientColors(@ColorInt int... colors) {
        this.checkedCubeSidesColors = colors;
        checkedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, checkedBgColorOrientation);
        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front) {
        if (this.checkedCubeFrontHeight == left && checkedCubeBackHeight == back && checkedCubeRightHeight == right && checkedCubeLeftHeight == left) {
            return;
        }
        this.checkedCubeFrontHeight = front;
        this.checkedCubeBackHeight = back;
        this.checkedCubeRightHeight = right;
        this.checkedCubeLeftHeight = left;
        checkedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        checkedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : checkedBorderColor, borderWidth,
                        checkedBackgroundTmp,
                        checkedBgStartColor,
                        checkedBgEndColor,
                        checkedBackgroundColors,
                        checkedBgColorOrientation),
                checkedCubeSidesBackground, checkedCubeLeftHeight, checkedCubeBackHeight, checkedCubeRightHeight, checkedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedCubeSidesGradientColors(@ColorInt int... colors) {
        this.activatedCubeSidesColors = colors;
        activatedCubeSidesBackground = getGradientDrawable(radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedCubeSidesColors, cubeSidesBorderColor, cubeSidesBorderWidth, activatedBgColorOrientation);
        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedCubeSidesHeight(@IntRange(from = 0) int left, @IntRange(from = 0) int back, @IntRange(from = 0) int right, @IntRange(from = 0) int front) {
        if (this.activatedCubeFrontHeight == front && activatedCubeRightHeight == right && activatedCubeLeftHeight == left && activatedCubeBackHeight == back) {
            return;
        }
        this.activatedCubeFrontHeight = front;
        this.activatedCubeBackHeight = back;
        this.activatedCubeRightHeight = right;
        this.activatedCubeLeftHeight = left;

        activatedBackground = getFinalLayerDrawable(
                getFinalDrawable(
                        radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                        activatedBorderColor == DEFAULT_COLOR_TRANSPARENT ? borderColor : activatedBorderColor, borderWidth,
                        activatedBackgroundTmp,
                        activatedBgStartColor,
                        activatedBgEndColor,
                        activatedBackgroundColors,
                        activatedBgColorOrientation),
                activatedCubeSidesBackground, activatedCubeLeftHeight, activatedCubeBackHeight, activatedCubeRightHeight, activatedCubeFrontHeight
        );
        setBackgroundSelector();
    }

    @Override
    public XRoundBackgroundHelper clearBackgrounds() {
        background = null;
        pressedBackground = null;
        checkedBackground = null;
        disabledBackground = null;
        activatedBackground = null;
        backgroundTmp = null;
        pressedBackgroundTmp = null;
        checkedBackgroundTmp = null;
        disabledBackgroundTmp = null;
        activatedBackgroundTmp = null;
        radius = 0;
        topLeftRadius = 0;
        topRightRadius = 0;
        bottomLeftRadius = 0;
        bottomRightRadius = 0;
        borderWidth = 0;
        borderColor = DEFAULT_COLOR_TRANSPARENT;
        pressedBorderColor = DEFAULT_COLOR_TRANSPARENT;
        checkedBorderColor = DEFAULT_COLOR_TRANSPARENT;
        disabledBorderColor = DEFAULT_COLOR_TRANSPARENT;
        activatedBorderColor = DEFAULT_COLOR_TRANSPARENT;
        backgroundColors = null;
        pressedBackgroundColors = null;
        checkedBackgroundColors = null;
        activatedBackgroundColors = null;
        disabledBackgroundColors = null;
        cubeSidesColors = null;
        cubeSidesBackground = null;
        cubeSidesBorderColor = DEFAULT_COLOR_TRANSPARENT;
        cubeSidesBorderWidth = 0;
        cubeFrontHeight = 0;
        cubeBackHeight = 0;
        cubeLeftHeight = 0;
        cubeRightHeight = 0;
        pressedCubeSidesBackground = null;
        pressedCubeSidesColors = null;
        pressedCubeFrontHeight = 0;
        pressedCubeBackHeight = 0;
        pressedCubeLeftHeight = 0;
        pressedCubeRightHeight = 0;
        disabledCubeSidesBackground = null;
        disabledCubeFrontHeight = 0;
        disabledCubeBackHeight = 0;
        disabledCubeLeftHeight = 0;
        disabledCubeRightHeight = 0;
        disabledCubeSidesColors = null;
        checkedCubeSidesBackground = null;
        checkedCubeSidesColors = null;
        checkedCubeFrontHeight = 0;
        checkedCubeBackHeight = 0;
        checkedCubeLeftHeight = 0;
        checkedCubeRightHeight = 0;
        activatedCubeSidesBackground = null;
        activatedCubeFrontHeight = 0;
        activatedCubeBackHeight = 0;
        activatedCubeLeftHeight = 0;
        activatedCubeRightHeight = 0;
        activatedCubeSidesColors = null;
        setBackgroundSelector();
        return this;
    }
}
