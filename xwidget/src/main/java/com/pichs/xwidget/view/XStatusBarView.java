package com.pichs.xwidget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.pichs.xwidget.utils.XStatusBarHelper;

/**
 * StatusBarView
 * 绘制的状态栏高度 View，
 * 可设置背景色
 */
public class XStatusBarView extends XView {

    private final int statusBarHeight;

    public XStatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        statusBarHeight = XStatusBarHelper.getStatusBarHeight(context);
    }

    public XStatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XStatusBarView(Context context) {
        this(context, null);
    }

    @Override
    public void initChecked(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Checkable owner) {
        super.initChecked(context, attrs, defStyleAttr, defStyleRes, owner);
    }

    /**
     * Compare to: {@link View#getDefaultSize(int, int)}
     * If mode is AT_MOST, return the child size instead of the parent size
     * (unless it is too big).
     */
    private static int getDefaultSize2(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
                statusBarHeight);
    }
}
