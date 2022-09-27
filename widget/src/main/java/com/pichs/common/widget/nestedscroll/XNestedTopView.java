package com.pichs.common.widget.nestedscroll;

public interface XNestedTopView extends XNestedScrollCommon {
    /**
     * consume scroll
     * @param dyUnconsumed the delta value to consume
     * @return the remain unconsumed value
     */
    int consumeScroll(int dyUnconsumed);

    int getCurrentScroll();

    int getScrollOffsetRange();
}