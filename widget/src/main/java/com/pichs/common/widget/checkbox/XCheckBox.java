package com.pichs.common.widget.checkbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XCardImageView;

public class XCheckBox extends XCardImageView implements Checkable, View.OnClickListener {

    private Drawable checkedDrawable;
    private Drawable normalDrawable;
    private boolean isChecked = false;
    private boolean isClickable = true;

    public XCheckBox(@NonNull Context context) {
        this(context, null);
    }

    public XCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context, attrs, defStyleAttr);
    }

    private void initThis(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XCheckBox);
            checkedDrawable = ta.getDrawable(R.styleable.XCheckBox_xp_src_checked);
            normalDrawable = ta.getDrawable(R.styleable.XCheckBox_android_src);
            isChecked = ta.getBoolean(R.styleable.XCheckBox_xp_checked, false);
            isClickable = ta.getBoolean(R.styleable.XCheckBox_xp_clickable, true);
            ta.recycle();
            setChecked(isChecked);
        }
        super.setOnClickListener(this);
        super.setClickable(true);
    }

    public void setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
        if (isChecked) {
            super.setImageDrawable(this.checkedDrawable);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    @Override
    public boolean isClickable() {
        return isClickable;
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        this.normalDrawable = drawable;
        if (!isChecked) {
            super.setImageDrawable(drawable);
        }
    }

    public void setNormalImageDrawable(@Nullable Drawable drawable) {
        setImageDrawable(drawable);
    }

    /**
     * @hide 隐藏方法
     */
    @Deprecated
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        // 禁止设置点击事件
        // super.setOnClickListener(l);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        if (isChecked) {
            super.setImageDrawable(checkedDrawable);
        } else {
            super.setImageDrawable(normalDrawable);
        }
        if (mChangeListener != null) {
            mChangeListener.onCheckedChanged(isChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public void onClick(View v) {
        if (isClickable) {
            toggle();
        }
    }


    private OnCheckedChangeListener mChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mChangeListener = listener;
    }


    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
