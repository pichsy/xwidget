package com.pichs.xwidget.radiobutton;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.view.XCheckedImageView;

public class XRadioImageView extends XCheckedImageView implements XRadioButton {

    private final XRadioItemHelper mRadioItemHelper;

    public XRadioImageView(@NonNull Context context) {
        this(context, null);
    }

    public XRadioImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRadioImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    public void setChecked(boolean checked) {
        Log.d("XWidget", "XRadioItemHelper XRadioImageView：setChecked: checked:" + checked);
        if (isChecked() == checked) {
            return;
        }
        super.setChecked(checked);
        if (mRadioItemHelper != null) {
            Log.d("XWidget", "XRadioItemHelper XRadioImageView--mRadioItemHelper：setChecked: checked:" + checked);
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

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (mRadioItemHelper != null) mRadioItemHelper.setOnCheckedChangeListener(listener);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (!isCheckStateFollowParent()) {
            super.setOnClickListener(l);
        }
    }

    @Override
    public void setCheckStateFollowParent(boolean followParent) {
        if (mRadioItemHelper != null) mRadioItemHelper.setCheckStateFollowParent(followParent);
    }

    @Override
    public boolean isCheckStateFollowParent() {
        return mRadioItemHelper != null && mRadioItemHelper.isCheckStateFollowParent();
    }

    @Override
    public void setCheckedByClickEnable(boolean isCheckedByClickEnable) {
        if (mRadioItemHelper != null) mRadioItemHelper.setCheckedByClickEnable(isCheckedByClickEnable);
    }

    @Override
    public boolean isCheckedByClickEnable() {
        return mRadioItemHelper != null && mRadioItemHelper.isCheckedByClickEnable();
    }

}
