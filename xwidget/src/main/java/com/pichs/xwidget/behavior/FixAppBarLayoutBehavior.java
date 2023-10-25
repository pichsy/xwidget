package com.pichs.xwidget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * -----------------------------------------------------------------
 * desc:解决AppBarLayout滑动时抖动问题
 * -----------------------------------------------------------------
 */
public class FixAppBarLayoutBehavior extends AppBarLayout.Behavior {
    /**
     * 是否处于惯性滑动状态
     */
    private boolean isFlinging = false;

    /**
     *  构造方法
     */
    public FixAppBarLayoutBehavior() {}

    /**
     * 构造方法
     * @param context 上下文
     * @param attrs 属性
     */
    public FixAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写此方法,当惯性滑动时不让执行紧贴操作
     * @param coordinatorLayout 协调布局
     * @param abl AppBarLayout
     * @param target 目标View
     * @param type 类型
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        //如果不是惯性滑动,让他可以执行紧贴操作
        if (!isFlinging) {
            super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        }
    }

    /**
     * 重写此方法,当惯性滑动时不让执行紧贴操作
     * @param coordinatorLayout 协调布局
     * @param child AppBarLayout
     * @param target 目标View
     * @param dx 滑动X轴距离
     * @param dy 滑动Y轴距离
     * @param consumed 消费
     * @param type 类型
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //type==1时处于非惯性滑动
        if (type == 1) {
            isFlinging = false;
        }
    }

    /**
     * 重写此方法,当惯性滑动时不让执行紧贴操作
     * @param coordinatorLayout 协调布局
     * @param child AppBarLayout
     * @param target 目标View
     * @param velocityX X轴速度
     * @param velocityY Y轴速度
     * @param consumed 消费
     * @return 是否消费
     */
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        //惯性滑动的时候设置为true
        isFlinging = true;
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}