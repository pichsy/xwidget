package com.pichs.common.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XIBackground;
import com.pichs.common.widget.cardview.XITextView;
import com.pichs.common.widget.utils.XBackgroundHelper;
import com.pichs.common.widget.utils.XEditTextHelper;
import com.pichs.common.widget.utils.XTextViewHelper;
import com.pichs.common.widget.utils.XTypefaceHelper;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/6$ 10:55$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/6$ 10:55$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XEditText extends AppCompatEditText implements XIBackground, XITextView {
    private XBackgroundHelper backgroundHelper;
    private XTextViewHelper textViewHelper;
    private boolean disableCopyAndPaste = false;

    public XEditText(@NonNull Context context) {
        super(context);
    }

    public XEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, android.R.attr.editTextStyle);
    }

    public XEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XBackgroundHelper(context, attrs, defStyleAttr, this);
        textViewHelper = new XTextViewHelper(context, attrs, defStyleAttr, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XEditText, defStyleAttr, 0);
        disableCopyAndPaste = ta.getBoolean(R.styleable.XEditText_xp_disableCopyAndPaste, false);
        ta.recycle();
        if (disableCopyAndPaste) {
            XEditTextHelper.disableCopyAndPaste(this);
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

    @Override
    public void setNormalBackground(Drawable drawable) {
        backgroundHelper.setNormalBackground(drawable);
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, int orientation) {
        backgroundHelper.setBackgroundGradient(starColor, endColor, orientation);
    }

    @Override
    public void setPressedBackground(Drawable pressedBackground) {
        backgroundHelper.setPressedBackground(pressedBackground);
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setPressedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setUnEnabledBackground(Drawable unEnabledBackground) {
        backgroundHelper.setUnEnabledBackground(unEnabledBackground);
    }

    @Override
    public void setUnEnabledBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setUnEnabledBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setCheckedBackground(Drawable checkedBackground) {
        backgroundHelper.setCheckedBackground(checkedBackground);
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setCheckedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setActivatedBackground(Drawable activateBackground) {
        backgroundHelper.setActivatedBackground(activateBackground);
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setActivatedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public XBackgroundHelper clearBackgrounds() {
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
    public void setUnEnabledTextColor(int color) {
        textViewHelper.setUnEnabledTextColor(color);
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
    public void setUnEnabledBackgroundGradientStartColor(int startColor) {
        backgroundHelper.setUnEnabledBackgroundGradientStartColor(startColor);
    }

    @Override
    public void setUnEnabledBackgroundGradientEndColor(int endColor) {
        backgroundHelper.setUnEnabledBackgroundGradientEndColor(endColor);
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
    public void setUnEnabledBackgroundColor(int color) {
        backgroundHelper.setUnEnabledBackgroundColor(color);
    }
}
