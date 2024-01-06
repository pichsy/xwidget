package com.pichs.xwidget.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.pichs.xwidget.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created pichs
 * 验证码输入框，可带加载动画，只限线性有动画
 */
public class XVerificationCodeEditText extends AppCompatEditText {

    private static final int TYPE_HOLLOW = 1;//空心
    private static final int TYPE_SOLID = 2;//实心
    private static final int TYPE_UNDERLINE = 3;//下划线

    private Paint borderPaint;// 边界画笔

    /**
     * 边界进度
     */
    private Paint borderLoadingPaint;//边界画笔
    /**
     * 只支持under_line类型
     */
    private Paint borderNoTextPaint;//没输入数字的边界画笔
    private Paint blockPaint;//实心块画笔
    private Paint textPaint;
    private Paint cursorPaint;

    private RectF borderRectF;
    private RectF boxRectF;//小方块、小矩形

    private int width;//可绘制宽度
    private int height;//可绘制高度

    private int boxWidth;//方块宽度
    private int boxHeight;//方块高度


    private int spacing;//方块之间间隙
    private int corner;//圆角
    private int maxLength;//最大位数
    private int borderWidth;//边界粗细
    private boolean password;//是否是密码类型
    private boolean showCursor;//显示光标
    private int cursorDuration;//光标闪动间隔
    private int cursorWidth;//光标宽度
    private int cursorColor;//光标颜色
    private int type;//实心方式、空心方式
    private int borderColor;// 有数字的边界下划线颜色
    private int borderNoTextColor;// 没数字的边界下划线颜色
    private int blockColor;
    private int textColor;
    private int borderLoadingColor;// 加载闪动动画下划线颜色
    private int borderColorTemp;// 有文字下划线颜色，缓存
    private int borderErrorColor;// 加载失败下划线颜色

    private boolean isCursorShowing;

    private CharSequence contentText;

    private TextChangedListener textChangedListener;

    private Timer timer;
    private TimerTask timerTask;

    private boolean isLoading = false;

    private float offsetX = 0f;
    // 55-255 200
    int alpha = 255;
    int alphaBounce = 55;
    // 是否弹回
    boolean isBounce = true;
    int alphaStep = 20;

    //，0开始左移，1开始右移，2回归原位
    int bounce_status = -1;

    // 移动延迟 1ms
    private static final int bounce_delay = 1;
    // 移动最大距离 px
    private static final float bounce_offset_max = 30;
    // 移动距离 px
    private static final float bounce_step = 10;

    public XVerificationCodeEditText(Context context) {
        this(context, null);
    }

