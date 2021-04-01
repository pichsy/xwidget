/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pichs.common.widget.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XILayout;

import java.lang.ref.WeakReference;

/**
 * @author cginechen
 * @date 2017-03-10
 */

public class XLayoutHelper implements XILayout {
    private Context mContext;
    // size
    private int mWidthLimit = 0;
    private int mHeightLimit = 0;
    private int mWidthMini = 0;
    private int mHeightMini = 0;

    // divider
    private int mTopDividerHeight = 0;
    private int mTopDividerInsetLeft = 0;
    private int mTopDividerInsetRight = 0;
    private int mTopDividerColor;
    private int mTopDividerAlpha = 255;

    private int mBottomDividerHeight = 0;
    private int mBottomDividerInsetLeft = 0;
    private int mBottomDividerInsetRight = 0;
    private int mBottomDividerColor;
    private int mBottomDividerAlpha = 255;

    private int mLeftDividerWidth = 0;
    private int mLeftDividerInsetTop = 0;
    private int mLeftDividerInsetBottom = 0;
    private int mLeftDividerColor;
    private int mLeftDividerAlpha = 255;

    private int mRightDividerWidth = 0;
    private int mRightDividerInsetTop = 0;
    private int mRightDividerInsetBottom = 0;
    private int mRightDividerColor;
    private int mRightDividerAlpha = 255;
    private Paint mDividerPaint;

    // round
    private Paint mClipPaint;
    private PorterDuffXfermode mMode;
    private int mRadius;
    // 圆角是否是自适应控件边界，即会使用最小边的一半作为弧度。
    private boolean isRadiusAdjustBounds = false;
    // 当前View的宽高
    private int mWidth;
    private int mHeight;

    private @HideRadiusSide
    int mHideRadiusSide = HIDE_RADIUS_SIDE_NONE;
    private float[] mRadiusArray;
    private RectF mBorderRect;
    private int mBorderColor = 0;
    private int mBorderWidth = 1;
    private int mOuterNormalColor = 0;
    private WeakReference<View> mOwner;
    private boolean mIsOutlineExcludePadding = false;
    private Path mPath = new Path();

    // shadow
    private boolean mIsShowBorderOnlyBeforeL = true;
    private int mShadowElevation = 0;
    private float mShadowAlpha = 1.0f;
    private int mShadowColor = Color.BLACK;

    // outline inset
    private int mOutlineInsetLeft = 0;
    private int mOutlineInsetRight = 0;
    private int mOutlineInsetTop = 0;
    private int mOutlineInsetBottom = 0;

    // 边框渐变 ---> 从左到右，从上到下
    // 开始颜色
    private int mBorderGradientStartColor = 0;
    // 结束颜色
    private int mBorderGradientEndColor = 0;
    // 渐变方向 0->横向， 1->竖向，2：左上->右下，3:左下->右上
    private @GradientOrientation
    int mBorderGradientOrientation = GradientOrientation.HORIZONTAL;
    private LinearGradient mLinearGradient;


