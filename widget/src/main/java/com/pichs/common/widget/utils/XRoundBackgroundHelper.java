package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XIRoundBackground;

import java.lang.ref.WeakReference;

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
    private int borderColor = 0;
    @ColorInt
    private int pressedBorderColor = 0;
    @ColorInt
    private int checkedBorderColor = 0;
    @ColorInt
    private int activatedBorderColor = 0;
    @ColorInt
    private int disabledBorderColor = 0;

    private int borderWidth = 0;

    private int bgStartColor;
    private int bgEndColor;
    @GradientOrientation
    private int bgColorOrientation = GradientOrientation.HORIZONTAL;
    private int pressedBgStartColor;
    private int pressedBgEndColor;
    @GradientOrientation
    private int pressedBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int checkedBgStartColor;
    private int checkedBgEndColor;
    @GradientOrientation
    private int checkedBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int disabledBgStartColor;
    private int disabledBgEndColor;
    @GradientOrientation
    private int disabledBgColorOrientation = GradientOrientation.HORIZONTAL;
    private int activatedBgStartColor;
    private int activatedBgEndColor;
    @GradientOrientation
    private int activatedBgColorOrientation = GradientOrientation.HORIZONTAL;

    // 渐变色，列表，支持多色渐变
    private int[] backgroundColors;
    private int[] activatedBackgroundColors;
    private int[] pressedBackgroundColors;
    private int[] checkedBackgroundColors;
    private int[] disabledBackgroundColors;

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
            borderColor = ta.getColor(R.styleable.XIRoundBackground_xp_borderColor, 0);
            pressedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBorderColor, 0);
            checkedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBorderColor, 0);
            disabledBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBorderColor, 0);
            activatedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBorderColor, 0);
            // 常色
            backgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_android_background);
            pressedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_pressedBackground);
            checkedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_checkedBackground);
            disabledBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_disabledBackground);
            activatedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_activatedBackground);
            // 渐变
            bgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientStartColor, 0);
            bgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientEndColor, 0);
            bgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL);
            pressedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundStartColor, 0);
            pressedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundEndColor, 0);
            pressedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_pressedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            checkedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundStartColor, 0);
            checkedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundEndColor, 0);
            checkedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_checkedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            disabledBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBackgroundStartColor, 0);
            disabledBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_disabledBackgroundEndColor, 0);
            disabledBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_disabledBackgroundOrientation, GradientOrientation.HORIZONTAL);
            activatedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundStartColor, 0);
            activatedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundEndColor, 0);
            activatedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_activatedBackgroundOrientation, GradientOrientation.HORIZONTAL);

            String backgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_backgroundGradientColors);
            String pressedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_pressedBackgroundGradientColors);
            String activatedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_activatedBackgroundGradientColors);
            String checkedBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_checkedBackgroundGradientColors);
            String disabledBackgroundColorString = ta.getString(R.styleable.XIRoundBackground_xp_disabledBackgroundGradientColors);

            backgroundColors = dealWithColors(backgroundColorString);
            pressedBackgroundColors = dealWithColors(pressedBackgroundColorString);
            activatedBackgroundColors = dealWithColors(activatedBackgroundColorString);
            checkedBackgroundColors = dealWithColors(checkedBackgroundColorString);
            disabledBackgroundColors = dealWithColors(disabledBackgroundColorString);

            background = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    borderColor, borderWidth,
                    backgroundTmp,
                    bgStartColor,
                    bgEndColor,
                    backgroundColors,
                    bgColorOrientation
            );

            pressedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                    pressedBackgroundTmp,
                    pressedBgStartColor,
                    pressedBgEndColor,
                    pressedBackgroundColors,
                    pressedBgColorOrientation
            );

            checkedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                    checkedBackgroundTmp,
                    checkedBgStartColor,
                    checkedBgEndColor,
                    checkedBackgroundColors,
                    checkedBgColorOrientation
            );

            disabledBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                    disabledBackgroundTmp,
                    disabledBgStartColor,
                    disabledBgEndColor,
                    disabledBackgroundColors,
                    disabledBgColorOrientation
            );

            activatedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                    activatedBackgroundTmp,
                    activatedBgStartColor,
                    activatedBgEndColor,
                    activatedBackgroundColors,
                    activatedBgColorOrientation
            );
            ta.recycle();
            setBackgroundSelector();
        }
    }

    private int[] dealWithColors(String backgroundColorString) {
        if (!TextUtils.isEmpty(backgroundColorString) && !TextUtils.isEmpty(backgroundColorString.trim())) {
            try {
                String[] cors = backgroundColorString.trim().split(",");
                if (cors.length > 0) {
                    int[] colors = new int[cors.length];
                    for (int i = 0; i < cors.length; i++) {
                        int color = XColorHelper.parseColor(cors[i]);
                        colors[i] = color;
                    }
                    return colors;
                }
            } catch (Exception e) {
                throw new RuntimeException("gradientColors:" + backgroundColorString + " 格式不正确，请用《,》分割，并且检查一下颜色值格式\n ex:" + e);
            }
        }
        return null;
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
        if (startColor != 0 && endColor != 0) {
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
        if (startColor != 0) {
            builder.setFillColor(startColor);
            return builder.build();
        }
        if (endColor != 0) {
            builder.setFillColor(endColor);
            return builder.build();
        }
        // 优先级最低
        if (gradientColors != null && gradientColors.length == 1) {
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
        background = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
        );

        disabledBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
        );

        disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
        );

        disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBorderColor(int pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
        pressedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledBorderColor(int disabledBorderColor) {
        this.disabledBorderColor = disabledBorderColor;
        disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBorderColor(int activatedBorderColor) {
        this.activatedBorderColor = activatedBorderColor;
        activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBorderColor(int checkedBorderColor) {
        this.checkedBorderColor = checkedBorderColor;
        checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
        );
        setBackgroundSelector();
    }


    @Override
    public void setNormalBackground(Drawable drawable) {
        this.backgroundTmp = drawable;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, @GradientOrientation int orientation) {
        this.bgStartColor = starColor;
        this.bgEndColor = endColor;
        this.bgColorOrientation = orientation;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
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
        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.pressedBgStartColor = startColor;
        this.pressedBgEndColor = endColor;
        this.pressedBgColorOrientation = orientation;
        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
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
        this.disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.disabledBgStartColor = startColor;
        this.disabledBgEndColor = endColor;
        this.disabledBgColorOrientation = orientation;
        this.disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
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
        this.checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.checkedBgEndColor = endColor;
        this.checkedBgStartColor = startColor;
        this.checkedBgColorOrientation = orientation;
        this.checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
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
        this.activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        this.activatedBgStartColor = startColor;
        this.activatedBgEndColor = endColor;
        this.activatedBgColorOrientation = orientation;
        this.activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
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
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
                backgroundColors,
                bgColorOrientation
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
        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBackgroundColors,
                pressedBgColorOrientation
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
        this.activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
                activatedBackgroundColors,
                activatedBgColorOrientation
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
        this.checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBackgroundColors,
                checkedBgColorOrientation
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
        this.disabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                disabledBorderColor == 0 ? borderColor : disabledBorderColor, borderWidth,
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBackgroundColors,
                disabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    @GradientOrientation
    public int getDisabledBackgroundGradientOrientation() {
        return disabledBgColorOrientation;
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
        borderColor = 0;
        pressedBorderColor = 0;
        checkedBorderColor = 0;
        disabledBorderColor = 0;
        activatedBorderColor = 0;
        backgroundColors = null;
        pressedBackgroundColors = null;
        checkedBackgroundColors = null;
        activatedBackgroundColors = null;
        disabledBackgroundColors = null;
        setBackgroundSelector();
        return this;
    }
}
