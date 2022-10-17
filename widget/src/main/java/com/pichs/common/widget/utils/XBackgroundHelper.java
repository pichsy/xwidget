package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XIBackground;

import java.lang.ref.WeakReference;

/**
 * 背景帮助类
 */
public class XBackgroundHelper implements XIBackground {

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

    private int bgStartColor;
    private int bgEndColor;
    private int bgColorOrientation;
    private int pressedBgStartColor;
    private int pressedBgEndColor;
    private int pressedBgColorOrientation;
    private int checkedBgStartColor;
    private int checkedBgEndColor;
    private int checkedBgColorOrientation;
    private int disabledBgStartColor;
    private int disabledBgEndColor;
    private int disabledBgColorOrientation;
    private int activatedBgStartColor;
    private int activatedBgEndColor;
    private int activatedBgColorOrientation;

    public XBackgroundHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XBackgroundHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, View owner) {
        mOwner = new WeakReference<>(owner);
        init(context, attrs, defAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defAttr, int defStyleRes) {
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XIBackground, defAttr, defStyleRes);
            // 常色
            backgroundTmp = ta.getDrawable(R.styleable.XIBackground_android_background);
            pressedBackgroundTmp = ta.getDrawable(R.styleable.XIBackground_xp_pressedBackground);
            checkedBackgroundTmp = ta.getDrawable(R.styleable.XIBackground_xp_checkedBackground);
            disabledBackgroundTmp = ta.getDrawable(R.styleable.XIBackground_xp_disabledBackground);
            activatedBackgroundTmp = ta.getDrawable(R.styleable.XIBackground_xp_activatedBackground);

            // 渐变
            bgStartColor = ta.getColor(R.styleable.XIBackground_xp_backgroundGradientStartColor, 0);
            bgEndColor = ta.getColor(R.styleable.XIBackground_xp_backgroundGradientEndColor, 0);
            bgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL);
            pressedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundStartColor, 0);
            pressedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundEndColor, 0);
            pressedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_pressedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            checkedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundStartColor, 0);
            checkedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundEndColor, 0);
            checkedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_checkedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            disabledBgStartColor = ta.getColor(R.styleable.XIBackground_xp_disabledBackgroundStartColor, 0);
            disabledBgEndColor = ta.getColor(R.styleable.XIBackground_xp_disabledBackgroundEndColor, 0);
            disabledBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_disabledBackgroundOrientation, GradientOrientation.HORIZONTAL);
            activatedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundStartColor, 0);
            activatedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundEndColor, 0);
            activatedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_activatedBackgroundOrientation, GradientOrientation.HORIZONTAL);

            background = getFinalDrawable(
                    backgroundTmp,
                    bgStartColor,
                    bgEndColor,
                    bgColorOrientation
            );

            pressedBackground = getFinalDrawable(
                    pressedBackgroundTmp,
                    pressedBgStartColor,
                    pressedBgEndColor,
                    pressedBgColorOrientation
            );

            checkedBackground = getFinalDrawable(
                    checkedBackgroundTmp,
                    checkedBgStartColor,
                    checkedBgEndColor,
                    checkedBgColorOrientation
            );

            disabledBackground = getFinalDrawable(
                    disabledBackgroundTmp,
                    disabledBgStartColor,
                    disabledBgEndColor,
                    disabledBgColorOrientation
            );

            activatedBackground = getFinalDrawable(

                    activatedBackgroundTmp,
                    activatedBgStartColor,
                    activatedBgEndColor,
                    activatedBgColorOrientation
            );
            ta.recycle();
            setBackgroundSelector();
        }
    }

    private Drawable getFinalDrawable(Drawable bg, int startColor, int endColor, int orientation) {
        if (bg != null && !(bg instanceof ColorDrawable) && !(bg instanceof GradientDrawable)) {
            return bg;
        }
        XGradientHelper.GradientDrawableBuilder builder = new XGradientHelper.GradientDrawableBuilder();
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
    public void setNormalBackground(Drawable drawable) {
        this.backgroundTmp = drawable;
        background = getFinalDrawable(
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
    public void setDisabledBackground(Drawable drawable) {
        this.disabledBackgroundTmp = drawable;
        this.disabledBackground = getFinalDrawable(
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
                disabledBgColorOrientation
        );
        setBackgroundSelector();
    }

    @Override
    public void setDisabledBackgroundGradient(int startColor, int endColor, int orientation) {
        this.disabledBgStartColor = startColor;
        this.disabledBgEndColor = endColor;
        this.disabledBgColorOrientation = orientation;
        this.disabledBackground = getFinalDrawable(
                disabledBackgroundTmp,
                disabledBgStartColor,
                disabledBgEndColor,
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
    public void setDisabledBackgroundColor(int color) {
        setDisabledBackground(new ColorDrawable(color));
    }

    @Override
    public XBackgroundHelper clearBackgrounds() {
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
        setBackgroundSelector();
        return this;
    }
}
