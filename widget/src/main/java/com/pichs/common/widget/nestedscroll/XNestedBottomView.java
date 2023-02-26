package com.pichs.common.widget.nestedscroll;

public interface XNestedBottomView extends XNestedScrollCommon {
    int HEIGHT_IS_ENOUGH_TO_SCROLL = -1;

    /**
     * consume scroll
     *
     * @param dyUnconsumed the delta value to consume
     */
    void consumeScroll(int dyUnconsumed);

    void smoothScrollYBy(int dy, int duration);

    void stopScroll();

    /**
     * sometimes the content of BottomView is not enough to scroll,
     * so BottomView should tell the this info to  {@link XNestedScrollLayout}
     *
     * @return {@link #HEIGHT_IS_ENOUGH_TO_SCROLL} if can scroll, or content height.
     */
    int getContentHeight();

    int getCurrentScroll();

    int getScrollOffsetRange();
}