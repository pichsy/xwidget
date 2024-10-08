package com.pichs.xwidget.roundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XIRoundBackground;
import com.pichs.xwidget.cardview.XITextView;
import com.pichs.xwidget.checkbox.IChecked;
import com.pichs.xwidget.utils.XBackgroundHelper;
import com.pichs.xwidget.utils.XCheckableHelper;
import com.pichs.xwidget.utils.XEditTextHelper;
import com.pichs.xwidget.utils.XRoundBackgroundHelper;
import com.pichs.xwidget.utils.XTextViewHelper;

/**
 * XEditText
 */
public class XRoundEditText extends AppCompatEditText implements XIRoundBackground, XITextView, IChecked, Checkable {
    private XRoundBackgroundHelper backgroundHelper;
    private XTextViewHelper textViewHelper;
    private boolean disableCopyAndPaste = false;
    private int textCursorColor = XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT;
    private int textCursorRadius = 0;
    private int textCursorWidth = 5;
    private Drawable textCursorDrawable;

    public XRoundEditText(@NonNull Context context) {
        super(context);
    }

    public XRoundEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, android.R.attr.editTextStyle);
    }

    public XRoundEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XRoundBackgroundHelper(context, attrs, defStyleAttr, this);
        textViewHelper = new XTextViewHelper(context, attrs, defStyleAttr, this);
        initChecked(context, attrs, defStyleAttr, 0, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XRoundEditText, defStyleAttr, 0);
        disableCopyAndPaste = ta.getBoolean(R.styleable.XRoundEditText_xp_disableCopyAndPaste, false);
        textCursorColor = ta.getColor(R.styleable.XRoundEditText_xp_textCursorColor, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT);
        textCursorRadius = ta.getDimensionPixelSize(R.styleable.XRoundEditText_xp_textCursorRadius, 0);
        textCursorWidth = ta.getDimensionPixelSize(R.styleable.XRoundEditText_xp_textCursorWidth, 5);
        ta.recycle();
        if (disableCopyAndPaste) {
            XEditTextHelper.disableCopyAndPaste(this);
        }

        if (textCursorWidth < 0) {
            textCursorWidth = 0;
        }
        if (textCursorRadius < 0) {
            textCursorRadius = 0;
        }
        if (textCursorColor != XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT) {
            XEditTextHelper.setCursorDrawable(this, textCursorColor, textCursorWidth, textCursorRadius);
        }
    }

    public boolean isDisableCopyAndPaste() {
        return disableCopyAndPaste;
    }

    public void setDisableCopyAndPaste(boolean disableCopyAndPaste) {
        this.disableCopyAndPaste = disableCopyAndPaste;
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (disableCopyAndPaste) {
            return true;
        }
        return super.onTextContextMenuItem(id);
    }

    public void setTextCursorColor(int color) {
        this.textCursorColor = color;
        XEditTextHelper.setCursorDrawable(this, textCursorColor, textCursorWidth, textCursorRadius);
    }

    public void setTextCursorRadius(int radius) {
        this.textCursorRadius = radius;
        XEditTextHelper.setCursorDrawable(this, textCursorColor, textCursorWidth, textCursorRadius);
    }

    public void setTextCursorWidth(int width) {
        this.textCursorWidth = width;
        XEditTextHelper.setCursorDrawable(this, textCursorColor, textCursorWidth, textCursorRadius);
    }

    public void setTextCursor(int color, int width, int radius) {
        this.textCursorColor = color;
        this.textCursorWidth = width;
        this.textCursorRadius = radius;
        XEditTextHelper.setCursorDrawable(this, textCursorColor, textCursorWidth, textCursorRadius);
    }

    public int getTextCursorColor() {
        return textCursorColor;
    }

    public int getTextCursorRadius() {
        return textCursorRadius;
    }

    public int getTextCursorWidth() {
        return textCursorWidth;
    }

    private boolean mChecked = false;

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
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        XCheckableHelper.initChecked(context, attrs, defStyleAttr, defStyleRes, owner);
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

}
