package com.pichs.xwidget.nestedscroll;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import com.pichs.xwidget.cardview.XCardLinearLayout;

public class XNestedTopLinearLayout extends XCardLinearLayout implements XNestedTopView {


    public XNestedTopLinearLayout(Context context) {
        super(context);
    }

    public XNestedTopLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XNestedTopLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int consumeScroll(int dyUnconsumed) {
        return dyUnconsumed;
    }

    @Override
    public int getCurrentScroll() {
        return 0;
    }

    @Override
    public int getScrollOffsetRange() {
        return 0;
    }

    @Override
    public void injectScrollNotifier(OnScrollNotifier notifier) {

    }

    @Override
    public void restoreScrollInfo(@NonNull Bundle bundle) {

    }

    @Override
    public void saveScrollInfo(@NonNull Bundle bundle) {

    }
}
