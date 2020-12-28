package com.pichs.common.widget.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pichs.common.widget.cardview.XIBackground;
import com.pichs.common.widget.utils.XBackgroundHelper;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/6$ 10:58$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/6$ 10:58$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XFrameLayout extends FrameLayout  implements XIBackground {

    private XBackgroundHelper backgroundHelper;

    public XFrameLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public XFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        backgroundHelper = new XBackgroundHelper(context, attrs, defStyleAttr, this);
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
