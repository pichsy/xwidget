package com.pichs.common.widget.textview;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class XLinkTouchDecorHelper {
    private WeakReference<IXTouchableSpan> mPressedSpanRf;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            IXTouchableSpan span = getPressedSpan(textView, spannable, event);
            if (span != null) {
                span.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(span),
                        spannable.getSpanEnd(span));
                mPressedSpanRf = new WeakReference<>(span);
            }
            if (textView instanceof XSpanTouch) {
                XSpanTouch tv = (XSpanTouch) textView;
                tv.setTouchSpanHit(span != null);
            }
            return span != null;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            IXTouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            IXTouchableSpan recordSpan = null;
            if (mPressedSpanRf != null){
                recordSpan = mPressedSpanRf.get();
            }

            if(recordSpan != null && recordSpan != touchedSpan){
                recordSpan.setPressed(false);
                mPressedSpanRf = null;
                recordSpan = null;
                Selection.removeSelection(spannable);
            }
            if (textView instanceof XSpanTouch) {
                XSpanTouch tv = (XSpanTouch) textView;
                tv.setTouchSpanHit(recordSpan != null);
            }
            return recordSpan != null;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchSpanHint = false;
            IXTouchableSpan recordSpan = null;
            if (mPressedSpanRf != null){
                recordSpan = mPressedSpanRf.get();
            }
            if (recordSpan != null) {
                touchSpanHint = true;
                recordSpan.setPressed(false);
                if(event.getAction() == MotionEvent.ACTION_UP){
                    recordSpan.onClick(textView);
                }
            }

            mPressedSpanRf = null;
            Selection.removeSelection(spannable);
            if (textView instanceof XSpanTouch) {
                XSpanTouch tv = (XSpanTouch) textView;
                tv.setTouchSpanHit(touchSpanHint);
            }
            return touchSpanHint;
        } else {
            IXTouchableSpan recordSpan = null;
            if (mPressedSpanRf != null){
                recordSpan = mPressedSpanRf.get();
            }
            if (recordSpan != null) {
                recordSpan.setPressed(false);
            }
            if (textView instanceof XSpanTouch) {
                XSpanTouch tv = (XSpanTouch) textView;
                tv.setTouchSpanHit(false);
            }
            mPressedSpanRf = null;
            Selection.removeSelection(spannable);
            return false;
        }

    }

    public IXTouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);

        /*
         * BugFix: https://issuetracker.google.com/issues/113348914
         */
        try {
            int off = layout.getOffsetForHorizontal(line, x);
            if (x < layout.getLineLeft(line) || x > layout.getLineRight(line)) {
                // 实际上没点到任何内容
                off = -1;
            }
            IXTouchableSpan[] link = spannable.getSpans(off, off, IXTouchableSpan.class);
            IXTouchableSpan touchedSpan = null;
            if (link.length > 0) {
                touchedSpan = link[0];
            }
            return touchedSpan;
        } catch (IndexOutOfBoundsException e) {
        }
        return null;
    }
}