    public XVerificationCodeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XVerificationCodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImeOptions(getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XVerificationCodeEditText);
        password = ta.getBoolean(R.styleable.XVerificationCodeEditText_xp_textPassword, false);
        showCursor = ta.getBoolean(R.styleable.XVerificationCodeEditText_xp_showCursor, true);
        borderColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_borderColor, Color.GRAY);
        borderColorTemp = ta.getColor(R.styleable.XVerificationCodeEditText_xp_borderColor, Color.GRAY);
        borderNoTextColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_borderNoTextColor, Color.GRAY);
        blockColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_blockColor, Color.BLUE);
        textColor = ta.getColor(R.styleable.XVerificationCodeEditText_android_textColor, Color.GRAY);
        cursorColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_cursorColor, Color.GRAY);
        corner = (int) ta.getDimension(R.styleable.XVerificationCodeEditText_xp_radius, 0);
        spacing = (int) ta.getDimension(R.styleable.XVerificationCodeEditText_xp_horizontalSpacing, 0);
        type = ta.getInt(R.styleable.XVerificationCodeEditText_xp_boxType, TYPE_HOLLOW);
        maxLength = ta.getInt(R.styleable.XVerificationCodeEditText_xp_maxNumber, 6);
        cursorDuration = ta.getInt(R.styleable.XVerificationCodeEditText_xp_cursorDuration, 500);
        cursorWidth = (int) ta.getDimension(R.styleable.XVerificationCodeEditText_xp_cursorWidth, 2);
        borderWidth = (int) ta.getDimension(R.styleable.XVerificationCodeEditText_xp_borderWidth, 5);
        borderLoadingColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_borderLoadingColor, Color.LTGRAY);
        borderErrorColor = ta.getColor(R.styleable.XVerificationCodeEditText_xp_borderErrorColor, Color.RED);
        ta.recycle();
        init();
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
        invalidate();
    }

    public void setCorner(int corner) {
        this.corner = corner;
        invalidate();
    }

    public void setMaxNumber(int maxLength) {
        this.maxLength = maxLength;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setPassword(boolean password) {
        this.password = password;
        invalidate();
    }

    public void setShowCursor(boolean showCursor) {
        this.showCursor = showCursor;
        postInvalidate();
    }

    public void setCursorDuration(int cursorDuration) {
        this.cursorDuration = cursorDuration;
        postInvalidate();
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
        postInvalidate();
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        postInvalidate();
    }

    public void setType(int type) {
        this.type = type;
        postInvalidate();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public void setBorderNoTextColor(int borderNoTextColor) {
        this.borderNoTextColor = borderNoTextColor;
        borderNoTextPaint = new Paint();
        borderNoTextPaint.setAntiAlias(true);
        borderNoTextPaint.setColor(borderNoTextColor);
        borderNoTextPaint.setStyle(Paint.Style.STROKE);
        borderNoTextPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public void setBlockColor(int blockColor) {
        this.blockColor = blockColor;
        this.blockPaint.setColor(blockColor);
        invalidate();
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }


    private void init() {
        this.setFocusableInTouchMode(true);
        this.setFocusable(true);
        this.requestFocus();
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        blockPaint = new Paint();
        blockPaint.setAntiAlias(true);
        blockPaint.setColor(blockColor);
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(1);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        borderNoTextPaint = new Paint();
        borderNoTextPaint.setAntiAlias(true);
        borderNoTextPaint.setColor(borderNoTextColor);
        borderNoTextPaint.setStyle(Paint.Style.STROKE);
        borderNoTextPaint.setStrokeWidth(borderWidth);

        borderLoadingPaint = new Paint();
        borderLoadingPaint.setAntiAlias(true);
        borderLoadingPaint.setColor(borderColor);
        borderLoadingPaint.setStyle(Paint.Style.STROKE);
        borderLoadingPaint.setStrokeWidth(borderWidth);
        borderLoadingPaint.setAlpha(alphaBounce);

        cursorPaint = new Paint();
        cursorPaint.setAntiAlias(true);
        cursorPaint.setColor(cursorColor);
        cursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cursorPaint.setStrokeWidth(cursorWidth);

        borderRectF = new RectF();
        boxRectF = new RectF();

        if (type == TYPE_HOLLOW){
            spacing = 0;
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        timer = new Timer();
    }


    /**
     * 开始动画
     */
    public void startLoading() {
        isLoading = true;
        borderColor = borderLoadingColor;
        setEnabled(false);
        Message message = Message.obtain();
        message.what = 1;
        message.arg1 = alpha;
        mHandler.sendMessage(message);
    }

    /**
     * 停止的动画
     *
     * @param isSuccess true成功 结束动画， 失败，则执行失败动画
     */
    public void stopLoading(boolean isSuccess) {

        isLoading = false;
        mHandler.sendEmptyMessage(0);
        if (isSuccess) {
            setBorderColor(borderColorTemp);
            offsetX = 0f;
            setEnabled(true);
        } else {
            setBorderColor(borderErrorColor);
            bounce_status = 0;
            mHandler.sendEmptyMessage(2);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    // 结束了
                    mHandler.removeMessages(1);
                    break;
                case 1:
                    if (isLoading) {
                        // 动画中
                        borderPaint = new Paint();
                        borderPaint.setAntiAlias(true);
                        borderPaint.setColor(borderColor);
                        borderPaint.setStyle(Paint.Style.STROKE);
                        borderPaint.setStrokeWidth(borderWidth);
                        borderPaint.setAlpha(alpha);

                        borderLoadingPaint = new Paint();
                        borderLoadingPaint.setAntiAlias(true);
                        borderLoadingPaint.setColor(borderColor);
                        borderLoadingPaint.setStyle(Paint.Style.STROKE);
                        borderLoadingPaint.setStrokeWidth(borderWidth);
                        borderLoadingPaint.setAlpha(alphaBounce);
                        postInvalidate();

                        if (isBounce) {
                            alpha -= alphaStep;
                            alphaBounce += alphaStep;
                            if (alpha <= 55) {
                                alpha = 55;
                                alphaBounce = 255;
                                isBounce = false;
                            }
                        } else {
                            alphaBounce -= alphaStep;
                            alpha += alphaStep;
                            if (alpha >= 255) {
                                alpha = 255;
                                alphaBounce = 55;
                                isBounce = true;
                            }
                        }
                        mHandler.sendEmptyMessageDelayed(1, 50);
                    }
                    break;
                case 2:
                    // 抖动监听
                    if (bounce_status == 0) {
                        offsetX -= bounce_step;
                        if (offsetX <= -bounce_offset_max) {
                            offsetX = -bounce_offset_max;
                            bounce_status = 1;
                        }
                        mHandler.sendEmptyMessageDelayed(2, bounce_delay);
                    } else if (bounce_status == 1) {
                        offsetX += bounce_step;
                        if (offsetX >= bounce_offset_max) {
                            offsetX = bounce_offset_max;
                            bounce_status = 2;
                        }
                        mHandler.sendEmptyMessageDelayed(2, bounce_delay);
                    } else if (bounce_status == 2) {
                        offsetX -= bounce_step;
                        if (offsetX <= 0) {
                            offsetX = 0;
                            bounce_status = -1;
                        }
                        mHandler.sendEmptyMessageDelayed(2, bounce_delay);
                    } else {
                        // 结束动画
                        mHandler.sendEmptyMessage(3);
                    }
                    invalidate();
                    break;
                case 3:
                    // 抖动结束
                    mHandler.removeMessages(2);
                    offsetX = 0;
                    invalidate();
                    setEnabled(true);
                    if (textChangedListener != null) {
                        textChangedListener.errorCompleted();
                    }
                    break;

            }
        }
    };


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        boxWidth = (width - spacing * (maxLength + 1)) / maxLength;
        boxHeight = height;

        borderRectF.set(0, 0, width, height);
        float textSize = getTextSize();
        if (textSize > (boxWidth >> 1)) {
            textSize = (boxWidth >> 1);
        }
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRect(canvas);

        drawText(canvas, contentText);

        drawCursor(canvas);

    }

    /**
     * 绘制光标
     *
     * @param canvas
     */
    private void drawCursor(Canvas canvas) {
        if (!isCursorShowing && showCursor && contentText.length() < maxLength && hasFocus()) {
            int cursorPosition = contentText.length() + 1;
            int startX = spacing * cursorPosition + boxWidth * (cursorPosition - 1) + boxWidth / 2;
            int startY = boxHeight / 4;
            int endX = startX;
            int endY = boxHeight - boxHeight / 4;
            canvas.drawLine(startX, startY, endX, endY, cursorPaint);
        }
    }

    private void drawRect(Canvas canvas) {
        for (int i = 0; i < maxLength; i++) {

            boxRectF.set(spacing * (i + 1) + boxWidth * i, 0,
                    spacing * (i + 1) + boxWidth * i + boxWidth,
                    boxHeight);

            if (type == TYPE_SOLID) {
                canvas.drawRoundRect(boxRectF, corner, corner, blockPaint);
            } else if (type == TYPE_UNDERLINE) {
                if (!isLoading) {
                    if (i <= contentText.length() - 1) {
                        canvas.drawLine(boxRectF.left + offsetX, boxRectF.bottom, boxRectF.right + offsetX, boxRectF.bottom, borderPaint);
                    } else {
                        canvas.drawLine(boxRectF.left + offsetX, boxRectF.bottom, boxRectF.right + offsetX, boxRectF.bottom, borderNoTextPaint);
                    }
                } else {
                    // 动画效果
                    if (i % 2 == 0) {
                        canvas.drawLine(boxRectF.left, boxRectF.bottom, boxRectF.right, boxRectF.bottom, borderPaint);
                    } else {
                        canvas.drawLine(boxRectF.left, boxRectF.bottom, boxRectF.right, boxRectF.bottom, borderLoadingPaint);
                    }
                }
            } else if (type == TYPE_HOLLOW) {
                if (i == 0 || i == maxLength) {
                    continue;
                }
                canvas.drawLine(boxRectF.left, boxRectF.top, boxRectF.left, boxRectF.bottom, borderPaint);
            }
        }

        if (type == TYPE_HOLLOW)
            canvas.drawRoundRect(borderRectF, corner, corner, borderPaint);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        contentText = text;
        if (!isLoading) {
            this.borderColor = borderColorTemp;
            borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
        }
        invalidate();
        if (textChangedListener != null) {
            if (text.length() == maxLength) {
                textChangedListener.textCompleted(text);
            } else {
                textChangedListener.textChanged(text);
            }
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //cursorFlashTime为光标闪动的间隔时间
        timer.scheduleAtFixedRate(timerTask, 0, cursorDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text = getText();
        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(selStart, selEnd);
    }

    private void drawText(Canvas canvas, CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            int startX = spacing * (i + 1) + boxWidth * i;
            int startY = 0;
            int baseX = (int) (startX + boxWidth / 2 - textPaint.measureText(String.valueOf(charSequence.charAt(i))) / 2);
            int baseY = (int) (startY + boxHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2);
            int centerX = startX + boxWidth / 2;
            int centerY = startY + boxHeight / 2;
            int radius = Math.min(boxWidth, boxHeight) / 6;
            if (password)
                canvas.drawCircle(centerX, centerY, radius, textPaint);
            else
                canvas.drawText(String.valueOf(charSequence.charAt(i)), baseX, baseY, textPaint);
        }

    }

    public void setTextChangedListener(TextChangedListener listener) {
        textChangedListener = listener;
    }

    public void clearText() {
        setText("");
    }

    /**
     * 密码监听者
     */
    public interface TextChangedListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void textChanged(CharSequence changeText);

        /**
         * 输入完成
         */
        void textCompleted(CharSequence text);

        /**
         * 错误提示完成
         */
        void errorCompleted();
    }

}
