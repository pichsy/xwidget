package com.pichs.common.widget.interpolator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @Description: 动画工具类
 * @Author: WuBo
 * @CreateDate: 2020/11/20$ 18:19$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/20$ 18:19$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AnimationUtils {

    /**
     * Q弹效果的弹窗。
     *
     * @param view View
     */
    public static void overScaleAnimation(View view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, AnimationProperty.scaleX, 0.2f, 1f),
                ObjectAnimator.ofFloat(view, AnimationProperty.scaleY, 0.2f, 1f),
                ObjectAnimator.ofFloat(view, AnimationProperty.alpha, 0.0f, 1f)
        );
        set.setInterpolator(XInterpolators.anticipateOvershoot);
        set.setDuration(520);
        set.start();
    }

    /**
     * Q弹效果的弹窗。
     *
     * @param view View
     */
    public static void scaleAnimation(View view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, AnimationProperty.scaleX, 0.2f, 1f),
                ObjectAnimator.ofFloat(view, AnimationProperty.scaleY, 0.2f, 1f),
                ObjectAnimator.ofFloat(view, AnimationProperty.alpha, 0.0f, 1f)
        );
        set.setInterpolator(XInterpolators.linearOutSlowIn);
        set.setDuration(500);
        set.start();
    }

}
