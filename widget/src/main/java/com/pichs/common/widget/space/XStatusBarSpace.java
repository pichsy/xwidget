package com.pichs.common.widget.space;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.pichs.common.widget.utils.XStatusBarHelper;

/**
 * StatusBarSpace
 * 不会绘制的状态栏占位 Space
 */
public class XStatusBarSpace extends View {

    private final int statusBarHeight;

    public XStatusBarSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getVisibility() == VISIBLE) {
            setVisibility(INVISIBLE);
        }
        statusBarHeight = XStatusBarHelper.getStatusBarHeight(context);
    }

    public XStatusBarSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XStatusBarSpace(Context context) {
        this(context, null);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
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
