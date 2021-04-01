package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XIRoundBackground;

import java.lang.ref.WeakReference;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/25$ 18:36$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/25$ 18:36$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XRoundBackgroundHelper implements XIRoundBackground {

    private final WeakReference<View> mOwner;
    private Drawable background;
    private Drawable pressedBackground;
    private Drawable checkedBackground;
    private Drawable unEnabledBackground;
    private Drawable activatedBackground;

    // 原始设置的background drawable
    private Drawable backgroundTmp;
    private Drawable pressedBackgroundTmp;
    private Drawable checkedBackgroundTmp;
    private Drawable unEnabledBackgroundTmp;
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
    private int unEnabledBorderColor = 0;

    private int borderWidth = 0;

    private int bgStartColor;
    private int bgEndColor;
    private int bgColorOrientation;
    private int pressedBgStartColor;
    private int pressedBgEndColor;
    private int pressedBgColorOrientation;
    private int checkedBgStartColor;
    private int checkedBgEndColor;
    private int checkedBgColorOrientation;
    private int unEnabledBgStartColor;
    private int unEnabledBgEndColor;
    private int unEnabledBgColorOrientation;
    private int activatedBgStartColor;
    private int activatedBgEndColor;
    private int activatedBgColorOrientation;

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
            unEnabledBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_unEnabledBorderColor, 0);
            activatedBorderColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBorderColor, 0);

            backgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_android_background);
            pressedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_pressedBackground);
            checkedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_checkedBackground);
            unEnabledBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_unEnabledBackground);
            activatedBackgroundTmp = ta.getDrawable(R.styleable.XIRoundBackground_xp_activatedBackground);

            bgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientStartColor, 0);
            bgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_backgroundGradientEndColor, 0);
            bgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL);
            pressedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundStartColor, 0);
            pressedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_pressedBackgroundEndColor, 0);
            pressedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_pressedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            checkedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundStartColor, 0);
            checkedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_checkedBackgroundEndColor, 0);
            checkedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_checkedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            unEnabledBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_unEnabledBackgroundStartColor, 0);
            unEnabledBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_unEnabledBackgroundEndColor, 0);
            unEnabledBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_unEnabledBackgroundOrientation, GradientOrientation.HORIZONTAL);
            activatedBgStartColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundStartColor, 0);
            activatedBgEndColor = ta.getColor(R.styleable.XIRoundBackground_xp_activatedBackgroundEndColor, 0);
            activatedBgColorOrientation = ta.getInt(R.styleable.XIRoundBackground_xp_activatedBackgroundOrientation, GradientOrientation.HORIZONTAL);

            background = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    borderColor, borderWidth,
                    backgroundTmp,
                    bgStartColor,
                    bgEndColor,
                    bgColorOrientation
            );

            pressedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                    pressedBackgroundTmp,
                    pressedBgStartColor,
                    pressedBgEndColor,
                    pressedBgColorOrientation
            );

            checkedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                    checkedBackgroundTmp,
                    checkedBgStartColor,
                    checkedBgEndColor,
                    checkedBgColorOrientation
            );

            unEnabledBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                    unEnabledBackgroundTmp,
                    unEnabledBgStartColor,
                    unEnabledBgEndColor,
                    unEnabledBgColorOrientation
            );

            activatedBackground = getFinalDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                    activatedBackgroundTmp,
                    activatedBgStartColor,
                    activatedBgEndColor,
                    activatedBgColorOrientation
            );
            ta.recycle();
            setBackgroundSelector();
        }
    }

    private Drawable getFinalDrawable(int radius, int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius, int borderColor, int borderWidth, Drawable bg, int startColor, int endColor, int orientation) {
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
        return null;
    }


    private void setBackgroundSelector() {
        if (pressedBackground == null && unEnabledBackground == null && checkedBackground == null && activatedBackground == null) {
            if (mOwner.get() != null) {
                mOwner.get().setBackground(background);
            }
        } else {
            if (mOwner.get() != null) {
                XGradientHelper.StateListDrawableBuilder builder = new XGradientHelper.StateListDrawableBuilder();
                if (pressedBackground != null) {
                    builder.addPressedState(pressedBackground);
                }
                if (unEnabledBackground != null) {
                    builder.addUnEnabledState(unEnabledBackground);
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
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBgColorOrientation
        );

        unEnabledBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                this.radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
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
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBgColorOrientation
        );

        unEnabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
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
                bgColorOrientation
        );

        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
                pressedBgColorOrientation
        );

        checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
                checkedBgColorOrientation
        );

        unEnabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
        );

        activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
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
                pressedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setUnEnabledBorderColor(int unEnabledBorderColor) {
        this.unEnabledBorderColor = unEnabledBorderColor;
        unEnabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
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
                bgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, int orientation) {
        this.bgStartColor = starColor;
        this.bgEndColor = endColor;
        this.bgColorOrientation = orientation;
        background = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                borderColor, borderWidth,
                backgroundTmp,
                bgStartColor,
                bgEndColor,
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
                pressedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.pressedBgStartColor = startColor;
        this.pressedBgEndColor = endColor;
        this.pressedBgColorOrientation = orientation;
        pressedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                pressedBorderColor == 0 ? borderColor : pressedBorderColor, borderWidth,
                pressedBackgroundTmp,
                pressedBgStartColor,
                pressedBgEndColor,
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
    public void setUnEnabledBackground(Drawable drawable) {
        this.unEnabledBackgroundTmp = drawable;
        this.unEnabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setUnEnabledBackgroundGradient(int startColor, int endColor, int orientation) {
        this.unEnabledBgStartColor = startColor;
        this.unEnabledBgEndColor = endColor;
        this.unEnabledBgColorOrientation = orientation;
        this.unEnabledBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                unEnabledBorderColor == 0 ? borderColor : unEnabledBorderColor, borderWidth,
                unEnabledBackgroundTmp,
                unEnabledBgStartColor,
                unEnabledBgEndColor,
                unEnabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setUnEnabledBackgroundGradientStartColor(int startColor) {
        setUnEnabledBackgroundGradient(startColor, unEnabledBgEndColor, unEnabledBgColorOrientation);
    }

    @Override
    public void setUnEnabledBackgroundGradientEndColor(int endColor) {
        setUnEnabledBackgroundGradient(unEnabledBgStartColor, endColor, unEnabledBgColorOrientation);
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
                checkedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.checkedBgEndColor = endColor;
        this.checkedBgStartColor = startColor;
        this.checkedBgColorOrientation = orientation;
        this.checkedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                checkedBorderColor == 0 ? borderColor : checkedBorderColor, borderWidth,
                checkedBackgroundTmp,
                checkedBgStartColor,
                checkedBgEndColor,
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
                activatedBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.activatedBgStartColor = startColor;
        this.activatedBgEndColor = endColor;
        this.activatedBgColorOrientation = orientation;
        this.activatedBackground = getFinalDrawable(
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                activatedBorderColor == 0 ? borderColor : activatedBorderColor, borderWidth,
                activatedBackgroundTmp,
                activatedBgStartColor,
                activatedBgEndColor,
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
    public void setUnEnabledBackgroundColor(int color) {
        setUnEnabledBackground(new ColorDrawable(color));
    }

    @Override
    public XRoundBackgroundHelper clearBackgrounds() {
        background = null;
        pressedBackground = null;
        checkedBackground = null;
        unEnabledBackground = null;
        activatedBackground = null;
        backgroundTmp = null;
        pressedBackgroundTmp = null;
        checkedBackgroundTmp = null;
        unEnabledBackgroundTmp = null;
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
        unEnabledBorderColor = 0;
        activatedBorderColor = 0;
        setBackgroundSelector();
        return this;
    }
}
