package com.pichs.common.widget.nestedscroll;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pichs.common.widget.utils.XLangHelper;

import java.util.ArrayList;
import java.util.List;

public class XNestedScrollLayout extends CoordinatorLayout implements
        XNestedTopAreaBehavior.Callback, XDraggableScrollBar.Callback {
    public static final String KEY_SCROLL_INFO_OFFSET = "@xwidget_nested_scroll_layout_offset";

    private XNestedTopView mTopView;
    private XNestedBottomView mBottomView;

    private XNestedTopAreaBehavior mTopAreaBehavior;
    private XNestedBottomAreaBehavior mBottomAreaBehavior;
    private List<OnScrollListener> mOnScrollListeners = new ArrayList<>();
    private Runnable mCheckLayoutAction = new Runnable() {
        @Override
        public void run() {
            checkLayout();
        }
    };
    private boolean mKeepBottomAreaStableWhenCheckLayout = false;
    private XDraggableScrollBar mDraggableScrollBar;
    private boolean mEnableScrollBarFadeInOut = true;
    private boolean mIsDraggableScrollBarEnabled = false;
    private int mCurrentScrollState = XNestedScrollCommon.SCROLL_STATE_IDLE;
    private boolean mIsDismissDownEvent = false;
    private float mDismissDownY = 0;
    private int mTouchSlap = -1;

    public XNestedScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public XNestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XNestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void ensureScrollBar() {
        if (mDraggableScrollBar == null) {
            mDraggableScrollBar = createScrollBar(getContext());
            mDraggableScrollBar.setEnableFadeInAndOut(mEnableScrollBarFadeInOut);
            mDraggableScrollBar.setCallback(this);
            CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.RIGHT;
            addView(mDraggableScrollBar, lp);
        }
    }

    public void setDraggableScrollBarEnabled(boolean draggableScrollBarEnabled) {
        if(mIsDraggableScrollBarEnabled != draggableScrollBarEnabled){
            mIsDraggableScrollBarEnabled = draggableScrollBarEnabled;
            if(mIsDraggableScrollBarEnabled && !mEnableScrollBarFadeInOut){
                ensureScrollBar();
                mDraggableScrollBar.setPercent(getCurrentScrollPercent());
                mDraggableScrollBar.awakenScrollBar();
            }
            if(mDraggableScrollBar != null){
                mDraggableScrollBar.setVisibility(draggableScrollBarEnabled ? View.VISIBLE: View.GONE);
            }
        }
    }

    public void setEnableScrollBarFadeInOut(boolean enableScrollBarFadeInOut) {
        if(mEnableScrollBarFadeInOut != enableScrollBarFadeInOut){
            mEnableScrollBarFadeInOut = enableScrollBarFadeInOut;
            if(mIsDraggableScrollBarEnabled && !mEnableScrollBarFadeInOut){
                ensureScrollBar();
                mDraggableScrollBar.setPercent(getCurrentScrollPercent());
                mDraggableScrollBar.awakenScrollBar();
            }
            if(mDraggableScrollBar != null){
                mDraggableScrollBar.setEnableFadeInAndOut(enableScrollBarFadeInOut);
                mDraggableScrollBar.invalidate();
            }
        }
    }

    protected XDraggableScrollBar createScrollBar(Context context) {
        return new XDraggableScrollBar(context);
    }

    @Override
    public void onDragStarted() {
        stopScroll();
    }

    @Override
    public void onDragToPercent(float percent) {
        int targetScroll = (int) (getScrollRange() * percent);
        scrollBy(targetScroll - getCurrentScroll());
    }

    @Override
    public void onDragEnd() {

    }

    public int getCurrentScroll() {
        int currentScroll = 0;
        if (mTopView != null) {
            currentScroll += mTopView.getCurrentScroll();
        }
        currentScroll += getOffsetCurrent();
        if (mBottomView != null) {
            currentScroll += mBottomView.getCurrentScroll();
        }
        return currentScroll;
    }

    public int getScrollRange() {
        int totalRange = 0;
        if (mTopView != null) {
            totalRange += mTopView.getScrollOffsetRange();
        }
        totalRange += getOffsetRange();

        if (mBottomView != null) {
            totalRange += mBottomView.getScrollOffsetRange();
        }
        return totalRange;
    }

    public float getCurrentScrollPercent() {
        int scrollRange = getScrollRange();
        if (scrollRange == 0) {
            return 0;
        }
        return getCurrentScroll() * 1f / scrollRange;
    }


    public void addOnScrollListener(@NonNull OnScrollListener onScrollListener) {
        if (!mOnScrollListeners.contains(onScrollListener)) {
            mOnScrollListeners.add(onScrollListener);
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListeners.remove(onScrollListener);
    }

    public void setKeepBottomAreaStableWhenCheckLayout(boolean keepBottomAreaStableWhenCheckLayout) {
        mKeepBottomAreaStableWhenCheckLayout = keepBottomAreaStableWhenCheckLayout;
    }

    public boolean isKeepBottomAreaStableWhenCheckLayout() {
        return mKeepBottomAreaStableWhenCheckLayout;
    }

    public void setTopAreaView(View topView, @Nullable LayoutParams layoutParams) {
        if (!(topView instanceof XNestedTopView)) {
            throw new IllegalStateException("topView must implement from XNestedTopView");
        }
        if (mTopView != null) {
            removeView(((View) mTopView));
        }
        mTopView = (XNestedTopView) topView;
        mTopView.injectScrollNotifier(new XNestedScrollCommon.OnScrollNotifier() {
            @Override
            public void notify(int innerOffset, int innerRange) {
                int offsetCurrent = mTopAreaBehavior == null ? 0 : -mTopAreaBehavior.getTopAndBottomOffset();
                int bottomCurrent = mBottomView == null ? 0 : mBottomView.getCurrentScroll();
                int bottomRange = mBottomView == null ? 0 : mBottomView.getScrollOffsetRange();
                dispatchScroll(innerOffset, innerRange, offsetCurrent, getOffsetRange(), bottomCurrent, bottomRange);
            }

            @Override
            public void onScrollStateChange(View view, int newScrollState) {
                // not need this. top view scroll is driven by top behavior
            }
        });
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        Behavior behavior = layoutParams.getBehavior();
        if (behavior instanceof XNestedTopAreaBehavior) {
            mTopAreaBehavior = (XNestedTopAreaBehavior) behavior;
        } else {
            mTopAreaBehavior = new XNestedTopAreaBehavior(getContext());
            layoutParams.setBehavior(mTopAreaBehavior);
        }
        mTopAreaBehavior.setCallback(this);
        addView(topView, 0, layoutParams);
    }

    public XNestedTopView getTopView() {
        return mTopView;
    }

    public XNestedBottomView getBottomView() {
        return mBottomView;
    }

    public XNestedTopAreaBehavior getTopAreaBehavior() {
        return mTopAreaBehavior;
    }

    public XNestedBottomAreaBehavior getBottomAreaBehavior() {
        return mBottomAreaBehavior;
    }

    public void setBottomAreaView(View bottomView, @Nullable LayoutParams layoutParams) {
        if (!(bottomView instanceof XNestedBottomView)) {
            throw new IllegalStateException("bottomView must implement from IQMUIContinuousNestedBottomView");
        }
        if (mBottomView != null) {
            removeView(((View) mBottomView));
        }
        mBottomView = (XNestedBottomView) bottomView;
        mBottomView.injectScrollNotifier(new XNestedBottomView.OnScrollNotifier() {
            @Override
            public void notify(int innerOffset, int innerRange) {
                int topCurrent = mTopView == null ? 0 : mTopView.getCurrentScroll();
                int topRange = mTopView == null ? 0 : mTopView.getScrollOffsetRange();
                int offsetCurrent = mTopAreaBehavior == null ? 0 : -mTopAreaBehavior.getTopAndBottomOffset();
                dispatchScroll(topCurrent, topRange, offsetCurrent, getOffsetRange(), innerOffset, innerRange);
            }

            @Override
            public void onScrollStateChange(View view, int newScrollState) {
                dispatchScrollStateChange(newScrollState, false);
            }
        });
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        Behavior behavior = layoutParams.getBehavior();
        if (behavior instanceof XNestedBottomAreaBehavior) {
            mBottomAreaBehavior = (XNestedBottomAreaBehavior) behavior;
        } else {
            mBottomAreaBehavior = new XNestedBottomAreaBehavior();
            layoutParams.setBehavior(mBottomAreaBehavior);
        }
        addView(bottomView, 0, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        postCheckLayout();
    }

    public void postCheckLayout() {
        removeCallbacks(mCheckLayoutAction);
        post(mCheckLayoutAction);
    }

    public void checkLayout() {
        if (mTopView == null || mBottomView == null) {
            return;
        }
        int topCurrent = mTopView.getCurrentScroll();
        int topRange = mTopView.getScrollOffsetRange();
        int offsetCurrent = -mTopAreaBehavior.getTopAndBottomOffset();
        int offsetRange = getOffsetRange();

        if (offsetRange <= 0) {
            return;
        }

        if (offsetCurrent >= offsetRange || (offsetCurrent > 0 && mKeepBottomAreaStableWhenCheckLayout)) {
            mTopView.consumeScroll(Integer.MAX_VALUE);
            if(mBottomView.getCurrentScroll() > 0){
                mTopAreaBehavior.setTopAndBottomOffset(-offsetRange);
            }
            return;
        }

        if (mBottomView.getCurrentScroll() > 0) {
            mBottomView.consumeScroll(Integer.MIN_VALUE);
        }

        if (topCurrent < topRange && offsetCurrent > 0) {
            int remain = topRange - topCurrent;
            if (offsetCurrent >= remain) {
                mTopView.consumeScroll(Integer.MAX_VALUE);
                mTopAreaBehavior.setTopAndBottomOffset(remain - offsetCurrent);
            } else {
                mTopView.consumeScroll(offsetCurrent);
                mTopAreaBehavior.setTopAndBottomOffset(0);
            }
        }
    }

    public void scrollBottomViewToTop() {
        if (mTopView != null) {
            mTopView.consumeScroll(Integer.MAX_VALUE);
        }

        if (mBottomView != null) {
            mBottomView.consumeScroll(Integer.MIN_VALUE);

            int contentHeight = mBottomView.getContentHeight();
            if (contentHeight != XNestedBottomView.HEIGHT_IS_ENOUGH_TO_SCROLL) {
                mTopAreaBehavior.setTopAndBottomOffset(Math.min(0, getHeight() - contentHeight - ((View) mTopView).getHeight()));
            } else {
                mTopAreaBehavior.setTopAndBottomOffset(
                        getHeight() - ((View) mBottomView).getHeight() - ((View) mTopView).getHeight());
            }
        }
    }

    private void dispatchScroll(int topCurrent, int topRange,
                                int offsetCurrent, int offsetRange,
                                int bottomCurrent, int bottomRange) {
        if (mIsDraggableScrollBarEnabled) {
            ensureScrollBar();
            mDraggableScrollBar.setPercent(getCurrentScrollPercent());
            mDraggableScrollBar.awakenScrollBar();

        }
        for (OnScrollListener onScrollListener : mOnScrollListeners) {
            onScrollListener.onScroll(this, topCurrent, topRange, offsetCurrent, offsetRange,
                    bottomCurrent, bottomRange);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if(dyUnconsumed > 0 && getCurrentScroll() >= getScrollRange()){
            // RecyclerView does not stop scroller when over scroll with NestedScrollingParent
            stopScroll();
        }
    }

    private void dispatchScrollStateChange(int newScrollState, boolean fromTopBehavior) {
        for (OnScrollListener onScrollListener : mOnScrollListeners) {
            onScrollListener.onScrollStateChange(this, newScrollState, fromTopBehavior);
        }
        mCurrentScrollState = newScrollState;
    }

    public void scrollBy(int dy) {
        if ((dy > 0 || mBottomView == null) && mTopAreaBehavior != null) {
            mTopAreaBehavior.scroll(this, ((View) mTopView), dy);
        } else if (dy != 0 && mBottomView != null) {
            mBottomView.consumeScroll(dy);
        }
    }

    public void smoothScrollBy(int dy, int duration) {
        if (dy == 0) {
            return;
        }
        if ((dy > 0 || mBottomView == null) && mTopAreaBehavior != null) {
            mTopAreaBehavior.smoothScrollBy(this, ((View) mTopView), dy, duration);
        } else if (mBottomView != null) {
            mBottomView.smoothScrollYBy(dy, duration);
        }
    }

    public void stopScroll() {
        if (mBottomView != null) {
            mBottomView.stopScroll();
        }
        if (mTopAreaBehavior != null) {
            mTopAreaBehavior.stopFlingOrScroll();
        }
    }

    public void scrollToTop() {
        if (mBottomView != null) {
            mBottomView.consumeScroll(Integer.MIN_VALUE);
        }
        if (mTopView != null) {
            mTopAreaBehavior.setTopAndBottomOffset(0);
            mTopView.consumeScroll(Integer.MIN_VALUE);
        }
    }


    public void scrollToBottom() {
        if (mTopView != null) {
            // consume the max value
            mTopView.consumeScroll(Integer.MAX_VALUE);
            if (mBottomView != null) {
                int contentHeight = mBottomView.getContentHeight();
                if (contentHeight != XNestedBottomView.HEIGHT_IS_ENOUGH_TO_SCROLL) {
                    // bottomView can not scroll
                    View topView = (View) mTopView;
                    if (topView.getHeight() + contentHeight < getHeight()) {
                        mTopAreaBehavior.setTopAndBottomOffset(0);
                    } else {
                        mTopAreaBehavior.setTopAndBottomOffset(
                                getHeight() - contentHeight - ((View) mTopView).getHeight());
                    }
                } else {
                    mTopAreaBehavior.setTopAndBottomOffset(
                            getHeight() - ((View) mBottomView).getHeight() - ((View) mTopView).getHeight());
                }
            }
        }
        if (mBottomView != null) {
            mBottomView.consumeScroll(Integer.MAX_VALUE);
        }
    }

    public int getOffsetCurrent() {
        return mTopAreaBehavior == null ? 0 : -mTopAreaBehavior.getTopAndBottomOffset();
    }

    public int getOffsetRange() {
        if (mTopView == null || mBottomView == null) {
            return 0;
        }
        int contentHeight = mBottomView.getContentHeight();
        if (contentHeight != XNestedBottomView.HEIGHT_IS_ENOUGH_TO_SCROLL) {
            return Math.max(0, ((View) mTopView).getHeight() + contentHeight - getHeight());
        }
        return Math.max(0, ((View) mTopView).getHeight() + ((View) mBottomView).getHeight() - getHeight());
    }

    @Override
    public void onTopAreaOffset(int offset) {
        int topCurrent = mTopView == null ? 0 : mTopView.getCurrentScroll();
        int topRange = mTopView == null ? 0 : mTopView.getScrollOffsetRange();
        int bottomCurrent = mBottomView == null ? 0 : mBottomView.getCurrentScroll();
        int bottomRange = mBottomView == null ? 0 : mBottomView.getScrollOffsetRange();
        dispatchScroll(topCurrent, topRange, -offset, getOffsetRange(), bottomCurrent, bottomRange);
    }

    @Override
    public void onTopBehaviorTouchBegin() {
        dispatchScrollStateChange(
                XNestedScrollCommon.SCROLL_STATE_DRAGGING, true);
    }

    @Override
    public void onTopBehaviorTouchEnd() {
        dispatchScrollStateChange(
                XNestedScrollCommon.SCROLL_STATE_IDLE, true);
    }

    @Override
    public void onTopBehaviorFlingOrScrollStart() {
        dispatchScrollStateChange(
                XNestedScrollCommon.SCROLL_STATE_SETTLING, true);
    }

    @Override
    public void onTopBehaviorFlingOrScrollEnd() {
        dispatchScrollStateChange(
                XNestedScrollCommon.SCROLL_STATE_IDLE, true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if(mCurrentScrollState != XNestedScrollCommon.SCROLL_STATE_IDLE){
                // must stop scroll and not use the current down event.
                // this is worked when topView scroll to bottomView or bottomView scroll to topView.
                stopScroll();
                mIsDismissDownEvent = true;
                mDismissDownY = ev.getY();
                if(mTouchSlap < 0){
                    mTouchSlap = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                }
                return true;
            }
        } else if(ev.getAction() == MotionEvent.ACTION_MOVE && mIsDismissDownEvent){
            if(Math.abs(ev.getY() - mDismissDownY) > mTouchSlap){
                MotionEvent down = MotionEvent.obtain(ev);
                down.setAction(MotionEvent.ACTION_DOWN);
                down.offsetLocation(0, mDismissDownY - ev.getY());
                super.dispatchTouchEvent(down);
                down.recycle();
            }else{
                return true;
            }
        }
        mIsDismissDownEvent = false;
        return super.dispatchTouchEvent(ev);
    }

    /**
     * save current scroll info to bundle
     *
     * @param bundle
     */
    public void saveScrollInfo(@NonNull Bundle bundle) {
        if (mTopView != null) {
            mTopView.saveScrollInfo(bundle);
        }
        if (mBottomView != null) {
            mBottomView.saveScrollInfo(bundle);
        }
        bundle.putInt(KEY_SCROLL_INFO_OFFSET, getOffsetCurrent());
    }


    /**
     * restore current scroll info from bundle
     *
     * @param bundle
     */
    public void restoreScrollInfo(@Nullable Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (mTopAreaBehavior != null) {
            int offset = bundle.getInt(KEY_SCROLL_INFO_OFFSET, 0);
            mTopAreaBehavior.setTopAndBottomOffset(XLangHelper.constrain(-offset, -getOffsetRange(), 0));
        }
        if (mTopView != null) {
            mTopView.restoreScrollInfo(bundle);
        }

        if (mBottomView != null) {
            mBottomView.restoreScrollInfo(bundle);
        }
    }

    public interface OnScrollListener {

        void onScroll(XNestedScrollLayout scrollLayout, int topCurrent, int topRange,
                      int offsetCurrent, int offsetRange,
                      int bottomCurrent, int bottomRange);

        void onScrollStateChange(XNestedScrollLayout scrollLayout, int newScrollState, boolean fromTopBehavior);
    }
}
