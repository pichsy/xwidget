package com.pichs.xwidget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/*
 * -----------------------------------------------------------------
 * desc:解决AppBarLayout滑动时抖动问题
 * -----------------------------------------------------------------
 */
public class FixAppBarLayoutBehavior extends AppBarLayout.Behavior {
    /**
     * 是否处于惯性滑动状态
     */
    private boolean isFlinging = false;
    public FixAppBarLayoutBehavior() {}

    public FixAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        //如果不是惯性滑动,让他可以执行紧贴操作
        if (!isFlinging) {
            super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        }
    }
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //type==1时处于非惯性滑动
        if (type == 1) {
            isFlinging = false;
        }
    }
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        //惯性滑动的时候设置为true
        isFlinging = true;
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}