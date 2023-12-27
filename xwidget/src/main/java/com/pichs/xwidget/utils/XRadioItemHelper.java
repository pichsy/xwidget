package com.pichs.xwidget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.xwidget.R;
import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.radiobutton.XRadioButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class XRadioItemHelper implements View.OnClickListener, XRadioButton {
    private boolean isIgnoreRadioGroup = false;
    private boolean isCheckedByClickEnable = false;
    private OnCheckedChangeListener mChangeListener;

    private XRadioItemHelper() {
    }

    private final ArrayList<XRadioButton> mChildCheckableList = new ArrayList<>();

    private WeakReference<View> mOwner;
    // 选中状态是否跟随父类，如果有父类 XRadioButtonCheckable，那么选中状态跟随父类, 否则不跟随
    private boolean isCheckStateFollowParent = false;

    public XRadioItemHelper(Context context, AttributeSet attr, int defStyleAttr, View owner) {
        init(context, attr, defStyleAttr, owner);
    }

    public void init(Context context, AttributeSet attr, int defStyleAttr, View owner) {
        mOwner = new WeakReference<>(owner);
        TypedArray ta = context.obtainStyledAttributes(attr, R.styleable.XRadioItem, defStyleAttr, 0);
        isIgnoreRadioGroup = ta.getBoolean(R.styleable.XRadioItem_xp_ignore_radio_group, false);
        isCheckStateFollowParent = ta.getBoolean(R.styleable.XRadioItem_xp_check_state_follow_parent, false);
        isCheckedByClickEnable = ta.getBoolean(R.styleable.XRadioItem_xp_checked_by_click, false);
        ta.recycle();
        if (isIgnoreRadioGroup) {
            setCheckStateFollowParent(false);
        }
        if (isCheckStateFollowParent) {
            isCheckedByClickEnable = false;
        }
        setCheckedByClickEnable(isCheckedByClickEnable);
    }

    @Override
    public boolean isIgnoreRadioGroup() {
        return isIgnoreRadioGroup;
    }

    @Override
    public void setIgnoreRadioGroup(boolean isIgnoreRadioGroup) {
        this.isIgnoreRadioGroup = isIgnoreRadioGroup;
    }

    @Override
    public void setChecked(boolean checked) {
        // 处理内部状态
        if (!mChildCheckableList.isEmpty()) {
            for (XRadioButton buttonCheckable : mChildCheckableList) {
                if (buttonCheckable != null && buttonCheckable.isCheckStateFollowParent()) {
                    buttonCheckable.setChecked(checked);
                }
            }
        }
        if (mChangeListener != null) {
            mChangeListener.onCheckedChanged(mOwner.get(), checked);
        }
    }

    @Override
    public boolean isChecked() {
        if (mOwner.get() instanceof XRadioButton) {
            return ((XRadioButton) mOwner.get()).isChecked();
        }
        return false;
    }

    @Override
    public void toggle() {
        if (mOwner.get() instanceof XRadioButton) {
            ((XRadioButton) mOwner.get()).toggle();
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        if (mOwner != null && mOwner.get() != null) {
            mOwner.get().setOnClickListener(listener);
        }
    }

    public boolean isCheckStateFollowParent() {
        return isCheckStateFollowParent;
    }

    @Override
    public void setCheckedByClickEnable(boolean isCheckedByClickEnable) {
        if (!isCheckStateFollowParent() && this.isCheckedByClickEnable != isCheckedByClickEnable) {
            this.isCheckedByClickEnable = isCheckedByClickEnable;
            if (this.isCheckedByClickEnable) {
                setOnClickListener(this);
            } else {
                setOnClickListener(null);
            }
        }
    }

    @Override
    public boolean isCheckedByClickEnable() {
        return isCheckedByClickEnable;
    }

    /**
     * 选中时间监听
     *
     * @param listener listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override
    public void setCheckStateFollowParent(boolean followParent) {
        this.isCheckStateFollowParent = followParent;
    }

    @Override
    public void onClick(View v) {
        toggle();
    }

    public void onViewRemoved(View view) {
        if (view instanceof XRadioButton) {
            mChildCheckableList.remove(view);
        }
    }

    public void onViewAdded(View view) {
        if (view instanceof XRadioButton) {
            mChildCheckableList.add((XRadioButton) view);
        }
    }
}
