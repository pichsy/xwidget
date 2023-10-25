package com.pichs.xwidget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XIAlpha;
import com.pichs.xwidget.cardview.XIBackground;
import com.pichs.xwidget.utils.XAlphaHelper;
import com.pichs.xwidget.utils.XBackgroundHelper;

/**
 * 可以属性自定义的ColorFilter的ImageView
 * 方便换肤，节省切图，降低apk体积
 */
public class XImageView extends AppCompatImageView implements XIBackground, XIAlpha, IPressedStateHelper {
    private XBackgroundHelper backgroundHelper;
    private XAlphaHelper xAlphaHelper;

    private PorterDuff.Mode mMode;

    public XImageView(Context context) {
        super(context);
        init(context, null, 0);
    }


    public XImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    public void setColorFilterOverride(int color) {
        if (getCurrentMode() != null) {
            setColorFilter(color, getCurrentMode());
        } else {
            setColorFilter(color);
        }
    }

    public PorterDuff.Mode getCurrentMode() {
        return mMode;
    }

    private PorterDuff.Mode getColorFilterMode(int mode) {
        switch (mode) {
            case 0:
                return PorterDuff.Mode.SRC;
            case 1:
                return PorterDuff.Mode.SRC_ATOP;
            case 2:
                return PorterDuff.Mode.SRC_IN;
            case 3:
                return PorterDuff.Mode.SRC_OUT;
            case 4:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.MULTIPLY;
            case 6:
                return PorterDuff.Mode.DST;
            case 7:
                return PorterDuff.Mode.DST_ATOP;
            case 8:
                return PorterDuff.Mode.DST_IN;
            case 9:
                return PorterDuff.Mode.DST_OUT;
            case 10:
                return PorterDuff.Mode.DST_OVER;
            case 11:
                return PorterDuff.Mode.CLEAR;
            case 12:
                return PorterDuff.Mode.XOR;
            case 13:
                return PorterDuff.Mode.SCREEN;
            case 14:
                return PorterDuff.Mode.DARKEN;
            case 15:
                return PorterDuff.Mode.LIGHTEN;
            case 16:
                return PorterDuff.Mode.ADD;
            case 17:
                return PorterDuff.Mode.OVERLAY;
        }
        return PorterDuff.Mode.SRC_ATOP;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XBackgroundHelper(context, attrs, defStyleAttr, this);
        xAlphaHelper = new XAlphaHelper(context, attrs, defStyleAttr, this);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XImageView);
            int colorFilter = ta.getColor(R.styleable.XImageView_xp_colorFilter, -1);
            int colorFilterMode = ta.getInt(R.styleable.XImageView_xp_colorFilterMode, 1);
            if (colorFilter == -1) {
                clearColorFilter();
            } else {
                mMode = getColorFilterMode(colorFilterMode);
                setColorFilter(colorFilter, mMode);
            }
            ta.recycle();
        }
    }

    @Override
    public void setNormalAlpha(float alpha) {
        xAlphaHelper.setNormalAlpha(alpha);
    }

    @Override
    public void setAlphaOnPressed(float alpha) {
        xAlphaHelper.setAlphaOnPressed(alpha);
    }

    @Override
    public void setAlphaOnDisabled(float alpha) {
        xAlphaHelper.setAlphaOnDisabled(alpha);
    }

    @Override
    public void setNormalScale(float scaleRate) {
        xAlphaHelper.setNormalScale(scaleRate);
    }

    @Override
    public void setScaleOnPressed(float scaleRate) {
        xAlphaHelper.setScaleOnPressed(scaleRate);
    }

    @Override
    public void setScaleOnDisabled(float scaleRate) {
        xAlphaHelper.setScaleOnDisabled(scaleRate);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        xAlphaHelper.onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        xAlphaHelper.onEnabledChanged(this, enabled);
    }

    @Override
    public void setNormalBackground(Drawable drawable) {
        backgroundHelper.setNormalBackground(drawable);
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, @GradientOrientation int orientation) {
        backgroundHelper.setBackgroundGradient(starColor, endColor, orientation);
    }

    @Override
    public void setPressedBackground(Drawable pressedBackground) {
        backgroundHelper.setPressedBackground(pressedBackground);
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        backgroundHelper.setPressedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setDisabledBackground(Drawable disabledBackground) {
        backgroundHelper.setDisabledBackground(disabledBackground);
    }

    @Override
    public void setDisabledBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        backgroundHelper.setDisabledBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setCheckedBackground(Drawable checkedBackground) {
        backgroundHelper.setCheckedBackground(checkedBackground);
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        backgroundHelper.setCheckedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setActivatedBackground(Drawable activateBackground) {
        backgroundHelper.setActivatedBackground(activateBackground);
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        backgroundHelper.setActivatedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public XBackgroundHelper clearBackgrounds() {
        return backgroundHelper.clearBackgrounds();
    }


    @Override
    public void setBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setBackgroundGradientEndColor(endColor);
    }

    @Override
    public void setPressedBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setPressedBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setPressedBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setPressedBackgroundGradientEndColor(endColor);
    }

    @Override
    public void setDisabledBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setDisabledBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setDisabledBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setDisabledBackgroundGradientEndColor(endColor);
    }

    @Override
    public void setCheckedBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setCheckedBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setCheckedBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setCheckedBackgroundGradientEndColor(endColor);
    }

    @Override
    public void setActivatedBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setActivatedBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setActivatedBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setActivatedBackgroundGradientEndColor(endColor);
    }

    @Override
    public void setNormalBackgroundColor(int color) {
        backgroundHelper.setNormalBackgroundColor(color);
    }

    @Override
    public void setPressedBackgroundColor(int color) {
        backgroundHelper.setPressedBackgroundColor(color);
    }

    @Override
    public void setActivatedBackgroundColor(int color) {
        backgroundHelper.setActivatedBackgroundColor(color);
    }

    @Override
    public void setCheckedBackgroundColor(int color) {
        backgroundHelper.setCheckedBackgroundColor(color);
    }

    @Override
    public void setDisabledBackgroundColor(int color) {
        backgroundHelper.setDisabledBackgroundColor(color);
    }


    @Override
    public void setBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        backgroundHelper.setBackgroundGradientColors(colors, orientation);
    }

    @Override
    @GradientOrientation
    public int getBackgroundGradientOrientation() {
        return backgroundHelper.getBackgroundGradientOrientation();
    }

    @Override
    public void setPressedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        backgroundHelper.setPressedBackgroundGradientColors(colors, orientation);
    }

    @Override
    @GradientOrientation
    public int getPressedBackgroundGradientOrientation() {
        return backgroundHelper.getPressedBackgroundGradientOrientation();
    }

    @Override
    public void setActivatedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        backgroundHelper.setActivatedBackgroundGradientColors(colors, orientation);
    }

    @Override
    @GradientOrientation
    public int getActivatedBackgroundGradientOrientation() {
        return backgroundHelper.getActivatedBackgroundGradientOrientation();
    }

    @Override
    public void setCheckedBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        backgroundHelper.setCheckedBackgroundGradientColors(colors, orientation);
    }

    @Override
    public int getCheckedBackgroundGradientOrientation() {
        return backgroundHelper.getCheckedBackgroundGradientOrientation();
    }

    @Override
    public void setDisabledBackgroundGradientColors(int[] colors, @GradientOrientation int orientation) {
        backgroundHelper.setDisabledBackgroundGradientColors(colors, orientation);
    }

    @Override
    public int getDisabledBackgroundGradientOrientation() {
        return backgroundHelper.getDisabledBackgroundGradientOrientation();
    }

    @Override
    public void setOnPressedStateListener(OnPressedStateListener listener) {
        xAlphaHelper.setOnPressedStateListener(listener);
    }
}