    public XLayoutHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    public XLayoutHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, View owner) {
        mContext = context;
        mOwner = new WeakReference<>(owner);
        mBottomDividerColor = mTopDividerColor =
                ContextCompat.getColor(context, R.color.xp_config_color_separator);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mClipPaint = new Paint();
        mClipPaint.setAntiAlias(true);
        mBorderRect = new RectF();

        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XILayout, defAttr, defStyleRes);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int index = ta.getIndex(i);
                if (index == R.styleable.XILayout_android_maxWidth) {
                    mWidthLimit = ta.getDimensionPixelSize(index, mWidthLimit);
                } else if (index == R.styleable.XILayout_android_maxHeight) {
                    mHeightLimit = ta.getDimensionPixelSize(index, mHeightLimit);
                } else if (index == R.styleable.XILayout_android_minWidth) {
                    mWidthMini = ta.getDimensionPixelSize(index, mWidthMini);
                } else if (index == R.styleable.XILayout_android_minHeight) {
                    mHeightMini = ta.getDimensionPixelSize(index, mHeightMini);
                } else if (index == R.styleable.XILayout_xp_topDividerColor) {
                    mTopDividerColor = ta.getColor(index, mTopDividerColor);
                } else if (index == R.styleable.XILayout_xp_topDividerHeight) {
                    mTopDividerHeight = ta.getDimensionPixelSize(index, mTopDividerHeight);
                } else if (index == R.styleable.XILayout_xp_topDividerInsetLeft) {
                    mTopDividerInsetLeft = ta.getDimensionPixelSize(index, mTopDividerInsetLeft);
                } else if (index == R.styleable.XILayout_xp_topDividerInsetRight) {
                    mTopDividerInsetRight = ta.getDimensionPixelSize(index, mTopDividerInsetRight);
                } else if (index == R.styleable.XILayout_xp_bottomDividerColor) {
                    mBottomDividerColor = ta.getColor(index, mBottomDividerColor);
                } else if (index == R.styleable.XILayout_xp_bottomDividerHeight) {
                    mBottomDividerHeight = ta.getDimensionPixelSize(index, mBottomDividerHeight);
                } else if (index == R.styleable.XILayout_xp_bottomDividerInsetLeft) {
                    mBottomDividerInsetLeft = ta.getDimensionPixelSize(index, mBottomDividerInsetLeft);
                } else if (index == R.styleable.XILayout_xp_bottomDividerInsetRight) {
                    mBottomDividerInsetRight = ta.getDimensionPixelSize(index, mBottomDividerInsetRight);
                } else if (index == R.styleable.XILayout_xp_leftDividerColor) {
                    mLeftDividerColor = ta.getColor(index, mLeftDividerColor);
                } else if (index == R.styleable.XILayout_xp_leftDividerWidth) {
                    mLeftDividerWidth = ta.getDimensionPixelSize(index, mLeftDividerWidth);
                } else if (index == R.styleable.XILayout_xp_leftDividerInsetTop) {
                    mLeftDividerInsetTop = ta.getDimensionPixelSize(index, mLeftDividerInsetTop);
                } else if (index == R.styleable.XILayout_xp_leftDividerInsetBottom) {
                    mLeftDividerInsetBottom = ta.getDimensionPixelSize(index, mLeftDividerInsetBottom);
                } else if (index == R.styleable.XILayout_xp_rightDividerColor) {
                    mRightDividerColor = ta.getColor(index, mRightDividerColor);
                } else if (index == R.styleable.XILayout_xp_rightDividerWidth) {
                    mRightDividerWidth = ta.getDimensionPixelSize(index, mRightDividerWidth);
                } else if (index == R.styleable.XILayout_xp_rightDividerInsetTop) {
                    mRightDividerInsetTop = ta.getDimensionPixelSize(index, mRightDividerInsetTop);
                } else if (index == R.styleable.XILayout_xp_rightDividerInsetBottom) {
                    mRightDividerInsetBottom = ta.getDimensionPixelSize(index, mRightDividerInsetBottom);
                } else if (index == R.styleable.XILayout_xp_borderColor) {
                    mBorderColor = ta.getColor(index, mBorderColor);
                } else if (index == R.styleable.XILayout_xp_borderWidth) {
                    mBorderWidth = ta.getDimensionPixelSize(index, mBorderWidth);
                } else if (index == R.styleable.XILayout_xp_radius) {
                    mRadius = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_isRadiusAdjustBounds) {
                    isRadiusAdjustBounds = ta.getBoolean(index, false);
                } else if (index == R.styleable.XILayout_xp_outerNormalColor) {
                    mOuterNormalColor = ta.getColor(index, mOuterNormalColor);
                } else if (index == R.styleable.XILayout_xp_hideRadiusSide) {
                    mHideRadiusSide = ta.getColor(index, mHideRadiusSide);
                } else if (index == R.styleable.XILayout_xp_showBorderOnlyBeforeL) {
                    mIsShowBorderOnlyBeforeL = ta.getBoolean(index, mIsShowBorderOnlyBeforeL);
                } else if (index == R.styleable.XILayout_xp_shadowElevation) {
                    mShadowElevation = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_shadowAlpha) {
                    mShadowAlpha = ta.getFloat(index, mShadowAlpha);
                } else if (index == R.styleable.XILayout_xp_shadowColor) {
                    mShadowColor = ta.getColor(index, mShadowColor);
                } else if (index == R.styleable.XILayout_xp_outlineInsetLeft) {
                    mOutlineInsetLeft = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_outlineInsetRight) {
                    mOutlineInsetRight = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_outlineInsetTop) {
                    mOutlineInsetTop = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_outlineInsetBottom) {
                    mOutlineInsetBottom = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.XILayout_xp_outlineExcludePadding) {
                    mIsOutlineExcludePadding = ta.getBoolean(index, false);
                } else if (index == R.styleable.XILayout_xp_borderGradientStartColor) {
                    mBorderGradientStartColor = ta.getColor(index, 0);
                } else if (index == R.styleable.XILayout_xp_borderGradientEndColor) {
                    mBorderGradientEndColor = ta.getColor(index, 0);
                } else if (index == R.styleable.XILayout_xp_borderGradientOrientation) {
                    mBorderGradientOrientation = ta.getInt(index, GradientOrientation.HORIZONTAL);
                }
            }
            ta.recycle();
        }
