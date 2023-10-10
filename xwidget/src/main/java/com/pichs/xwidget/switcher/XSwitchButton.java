package com.pichs.xwidget.switcher;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.content.ContextCompat;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;

import com.pichs.xwidget.R;
import com.pichs.xwidget.utils.XGradientHelper;

/**
 * switch切换按钮
 * <p>
 * XSwitchButton
 */
public class XSwitchButton extends CompoundButton {
    public static final float DEFAULT_THUMB_RANGE_RATIO = 1.8f;
    public static final int DEFAULT_THUMB_SIZE_DP = 20;
    public static final int DEFAULT_THUMB_MARGIN_DP = 2;
    public static final int DEFAULT_ANIMATION_DURATION = 250;
    public static final int DEFAULT_TINT_COLOR = 0x327FC2;
    private static final int[] CHECKED_PRESSED_STATE = new int[]{android.R.attr.state_checked, android.R.attr.state_enabled, android.R.attr.state_pressed};
    private static final int[] UNCHECKED_PRESSED_STATE = new int[]{-android.R.attr.state_checked, android.R.attr.state_enabled, android.R.attr.state_pressed};
    private int mCurrThumbColor, mCurrBackColor, mNextBackColor, mOnTextColor, mOffTextColor;
    private Drawable mCurrentBackDrawable, mNextBackDrawable;
    private RectF mThumbRectF, mBackRectF, mSafeRectF, mTextOnRectF, mTextOffRectF;
    private RectF mThumbMargin;
    private Paint mPaint;
    private boolean mDrawDebugRect = false;
    private ObjectAnimator mProgressAnimator;
    // animation control
    private float mProgress;
    // temp position of thumb when dragging or animating
    private RectF mPresentThumbRectF;
    private float mStartX, mStartY, mLastX;
    private int mTouchSlop;
    private int mClickTimeout;
    private Paint mRectPaint;
    private TextPaint mTextPaint;
    private Layout mOnLayout;
    private Layout mOffLayout;
    private int mTextWidth;
    private int mTextHeight;
    // FIX #78,#85 : When restoring saved states, setChecked() called by super. So disable
    // animation and event listening when restoring.
    private boolean mRestoring = false;
    private boolean mReady = false;
    private boolean mCatch = false;

    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;

    private int thumbColor = Color.WHITE;
    private int thumbCheckedColor = thumbColor;
    private int thumbPressedColor = thumbColor;
    private int thumbDisabledColor = Color.LTGRAY;
    private int switchOnBackgroundColor = Color.BLUE;
    private int switchOffBackgroundColor = Color.BLUE;

    private float margin = 5;
    private float marginLeft = 0;
    private float marginRight = 0;
    private float marginTop = 0;
    private float marginBottom = 0;
    private int thumbWidth = 0;
    private int thumbHeight = 0;
    private float thumbRadius = -1;
    private float backgroundRadius = -1f;
    private float thumbRangeRatio = DEFAULT_THUMB_RANGE_RATIO;
    private int animationDuration = DEFAULT_ANIMATION_DURATION;
    private boolean isFadeBackground = true;
    private CharSequence switchOnText = "";
    private CharSequence switchOffText = "";
    private int textThumbInset = 0;
    private int textExtra = 0;
    private int textAdjust = 0;
    private Drawable backgroundDrawable;
    private Drawable thumbDrawable;
    private ColorStateList thumbColorStateList;
    // 背景宽度
    private int mBackgroundWidth;
    private int mBackgroundHeight;

