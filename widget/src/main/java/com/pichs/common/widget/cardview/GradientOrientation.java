package com.pichs.common.widget.cardview;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author pichs
 * 渐变色方向管理类
 */
@IntDef({
        GradientOrientation.HORIZONTAL,
        GradientOrientation.VERTICAL,
        GradientOrientation.TL_BR,
        GradientOrientation.BL_TR
})
@Retention(RetentionPolicy.SOURCE)
public @interface GradientOrientation {
    /**
     * 渐变色方向 横向 ，从左到右   （左右）
     */
    int HORIZONTAL = 0;
    /**
     * 渐变色方向，竖向，从上到下   （上下）
     */
    int VERTICAL = 1;
    /**
     * 渐变色仿向，斜对角，从左上到右下 （左右）
     */
    int TL_BR = 2;
    /**
     * 渐变色仿向，斜对角，从左下到右上 （左右）
     */
    int BL_TR = 3;
}