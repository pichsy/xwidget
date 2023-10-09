package com.pichs.xwidget.space;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 一个填充控件 填充空位，代替Space
 * google弃用了 Space，但是有时开发挺需要。
 */
public class XSpace extends View {

    public XSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getVisibility() == VISIBLE) {
            setVisibility(INVISIBLE);
        }
    }

    public XSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSpace(Context context) {
        this(context, null);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        // TODO("NOthing need do")
        // super.draw(canvas);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            super.setVisibility(View.INVISIBLE);
        } else {
            super.setVisibility(visibility);
        }
    }

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
                getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
    }
}
