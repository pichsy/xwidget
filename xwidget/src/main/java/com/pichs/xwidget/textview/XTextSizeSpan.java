package com.pichs.xwidget.textview;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;

/**
 * 支持调整字体大小的 span。{@link android.text.style.AbsoluteSizeSpan} 可以调整字体大小，但在中英文混排下由于 decent 的不同，
 * 无法根据具体需求进行底部对齐或者顶部对齐。而 QMUITextSizeSpan 则可以多传一个参数，让你可以根据具体情况来决定偏移值。
 */
public class XTextSizeSpan extends ReplacementSpan {
    private int mTextSize;
    private int mVerticalOffset;
    private Paint mPaint;
    private Typeface mTypeface;

    public XTextSizeSpan(int textSize, int verticalOffset){
       this(textSize, verticalOffset, null);
    }

    public XTextSizeSpan(int textSize, int verticalOffset, Typeface typeface){
        mTextSize = textSize;
        mVerticalOffset = verticalOffset;
        mTypeface = typeface;
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(mTypeface);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if(mTextSize > paint.getTextSize() && fm != null){
            Paint.FontMetricsInt newFm = mPaint.getFontMetricsInt();
            fm.descent = newFm.descent;
            fm.ascent = newFm.ascent;
            fm.top = newFm.top;
            fm.bottom = newFm.bottom;
        }
        return (int) mPaint.measureText(text, start, end);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top,
                     int y, int bottom, @NonNull Paint paint) {
        mPaint.setColor(paint.getColor());
        mPaint.setStyle(paint.getStyle());
        mPaint.setAntiAlias(paint.isAntiAlias());
        int baseline = y + mVerticalOffset;
        canvas.drawText(text, start, end, x, baseline, mPaint);
    }
}
