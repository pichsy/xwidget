package com.pichs.xwidget.checkbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.XCardImageView;

/**
 * 自定义CheckBox，使用更舒适
 * 复选框
 * 放在 XRadioGroup 中会自动变成单选框
 */
public class XCheckBox extends XCardImageView implements Checkable, View.OnClickListener {

    protected Drawable checkedDrawable;
    protected Drawable normalDrawable;
    protected boolean isChecked = false;
    protected boolean mCanClick = true;
    protected OnCheckedChangeListener mChangeListener;

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
            this.checkedDrawable = ta.getDrawable(R.styleable.XCheckBox_xp_checked_src);
            this.normalDrawable = ta.getDrawable(R.styleable.XCheckBox_android_src);
            this.isChecked = ta.getBoolean(R.styleable.XCheckBox_xp_checked, false);
            this.mCanClick = ta.getBoolean(R.styleable.XCheckBox_xp_clickable, true);
            ta.recycle();
        }
        Log.d("XCheckBox", "mClickable: " + mCanClick);
        super.setImageDrawable(isChecked ? this.checkedDrawable : normalDrawable);
        super.setClickable(true);
        super.setOnClickListener(this);
    }

    /**
     * 设置选中状态
     *
     * @param checkedDrawable 选中状态的图片
     */
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

    /**
     * 设置未选中状态的图片
     *
     * @param drawable the Drawable to set, or {@code null} to clear the
     *                 content
     */
    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        this.normalDrawable = drawable;
        if (!this.isChecked) {
            super.setImageDrawable(drawable);
        }
    }

    /**
     * 设置未选中状态的图片
     *
     * @param drawable the Drawable to set, or {@code null} to clear the
     *                 content
     * {@link #setImageDrawable(Drawable)}
     */
    public void setNormalImageDrawable(@Nullable Drawable drawable) {
        setImageDrawable(drawable);
    }

    /**
     * @hide 隐藏方法
     * @param l 点击事件监听器
     */
    @Deprecated
    @Override
    public final void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    /**
     *  选中
     * @param checked checked
     */
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
        if (mChangeListener != null) {
            mChangeListener.onCheckedChanged(this, this.isChecked);
        }
    }

    /**
     * @return 选中状态
     */
    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    /**
     * 切换选中状态
     */
    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        Log.d("XCheckBox", "mCanClick: " + mCanClick);
        if (this.mCanClick) {
            toggle();
        }
    }

    /**
     * 选中时间监听
     *
     * @param listener listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mChangeListener = listener;
    }

}
