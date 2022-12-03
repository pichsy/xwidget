package com.pichs.common.widget.roundview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XIAlpha;
import com.pichs.common.widget.cardview.XIRoundBackground;
import com.pichs.common.widget.cardview.XITextView;
import com.pichs.common.widget.utils.XAlphaHelper;
import com.pichs.common.widget.utils.XRoundBackgroundHelper;
import com.pichs.common.widget.utils.XTextViewHelper;

/**
 * XRoundButton 自定义基类
 */
public class XRoundButton extends AppCompatButton implements XIRoundBackground, XITextView , XIAlpha {

    private XRoundBackgroundHelper backgroundHelper;
    private XTextViewHelper textViewHelper;
    private XAlphaHelper xAlphaHelper;


    public XRoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public XRoundButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XRoundButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XRoundBackgroundHelper(context, attrs, defStyleAttr, this);
        textViewHelper = new XTextViewHelper(context, attrs, defStyleAttr, this);
        xAlphaHelper = new XAlphaHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public void setChangeAlphaOnPressed(boolean isChangeAlphaOnPressed) {
        xAlphaHelper.setChangeAlphaOnPressed(isChangeAlphaOnPressed);
    }

    @Override
    public void setChangeAlphaOnDisabled(boolean isChangeAlphaOnDisabled) {
        xAlphaHelper.setChangeAlphaOnDisabled(isChangeAlphaOnDisabled);
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


    @Override
    public void setCubeFrontGradientColors(int... colors) {
        backgroundHelper.setCubeFrontGradientColors(colors);
    }

    @Override
    public void setCubeFrontHeight(int height) {
        backgroundHelper.setCubeFrontHeight(height);
    }

    @Override
    public void setCubeFrontBorderColor(int color) {
        backgroundHelper.setCubeFrontBorderColor(color);
    }

    @Override
    public void setCubeFrontBorderWidth(int width) {
        backgroundHelper.setCubeFrontBorderWidth(width);
    }

    @Override
    public void setPressedCubeFrontGradientColors(int... colors) {
        backgroundHelper.setPressedCubeFrontGradientColors(colors);
    }

    @Override
    public void setPressedCubeFrontHeight(int height) {
        backgroundHelper.setPressedCubeFrontHeight(height);
    }

    @Override
    public void setDisabledCubeFrontGradientColors(int... colors) {
        backgroundHelper.setDisabledCubeFrontGradientColors(colors);
    }

    @Override
    public void setDisabledCubeFrontHeight(int height) {
        backgroundHelper.setDisabledCubeFrontHeight(height);
    }

    @Override
    public void setCheckedCubeFrontGradientColors(int... colors) {
        backgroundHelper.setCheckedCubeFrontGradientColors(colors);
    }

    @Override
    public void setCheckedCubeFrontHeight(int height) {
        backgroundHelper.setCheckedCubeFrontHeight(height);
    }

    @Override
    public void setActivatedCubeFrontGradientColors(int... colors) {
        backgroundHelper.setActivatedCubeFrontGradientColors(colors);
    }

    @Override
    public void setActivatedCubeFrontHeight(int height) {
        backgroundHelper.setActivatedCubeFrontHeight(height);
    }
}
