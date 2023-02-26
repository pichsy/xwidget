package com.pichs.common.widget.nestedscroll;

import android.view.View;

import androidx.core.view.ViewCompat;

/**
 * Utility helper for moving a {@link View} around using
 * {@link View#offsetLeftAndRight(int)} and
 * {@link View#offsetTopAndBottom(int)}.
 * <p>
 * Also the setting of absolute offsets (similar to translationX/Y), rather than additive
 * offsets.
 */
public final class XViewOffsetHelper {

    private final View mView;

    private int mLayoutTop;
    private int mLayoutLeft;
    private int mOffsetTop;
    private int mOffsetLeft;

    private boolean mVerticalOffsetEnabled = true;
    private boolean mHorizontalOffsetEnabled = true;

    public XViewOffsetHelper(View view) {
        mView = view;
    }

    public void onViewLayout() {
        onViewLayout(true);
    }

    public void onViewLayout(boolean applyOffset) {
        mLayoutTop = mView.getTop();
        mLayoutLeft = mView.getLeft();
        if(applyOffset){
            applyOffsets();
        }
    }

    public void applyOffsets() {
        ViewCompat.offsetTopAndBottom(mView, mOffsetTop - (mView.getTop() - mLayoutTop));
        ViewCompat.offsetLeftAndRight(mView, mOffsetLeft - (mView.getLeft() - mLayoutLeft));
    }

    /**
     * Set the top and bottom offset for this {@link XViewOffsetHelper}'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setTopAndBottomOffset(int offset) {
        if (mVerticalOffsetEnabled && mOffsetTop != offset) {
            mOffsetTop = offset;
            applyOffsets();
            return true;
        }
        return false;
    }

    /**
     * Set the left and right offset for this {@link XViewOffsetHelper}'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setLeftAndRightOffset(int offset) {
        if (mHorizontalOffsetEnabled && mOffsetLeft != offset) {
            mOffsetLeft = offset;
            applyOffsets();
            return true;
        }
        return false;
    }

    public boolean setOffset(int leftOffset, int topOffset) {
        if(!mHorizontalOffsetEnabled && !mVerticalOffsetEnabled){
            return false;
        }else if(mHorizontalOffsetEnabled && mVerticalOffsetEnabled){
            if (mOffsetLeft != leftOffset || mOffsetTop != topOffset) {
                mOffsetLeft = leftOffset;
                mOffsetTop = topOffset;
                applyOffsets();
                return true;
            }
            return false;
        }else if(mHorizontalOffsetEnabled){
            return setLeftAndRightOffset(leftOffset);
        }else{
            return setTopAndBottomOffset(topOffset);
        }
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }

    public int getLeftAndRightOffset() {
        return mOffsetLeft;
    }

    public int getLayoutTop() {
        return mLayoutTop;
    }

    public int getLayoutLeft() {
        return mLayoutLeft;
    }

    public void setHorizontalOffsetEnabled(boolean horizontalOffsetEnabled) {
        mHorizontalOffsetEnabled = horizontalOffsetEnabled;
    }

    public boolean isHorizontalOffsetEnabled() {
        return mHorizontalOffsetEnabled;
    }

    public void setVerticalOffsetEnabled(boolean verticalOffsetEnabled) {
        mVerticalOffsetEnabled = verticalOffsetEnabled;
    }

    public boolean isVerticalOffsetEnabled() {
        return mVerticalOffsetEnabled;
    }
}