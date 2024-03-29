package com.pichs.xwidget.nestedscroll;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class XNestedTopRecyclerView extends RecyclerView implements XNestedTopView {

    public static final String KEY_SCROLL_INFO_POSITION = "@xwidget_scroll_info_top_rv_pos";
    public static final String KEY_SCROLL_INFO_OFFSET = "@xwidget_scroll_info_top_rv_offset";

    private OnScrollNotifier mScrollNotifier;
    private final int[] mScrollConsumed = new int[2];

    public XNestedTopRecyclerView(@NonNull Context context) {
        this(context, null);
        init();
    }

    public XNestedTopRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public XNestedTopRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setVerticalScrollBarEnabled(false);
    }

    @Override
    public int consumeScroll(int dyUnconsumed) {
        if (dyUnconsumed == Integer.MIN_VALUE) {
            scrollToPosition(0);
            return Integer.MIN_VALUE;
        } else if (dyUnconsumed == Integer.MAX_VALUE) {
            Adapter adapter = getAdapter();
            if (adapter != null) {
                scrollToPosition(adapter.getItemCount() - 1);
            }
            return Integer.MAX_VALUE;
        }

        boolean reStartNestedScroll = false;
        if (!hasNestedScrollingParent(ViewCompat.TYPE_TOUCH)) {
            // the scrollBy use ViewCompat.TYPE_TOUCH to handle nested scroll...
            reStartNestedScroll = true;
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);

            // and scrollBy only call dispatchNestedScroll, not call dispatchNestedPreScroll
            mScrollConsumed[0] = 0;
            mScrollConsumed[1] = 0;
            dispatchNestedPreScroll(0, dyUnconsumed, mScrollConsumed, null, ViewCompat.TYPE_TOUCH);
            dyUnconsumed -= mScrollConsumed[1];
        }
        scrollBy(0, dyUnconsumed);
        if (reStartNestedScroll) {
            stopNestedScroll(ViewCompat.TYPE_TOUCH);
        }
        return 0;
    }

    @Override
    public int getCurrentScroll() {
        return computeVerticalScrollOffset();
    }

    @Override
    public int getScrollOffsetRange() {
        return Math.max(0, computeVerticalScrollRange() - getHeight());
    }

    @Override
    public void injectScrollNotifier(OnScrollNotifier notifier) {
        mScrollNotifier = notifier;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if(mScrollNotifier != null){
            mScrollNotifier.notify(getCurrentScroll(), getScrollOffsetRange());
        }
    }

    @Override
    public void saveScrollInfo(@NonNull Bundle bundle) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            int pos = lm.findFirstVisibleItemPosition();
            View firstView = lm.findViewByPosition(pos);
            int offset = firstView == null ? 0 : firstView.getTop();
            bundle.putInt(KEY_SCROLL_INFO_POSITION, pos);
            bundle.putInt(KEY_SCROLL_INFO_OFFSET, offset);
        }
    }

    @Override
    public void restoreScrollInfo(@NonNull Bundle bundle) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int pos = bundle.getInt(KEY_SCROLL_INFO_POSITION, 0);
            int offset = bundle.getInt(KEY_SCROLL_INFO_OFFSET, 0);
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(pos, offset);
            if(mScrollNotifier != null){
                mScrollNotifier.notify(getCurrentScroll(), getScrollOffsetRange());
            }
        }
    }
}
