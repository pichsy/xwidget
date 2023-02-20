package com.pichs.common.widget.roundview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XIAlpha;
import com.pichs.common.widget.cardview.XIRoundBackground;
import com.pichs.common.widget.cardview.XITextView;
import com.pichs.common.widget.utils.XAlphaHelper;
import com.pichs.common.widget.utils.XRoundBackgroundHelper;
import com.pichs.common.widget.utils.XTextViewHelper;

/**
 * XRoundTextView
 */
public class XRoundTextView extends AppCompatTextView implements XIRoundBackground, XITextView, XIAlpha {

    private XRoundBackgroundHelper backgroundHelper;
    private XTextViewHelper textViewHelper;
    private XAlphaHelper xAlphaHelper;

    public XRoundTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public XRoundTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XRoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XRoundBackgroundHelper(context, attrs, defStyleAttr, this);
        textViewHelper = new XTextViewHelper(context, attrs, defStyleAttr, this);
        xAlphaHelper = new XAlphaHelper(context, attrs, defStyleAttr, this);
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
        xAlphaHelper.onPressedChanged(this,pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        xAlphaHelper.onEnabledChanged(this,enabled);
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
    public void setNormalTextColor(int color) {
        textViewHelper.setNormalTextColor(color);
    }

    @Override
    public void setPressedTextColor(int color) {
        textViewHelper.setPressedTextColor(color);
    }

    @Override
    public void setCheckedTextColor(int color) {
        textViewHelper.setCheckedTextColor(color);
    }

    @Override
    public void setActivatedTextColor(int color) {
        textViewHelper.setActivatedTextColor(color);
    }

    @Override
    public void setDisabledTextColor(int color) {
        textViewHelper.setDisabledTextColor(color);
    }

    @Override
    public void clearTextStateColor() {
        textViewHelper.clearTextStateColor();
    }

    @Override
    public void setIgnoreGlobalTypeface(boolean isIgnoreGlobalTypeface) {
        textViewHelper.setIgnoreGlobalTypeface(isIgnoreGlobalTypeface);
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
        backgroundHelper.setCubeSidesHeight(left,back,right,front);
    }

    @Override
    public void setPressedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setPressedCubeSidesHeight(left,back,right,front);
    }

    @Override
    public void setDisabledCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setDisabledCubeSidesHeight(left,back,right,front);
    }

    @Override
    public void setCheckedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setCheckedCubeSidesHeight(left,back,right,front);
    }

    @Override
    public void setActivatedCubeSidesHeight(int left, int back, int right, int front) {
        backgroundHelper.setActivatedCubeSidesHeight(left,back,right,front);
    }
}

