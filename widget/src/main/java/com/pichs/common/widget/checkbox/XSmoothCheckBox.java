/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.pichs.common.widget.checkbox;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Checkable;

import androidx.core.content.ContextCompat;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XIAlpha;
import com.pichs.common.widget.utils.XAlphaHelper;
import com.pichs.common.widget.utils.XDisplayHelper;

/**
 * 自带动画的checkbox
 * <p>
 * XSmoothCheckBox
 *
 * @since 2020-01-06 9:55
 */
public class XSmoothCheckBox extends View implements Checkable , XIAlpha {
    private static final String KEY_INSTANCE_STATE = "InstanceState";

    private static final int COLOR_FLOOR_UNCHECKED = Color.parseColor("#DFDFDF");
    private XAlphaHelper xAlphaHelper;

    private static final int DEF_DRAW_SIZE = 25;
    private static final int DEF_ANIM_DURATION = 300;

    private Paint mPaint, mTickPaint, mFloorPaint;
    private Point[] mTickPoints;
    private Point mCenterPoint;
    private Path mTickPath;

    private float mLeftLineDistance, mRightLineDistance, mDrewDistance;
    private float mScaleVal = 1.0f, mFloorScale = 1.0f;
    private int mWidth, mAnimDuration, mStrokeWidth;
    private int mCheckedColor, mUnCheckedColor, mFloorColor, mFloorUnCheckedColor;

    private boolean mChecked;
    private boolean mTickDrawing;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int mTickColor;

    public XSmoothCheckBox(Context context) {
        this(context, null);
    }

