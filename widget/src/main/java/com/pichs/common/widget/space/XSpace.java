package com.pichs.common.widget.space;

import android.content.Context;
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

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            super.setVisibility(View.INVISIBLE);
        } else {
            super.setVisibility(visibility);
        }
    }
}
