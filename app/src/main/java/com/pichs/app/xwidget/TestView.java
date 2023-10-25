package com.pichs.app.xwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

import com.pichs.xwidget.view.XView;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 2/22/21 4:06 PM
 * @UpdateUser: 吴波
 * @UpdateDate: 2/22/21 4:06 PM
 * @UpdateRemark: 更新说明
 * @Verion: 1.0
 */
public class TestView extends XView {

    public TestView(Context context) {
        super(context);
        init(context);
    }


    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint mPaint;
    private RectF mRectF = new RectF();
    private Rect mRect = new Rect();
    private Path mPath = new Path();
    private float radius = 60f;

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);


    }


    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        mRectF.set(0, 0, width, height);
//        float[] radii = {
//                radius, radius,
//                0, 0,
//                radius, radius,
//                0, 0,
//        };
//        mPath.addRoundRect(mRectF, radii, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
//                mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
                final int margin = 0 ;
                outline.setRoundRect((int) (-radius), (int) -radius, width, height, radius);
            }
        });
        setClipToOutline(true);
//        invalidate();

    }
}
