/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pichs.common.widget.interpolator;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/**
 * @author cginechen
 */
public class XInterpolators {

    public static final Interpolator linear = new LinearInterpolator();

    public static final Interpolator fastOutSlowIn = new FastOutSlowInInterpolator();

    public static final Interpolator fastOutLinearIn = new FastOutLinearInInterpolator();

    public static final Interpolator linearOutSlowIn = new LinearOutSlowInInterpolator();

    public static final Interpolator decelerate = new DecelerateInterpolator();

    public static final Interpolator accelerateDecelerate = new AccelerateDecelerateInterpolator();

    public static final Interpolator accelerate = new AccelerateInterpolator();

    public static final Interpolator anticipateOvershoot = new AnticipateOvershootInterpolator();

    public static final Interpolator overshoot = new OvershootInterpolator();

    public static final Interpolator bounce = new BounceInterpolator();

    /**
     * 质子化的, 非常顺滑的效果, 先快 后慢结合
     */
    public static final Interpolator quintic = t -> {
        t -= 1.0f;
        return t * t * t * t * t + 1.0f;
    };

    /**
     * 贝塞尔曲线动画加速器，特别好玩
     * <p>
     * 0，2位置数 > 1,3位置数，则先快后慢
     * <br/>
     * 0，2位置数 < 1,3位置数，则先慢后快
     * <br/>
     * 0,1,2,3位置数值相等时，匀速前进
     * <p>
     * eg:  (.75f,0f,.25f,1f), (.34f,.99f,.34f,.99f), (.78f,.16f,.78f,.16f)
     * 坐标生成器： https://cubic-bezier.com/#1,.22,.97,.52
     *
     * @param startX 开始点的X坐标
     * @param startY 开始点的Y坐标
     * @param endX   结束点的X坐标
     * @param endY   结束点的Y坐标
     * @return {@link CubicBezierInterpolator}
     */
    public static CubicBezierInterpolator cubicBezier(float startX, float startY, float endX, float endY) {
        return new CubicBezierInterpolator(startX, startY, endX, endY);
    }


}
