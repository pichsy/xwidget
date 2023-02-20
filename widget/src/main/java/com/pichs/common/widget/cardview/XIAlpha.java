package com.pichs.common.widget.cardview;

public interface XIAlpha {

    // 正常状态下的Alpha效果
    void setNormalAlpha(float alpha);

    // 按压状态下的Alpha效果
    void setChangeAlphaOnPressed(float alpha);

    // 不可点击状态的Alpha大小
    void setChangeAlphaOnDisabled(float alpha);

    // 正常状态的大小
    void setNormalScale(float scaleRate);
    // 按压按钮变化，变大或者变小。
    void setChangeScaleOnPressed(float scaleRate);
    // 不可点击状态的scale大小
    void setChangeScaleOnDisabled(float scaleRate);
}