//        setRadiusAndShadow(mRadius, mHideRadiusSide, mShadowElevation, mShadowColor, mShadowAlpha);
    }

    @Override
    public void setBorderGradientStartColor(int borderGradientStartColor) {
        this.mBorderGradientStartColor = borderGradientStartColor;
        invalidate();
    }

    @Override
    public void setBorderGradientEndColor(int borderGradientEndColor) {
        this.mBorderGradientEndColor = borderGradientEndColor;
        invalidate();
    }

    @Override
    public void setBorderGradientOrientation(@GradientOrientation int orientation) {
        this.mBorderGradientOrientation = orientation;
        invalidate();
    }

    @Override
    public void setBorderGradientOrientation(int borderGradientStartColor, int borderGradientEndColor, @GradientOrientation int orientation) {
        this.mBorderGradientStartColor = borderGradientStartColor;
        this.mBorderGradientEndColor = borderGradientEndColor;
        this.mBorderGradientOrientation = orientation;
        invalidate();
    }

    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mIsOutlineExcludePadding = outlineExcludePadding;
            owner.invalidateOutline();
        }
    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mWidthLimit != widthLimit) {
            mWidthLimit = widthLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mHeightLimit != heightLimit) {
            mHeightLimit = heightLimit;
            return true;
        }
        return false;
    }

    @Override
    public void updateLeftSeparatorColor(int color) {
        if (mLeftDividerColor != color) {
            mLeftDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateBottomSeparatorColor(int color) {
        if (mBottomDividerColor != color) {
            mBottomDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateTopSeparatorColor(int color) {
        if (mTopDividerColor != color) {
            mTopDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateRightSeparatorColor(int color) {
        if (mRightDividerColor != color) {
            mRightDividerColor = color;
            invalidate();
        }
    }

    @Override
    public int getShadowElevation() {
        return mShadowElevation;
    }

    @Override
    public float getShadowAlpha() {
        return mShadowAlpha;
    }

    @Override
    public int getShadowColor() {
        return mShadowColor;
    }

    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mOutlineInsetLeft = left;
            mOutlineInsetRight = right;
            mOutlineInsetTop = top;
            mOutlineInsetBottom = bottom;
            owner.invalidateOutline();
        }
    }

    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        mIsShowBorderOnlyBeforeL = showBorderOnlyBeforeL;
        invalidate();
    }

    @Override
    public void setShadowElevation(int elevation) {
        if (mShadowElevation == elevation) {
            return;
        }
        mShadowElevation = elevation;
        invalidateOutline();
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        if (mShadowAlpha == shadowAlpha) {
            return;
        }
        mShadowAlpha = shadowAlpha;
        invalidateOutline();
    }

    @Override
    public void setShadowColor(int shadowColor) {
        if (mShadowColor == shadowColor) {
            return;
        }
        mShadowColor = shadowColor;
        setShadowColorInner(mShadowColor);
    }

    private void setShadowColorInner(int shadowColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            owner.setOutlineAmbientShadowColor(shadowColor);
            owner.setOutlineSpotShadowColor(shadowColor);
        }
    }

    private void invalidateOutline() {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            if (mShadowElevation == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            owner.invalidateOutline();
        }
    }

    private void invalidate() {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        owner.invalidate();
    }

    @Override
    public void setHideRadiusSide(@HideRadiusSide int hideRadiusSide) {
        if (mHideRadiusSide == hideRadiusSide) {
            return;
        }
        setRadiusAndShadow(mRadius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getHideRadiusSide() {
        return mHideRadiusSide;
    }

    @Override
    public void setRadius(int radius) {
        if (mRadius != radius) {
            setRadiusAndShadow(radius, mShadowElevation, mShadowAlpha);
        }
    }

    @Override
    public void setRadius(int radius, @HideRadiusSide int hideRadiusSide) {
        if (mRadius == radius && hideRadiusSide == mHideRadiusSide) {
            return;
        }
        setRadiusAndShadow(radius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getRadius() {
        return mRadius;
    }

    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, mHideRadiusSide, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, @HideRadiusSide int hideRadiusSide, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, mShadowColor, shadowAlpha);
    }

    private void setRadiusAndShadow() {
        setRadiusAndShadow(mRadius, mHideRadiusSide, mShadowElevation, mShadowColor, mShadowAlpha, false);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowColor, shadowAlpha, true);
    }

    private void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha, boolean isRefresh) {
        final View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        if (isRefresh && !isRadiusAdjustBounds) {
            mRadius = radius;
            if (mHeight > 0 && mWidth > 0) {
                mRadius = Math.min(mRadius, Math.min(mHeight, mWidth) / 2);
            }
        }
        mHideRadiusSide = hideRadiusSide;
        if (mRadius > 0) {
            if (hideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                mRadiusArray = new float[]{0, 0, 0, 0, mRadius, mRadius, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                mRadiusArray = new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                mRadiusArray = new float[]{mRadius, mRadius, mRadius, mRadius, 0, 0, 0, 0};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                mRadiusArray = new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0};
            } else {
                mRadiusArray = null;
            }
        }

        mShadowElevation = shadowElevation;
        mShadowAlpha = shadowAlpha;
        mShadowColor = shadowColor;
        if (useFeature()) {
            if (mShadowElevation == 0 || isRadiusWithSideHidden()) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }

            setShadowColorInner(mShadowColor);
            owner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth(), h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    if (isRadiusWithSideHidden()) {
                        int left = 0, top = 0, right = w, bottom = h;
                        if (mHideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                            left -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                            top -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                            right += mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                            bottom += mRadius;
                        }
                        outline.setRoundRect(left, top,
                                right, bottom, mRadius);
                        return;
                    }

                    int top = mOutlineInsetTop, bottom = Math.max(top + 1, h - mOutlineInsetBottom),
                            left = mOutlineInsetLeft, right = w - mOutlineInsetRight;
                    if (mIsOutlineExcludePadding) {
                        left += view.getPaddingLeft();
                        top += view.getPaddingTop();
                        right = Math.max(left + 1, right - view.getPaddingRight());
                        bottom = Math.max(top + 1, bottom - view.getPaddingBottom());
                    }

                    float shadowAlpha = mShadowAlpha;
                    if (mShadowElevation == 0) {
                        // outline.setAlpha will work even if shadowElevation == 0
                        shadowAlpha = 1f;
                    }

                    outline.setAlpha(shadowAlpha);

                    if (mRadius <= 0) {
                        outline.setRect(left, top,
                                right, bottom);
                    } else {
                        outline.setRoundRect(left, top,
                                right, bottom, mRadius);
                    }
                }
            });
            owner.setClipToOutline(mRadius > 0);

        }
        if (isRefresh) {
            owner.invalidate();
        }
    }

    /**
     * 有radius, 但是有一边不显示radius。
     *
     * @return
     */
    public boolean isRadiusWithSideHidden() {
        return mRadius > 0 && mHideRadiusSide != HIDE_RADIUS_SIDE_NONE;
    }

    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        if (topInsetLeft >= 0) {
            mTopDividerInsetLeft = topInsetLeft;
        }
        if (topInsetRight >= 0) {
            mTopDividerInsetRight = topInsetRight;
        }
        if (topDividerHeight >= 0) {
            mTopDividerHeight = topDividerHeight;
        }
        mTopDividerColor = topDividerColor;
    }

    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        if (bottomInsetLeft > 0) mBottomDividerInsetLeft = bottomInsetLeft;
        if (bottomInsetRight > 0) mBottomDividerInsetRight = bottomInsetRight;
        if (bottomDividerHeight > 0) mBottomDividerHeight = bottomDividerHeight;
        mBottomDividerColor = bottomDividerColor;

    }

    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        if (leftInsetTop > 0) mLeftDividerInsetTop = leftInsetTop;
        if (leftInsetBottom > 0) mLeftDividerInsetBottom = leftInsetBottom;
        if (leftDividerWidth > 0) mLeftDividerWidth = leftDividerWidth;
        mLeftDividerColor = leftDividerColor;
    }

    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        if (rightInsetTop > 0) mRightDividerInsetTop = rightInsetTop;
        if (rightInsetBottom > 0) mRightDividerInsetBottom = rightInsetBottom;
        if (rightInsetBottom > 0) mRightDividerWidth = rightInsetBottom;
        mRightDividerColor = rightDividerColor;
    }

    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight,
                                   int topDividerHeight, int topDividerColor) {
        updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight,
                                      int bottomDividerHeight, int bottomDividerColor) {
        updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
    }

    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        mLeftDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        mTopDividerAlpha = dividerAlpha;
    }

    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        mBottomDividerAlpha = dividerAlpha;
    }

    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        mLeftDividerAlpha = dividerAlpha;
    }

    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        mRightDividerAlpha = dividerAlpha;
    }


    public int handleMiniWidth(int widthMeasureSpec, int measuredWidth) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredWidth < mWidthMini) {
            return View.MeasureSpec.makeMeasureSpec(mWidthMini, View.MeasureSpec.EXACTLY);
        }
        return widthMeasureSpec;
    }

    public int handleMiniHeight(int heightMeasureSpec, int measuredHeight) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredHeight < mHeightMini) {
            return View.MeasureSpec.makeMeasureSpec(mHeightMini, View.MeasureSpec.EXACTLY);
        }
        return heightMeasureSpec;
    }

    public int getMeasuredWidthSpec(int widthMeasureSpec) {
        if (mWidthLimit > 0) {
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            if (size > mWidthLimit) {
                int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }

            }
        }
        return widthMeasureSpec;
    }

    public int getMeasuredHeightSpec(int heightMeasureSpec) {
        if (mHeightLimit > 0) {
            int size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (size > mHeightLimit) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return heightMeasureSpec;
    }

    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        mBorderColor = borderColor;
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
    }

    @Override
    public void setOuterNormalColor(int color) {
        mOuterNormalColor = color;
        View owner = mOwner.get();
        if (owner != null) {
            owner.invalidate();
        }
    }

    @Override
    public boolean hasTopSeparator() {
        return mTopDividerHeight > 0;
    }

    @Override
    public boolean hasRightSeparator() {
        return mRightDividerWidth > 0;
    }

    @Override
    public boolean hasBottomSeparator() {
        return mBottomDividerHeight > 0;
    }

    @Override
    public boolean hasLeftSeparator() {
        return mLeftDividerWidth > 0;
    }

    @Override
    public boolean hasBorder() {
        return mBorderWidth > 0;
    }

    public void drawDividers(Canvas canvas, int w, int h) {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        if (mDividerPaint == null &&
                (mTopDividerHeight > 0 || mBottomDividerHeight > 0 || mLeftDividerWidth > 0 || mRightDividerWidth > 0)) {
            mDividerPaint = new Paint();
        }
        canvas.save();
        canvas.translate(owner.getScrollX(), owner.getScrollY());
        if (mTopDividerHeight > 0) {
            mDividerPaint.setStrokeWidth(mTopDividerHeight);
            mDividerPaint.setColor(mTopDividerColor);
            if (mTopDividerAlpha < 255) {
                mDividerPaint.setAlpha(mTopDividerAlpha);
            }
            float y = mTopDividerHeight * 1f / 2;
            canvas.drawLine(mTopDividerInsetLeft, y, w - mTopDividerInsetRight, y, mDividerPaint);
        }

        if (mBottomDividerHeight > 0) {
            mDividerPaint.setStrokeWidth(mBottomDividerHeight);
            mDividerPaint.setColor(mBottomDividerColor);
            if (mBottomDividerAlpha < 255) {
                mDividerPaint.setAlpha(mBottomDividerAlpha);
            }
            float y = (float) Math.floor(h - mBottomDividerHeight * 1f / 2);
            canvas.drawLine(mBottomDividerInsetLeft, y, w - mBottomDividerInsetRight, y, mDividerPaint);
        }

        if (mLeftDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mLeftDividerWidth);
            mDividerPaint.setColor(mLeftDividerColor);
            if (mLeftDividerAlpha < 255) {
                mDividerPaint.setAlpha(mLeftDividerAlpha);
            }
            canvas.drawLine(0, mLeftDividerInsetTop, 0, h - mLeftDividerInsetBottom, mDividerPaint);
        }

        if (mRightDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mRightDividerWidth);
            mDividerPaint.setColor(mRightDividerColor);
            if (mRightDividerAlpha < 255) {
                mDividerPaint.setAlpha(mRightDividerAlpha);
            }
            canvas.drawLine(w, mRightDividerInsetTop, w, h - mRightDividerInsetBottom, mDividerPaint);
        }
        canvas.restore();
    }


    public void dispatchRoundBorderDraw(Canvas canvas) {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        mWidth = owner.getMeasuredWidth();
        mHeight = owner.getMeasuredHeight();
        if (mHeight > 0 && mWidth > 0) {
            if (isRadiusAdjustBounds) {
                mRadius = Math.min(mHeight, mWidth) / 2;
            } else {
                mRadius = Math.min(mRadius, Math.min(mHeight, mWidth) / 2);
            }
        }
        setRadiusAndShadow();
        boolean needCheckFakeOuterNormalDraw = mRadius > 0 && !useFeature() && mOuterNormalColor != 0;
        boolean needDrawBorder = mBorderWidth > 0 && (mBorderColor != 0 || (mBorderGradientStartColor != 0 && mBorderGradientEndColor != 0));
        if (!needCheckFakeOuterNormalDraw && !needDrawBorder) {
            return;
        }

        if (mIsShowBorderOnlyBeforeL && useFeature() && mShadowElevation != 0) {
            return;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.save();
        canvas.translate(owner.getScrollX(), owner.getScrollY());

        // react
        if (mIsOutlineExcludePadding) {
            mBorderRect.set(
                    owner.getPaddingLeft(),
                    owner.getPaddingTop(),
                    width - owner.getPaddingRight(),
                    height - owner.getPaddingBottom());
        } else {
            mBorderRect.set(0, 0, width, height);
        }


        if (needCheckFakeOuterNormalDraw) {
            int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawColor(mOuterNormalColor);
            mClipPaint.setColor(mOuterNormalColor);
            mClipPaint.setStyle(Paint.Style.FILL);
            mClipPaint.setXfermode(mMode);
            if (mRadiusArray == null) {
                canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
            } else {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
            }
            mClipPaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }

        if (needDrawBorder) {
            if (mBorderGradientStartColor != 0 && mBorderGradientEndColor != 0) {
                if (mLinearGradient == null) {
                    if (mBorderGradientOrientation == 0) {
                        mLinearGradient = new LinearGradient(0, 0, mBorderRect.right, 0, mBorderGradientStartColor, mBorderGradientEndColor, Shader.TileMode.CLAMP);
                    } else if (mBorderGradientOrientation == 1) {
                        mLinearGradient = new LinearGradient(0, 0, 0, mBorderRect.bottom, mBorderGradientStartColor, mBorderGradientEndColor, Shader.TileMode.CLAMP);
                    } else if (mBorderGradientOrientation == 2) {
                        mLinearGradient = new LinearGradient(0, 0, mBorderRect.right, mBorderRect.bottom, mBorderGradientStartColor, mBorderGradientEndColor, Shader.TileMode.CLAMP);
                    } else if (mBorderGradientOrientation == 3) {
                        mLinearGradient = new LinearGradient(0, mBorderRect.bottom, mBorderRect.right, 0, mBorderGradientStartColor, mBorderGradientEndColor, Shader.TileMode.CLAMP);
                    }
                }
                mClipPaint.setShader(mLinearGradient);
            } else {
                mClipPaint.setColor(mBorderColor);
            }
            mClipPaint.setStrokeWidth(mBorderWidth);
            mClipPaint.setStyle(Paint.Style.STROKE);
            if (mRadiusArray != null) {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
            } else if (mRadius <= 0) {
                canvas.drawRect(mBorderRect, mClipPaint);
            } else {
                canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
            }
        }
        canvas.restore();
    }

    private void drawRoundRect(Canvas canvas, RectF rect, float[] radiusArray, Paint paint) {
        mPath.reset();
        mPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
        canvas.drawPath(mPath, paint);
    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }

}
