package com.pichs.common.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XIBackground;
import com.pichs.common.widget.utils.XBackgroundHelper;

/**
 * 可以属性自定义的ColorFilter的ImageView
 * 方便换肤，节省切图，降低apk体积
 */
public class XImageView extends AppCompatImageView implements XIBackground {
    private XBackgroundHelper backgroundHelper;

    private PorterDuff.Mode mMode;

    public XImageView(Context context) {
        super(context);
        init(context, null, 0);
    }


    public XImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    public void setColorFilterOverride(int color) {
        if (getCurrentMode() != null) {
            setColorFilter(color, getCurrentMode());
        } else {
            setColorFilter(color);
        }
    }

    public PorterDuff.Mode getCurrentMode() {
        return mMode;
    }

    private PorterDuff.Mode getColorFilterMode(int mode) {
        switch (mode) {
            case 0:
                return PorterDuff.Mode.SRC;
            case 1:
                return PorterDuff.Mode.SRC_ATOP;
            case 2:
                return PorterDuff.Mode.SRC_IN;
            case 3:
                return PorterDuff.Mode.SRC_OUT;
            case 4:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.MULTIPLY;
            case 6:
                return PorterDuff.Mode.DST;
            case 7:
                return PorterDuff.Mode.DST_ATOP;
            case 8:
                return PorterDuff.Mode.DST_IN;
            case 9:
                return PorterDuff.Mode.DST_OUT;
            case 10:
                return PorterDuff.Mode.DST_OVER;
            case 11:
                return PorterDuff.Mode.CLEAR;
            case 12:
                return PorterDuff.Mode.XOR;
            case 13:
                return PorterDuff.Mode.SCREEN;
            case 14:
                return PorterDuff.Mode.DARKEN;
            case 15:
                return PorterDuff.Mode.LIGHTEN;
            case 16:
                return PorterDuff.Mode.ADD;
            case 17:
                return PorterDuff.Mode.OVERLAY;
        }
        return PorterDuff.Mode.SRC_ATOP;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XBackgroundHelper(context, attrs, defStyleAttr, this);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XImageView);
            int colorFilter = ta.getColor(R.styleable.XImageView_xp_colorFilter, -1);
            int colorFilterMode = ta.getInt(R.styleable.XImageView_xp_colorFilterMode, 1);
            if (colorFilter == -1) {
                clearColorFilter();
            } else {
                mMode = getColorFilterMode(colorFilterMode);
                setColorFilter(colorFilter, mMode);
            }
            ta.recycle();
        }
    }

    @Override
    public void setNormalBackground(Drawable drawable) {
        backgroundHelper.setNormalBackground(drawable);
    }

    @Override
    public void setBackgroundGradient(int starColor, int endColor, int orientation) {
        backgroundHelper.setBackgroundGradient(starColor, endColor, orientation);
    }

    @Override
    public void setPressedBackground(Drawable pressedBackground) {
        backgroundHelper.setPressedBackground(pressedBackground);
    }

    @Override
    public void setPressedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setPressedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setUnEnabledBackground(Drawable unEnabledBackground) {
        backgroundHelper.setUnEnabledBackground(unEnabledBackground);
    }

    @Override
    public void setUnEnabledBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setUnEnabledBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setCheckedBackground(Drawable checkedBackground) {
        backgroundHelper.setCheckedBackground(checkedBackground);
    }

    @Override
    public void setCheckedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setCheckedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public void setActivatedBackground(Drawable activateBackground) {
        backgroundHelper.setActivatedBackground(activateBackground);
    }

    @Override
    public void setActivatedBackgroundGradient(int startColor, int endColor, int orientation) {
        backgroundHelper.setActivatedBackgroundGradient(startColor, endColor, orientation);
    }

    @Override
    public XBackgroundHelper clearBackgrounds() {
        return backgroundHelper.clearBackgrounds();
    }

}
