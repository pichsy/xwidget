

package com.pichs.xwidget.shinebutton;

import android.animation.ValueAnimator;

import com.pichs.xwidget.shinebutton.interpolator.Ease;
import com.pichs.xwidget.shinebutton.interpolator.EasingInterpolator;

/**
 * ShineAnimator
 * @since 2020-01-06 11:55
 */
public class ShineAnimator extends ValueAnimator {

    private static final float DEFAULT_MAX_VALUE = 1.5f;
    private static final long DEFAULT_ANIM_DURATION = 1500;

    public ShineAnimator() {
        setFloatValues(1f, DEFAULT_MAX_VALUE);
        setDuration(DEFAULT_ANIM_DURATION);
        setStartDelay(200);
        setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
    }

    public ShineAnimator(long duration, float maxValue, long delay) {
        setFloatValues(1f, maxValue);
        setDuration(duration);
        setStartDelay(delay);
        setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
    }
}
