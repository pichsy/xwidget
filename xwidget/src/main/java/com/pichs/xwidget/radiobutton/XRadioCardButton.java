package com.pichs.xwidget.radiobutton;

import android.content.Context;
import android.util.AttributeSet;

import com.pichs.xwidget.cardview.XCardButton;
import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.utils.XRadioItemHelper;

public class XRadioCardButton extends XCardButton implements XRadioButtonCheckable {

    private final XRadioItemHelper mRadioItemHelper;

    public XRadioCardButton(Context context) {
       this(context, null);
    }

    public XRadioCardButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRadioCardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadioItemHelper = new XRadioItemHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public boolean isIgnoreRadioGroup() {
        if (mRadioItemHelper == null) return false;
        return mRadioItemHelper.isIgnoreRadioGroup();
    }

    @Override
    public void setIgnoreRadioGroup(boolean isIgnoreRadioGroup) {
        if (mRadioItemHelper == null) return;
        mRadioItemHelper.setIgnoreRadioGroup(isIgnoreRadioGroup);
    }

    @Override
    public boolean isRadioButtonMode() {
        if (mRadioItemHelper == null) return false;
        return mRadioItemHelper.isRadioButtonMode();
    }

    @Override
    public void setRadioButtonMode(boolean isRadioButtonMode) {
        if (mRadioItemHelper != null) {
            mRadioItemHelper.setRadioButtonMode(isRadioButtonMode);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked() == checked) {
            return;
        }
        super.setChecked(checked);
        if (mRadioItemHelper != null) {
            mRadioItemHelper.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        return super.isChecked();
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    /**
     * 选中时间监听
     *
     * @param listener listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (mRadioItemHelper == null) {
            return;
        }
        mRadioItemHelper.setOnCheckedChangeListener(listener);
    }
}
