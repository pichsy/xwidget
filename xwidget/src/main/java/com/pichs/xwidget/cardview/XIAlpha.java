package com.pichs.xwidget.cardview;

public interface XIAlpha {

    /**
     * 正常状态下的Alpha效果
     *
     * @param alpha 透明度
     */
    void setNormalAlpha(float alpha);

    /**
     * 按压状态下的Alpha效果
     *
     * @param alpha 透明度
     */
    void setAlphaOnPressed(float alpha);

    /**
     * 设置按钮的Alpha效果
     *
     * @param alpha 透明度
     */
    // 不可点击状态的Alpha大小
    void setAlphaOnDisabled(float alpha);

    /**
     * 正常状态的大小
     *
     * @param scaleRate 缩放比例
     */
    void setNormalScale(float scaleRate);

    /**
     * 按压按钮变化，变大或者变小。
     *
     * @param scaleRate 缩放比例
     */
    void setScaleOnPressed(float scaleRate);

    /**
     * 不可点击状态的scale大小
     *
     * @param scaleRate 缩放比例
     */
    void setScaleOnDisabled(float scaleRate);
}
