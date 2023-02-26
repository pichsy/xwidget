package com.pichs.common.widget.nestedscroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;

public class XNestedBottomAreaBehavior extends XViewOffsetBehavior<View> {

    private final Rect tempRect1 = new Rect();
    private final Rect tempRect2 = new Rect();

    private int mTopInset = 0;

    public void setTopInset(int topInset) {
        mTopInset = topInset;
    }

    public XNestedBottomAreaBehavior() {
    }

    public XNestedBottomAreaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int childLpHeight = child.getLayoutParams().height;
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {

            int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
            if (availableHeight == 0) {
                availableHeight = parent.getHeight();
            }

            availableHeight -= mTopInset;

            final int heightMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(
                            availableHeight,
                            childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                                    ? View.MeasureSpec.EXACTLY
                                    : View.MeasureSpec.AT_MOST);

            parent.onMeasureChild(
                    child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

            return true;
        }
        return false;
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        List<View> dependencies = parent.getDependencies(child);
        if (!dependencies.isEmpty()) {
            View topView = dependencies.get(0);
            final CoordinatorLayout.LayoutParams lp =
                    (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            final Rect available = tempRect1;
            available.set(
                    parent.getPaddingLeft() + lp.leftMargin,
                    topView.getBottom() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    parent.getHeight() + topView.getBottom() - parent.getPaddingBottom() - lp.bottomMargin);

            final Rect out = tempRect2;
            GravityCompat.apply(
                    resolveGravity(lp.gravity),
                    child.getMeasuredWidth(),
                    child.getMeasuredHeight(),
                    available,
                    out,
                    layoutDirection);

            child.layout(out.left, out.top, out.right, out.bottom);
        } else {
            super.layoutChild(parent, child, layoutDirection);
        }
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        boolean ret = super.onLayoutChild(parent, child, layoutDirection);
        List<View> dependencies = parent.getDependencies(child);
        if (!dependencies.isEmpty()) {
            View topView = dependencies.get(0);
            setTopAndBottomOffset(topView.getBottom() - getLayoutTop());
        }
        return ret;
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof XNestedTopView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        setTopAndBottomOffset(dependency.getBottom() - getLayoutTop());
        return false;
    }
}