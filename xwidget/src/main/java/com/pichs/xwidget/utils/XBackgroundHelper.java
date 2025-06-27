package com.pichs.xwidget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XIBackground;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 背景帮助类
 */
public class XBackgroundHelper implements XIBackground {

    // 默认透明颜色值 0x0000000f = 15
    public static final int DEFAULT_COLOR_TRANSPARENT = 0x0000000f;

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
            bgStartColor = ta.getColor(R.styleable.XIBackground_xp_backgroundGradientStartColor, bgStartColor);
            bgEndColor = ta.getColor(R.styleable.XIBackground_xp_backgroundGradientEndColor, bgEndColor);
            bgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL);
            pressedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundStartColor, pressedBgStartColor);
            pressedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundEndColor, pressedBgEndColor);
            pressedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_pressedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            checkedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundStartColor, checkedBgStartColor);
            checkedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundEndColor, checkedBgEndColor);
            checkedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_checkedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            disabledBgStartColor = ta.getColor(R.styleable.XIBackground_xp_disabledBackgroundStartColor, disabledBgStartColor);
            disabledBgEndColor = ta.getColor(R.styleable.XIBackground_xp_disabledBackgroundEndColor, disabledBgEndColor);
            disabledBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_disabledBackgroundOrientation, GradientOrientation.HORIZONTAL);
            activatedBgStartColor = ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundStartColor, activatedBgStartColor);
            activatedBgEndColor = ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundEndColor, activatedBgEndColor);
            activatedBgColorOrientation = ta.getInt(R.styleable.XIBackground_xp_activatedBackgroundOrientation, GradientOrientation.HORIZONTAL);
            String backgroundColorString = ta.getString(R.styleable.XIBackground_xp_backgroundGradientColors);
            String pressedBackgroundColorString = ta.getString(R.styleable.XIBackground_xp_pressedBackgroundGradientColors);
            String activatedBackgroundColorString = ta.getString(R.styleable.XIBackground_xp_activatedBackgroundGradientColors);
            String checkedBackgroundColorString = ta.getString(R.styleable.XIBackground_xp_checkedBackgroundGradientColors);
            String disabledBackgroundColorString = ta.getString(R.styleable.XIBackground_xp_disabledBackgroundGradientColors);

            backgroundColors = dealWithColors(backgroundColorString);
            pressedBackgroundColors = dealWithColors(pressedBackgroundColorString);
            activatedBackgroundColors = dealWithColors(activatedBackgroundColorString);
            checkedBackgroundColors = dealWithColors(checkedBackgroundColorString);
            disabledBackgroundColors = dealWithColors(disabledBackgroundColorString);
            background = getFinalDrawable(
                    backgroundTmp,
                    bgStartColor,
                    bgEndColor,
                    backgroundColors,
                    bgColorOrientation
            );

            pressedBackground = getFinalDrawable(
                    pressedBackgroundTmp,
                    pressedBgStartColor,
                    pressedBgEndColor,
                    pressedBackgroundColors,
                    pressedBgColorOrientation
            );

            checkedBackground = getFinalDrawable(
                    checkedBackgroundTmp,
                    checkedBgStartColor,
                    checkedBgEndColor,
                    checkedBackgroundColors,
                    checkedBgColorOrientation
            );

            disabledBackground = getFinalDrawable(
                    disabledBackgroundTmp,
                    disabledBgStartColor,
                    disabledBgEndColor,
                    disabledBackgroundColors,
                    disabledBgColorOrientation
            );

            activatedBackground = getFinalDrawable(
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

    private Drawable getFinalDrawable(Drawable bg, int startColor, int endColor, int[] gradientColors, int orientation) {
        if (bg != null) {
            if (this.mOwner.get() instanceof EditText) {
                // 对于EditText，检查bg不是ColorDrawable、GradientDrawable和InsetDrawable
                if (!(bg instanceof ColorDrawable) && !(bg instanceof GradientDrawable) && !(bg instanceof InsetDrawable)) {
                    return bg;
                }
            } else {
                // 对于非EditText，检查bg不是ColorDrawable和GradientDrawable
                if (!(bg instanceof ColorDrawable) && !(bg instanceof GradientDrawable)) {
                    return bg;
                }
            }
        }

        XGradientHelper.GradientDrawableBuilder builder = new XGradientHelper.GradientDrawableBuilder();
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
        // 如果没有渐变色，则优先取原背景色
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
        if (gradientColors != null && gradientColors.length == 1 && gradientColors[0] != DEFAULT_COLOR_TRANSPARENT) {
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
    public void setNormalBackground(Drawable drawable) {
        this.backgroundTmp = drawable;
        background = getFinalDrawable(
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
        backgroundColors = null;
        pressedBackgroundColors = null;
        checkedBackgroundColors = null;
        activatedBackgroundColors = null;
        disabledBackgroundColors = null;
        setBackgroundSelector();
        return this;
    }
}
