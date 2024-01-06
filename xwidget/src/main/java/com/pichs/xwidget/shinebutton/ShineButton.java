package com.pichs.xwidget.shinebutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Checkable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.pichs.xwidget.R;
import com.pichs.xwidget.utils.XColorHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 增强效果的按钮
 * <p>
 * ShineButton
 *
 * @since 2020-01-06 13:16
 */
public class ShineButton extends PorterShapeImageView implements Checkable {
    private boolean mIsChecked = false;
    private int mNormalColor;
    private int mCheckedColor;
    private ValueAnimator mShakeAnimator;
    private ShineView.ShineParams mShineParams = new ShineView.ShineParams();

    private OnCheckedChangeListener mOnCheckedChangeListener;

    WeakReference<Window> mWindow;
    private boolean mIsDialog;

    public ShineButton(Context context) {
        this(context, null);
    }

    public ShineButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ShineButtonStyle);
    }

    public ShineButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton(context, attrs);
    }

    private void initButton(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShineButton);
        mNormalColor = a.getColor(R.styleable.ShineButton_xp_shine_normal_color, Color.LTGRAY);
        mCheckedColor = a.getColor(R.styleable.ShineButton_xp_shine_checked_color, Color.BLUE);
        mShineParams.allowRandomColor = a.getBoolean(R.styleable.ShineButton_xp_shine_allow_random_color, false);
        mShineParams.animDuration = a.getInteger(R.styleable.ShineButton_xp_shine_animation_duration, (int) mShineParams.animDuration);
        mShineParams.clickAnimDuration = a.getInteger(R.styleable.ShineButton_xp_shine_click_animation_duration, (int) mShineParams.clickAnimDuration);
        mShineParams.enableFlashing = a.getBoolean(R.styleable.ShineButton_xp_shine_enable_flashing, false);
        mShineParams.shineDistanceMultiple = a.getFloat(R.styleable.ShineButton_xp_shine_distance_multiple, mShineParams.shineDistanceMultiple);
        mShineParams.shineCount = a.getInteger(R.styleable.ShineButton_xp_shine_count, mShineParams.shineCount);
        mShineParams.shineSize = a.getDimensionPixelSize(R.styleable.ShineButton_xp_shine_size, mShineParams.shineSize);
        mShineParams.shineTurnAngle = a.getFloat(R.styleable.ShineButton_xp_shine_turn_angle, mShineParams.shineTurnAngle);
        mShineParams.smallShineOffsetAngle = a.getFloat(R.styleable.ShineButton_xp_shine_small_shine_offset_angle, mShineParams.smallShineOffsetAngle);
        mShineParams.smallShineColor = a.getColor(R.styleable.ShineButton_xp_shine_small_shine_color, mShineParams.smallShineColor);
        mShineParams.bigShineColor = a.getColor(R.styleable.ShineButton_xp_shine_big_shine_color, mShineParams.bigShineColor);
        String colors = a.getString(R.styleable.ShineButton_xp_shine_flashing_colors);

        ArrayList<Integer> colorList = new ArrayList<>();
        if (colors != null && !colors.trim().isEmpty()) {
            String[] colorArr = colors.split("[,，]");
            for (String colorStr : colorArr) {
                try {
                    if (colorStr.trim().isEmpty()) {
                        continue;
                    }
                    int color = XColorHelper.parseColor(colorStr);
                    colorList.add(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mShineParams.flashingColors = colorList;

        a.recycle();
        setTintColor(mNormalColor);

        if (context instanceof Activity) {
            initWindow((Activity) context);
        }
    }

    /**
     * 修复对话框中显示的问题
     *
     * @param dialog
     */
    public void fitDialog(Dialog dialog) {
        mWindow = new WeakReference<>(dialog.getWindow());
        mIsDialog = true;
    }

    public void fitFragment(Fragment fragment) {
        initWindow(fragment.getActivity());
    }

    public int getColor() {
        return mCheckedColor;
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    public ShineButton setNormalColor(int normalColor) {
        mNormalColor = normalColor;
        setTintColor(mNormalColor);
        return this;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public ShineButton setCheckedColor(int checkedColor) {
        mCheckedColor = checkedColor;
        return this;
    }

    public void setChecked(boolean checked, boolean anim) {
        setChecked(checked, anim, true);
    }

    private void setChecked(boolean checked, boolean anim, boolean callBack) {
        mIsChecked = checked;
        if (checked) {
            setTintColor(mCheckedColor);
            mIsChecked = true;
            if (anim) {
                showAnim();
            }
        } else {
            setTintColor(mNormalColor);
            mIsChecked = false;
            if (anim) {
                setCancel();
            }
        }
        if (callBack) {
            onCheckedChanged(checked);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        setChecked(checked, false, false);
    }

    private void onCheckedChanged(boolean checked) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, checked);
        }
    }

    public void setCancel() {
        setTintColor(mNormalColor);
        if (mShakeAnimator != null) {
            mShakeAnimator.end();
            mShakeAnimator.cancel();
        }
    }

    public ShineButton setAllowRandomColor(boolean allowRandomColor) {
        mShineParams.allowRandomColor = allowRandomColor;
        return this;
    }

    public ShineButton setAnimDuration(int durationMs) {
        mShineParams.animDuration = durationMs;
        return this;
    }

    public ShineButton setBigShineColor(int color) {
        mShineParams.bigShineColor = color;
        return this;
    }

    public ShineButton setClickAnimDuration(int durationMs) {
        mShineParams.clickAnimDuration = durationMs;
        return this;
    }

    public ShineButton enableFlashing(boolean enable) {
        mShineParams.enableFlashing = enable;
        return this;
    }

    public ShineButton setShineCount(int count) {
        mShineParams.shineCount = count;
        return this;
    }

    public ShineButton setShineDistanceMultiple(float multiple) {
        mShineParams.shineDistanceMultiple = multiple;
        return this;
    }

    public ShineButton setShineTurnAngle(float angle) {
        mShineParams.shineTurnAngle = angle;
        return this;
    }

    public ShineButton setSmallShineColor(int color) {
        mShineParams.smallShineColor = color;
        return this;
    }

    public ShineButton setSmallShineOffAngle(float angle) {
        mShineParams.smallShineOffsetAngle = angle;
        return this;
    }

    public ShineButton setShineSize(int size) {
        mShineParams.shineSize = size;
        return this;
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        if (listener instanceof OnClickListenWrapper) {
            super.setOnClickListener(listener);
        } else {
            if (mOnClickListenWrapper != null) {
                mOnClickListenWrapper.wrapListener(listener);
            }
        }
    }

    private OnClickListenWrapper mOnClickListenWrapper;

    public void initWindow(Activity activity) {
        initWindow(activity.getWindow());
        mIsDialog = false;
    }

    public void initWindow(Window window) {
        mWindow = new WeakReference<>(window);
        mOnClickListenWrapper = new OnClickListenWrapper();
        setOnClickListener(mOnClickListenWrapper);
    }

    public void showAnim() {
        if (getWindow() != null) {
            ShineView shineView = new ShineView(getContext(), this, mShineParams);
            ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
            if (mIsDialog) {
                View innerView = rootView.findViewById(Window.ID_ANDROID_CONTENT);
                rootView.addView(shineView, new ViewGroup.LayoutParams(innerView.getWidth(), innerView.getHeight()));
            } else {
                rootView.addView(shineView, new ViewGroup.LayoutParams(rootView.getWidth(), rootView.getHeight()));
            }
            doShareAnim();
        }
    }

    public void removeView(View view) {
        if (getWindow() != null) {
            final ViewGroup rootView = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            rootView.removeView(view);
        }
    }

    public Window getWindow() {
        if (mWindow != null) {
            return mWindow.get();
        }
        return null;
    }

    public ShineButton setIconResource(int resId) {
        setIconDrawable(ContextCompat.getDrawable(getContext(), resId));
        return this;
    }

    private void doShareAnim() {
        mShakeAnimator = ValueAnimator.ofFloat(0.4f, 1f, 0.9f, 1f);
        mShakeAnimator.setInterpolator(new LinearInterpolator());
        mShakeAnimator.setDuration(500);
        mShakeAnimator.setStartDelay(180);
        invalidate();
        mShakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setScaleX((float) valueAnimator.getAnimatedValue());
                setScaleY((float) valueAnimator.getAnimatedValue());
            }
        });
        mShakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                setTintColor(mCheckedColor);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setTintColor(mIsChecked ? mCheckedColor : mNormalColor);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                setTintColor(mNormalColor);
            }

        });
        mShakeAnimator.start();
    }

    public class OnClickListenWrapper implements OnClickListener {

        public void wrapListener(OnClickListener listener) {
            mListener = listener;
        }

        private OnClickListener mListener;

        OnClickListenWrapper() {
        }

        public OnClickListenWrapper(OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            if (!mIsChecked) {
                mIsChecked = true;
                showAnim();
            } else {
                mIsChecked = false;
                setCancel();
            }
            onCheckedChanged(mIsChecked);
            if (mListener != null) {
                mListener.onClick(view);
            }
        }
    }

    public ShineButton setOnCheckStateChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
        return this;
    }

    /**
     * 点击切换的监听
     */
    public interface OnCheckedChangeListener {
        /**
         * 选中状态改变
         *
         * @param shineButton
         * @param isChecked
         */
        void onCheckedChanged(ShineButton shineButton, boolean isChecked);
    }

}
