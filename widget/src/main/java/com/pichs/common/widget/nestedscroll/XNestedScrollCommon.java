package com.pichs.common.widget.nestedscroll;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface XNestedScrollCommon {

    int SCROLL_STATE_IDLE = RecyclerView.SCROLL_STATE_IDLE;
    int SCROLL_STATE_DRAGGING = RecyclerView.SCROLL_STATE_DRAGGING;
    int SCROLL_STATE_SETTLING = RecyclerView.SCROLL_STATE_SETTLING;

    void saveScrollInfo(@NonNull Bundle bundle);

    void restoreScrollInfo(@NonNull Bundle bundle);

    void injectScrollNotifier(OnScrollNotifier notifier);

    interface OnScrollNotifier {
        void notify(int innerOffset, int innerRange);

        void onScrollStateChange(View view, int newScrollState);
    }
}
