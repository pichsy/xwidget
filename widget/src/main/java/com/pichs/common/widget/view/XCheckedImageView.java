package com.pichs.common.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XCardImageView;

/**
 * 带有选中效果的ImageView
 */
public class XCheckedImageView extends XCardImageView implements Checkable {

    private Drawable checkedDrawable;
    private Drawable normalDrawable;
    private boolean isChecked = false;

    public XCheckedImageView(@NonNull Context context) {
        this(context, null);
    }

    public XCheckedImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCheckedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context, attrs, defStyleAttr);
    }

    private void initThis(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XCheckedImageView);
            this.checkedDrawable = ta.getDrawable(R.styleable.XCheckedImageView_xp_checked_src);
            this.normalDrawable = ta.getDrawable(R.styleable.XCheckedImageView_android_src);
            this.isChecked = ta.getBoolean(R.styleable.XCheckedImageView_xp_checked, false);
            ta.recycle();
        }
        super.setImageDrawable(this.isChecked ? this.checkedDrawable : this.normalDrawable);
    }

    public void setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
        if (this.isChecked) {
            super.setImageDrawable(this.checkedDrawable);
        }
    }

    /**
     * @hide {@link #setNormalImageDrawable(Drawable)}
     */
    @Deprecated
    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        setNormalImageDrawable(drawable);
    }

    public void setNormalImageDrawable(@Nullable Drawable drawable) {
        this.normalDrawable = drawable;
        if (!this.isChecked) {
            super.setImageDrawable(drawable);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked == checked) {
            return;
        }
        this.isChecked = checked;
        super.setImageDrawable(this.isChecked ? this.checkedDrawable : this.normalDrawable);
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

}