    public XSwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public XSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public XSwitchButton(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);
        mTextPaint = getPaint();
        mThumbRectF = new RectF();
        mBackRectF = new RectF();
        mSafeRectF = new RectF();
        mThumbMargin = new RectF();
        mTextOnRectF = new RectF();
        mTextOffRectF = new RectF();
        mProgressAnimator = ObjectAnimator.ofFloat(this, "progress", 0, 0).setDuration(DEFAULT_ANIMATION_DURATION);
        mProgressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mPresentThumbRectF = new RectF();
        TypedArray ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, R.styleable.XSwitchButton);
        if (ta != null) {
            thumbDrawable = ta.getDrawable(R.styleable.XSwitchButton_xp_swb_thumbDrawable);
            thumbColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_thumbColor, Color.WHITE);
            thumbCheckedColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_thumbCheckedColor, thumbColor);
            thumbPressedColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_thumbPressedColor, thumbColor);
            thumbCheckedColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_thumbCheckedColor, thumbColor);
            thumbDisabledColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_thumbDisabledColor, thumbColor);
            margin = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbMargin, margin);
            marginLeft = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbMarginLeft, margin);
            marginRight = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbMarginRight, margin);
            marginTop = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbMarginTop, margin);
            marginBottom = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbMarginBottom, margin);
            thumbWidth = ta.getDimensionPixelSize(R.styleable.XSwitchButton_xp_swb_thumbWidth, thumbWidth);
            thumbHeight = ta.getDimensionPixelSize(R.styleable.XSwitchButton_xp_swb_thumbHeight, thumbHeight);
            thumbRadius = ta.getDimension(R.styleable.XSwitchButton_xp_swb_thumbRadius, thumbRadius);
            backgroundRadius = ta.getDimension(R.styleable.XSwitchButton_xp_swb_backgroundRadius, backgroundRadius);
            backgroundDrawable = ta.getDrawable(R.styleable.XSwitchButton_xp_swb_backgroundDrawable);
            switchOnBackgroundColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_backgroundColor_switchOn, switchOnBackgroundColor);
            switchOffBackgroundColor = ta.getColor(R.styleable.XSwitchButton_xp_swb_backgroundColor_switchOff, switchOnBackgroundColor);
            thumbRangeRatio = ta.getFloat(R.styleable.XSwitchButton_xp_swb_thumbRangeRatio, thumbRangeRatio);
            animationDuration = ta.getInteger(R.styleable.XSwitchButton_xp_swb_animationDuration, animationDuration);
            isFadeBackground = ta.getBoolean(R.styleable.XSwitchButton_xp_swb_fadeBackground, true);
            switchOnText = ta.getString(R.styleable.XSwitchButton_xp_swb_text_switchOn);
            switchOffText = ta.getString(R.styleable.XSwitchButton_xp_swb_text_switchOff);
            textThumbInset = ta.getDimensionPixelSize(R.styleable.XSwitchButton_xp_swb_textThumbInset, 0);
            textExtra = ta.getDimensionPixelSize(R.styleable.XSwitchButton_xp_swb_textExtra, 0);
            textAdjust = ta.getDimensionPixelSize(R.styleable.XSwitchButton_xp_swb_textAdjust, 0);
            ta.recycle();
        }

        // click
        ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, new int[]{android.R.attr.focusable, android.R.attr.clickable});
        if (ta != null) {
            boolean focusable = ta.getBoolean(0, true);
            //noinspection ResourceType
            @SuppressLint("ResourceType")
            boolean clickable = ta.getBoolean(1, focusable);
            setFocusable(focusable);
            setClickable(clickable);
            ta.recycle();
        } else {
            setFocusable(true);
            setClickable(true);
        }
        if (thumbDrawable == null) {
            thumbColorStateList = new XGradientHelper.ColorStateListBuilder()
                    .setUnSateColor(thumbColor)
                    .addPressedColor(thumbPressedColor)
                    .addCheckedColor(thumbCheckedColor)
                    .addDisabledColor(thumbDisabledColor)
                    .build();
            mCurrThumbColor = thumbColorStateList.getDefaultColor();
        }

        // thumbSize
        thumbWidth = ceil(thumbWidth);
        thumbHeight = ceil(thumbHeight);

        // back drawable and color
        if (backgroundDrawable == null) {
            thumbColorStateList = new XGradientHelper.ColorStateListBuilder()
                    .setUnSateColor(switchOffBackgroundColor)
                    .addCheckedColor(switchOnBackgroundColor)
                    .build();
            mCurrBackColor = thumbColorStateList.getDefaultColor();
            mNextBackColor = thumbColorStateList.getColorForState(CHECKED_PRESSED_STATE, mCurrBackColor);
        }

        // margin
        mThumbMargin.set(marginLeft, marginTop, marginRight, marginBottom);
        // size & measure params must larger than 1
        thumbRangeRatio = mThumbMargin.width() >= 0 ? Math.max(thumbRangeRatio, 1) : thumbRangeRatio;
        mProgressAnimator.setDuration(animationDuration);
        // sync checked status
        if (isChecked()) {
            setProgress(1);
        }
    }

    private Layout makeLayout(CharSequence text) {
        return new StaticLayout(text, mTextPaint, (int) Math.ceil(Layout.getDesiredWidth(text, mTextPaint)), Layout.Alignment.ALIGN_CENTER, 1.f, 0, false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
         * ensure textLayout
         */
        if (mOnLayout == null && !TextUtils.isEmpty(switchOnText)) {
            mOnLayout = makeLayout(switchOnText);
        }
        if (mOffLayout == null && !TextUtils.isEmpty(switchOffText)) {
            mOffLayout = makeLayout(switchOffText);
        }

        int onWidth = mOnLayout != null ? mOnLayout.getWidth() : 0;
        int offWidth = mOffLayout != null ? mOffLayout.getWidth() : 0;
        if (onWidth != 0 || offWidth != 0) {
            mTextWidth = Math.max(onWidth, offWidth);
        } else {
            mTextWidth = 0;
        }

        int onHeight = mOnLayout != null ? mOnLayout.getHeight() : 0;
        int offHeight = mOffLayout != null ? mOffLayout.getHeight() : 0;
        if (onHeight != 0 || offHeight != 0) {
            mTextHeight = Math.max(onHeight, offHeight);
        } else {
            mTextHeight = 0;
        }

        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * SwitchButton use this formula to determine the final size of thumb, background and itself.
     * <p>
     * textWidth = max(onWidth, offWidth)
     * thumbRange = thumbWidth * rangeRatio
     * textExtraSpace = textWidth + textExtra - (moveRange - thumbWidth + max(thumbMargin.left, thumbMargin.right) + textThumbInset)
     * backWidth = thumbRange + thumbMargin.left + thumbMargin.right + max(textExtraSpace, 0)
     * contentSize = thumbRange + max(thumbMargin.left, 0) + max(thumbMargin.right, 0) + max(textExtraSpace, 0)
     *
     * @param widthMeasureSpec widthMeasureSpec
     * @return measuredWidth
     */
    private int measureWidth(int widthMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measuredWidth = widthSize;

        if (thumbWidth == 0 && thumbDrawable != null) {
            thumbWidth = thumbDrawable.getIntrinsicWidth();
        }

        int moveRange;
        int textWidth = ceil(mTextWidth);
        // how much the background should extend to fit text.
        int textExtraSpace;
        int contentSize;

        if (thumbRangeRatio == 0) {
            thumbRangeRatio = DEFAULT_THUMB_RANGE_RATIO;
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            contentSize = widthSize - getPaddingLeft() - getPaddingRight();

            if (thumbWidth != 0) {
                moveRange = ceil(thumbWidth * thumbRangeRatio);
                textExtraSpace = (int) (textWidth + textExtra - (moveRange - thumbWidth + ceil(Math.max(mThumbMargin.left, mThumbMargin.right))));
                mBackgroundWidth = ceil(moveRange + mThumbMargin.left + mThumbMargin.right + Math.max(textExtraSpace, 0));
                if (mBackgroundWidth < 0) {
                    thumbWidth = 0;
                }
                if (moveRange + Math.max(mThumbMargin.left, 0) + Math.max(mThumbMargin.right, 0) + Math.max(textExtraSpace, 0) > contentSize) {
                    thumbWidth = 0;
                }
            }

            if (thumbWidth == 0) {
                contentSize = widthSize - getPaddingLeft() - getPaddingRight();
                moveRange = ceil(contentSize - Math.max(mThumbMargin.left, 0) - Math.max(mThumbMargin.right, 0));
                if (moveRange < 0) {
                    thumbWidth = 0;
                    mBackgroundWidth = 0;
                    return measuredWidth;
                }
                thumbWidth = ceil(moveRange / thumbRangeRatio);
                mBackgroundWidth = ceil(moveRange + mThumbMargin.left + mThumbMargin.right);
                if (mBackgroundWidth < 0) {
                    thumbWidth = 0;
                    mBackgroundWidth = 0;
                    return measuredWidth;
                }
                textExtraSpace = (int) (textWidth + textExtra - (moveRange - thumbWidth + ceil(Math.max(mThumbMargin.left, mThumbMargin.right))));
                if (textExtraSpace > 0) {
                    // since backWidth is determined by view width, so we can only reduce thumbSize.
                    thumbWidth = thumbWidth - textExtraSpace;
                }
                if (thumbWidth < 0) {
                    thumbWidth = 0;
                    mBackgroundWidth = 0;
                    return measuredWidth;
                }
            }
        } else {
            /*
            If parent view want SwitchButton to determine it's size itself, we calculate the minimal
            size of it's content. Further more, we ignore the limitation of widthSize since we want
            to display SwitchButton in its actual size rather than compress the shape.
             */
            if (this.thumbWidth == 0) {
                /*
                If thumbWidth is not set, use the default one.
                 */
                this.thumbWidth = ceil(getResources().getDisplayMetrics().density * DEFAULT_THUMB_SIZE_DP);
            }
            if (thumbRangeRatio == 0) {
                thumbRangeRatio = DEFAULT_THUMB_RANGE_RATIO;
            }

            moveRange = ceil(this.thumbWidth * thumbRangeRatio);
            textExtraSpace = ceil(textWidth + textExtra - (moveRange - this.thumbWidth + Math.max(mThumbMargin.left, mThumbMargin.right) + textThumbInset));
            mBackgroundWidth = ceil(moveRange + mThumbMargin.left + mThumbMargin.right + Math.max(0, textExtraSpace));
            if (mBackgroundWidth < 0) {
                this.thumbWidth = 0;
                mBackgroundWidth = 0;
                return measuredWidth;
            }
            contentSize = ceil(moveRange + Math.max(0, mThumbMargin.left) + Math.max(0, mThumbMargin.right) + Math.max(0, textExtraSpace));

            measuredWidth = Math.max(contentSize, contentSize + getPaddingLeft() + getPaddingRight());
        }
        return measuredWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measuredHeight = heightSize;

        if (this.thumbHeight == 0 && thumbDrawable != null) {
            this.thumbHeight = thumbDrawable.getIntrinsicHeight();
        }
        int contentSize;
        int textExtraSpace;
        if (heightMode == MeasureSpec.EXACTLY) {
            if (this.thumbHeight != 0) {
                /*
                If thumbHeight has been set, we calculate backHeight and check if there is enough room.
                 */
                mBackgroundHeight = ceil(this.thumbHeight + mThumbMargin.top + mThumbMargin.bottom);
                mBackgroundHeight = ceil(Math.max(mBackgroundHeight, mTextHeight));
                if (mBackgroundHeight + getPaddingTop() + getPaddingBottom() - Math.min(0, mThumbMargin.top) - Math.min(0, mThumbMargin.bottom) > heightSize) {
                    // No enough room, we set thumbHeight to zero to calculate these value again.
                    this.thumbHeight = 0;
                }
            }

            if (this.thumbHeight == 0) {
                mBackgroundHeight = ceil(heightSize - getPaddingTop() - getPaddingBottom() + Math.min(0, mThumbMargin.top) + Math.min(0, mThumbMargin.bottom));
                if (mBackgroundHeight < 0) {
                    mBackgroundHeight = 0;
                    this.thumbHeight = 0;
                    return measuredHeight;
                }
                this.thumbHeight = ceil(mBackgroundHeight - mThumbMargin.top - mThumbMargin.bottom);
            }
            if (this.thumbHeight < 0) {
                mBackgroundHeight = 0;
                this.thumbHeight = 0;
                return measuredHeight;
            }
        } else {
            if (this.thumbHeight == 0) {
                this.thumbHeight = ceil(getResources().getDisplayMetrics().density * DEFAULT_THUMB_SIZE_DP);
            }
            mBackgroundHeight = ceil(this.thumbHeight + mThumbMargin.top + mThumbMargin.bottom);
            if (mBackgroundHeight < 0) {
                mBackgroundHeight = 0;
                this.thumbHeight = 0;
                return measuredHeight;
            }
            textExtraSpace = ceil(mTextHeight - mBackgroundHeight);
            if (textExtraSpace > 0) {
                mBackgroundHeight += textExtraSpace;
                this.thumbHeight += textExtraSpace;
            }
            contentSize = (int) Math.max(this.thumbHeight, mBackgroundHeight);

            measuredHeight = Math.max(contentSize, contentSize + getPaddingTop() + getPaddingBottom());
            measuredHeight = Math.max(measuredHeight, getSuggestedMinimumHeight());
        }

        return measuredHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            setup();
        }
    }

    private int ceil(double dimen) {
        return (int) Math.ceil(dimen);
    }

    /**
     * set up the rect of back and thumb
     */
    private void setup() {
        if (this.thumbWidth == 0 || this.thumbHeight == 0 || mBackgroundWidth == 0 || mBackgroundHeight == 0) {
            return;
        }

        if (this.thumbRadius == -1) {
            this.thumbRadius = Math.min(this.thumbWidth, this.thumbHeight) / 2F;
        }
        if (backgroundRadius == -1) {
            backgroundRadius = Math.min(mBackgroundWidth, mBackgroundHeight) / 2F;
        }

        int contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int contentHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        // max range of drawing content, when thumbMargin is negative, drawing range is larger than backWidth
        int drawingWidth = ceil(mBackgroundWidth - Math.min(0, mThumbMargin.left) - Math.min(0, mThumbMargin.right));
        int drawingHeight = ceil(mBackgroundHeight - Math.min(0, mThumbMargin.top) - Math.min(0, mThumbMargin.bottom));

        float thumbTop;
        if (contentHeight <= drawingHeight) {
            thumbTop = getPaddingTop() + Math.max(0, mThumbMargin.top);
        } else {
            // center vertical in content area
            thumbTop = getPaddingTop() + Math.max(0, mThumbMargin.top) + (contentHeight - drawingHeight + 1) / 2F;
        }

        float thumbLeft;
        if (contentWidth <= mBackgroundWidth) {
            thumbLeft = getPaddingLeft() + Math.max(0, mThumbMargin.left);
        } else {
            thumbLeft = getPaddingLeft() + Math.max(0, mThumbMargin.left) + (contentWidth - drawingWidth + 1) / 2F;
        }

        mThumbRectF.set(thumbLeft, thumbTop, thumbLeft + this.thumbWidth, thumbTop + this.thumbHeight);

        float backLeft = mThumbRectF.left - mThumbMargin.left;
        mBackRectF.set(backLeft,
                mThumbRectF.top - mThumbMargin.top,
                backLeft + mBackgroundWidth,
                mThumbRectF.top - mThumbMargin.top + mBackgroundHeight);

        mSafeRectF.set(mThumbRectF.left, 0, mBackRectF.right - mThumbMargin.right - mThumbRectF.width(), 0);

        float minBackRadius = Math.min(mBackRectF.width(), mBackRectF.height()) / 2.f;
        backgroundRadius = Math.min(minBackRadius, backgroundRadius);

        if (backgroundDrawable != null) {
            backgroundDrawable.setBounds((int) mBackRectF.left, (int) mBackRectF.top, ceil(mBackRectF.right), ceil(mBackRectF.bottom));
        }

        if (mOnLayout != null) {
            float onLeft = mBackRectF.left + (mBackRectF.width() + this.textThumbInset - this.thumbWidth - mThumbMargin.right - mOnLayout.getWidth()) / 2f - textAdjust;
            float onTop = mBackRectF.top + (mBackRectF.height() - mOnLayout.getHeight()) / 2;
            mTextOnRectF.set(onLeft, onTop, onLeft + mOnLayout.getWidth(), onTop + mOnLayout.getHeight());
        }

        if (mOffLayout != null) {
            float offLeft = mBackRectF.right - (mBackRectF.width() + textThumbInset - this.thumbWidth - mThumbMargin.left - mOffLayout.getWidth()) / 2f - mOffLayout.getWidth() + textAdjust;
            float offTop = mBackRectF.top + (mBackRectF.height() - mOffLayout.getHeight()) / 2;
            mTextOffRectF.set(offLeft, offTop, offLeft + mOffLayout.getWidth(), offTop + mOffLayout.getHeight());
        }

        mReady = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mReady) {
            setup();
        }
        if (!mReady) {
            return;
        }

        // fade back
        if (backgroundDrawable != null) {
            if (isFadeBackground && mCurrentBackDrawable != null && mNextBackDrawable != null) {
                // fix #75, 70%A + 30%B != 30%B + 70%A, order matters when mix two layer of different alpha.
                // So make sure the order of on/off layers never change during slide from one endpoint to another.
                Drawable below = isChecked() ? mCurrentBackDrawable : mNextBackDrawable;
                Drawable above = isChecked() ? mNextBackDrawable : mCurrentBackDrawable;

                int alpha = (int) (255 * getProgress());
                below.setAlpha(alpha);
                below.draw(canvas);
                alpha = 255 - alpha;
                above.setAlpha(alpha);
                above.draw(canvas);
            } else {
                backgroundDrawable.setAlpha(255);
                backgroundDrawable.draw(canvas);
            }
        } else {
            if (isFadeBackground) {
                int alpha;
                int colorAlpha;

                // fix #75
                int belowColor = isChecked() ? mCurrBackColor : mNextBackColor;
                int aboveColor = isChecked() ? mNextBackColor : mCurrBackColor;

                // curr back
                alpha = (int) (255 * getProgress());
                colorAlpha = Color.alpha(belowColor);
                colorAlpha = colorAlpha * alpha / 255;
                mPaint.setARGB(colorAlpha, Color.red(belowColor), Color.green(belowColor), Color.blue(belowColor));
                canvas.drawRoundRect(mBackRectF, backgroundRadius, backgroundRadius, mPaint);

                // next back
                alpha = 255 - alpha;
                colorAlpha = Color.alpha(aboveColor);
                colorAlpha = colorAlpha * alpha / 255;
                mPaint.setARGB(colorAlpha, Color.red(aboveColor), Color.green(aboveColor), Color.blue(aboveColor));
                canvas.drawRoundRect(mBackRectF, backgroundRadius, backgroundRadius, mPaint);

                mPaint.setAlpha(255);
            } else {
                mPaint.setColor(mCurrBackColor);
                canvas.drawRoundRect(mBackRectF, backgroundRadius, backgroundRadius, mPaint);
            }
        }

        // text
        Layout switchText = getProgress() > 0.5 ? mOnLayout : mOffLayout;
        RectF textRectF = getProgress() > 0.5 ? mTextOnRectF : mTextOffRectF;
        if (switchText != null && textRectF != null) {
            int alpha = (int) (255 * (getProgress() >= 0.75 ? getProgress() * 4 - 3 : (getProgress() < 0.25 ? 1 - getProgress() * 4 : 0)));
            int textColor = getProgress() > 0.5 ? mOnTextColor : mOffTextColor;
            int colorAlpha = Color.alpha(textColor);
            colorAlpha = colorAlpha * alpha / 255;
            switchText.getPaint().setARGB(colorAlpha, Color.red(textColor), Color.green(textColor), Color.blue(textColor));
            canvas.save();
            canvas.translate(textRectF.left, textRectF.top);
            switchText.draw(canvas);
            canvas.restore();
        }

        // thumb
        mPresentThumbRectF.set(mThumbRectF);
        mPresentThumbRectF.offset(mProgress * mSafeRectF.width(), 0);
        if (thumbDrawable != null) {
            this.thumbDrawable.setBounds((int) mPresentThumbRectF.left, (int) mPresentThumbRectF.top, ceil(mPresentThumbRectF.right), ceil(mPresentThumbRectF.bottom));
            this.thumbDrawable.draw(canvas);
        } else {
            mPaint.setColor(mCurrThumbColor);
            canvas.drawRoundRect(mPresentThumbRectF, this.thumbRadius, this.thumbRadius, mPaint);
        }

        if (mDrawDebugRect) {
            mRectPaint.setColor(Color.parseColor("#AA0000"));
            canvas.drawRect(mBackRectF, mRectPaint);
            mRectPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawRect(mPresentThumbRectF, mRectPaint);
            mRectPaint.setColor(Color.parseColor("#000000"));
            canvas.drawLine(mSafeRectF.left, mThumbRectF.top, mSafeRectF.right, mThumbRectF.top, mRectPaint);
            mRectPaint.setColor(Color.parseColor("#00CC00"));
            canvas.drawRect(getProgress() > 0.5 ? mTextOnRectF : mTextOffRectF, mRectPaint);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (thumbDrawable == null) {
            thumbColorStateList = new XGradientHelper.ColorStateListBuilder()
                    .setUnSateColor(thumbColor)
                    .addPressedColor(thumbPressedColor)
                    .addCheckedColor(thumbCheckedColor)
                    .addDisabledColor(thumbDisabledColor)
                    .build();
            mCurrThumbColor = thumbColorStateList.getColorForState(getDrawableState(), mCurrThumbColor);
        } else {
            setDrawableState(this.thumbDrawable);
        }

        int[] nextState = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultTextColor = textColors.getDefaultColor();
            mOnTextColor = textColors.getColorForState(CHECKED_PRESSED_STATE, defaultTextColor);
            mOffTextColor = textColors.getColorForState(UNCHECKED_PRESSED_STATE, defaultTextColor);
        }
        if (backgroundDrawable == null) {
            thumbColorStateList = new XGradientHelper.ColorStateListBuilder()
                    .setUnSateColor(switchOffBackgroundColor)
                    .addCheckedColor(switchOnBackgroundColor)
                    .build();
            mCurrBackColor = thumbColorStateList.getColorForState(getDrawableState(), mCurrBackColor);
            mNextBackColor = thumbColorStateList.getColorForState(nextState, mCurrBackColor);
        } else {
            if (backgroundDrawable instanceof StateListDrawable && isFadeBackground) {
                backgroundDrawable.setState(nextState);
                mNextBackDrawable = backgroundDrawable.getCurrent().mutate();
            } else {
                mNextBackDrawable = null;
            }
            setDrawableState(backgroundDrawable);
            if (backgroundDrawable != null) {
                mCurrentBackDrawable = backgroundDrawable.getCurrent().mutate();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled() || !isClickable() || !isFocusable() || !mReady) {
            return false;
        }

        int action = event.getAction();

        float deltaX = event.getX() - mStartX;
        float deltaY = event.getY() - mStartY;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                mLastX = mStartX;
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                setProgress(getProgress() + (x - mLastX) / mSafeRectF.width());
                if (!mCatch && (Math.abs(deltaX) > mTouchSlop / 2F || Math.abs(deltaY) > mTouchSlop / 2F)) {
                    if (deltaY == 0 || Math.abs(deltaX) > Math.abs(deltaY)) {
                        catchView();
                    } else if (Math.abs(deltaY) > Math.abs(deltaX)) {
                        return false;
                    }
                }
                mLastX = x;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mCatch = false;
                setPressed(false);
                float time = event.getEventTime() - event.getDownTime();
                if (Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop && time < mClickTimeout) {
                    performClick();
                } else {
                    boolean nextStatus = getStatusBasedOnPos();
                    if (nextStatus != isChecked()) {
                        playSoundEffect(SoundEffectConstants.CLICK);
                        setChecked(nextStatus);
                    } else {
                        animateToState(nextStatus);
                    }
                }
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * return the status based on position of thumb
     *
     * @return whether checked or not
     */
    private boolean getStatusBasedOnPos() {
        return getProgress() > 0.5f;
    }

    private float getProgress() {
        return mProgress;
    }

    private void setProgress(final float progress) {
        float tp = progress;
        if (tp > 1) {
            tp = 1;
        } else if (tp < 0) {
            tp = 0;
        }
        this.mProgress = tp;
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * processing animation
     *
     * @param checked checked or unChecked
     */
    protected void animateToState(boolean checked) {
        if (mProgressAnimator == null) {
            return;
        }
        if (mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }
        mProgressAnimator.setDuration(animationDuration);
        if (checked) {
            mProgressAnimator.setFloatValues(mProgress, 1f);
        } else {
            mProgressAnimator.setFloatValues(mProgress, 0);
        }
        mProgressAnimator.start();
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        mCatch = true;
    }

    @Override
    public void setChecked(final boolean checked) {
        // animate before super.setChecked() become user may call setChecked again in OnCheckedChangedListener
        if (isChecked() != checked) {
            animateToState(checked);
        }
        if (mRestoring) {
            setCheckedImmediatelyNoEvent(checked);
        } else {
            super.setChecked(checked);
        }
    }

    public void setCheckedNoEvent(final boolean checked) {
        if (mChildOnCheckedChangeListener == null) {
            setChecked(checked);
        } else {
            super.setOnCheckedChangeListener(null);
            setChecked(checked);
            super.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
        }
    }

    public void setCheckedImmediatelyNoEvent(boolean checked) {
        if (mChildOnCheckedChangeListener == null) {
            setCheckedImmediately(checked);
        } else {
            super.setOnCheckedChangeListener(null);
            setCheckedImmediately(checked);
            super.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
        }
    }

    public void toggleNoEvent() {
        if (mChildOnCheckedChangeListener == null) {
            toggle();
        } else {
            super.setOnCheckedChangeListener(null);
            toggle();
            super.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
        }
    }

    public void toggleImmediatelyNoEvent() {
        if (mChildOnCheckedChangeListener == null) {
            toggleImmediately();
        } else {
            super.setOnCheckedChangeListener(null);
            toggleImmediately();
            super.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
        }
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        mChildOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setCheckedImmediately(boolean checked) {
        super.setChecked(checked);
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }
        setProgress(checked ? 1 : 0);
        invalidate();
    }

    public void toggleImmediately() {
        setCheckedImmediately(!isChecked());
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
            invalidate();
        }
    }

    public boolean isDrawDebugRect() {
        return mDrawDebugRect;
    }

    public void setDrawDebugRect(boolean drawDebugRect) {
        mDrawDebugRect = drawDebugRect;
        invalidate();
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public Drawable getThumbDrawable() {
        return this.thumbDrawable;
    }

    public void setThumbDrawable(Drawable thumbDrawable) {
        this.thumbDrawable = thumbDrawable;
        refreshDrawableState();
        mReady = false;
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int thumbDrawableRes) {
        setThumbDrawable(ContextCompat.getDrawable(getContext(), thumbDrawableRes));
    }

    public Drawable getBackDrawable() {
        return backgroundDrawable;
    }

    public void setBackDrawable(Drawable backDrawable) {
        backgroundDrawable = backDrawable;
        refreshDrawableState();
        mReady = false;
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int backDrawableRes) {
        setBackDrawable(ContextCompat.getDrawable(getContext(), backDrawableRes));
    }

    public float getThumbRangeRatio() {
        return thumbRangeRatio;
    }

    public void setThumbRangeRatio(float thumbRangeRatio) {
        this.thumbRangeRatio = thumbRangeRatio;
        // We need to mark "ready" to false since requestLayout may not cause size changed.
        mReady = false;
        requestLayout();
    }

    public RectF getThumbMargin() {
        return mThumbMargin;
    }

    public void setThumbMargin(RectF thumbMargin) {
        if (thumbMargin == null) {
            setThumbMargin(0, 0, 0, 0);
        } else {
            setThumbMargin(thumbMargin.left, thumbMargin.top, thumbMargin.right, thumbMargin.bottom);
        }
    }

    public void setThumbMargin(float left, float top, float right, float bottom) {
        mThumbMargin.set(left, top, right, bottom);
        mReady = false;
        requestLayout();
    }

    public void setThumbSize(int width, int height) {
        this.thumbWidth = width;
        this.thumbHeight = height;
        mReady = false;
        requestLayout();
    }

    public float getThumbWidth() {
        return this.thumbWidth;
    }

    public float getThumbHeight() {
        return this.thumbHeight;
    }

    public float getThumbRadius() {
        return this.thumbRadius;
    }

    public void setThumbRadius(float thumbRadius) {
        this.thumbRadius = thumbRadius;
        if (thumbDrawable == null) {
            invalidate();
        }
    }

    public PointF getBackSizeF() {
        return new PointF(mBackRectF.width(), mBackRectF.height());
    }

    public float getBackRadius() {
        return backgroundRadius;
    }

    public void setBackRadius(float backRadius) {
        backgroundRadius = backRadius;
        if (backgroundDrawable == null) {
            invalidate();
        }
    }

    public boolean isFadeBackground() {
        return isFadeBackground;
    }

    public void setFadeBackground(boolean isFadeBackground) {
        this.isFadeBackground = isFadeBackground;
    }

    public void setText(CharSequence onText, CharSequence offText) {
        switchOnText = onText;
        switchOffText = offText;
        mOnLayout = null;
        mOffLayout = null;
        mReady = false;
        requestLayout();
        invalidate();
    }

    public CharSequence getTextOn() {
        return switchOnText;
    }

    public CharSequence getTextOff() {
        return switchOffText;
    }

    public void setTextThumbInset(int textThumbInset) {
        this.textThumbInset = textThumbInset;
        mReady = false;
        requestLayout();
        invalidate();
    }

    public void setTextExtra(int textExtra) {
        this.textExtra = textExtra;
        mReady = false;
        requestLayout();
        invalidate();
    }

    public void setTextAdjust(int textAdjust) {
        this.textAdjust = textAdjust;
        mReady = false;
        requestLayout();
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.onText = switchOnText;
        ss.offText = switchOffText;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        setText(ss.onText, ss.offText);
        mRestoring = true;
        super.onRestoreInstanceState(ss.getSuperState());
        mRestoring = false;
    }

    static class SavedState extends BaseSavedState {
        CharSequence onText;
        CharSequence offText;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            onText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            offText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(onText, out, flags);
            TextUtils.writeToParcel(offText, out, flags);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public void setBackgroundColor(int offColor, int onColor) {
        this.switchOnBackgroundColor = onColor;
        this.switchOffBackgroundColor = offColor;
        invalidate();
    }

    public void setBackgroundSwitchOnColor(int onColor) {
        this.switchOnBackgroundColor = onColor;
        invalidate();
    }

    public void setBackgroundSwitchOffColor(int offColor) {
        this.switchOffBackgroundColor = offColor;
        invalidate();
    }

    public void setThumbColor(int thumbColor) {
        this.thumbColor = thumbColor;
        thumbColorStateList = new XGradientHelper.ColorStateListBuilder().setUnSateColor(thumbColor)
                .addPressedColor(thumbPressedColor)
                .addCheckedColor(thumbCheckedColor)
                .addDisabledColor(thumbDisabledColor)
                .build();
        invalidate();
    }

    public void setThumbCheckedColor(int thumbCheckedColor) {
        this.thumbCheckedColor = thumbCheckedColor;
        thumbColorStateList = new XGradientHelper.ColorStateListBuilder().setUnSateColor(thumbColor)
                .addPressedColor(thumbPressedColor)
                .addCheckedColor(thumbCheckedColor)
                .addDisabledColor(thumbDisabledColor)
                .build();
        invalidate();
    }

    public void setThumbPressedColor(int thumbPressedColor) {
        this.thumbPressedColor = thumbPressedColor;
        thumbColorStateList = new XGradientHelper.ColorStateListBuilder().setUnSateColor(thumbColor)
                .addPressedColor(thumbPressedColor)
                .addCheckedColor(thumbCheckedColor)
                .addDisabledColor(thumbDisabledColor)
                .build();
        invalidate();
    }

    public void setThumbDisabledColor(int thumbDisabledColor) {
        this.thumbDisabledColor = thumbDisabledColor;
        thumbColorStateList = new XGradientHelper.ColorStateListBuilder().setUnSateColor(thumbColor)
                .addPressedColor(thumbPressedColor)
                .addCheckedColor(thumbCheckedColor)
                .addDisabledColor(thumbDisabledColor)
                .build();
        invalidate();
    }

    public void setThumbColor(int thumbColor, int thumbCheckedColor, int thumbPressedColor, int thumbDisabledColor) {
        this.thumbColor = thumbColor;
        this.thumbPressedColor = thumbPressedColor;
        this.thumbCheckedColor = thumbCheckedColor;
        this.thumbDisabledColor = thumbDisabledColor;
        thumbColorStateList = new XGradientHelper.ColorStateListBuilder().setUnSateColor(thumbColor)
                .addPressedColor(thumbPressedColor)
                .addCheckedColor(thumbCheckedColor)
                .addDisabledColor(thumbDisabledColor)
                .build();
        invalidate();
    }
}