    public XSmoothCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SmoothCheckBoxStyle);
    }

    public XSmoothCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XSmoothCheckBox, defStyleAttr, 0);
        mTickColor = array.getColor(R.styleable.XSmoothCheckBox_xp_cbox_color_tick, Color.WHITE);
        mAnimDuration = array.getInt(R.styleable.XSmoothCheckBox_xp_cbox_duration, DEF_ANIM_DURATION);
        mFloorColor = array.getColor(R.styleable.XSmoothCheckBox_xp_cbox_color_unchecked_stroke, COLOR_FLOOR_UNCHECKED);
        mCheckedColor = array.getColor(R.styleable.XSmoothCheckBox_xp_cbox_color_checked, ContextCompat.getColor(context, R.color.xp_config_color_blue));
        mUnCheckedColor = array.getColor(R.styleable.XSmoothCheckBox_xp_cbox_color_unchecked, Color.WHITE);
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.XSmoothCheckBox_xp_cbox_stroke_width, 0);
        mChecked = array.getBoolean(R.styleable.XSmoothCheckBox_xp_checked, false);
        array.recycle();

        mFloorUnCheckedColor = mFloorColor;
        mTickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTickPaint.setStyle(Paint.Style.STROKE);
        mTickPaint.setStrokeCap(Paint.Cap.ROUND);
        mTickPaint.setColor(mTickColor);

        mFloorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFloorPaint.setStyle(Paint.Style.FILL);
        mFloorPaint.setColor(mFloorColor);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCheckedColor);

        mTickPath = new Path();
        mCenterPoint = new Point();
        mTickPoints = new Point[3];
        mTickPoints[0] = new Point();
        mTickPoints[1] = new Point();
        mTickPoints[2] = new Point();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
                mTickDrawing = false;
                mDrewDistance = 0;
                if (isChecked()) {
                    startCheckedAnimation();
                } else {
                    startUnCheckedAnimation();
                }
            }
        });
        xAlphaHelper = new XAlphaHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putBoolean(KEY_INSTANCE_STATE, isChecked());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            boolean isChecked = bundle.getBoolean(KEY_INSTANCE_STATE);
            setChecked(isChecked);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setTickColor(int tickColor) {
        this.mTickColor = tickColor;
        mTickPaint.setColor(mTickColor);
        invalidate();
    }

    public void setCheckedColor(int color) {
        this.mCheckedColor = color;
        mPaint.setColor(this.mCheckedColor);
        invalidate();
    }

    public void setUnCheckedColor(int color) {
        this.mUnCheckedColor = color;
        invalidate();
    }

    public void setUnCheckedStrokeColor(int color) {
        this.mFloorUnCheckedColor = color;
        mFloorPaint.setColor(this.mFloorUnCheckedColor);
        invalidate();
    }



    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        this.setChecked(!isChecked());
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        reset();
        invalidate();
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(XSmoothCheckBox.this, mChecked);
        }
    }

    /**
     * 静默设置
     *
     * @param checked
     */
    public void setCheckedSilent(boolean checked) {
        mChecked = checked;
        reset();
        invalidate();
    }

    /**
     * checked with animation
     *
     * @param checked checked
     * @param animate change with animation
     */
    public void setChecked(boolean checked, boolean animate) {
        if (animate) {
            mTickDrawing = false;
            mChecked = checked;
            mDrewDistance = 0f;
            if (checked) {
                startCheckedAnimation();
            } else {
                startUnCheckedAnimation();
            }
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(XSmoothCheckBox.this, mChecked);
            }
        } else {
            this.setChecked(checked);
        }
    }

    private void reset() {
        mTickDrawing = true;
        mFloorScale = 1.0f;
        mScaleVal = isChecked() ? 0f : 1.0f;
        mFloorColor = isChecked() ? mCheckedColor : mFloorUnCheckedColor;
        mDrewDistance = isChecked() ? (mLeftLineDistance + mRightLineDistance) : 0;
    }

    private int measureSize(int measureSpec) {
        int defSize = XDisplayHelper.dp2px(getContext(), DEF_DRAW_SIZE);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getMeasuredWidth();
        mStrokeWidth = (mStrokeWidth == 0 ? getMeasuredWidth() / 10 : mStrokeWidth);
        mStrokeWidth = Math.min(mStrokeWidth, getMeasuredWidth() / 5);
        mStrokeWidth = Math.max(mStrokeWidth, 3);
        mCenterPoint.x = mWidth / 2;
        mCenterPoint.y = getMeasuredHeight() / 2;

        mTickPoints[0].x = Math.round((float) getMeasuredWidth() / 30 * 7);
        mTickPoints[0].y = Math.round((float) getMeasuredHeight() / 30 * 14);
        mTickPoints[1].x = Math.round((float) getMeasuredWidth() / 30 * 13);
        mTickPoints[1].y = Math.round((float) getMeasuredHeight() / 30 * 20);
        mTickPoints[2].x = Math.round((float) getMeasuredWidth() / 30 * 22);
        mTickPoints[2].y = Math.round((float) getMeasuredHeight() / 30 * 10);

        mLeftLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[1].x - mTickPoints[0].x, 2) +
                Math.pow(mTickPoints[1].y - mTickPoints[0].y, 2));
        mRightLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[2].x - mTickPoints[1].x, 2) +
                Math.pow(mTickPoints[2].y - mTickPoints[1].y, 2));
        mTickPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawCenter(canvas);
        drawTick(canvas);
    }

    private void drawCenter(Canvas canvas) {
        mPaint.setColor(mUnCheckedColor);
        float radius = (mCenterPoint.x - mStrokeWidth) * mScaleVal;
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radius, mPaint);
    }

    private void drawBorder(Canvas canvas) {
        mFloorPaint.setColor(mFloorColor);
        int radius = mCenterPoint.x;
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radius * mFloorScale, mFloorPaint);
    }

    private void drawTick(Canvas canvas) {
        if (mTickDrawing && isChecked()) {
            drawTickPath(canvas);
        }
    }

    private void drawTickPath(Canvas canvas) {
        mTickPath.reset();
        // draw left of the tick
        if (mDrewDistance < mLeftLineDistance) {
            float step = (mWidth / 20.0f) < 3 ? 3 : (mWidth / 20.0f);
            mDrewDistance += step;
            float stopX = mTickPoints[0].x + (mTickPoints[1].x - mTickPoints[0].x) * mDrewDistance / mLeftLineDistance;
            float stopY = mTickPoints[0].y + (mTickPoints[1].y - mTickPoints[0].y) * mDrewDistance / mLeftLineDistance;

            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(stopX, stopY);
            canvas.drawPath(mTickPath, mTickPaint);

            if (mDrewDistance > mLeftLineDistance) {
                mDrewDistance = mLeftLineDistance;
            }
        } else {

            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(mTickPoints[1].x, mTickPoints[1].y);
            canvas.drawPath(mTickPath, mTickPaint);

            // draw right of the tick
            if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
                float stopX = mTickPoints[1].x + (mTickPoints[2].x - mTickPoints[1].x) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance;
                float stopY = mTickPoints[1].y - (mTickPoints[1].y - mTickPoints[2].y) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance;

                mTickPath.reset();
                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(stopX, stopY);
                canvas.drawPath(mTickPath, mTickPaint);

                float step = Math.max((mWidth / 20), 3);
                mDrewDistance += step;
            } else {
                mTickPath.reset();
                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(mTickPoints[2].x, mTickPoints[2].y);
                canvas.drawPath(mTickPath, mTickPaint);
            }
        }

        // invalidate
        if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    postInvalidate();
                }
            }, 10);
        }
    }

    private void startCheckedAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0f);
        animator.setDuration(mAnimDuration / 3 * 2);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleVal = (float) animation.getAnimatedValue();
                mFloorColor = getGradientColor(mUnCheckedColor, mCheckedColor, 1 - mScaleVal);
                postInvalidate();
            }
        });
        animator.start();

        ValueAnimator floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
        floorAnimator.setDuration(mAnimDuration);
        floorAnimator.setInterpolator(new LinearInterpolator());
        floorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloorScale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        floorAnimator.start();

        drawTickDelayed();
    }

    private void startUnCheckedAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
        animator.setDuration(mAnimDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleVal = (float) animation.getAnimatedValue();
                mFloorColor = getGradientColor(mCheckedColor, mFloorUnCheckedColor, mScaleVal);
                postInvalidate();
            }
        });
        animator.start();

        ValueAnimator floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
        floorAnimator.setDuration(mAnimDuration);
        floorAnimator.setInterpolator(new LinearInterpolator());
        floorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloorScale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        floorAnimator.start();
    }

    private void drawTickDelayed() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mTickDrawing = true;
                postInvalidate();
            }
        }, mAnimDuration);
    }

    private static int getGradientColor(int startColor, int endColor, float percent) {
        int startA = Color.alpha(startColor);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endA = Color.alpha(endColor);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        int currentA = (int) (startA * (1 - percent) + endA * percent);
        int currentR = (int) (startR * (1 - percent) + endR * percent);
        int currentG = (int) (startG * (1 - percent) + endG * percent);
        int currentB = (int) (startB * (1 - percent) + endB * percent);
        return Color.argb(currentA, currentR, currentG, currentB);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void setChangeAlphaOnPressed(boolean isChangeAlphaOnPressed) {
        xAlphaHelper.setChangeAlphaOnPressed(isChangeAlphaOnPressed);
    }

    @Override
    public void setChangeAlphaOnDisabled(boolean isChangeAlphaOnDisabled) {
        xAlphaHelper.setChangeAlphaOnDisabled(isChangeAlphaOnDisabled);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        xAlphaHelper.onPressedChanged(this,pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        xAlphaHelper.onEnabledChanged(this,enabled);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(XSmoothCheckBox checkBox, boolean isChecked);
    }
}
