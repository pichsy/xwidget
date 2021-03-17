package com.pichs.common.widget.checkbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
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
    private boolean mCanClick = true;

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
            this.checkedDrawable = ta.getDrawable(R.styleable.XCheckBox_xp_src_checked);
            this.normalDrawable = ta.getDrawable(R.styleable.XCheckBox_android_src);
            this.isChecked = ta.getBoolean(R.styleable.XCheckBox_xp_checked, false);
            this.mCanClick = ta.getBoolean(R.styleable.XCheckBox_xp_clickable, true);
            ta.recycle();
        }
        Log.d("XCheckBox", "mClickable: " + mCanClick);
        setChecked(this.isChecked);
        super.setClickable(true);
        super.setOnClickListener(this);
    }

    public void setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
        if (this.isChecked) {
            super.setImageDrawable(this.checkedDrawable);
        }
    }

    /**
     * 是否开启可点击
     *
     * @param canClick canClick
     */
    public final void setCanClick(boolean canClick) {
        this.mCanClick = canClick;
    }

    /**
     * isCanClick
     *
     * @return isCanClick
     */
    public final boolean isCanClick() {
        return this.mCanClick;
    }

    /**
     * 请使用 {@link #setCanClick(boolean)}
     *
     * @param clickable clickable
     * @hide {@link #setClickable(boolean)}
     */
    @Deprecated
    @Override
    public final void setClickable(boolean clickable) {
        super.setClickable(clickable);
    }

    /**
     * 请使用 {@link #isCanClick()}
     *
     * @hide {@link #isClickable()}
     */
    @Deprecated
    @Override
    public final boolean isClickable() {
        return super.isClickable();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        this.normalDrawable = drawable;
        if (!this.isChecked) {
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
    public final void setOnClickListener(@Nullable OnClickListener l) {
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked == checked) {
            return;
        }
        this.isChecked = checked;
        if (this.isChecked) {
            super.setImageDrawable(this.checkedDrawable);
        } else {
            super.setImageDrawable(this.normalDrawable);
        }
        if (this.mChangeListener != null) {
            this.mChangeListener.onCheckedChanged(this.isChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }

    @Override
    public void onClick(View v) {
        Log.d("XCheckBox", "mCanClick: " + mCanClick);
        if (this.mCanClick) {
            toggle();
        }
    }

    private OnCheckedChangeListener mChangeListener;

    /**
     * 选中时间监听
     *
     * @param listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mChangeListener = listener;
    }


    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
