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
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/25$ 18:36$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/25$ 18:36$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XBackgroundHelper implements XIBackground {

    private final WeakReference<View> mOwner;
    private Drawable background;
    private Drawable pressedBackground;
    private Drawable checkedBackground;
    private Drawable unEnabledBackground;
    private Drawable activatedBackground;

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
            background = getFinalDrawable(
                    ta.getDrawable(R.styleable.XIBackground_android_background),
                    ta.getColor(R.styleable.XIBackground_xp_backgroundGradientStartColor, 0),
                    ta.getColor(R.styleable.XIBackground_xp_backgroundGradientEndColor, 0),
                    ta.getInt(R.styleable.XIBackground_xp_backgroundGradientOrientation, GradientOrientation.HORIZONTAL)
            );

            pressedBackground = getFinalDrawable(
                    ta.getDrawable(R.styleable.XIBackground_xp_pressedBackground),
                    ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundStartColor, 0),
                    ta.getColor(R.styleable.XIBackground_xp_pressedBackgroundEndColor, 0),
                    ta.getInt(R.styleable.XIBackground_xp_pressedBackgroundOrientation, GradientOrientation.HORIZONTAL)
            );

            checkedBackground = getFinalDrawable(
                    ta.getDrawable(R.styleable.XIBackground_xp_checkedBackground),
                    ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundStartColor, 0),
                    ta.getColor(R.styleable.XIBackground_xp_checkedBackgroundEndColor, 0),
                    ta.getInt(R.styleable.XIBackground_xp_checkedBackgroundOrientation, GradientOrientation.HORIZONTAL)
            );

            unEnabledBackground = getFinalDrawable(
                    ta.getDrawable(R.styleable.XIBackground_xp_unEnabledBackground),
                    ta.getColor(R.styleable.XIBackground_xp_unEnabledBackgroundStartColor, 0),
                    ta.getColor(R.styleable.XIBackground_xp_unEnabledBackgroundEndColor, 0),
                    ta.getInt(R.styleable.XIBackground_xp_unEnabledBackgroundOrientation, GradientOrientation.HORIZONTAL)
            );

            activatedBackground = getFinalDrawable(
                    ta.getDrawable(R.styleable.XIBackground_xp_activatedBackground),
                    ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundStartColor, 0),
                    ta.getColor(R.styleable.XIBackground_xp_activatedBackgroundEndColor, 0),
                    ta.getInt(R.styleable.XIBackground_xp_activatedBackgroundOrientation, GradientOrientation.HORIZONTAL)
            );
            ta.recycle();
            setBackgroundSelector();
        }
    }

    private Drawable getFinalDrawable(Drawable bg, int startColor, int endColor, int orientation) {
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
            return XGradientHelper.getGradientDrawable(0, ot, new int[]{startColor, endColor});
        }
        if (bg != null) {
            return bg;
        }
        if (startColor != 0) {
            return new ColorDrawable(startColor);
        }
        if (endColor != 0) {
            return new ColorDrawable(endColor);
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
                mOwner.get().setBackgroundDrawable(builder.build());
            }
//            new SelectorUtils.StateListDrawableBuilder()
//                    .addPressedState(pressedColor)
//                    .addEnabledState(unEnabledColor)
//                    .addCheckedState(checkedColor)
//                    .addActivatedState(activatedColor)
//                    .addUnState(normalColor)
//                    .build();
        }
    }


    @Override
    public void setNormalBackground(Drawable drawable) {
        background = drawable;
        setBackgroundSelector();
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, int orientation) {
        background = getFinalDrawable(background, starColor, endColor, orientation);
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackground(Drawable pressedBackground) {
        this.pressedBackground = pressedBackground;
        setBackgroundSelector();
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.pressedBackground = getFinalDrawable(pressedBackground, startColor, endColor, orientation);
        setBackgroundSelector();
    }

    @Override
    public void setUnEnabledBackground(Drawable unEnabledBackground) {
        this.unEnabledBackground = unEnabledBackground;
        setBackgroundSelector();
    }

    @Override
    public void setUnEnabledBackgroundGradient(int startColor, int endColor, int orientation) {
        this.unEnabledBackground = getFinalDrawable(unEnabledBackground, startColor, endColor, orientation);
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackground(Drawable checkedBackground) {
        this.checkedBackground = checkedBackground;
        setBackgroundSelector();
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.checkedBackground = getFinalDrawable(checkedBackground, startColor, endColor, orientation);
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackground(Drawable activatedBackground) {
        this.activatedBackground = activatedBackground;
        setBackgroundSelector();
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, int orientation) {
        this.activatedBackground = getFinalDrawable(activatedBackground, startColor, endColor, orientation);
        setBackgroundSelector();
    }

    @Override
    public XBackgroundHelper clearBackgrounds() {
        background = null;
        pressedBackground = null;
        checkedBackground = null;
        unEnabledBackground = null;
        activatedBackground = null;
        setBackgroundSelector();
        return this;
    }
}
