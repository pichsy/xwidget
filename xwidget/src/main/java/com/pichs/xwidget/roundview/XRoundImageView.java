package com.pichs.xwidget.roundview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XIAlpha;
import com.pichs.xwidget.cardview.XIRoundBackground;
import com.pichs.xwidget.checkbox.IChecked;
import com.pichs.xwidget.utils.XAlphaHelper;
import com.pichs.xwidget.utils.XBackgroundHelper;
import com.pichs.xwidget.utils.XCheckableHelper;
import com.pichs.xwidget.utils.XRoundBackgroundHelper;

/**
 * @author 高延荣
 * @date 2019/1/22 14:54
 * 描述:
 */
@SuppressLint("AppCompatCustomView")
public class XRoundImageView extends ImageView implements XIRoundBackground, Checkable, IChecked, XIAlpha {

    private XRoundBackgroundHelper backgroundHelper;
    private XAlphaHelper xAlphaHelper;
    private PorterDuff.Mode mMode;
    public XRoundImageView(Context context) {
        this(context, null);
    }

    public XRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XRoundBackgroundHelper(context, attrs, defStyleAttr, this);
        xAlphaHelper = new XAlphaHelper(context, attrs, defStyleAttr, this);
        initChecked(context, attrs, defStyleAttr, 0, this);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XRoundImageView);
            int colorFilter = ta.getColor(R.styleable.XRoundImageView_xp_colorFilter, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT);
            int colorFilterMode = ta.getInt(R.styleable.XRoundImageView_xp_colorFilterMode, 1);
            if (colorFilter == XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT) {
                clearColorFilter();
            } else {
                mMode = getColorFilterMode(colorFilterMode);
                setColorFilter(colorFilter, mMode);
            }
            ta.recycle();
        }
    }

    public void setColorFilterOverride(int color) {
        if (getCurrentMode() != null) {
            setColorFilter(color, getCurrentMode());
        } else {
            setColorFilter(color);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        backgroundHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        backgroundHelper.onDraw(canvas);
        super.onDraw(canvas);
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

    @Override
    public void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        XCheckableHelper.initChecked(context, attrs, defStyleAttr, defStyleRes, owner);
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
    public void setRadius(int radius) {
        backgroundHelper.setRadius(radius);
    }

    @Override
    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        backgroundHelper.setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
    }

    @Override
    public void setBorderColor(int borderColor) {
        backgroundHelper.setBorderColor(borderColor);
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        backgroundHelper.setBorderWidth(borderWidth);
    }

    @Override
    public void setPressedBorderColor(int pressedBorderColor) {
        backgroundHelper.setPressedBorderColor(pressedBorderColor);
    }

    @Override
    public void setDisabledBorderColor(int disabledBorderColor) {
        backgroundHelper.setDisabledBorderColor(disabledBorderColor);
    }

    @Override
    public void setActivatedBorderColor(int activatedBorderColor) {
        backgroundHelper.setActivatedBorderColor(activatedBorderColor);
    }

    @Override
    public void setCheckedBorderColor(int checkedBorderColor) {
        backgroundHelper.setCheckedBorderColor(checkedBorderColor);
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
    public XRoundBackgroundHelper clearBackgrounds() {
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

    /**
     * 请使用： {@link #setCubeSidesGradientColors(int...)}
     *
     * @param colors @Deprecated
     */
    @Deprecated
    @Override
    public void setCubeFrontGradientColors(int... colors) {
        backgroundHelper.setCubeFrontGradientColors(colors);
    }

    @Override
    public void setCubeSidesBorderColor(int color) {
        backgroundHelper.setCubeSidesBorderColor(color);
    }

    @Override
    public void setCubeSidesBorderWidth(int width) {
        backgroundHelper.setCubeSidesBorderWidth(width);
    }

    @Override
    public void setPressedCubeSidesGradientColors(int... colors) {
        backgroundHelper.setPressedCubeSidesGradientColors(colors);
    }

    @Override
    public void setDisabledCubeSidesGradientColors(int... colors) {
        backgroundHelper.setDisabledCubeSidesGradientColors(colors);
    }

    @Override
    public void setCheckedCubeSidesGradientColors(int... colors) {
        backgroundHelper.setCheckedCubeSidesGradientColors(colors);
    }

    @Override
    public void setActivatedCubeSidesGradientColors(int... colors) {
        backgroundHelper.setActivatedCubeSidesGradientColors(colors);
    }

    @Override
    public void setCubeSidesGradientColors(int... colors) {
        backgroundHelper.setCubeSidesGradientColors(colors);
    }

    @Override
    public void setCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setCubeSidesHeight(left, back, right, front);
    }

    @Override
    public void setPressedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setPressedCubeSidesHeight(left, back, right, front);
    }

    @Override
    public void setDisabledCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setDisabledCubeSidesHeight(left, back, right, front);
    }

    @Override
    public void setCheckedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setCheckedCubeSidesHeight(left, back, right, front);
    }

    @Override
    public void setActivatedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setActivatedCubeSidesHeight(left, back, right, front);
    }

    protected boolean mChecked = false;

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
