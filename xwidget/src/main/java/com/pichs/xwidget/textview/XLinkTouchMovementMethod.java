package com.pichs.xwidget.textview;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 配合 {@link XLinkTouchDecorHelper} 使用
 */
public class XLinkTouchMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        return sHelper.onTouchEvent(widget, buffer, event)
                || Touch.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new XLinkTouchMovementMethod();

        return sInstance;
    }

    private static XLinkTouchMovementMethod sInstance;
    private static XLinkTouchDecorHelper sHelper = new XLinkTouchDecorHelper();

}
