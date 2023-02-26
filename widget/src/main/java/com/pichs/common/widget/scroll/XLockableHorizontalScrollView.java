package com.pichs.common.widget.scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class XLockableHorizontalScrollView extends HorizontalScrollView {

    public XLockableHorizontalScrollView(Context context) {
        this(context,null);
    }

    public XLockableHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XLockableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isScrollable = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollable && super.onInterceptTouchEvent(ev);
    }

    public void setScrollable(boolean scrollable) {
        this.isScrollable = scrollable;
    }

    public boolean isScrollable() {
        return isScrollable;
    }
}
