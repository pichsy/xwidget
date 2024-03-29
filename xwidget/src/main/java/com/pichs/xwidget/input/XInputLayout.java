package com.pichs.xwidget.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pichs.xwidget.R;
import com.pichs.xwidget.cardview.XCardLinearLayout;
import com.pichs.xwidget.utils.XDisplayHelper;
import com.pichs.xwidget.view.XEditText;
import com.pichs.xwidget.view.XImageView;

/**
 * 输入框 带密码和 清空按钮
 */
public class XInputLayout extends XCardLinearLayout {

    private XEditText mEditText;
    private XImageView mClearImageView;
    private XImageView mEyeImageView;
    // 显示清空按钮吗
    private boolean isClearIconShow = false;
    // 显示眼睛吗
    private boolean isEyeIconShow = false;
    // 是否是密文
    private boolean isTextPassword = false;

    // 睁眼的图片
    private Drawable mEyeOpenDrawable;
    // 闭眼的图片
    private Drawable mEyeCloseDrawable;
    // 关闭按钮
    private Drawable mClearDrawable;
    // 输入类型,密文输入类型
    private int pwdInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    // 明文输入类型
    private int norInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
    private boolean disableCopyAndPaste = false;
    private int maxLength = 0;
    /**
     * typeface
     * {@link Typeface#NORMAL},
     * {@link Typeface#BOLD},
     * {@link Typeface#ITALIC}
     */
    private int textStyle = Typeface.NORMAL;
    private int maxLines = 1;
    private int textColor = Color.BLACK, textHintColor = Color.LTGRAY;
    private int textSize = 0;
    private int clearIconPadding = 0;
    private int eyeIconPadding = 0;
    // 图标大小
    private int clearIconWidth, clearIconHeight;
    private int eyeIconWidth, eyeIconHeight;

    private CharSequence text = "";
    private CharSequence hintText = "";
    private int eyeIconColorFilter, clearIconColorFilter;

    public XInputLayout(Context context) {
        this(context, null);
    }

    public XInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.widget_input_layout, this);
        mEditText = findViewById(R.id.et_content);
        mClearImageView = findViewById(R.id.iv_clear);
        mEyeImageView = findViewById(R.id.iv_eye);
        eyeIconWidth = XDisplayHelper.dp2px(context, 30);
        eyeIconHeight = eyeIconWidth;
        clearIconWidth = XDisplayHelper.dp2px(context, 26);
        clearIconHeight = clearIconWidth;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XInputLayout, defStyleAttr, 0);
        int indexCount = ta.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.XInputLayout_xp_clearIconVisible) {
                isClearIconShow = ta.getBoolean(index, false);
            } else if (index == R.styleable.XInputLayout_xp_eyeIconVisible) {
                isEyeIconShow = ta.getBoolean(index, false);
            } else if (index == R.styleable.XInputLayout_xp_textPassword) {
                isTextPassword = ta.getBoolean(index, false);
            } else if (index == R.styleable.XInputLayout_xp_clearDrawable) {
                mClearDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.XInputLayout_xp_eyeOpenDrawable) {
                mEyeOpenDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.XInputLayout_xp_eyeCloseDrawable) {
                mEyeCloseDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.XInputLayout_xp_eyeIcon_padding) {
                eyeIconPadding = ta.getDimensionPixelSize(index, eyeIconPadding);
            } else if (index == R.styleable.XInputLayout_xp_eyeIcon_width) {
                eyeIconWidth = ta.getDimensionPixelSize(index, eyeIconWidth);
            } else if (index == R.styleable.XInputLayout_xp_eyeIcon_height) {
                eyeIconHeight = ta.getDimensionPixelSize(index, eyeIconHeight);
            } else if (index == R.styleable.XInputLayout_xp_clearIcon_height) {
                clearIconHeight = ta.getDimensionPixelSize(index, clearIconHeight);
            } else if (index == R.styleable.XInputLayout_xp_clearIcon_width) {
                clearIconWidth = ta.getDimensionPixelSize(index, clearIconWidth);
            } else if (index == R.styleable.XInputLayout_xp_clearIcon_padding) {
                clearIconPadding = ta.getDimensionPixelSize(index, clearIconPadding);
            } else if (index == R.styleable.XInputLayout_android_text) {
                text = ta.getString(index);
            } else if (index == R.styleable.XInputLayout_android_textStyle) {
                textStyle = ta.getInt(index, textStyle);
            } else if (index == R.styleable.XInputLayout_xp_eyeIconColorFilter) {
                eyeIconColorFilter = ta.getColor(index, 0);
            } else if (index == R.styleable.XInputLayout_xp_clearIconColorFilter) {
                clearIconColorFilter = ta.getColor(index, 0);
            } else if (index == R.styleable.XInputLayout_android_hint) {
                hintText = ta.getString(index);
            } else if (index == R.styleable.XInputLayout_android_textSize) {
                textSize = ta.getDimensionPixelSize(index, textSize);
            } else if (index == R.styleable.XInputLayout_android_textColorHint) {
                textHintColor = ta.getColor(index, textHintColor);
            } else if (index == R.styleable.XInputLayout_android_textColor) {
                textColor = ta.getColor(index, textColor);
            } else if (index == R.styleable.XInputLayout_android_maxLength) {
                maxLength = ta.getInt(index, maxLength);
            } else if (index == R.styleable.XInputLayout_android_maxLines) {
                maxLines = ta.getInt(index, maxLines);
            } else if (index == R.styleable.XInputLayout_xp_inputType_password) {
                pwdInputType = ta.getInt(index, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (index == R.styleable.XInputLayout_xp_inputType_normal) {
                norInputType = ta.getInt(index, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            } else if (index == R.styleable.XInputLayout_xp_disableCopyAndPaste) {
                disableCopyAndPaste = ta.getBoolean(index, false);
            }
        }
        ta.recycle();

        setText(text);
        setHintText(hintText);
        setTextSize(textSize);
        setTextColor(textColor);
        setHintTextColor(textHintColor);
        setMaxLength(maxLength);
        setTextStyle(textStyle);


        setEyeIconVisible(isEyeIconShow);
        setEyeCloseDrawable(mEyeCloseDrawable);
        setEyeOpenDrawable(mEyeOpenDrawable);
        setEyeIconSize(eyeIconWidth, eyeIconHeight);
        setEyeIconPadding(eyeIconPadding);
        setEyeIconColorFilter(eyeIconColorFilter);

        setClearDrawable(mClearDrawable);
        setClearIconVisible(isClearIconShow);
        setClearIconSize(clearIconWidth, clearIconHeight);
        setClearIconPadding(clearIconPadding);
        setClearIconColorFilter(clearIconColorFilter);
        handleViewData();
    }

    public XInputLayout setClearIconColorFilter(int clearIconColorFilter) {
        this.clearIconColorFilter = clearIconColorFilter;
        mClearImageView.setColorFilterOverride(clearIconColorFilter);
        return this;
    }

    public XInputLayout setEyeIconColorFilter(int eyeIconColorFilter) {
        this.eyeIconColorFilter = eyeIconColorFilter;
        mEyeImageView.setColorFilterOverride(eyeIconColorFilter);
        return this;
    }

    public XInputLayout setTextStyle(int textStyle) {
        if (textStyle == 0 || textStyle == 1 || textStyle == 2) {
            this.textStyle = textStyle;
            mEditText.setTypeface(mEditText.getTypeface(), textStyle);
        }
        return this;
    }

    public XInputLayout setHintTextColor(int textHintColor) {
        if (textHintColor != 0) {
            this.textHintColor = textHintColor;
            mEditText.setHintTextColor(textHintColor);
        }
        return this;
    }

    public XInputLayout setTextColor(int color) {
        if (color != 0) {
            this.textColor = color;
            mEditText.setTextColor(textColor);
        }
        return this;
    }

    public XInputLayout setTextSize(int textSize) {
        if (textSize != 0) {
            this.textSize = textSize;
            mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        return this;
    }

    public XInputLayout setHintText(CharSequence hintText) {
        this.hintText = hintText;
        if (!TextUtils.isEmpty(hintText)) {
            mEditText.setHint(hintText);
        } else {
            this.hintText = "";
            mEditText.setHint(this.hintText);
        }
        return this;
    }

    private InputFilter[] getLengthFilters(int maxLength) {
        return new InputFilter[]{
                new InputFilter.LengthFilter(maxLength)
        };
    }

    /**
     * 设置输入字符的个数最大不超过maxLength
     */
    public XInputLayout setMaxLength(int maxLength) {
        if (maxLength != 0) {
            this.maxLength = maxLength;
            mEditText.setFilters(getLengthFilters(maxLength));
        }
        return this;
    }

    public XInputLayout setMaxLines(int maxLines) {
        if (maxLines < 1) {
            maxLines = 1;
        }
        mEditText.setMaxLines(maxLines);
        return this;
    }

    public XInputLayout setClearDrawable(Drawable clearDrawable) {
        mClearDrawable = clearDrawable;
        mClearImageView.setImageDrawable(mClearDrawable);
        return this;
    }


    public XInputLayout setClearIconPadding(int clearIconPadding) {
        this.clearIconPadding = clearIconPadding;
        if (mClearImageView != null) {
            mClearImageView.setPadding(clearIconPadding, clearIconPadding, clearIconPadding, clearIconPadding);
        }
        return this;
    }

    public XInputLayout setClearIconSize(int width, int height) {
        this.clearIconHeight = height;
        this.clearIconWidth = width;
        if (mClearImageView != null) {
            ViewGroup.LayoutParams layoutParams = mClearImageView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = clearIconHeight;
                layoutParams.width = clearIconWidth;
                mClearImageView.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public XInputLayout setDisableCopyAndPaste(boolean disableCopyAndPaste) {
        this.disableCopyAndPaste = disableCopyAndPaste;
        mEditText.setDisableCopyAndPaste(this.disableCopyAndPaste);
        return this;
    }


    public XInputLayout setEyeIconSize(int width, int height) {
        this.eyeIconWidth = width;
        this.eyeIconHeight = height;
        if (mEyeImageView != null) {
            ViewGroup.LayoutParams layoutParams = mEyeImageView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = eyeIconWidth;
                layoutParams.height = eyeIconHeight;
                mEyeImageView.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public XInputLayout setEyeIconPadding(int eyeIconPadding) {
        this.eyeIconPadding = eyeIconPadding;
        if (mEyeImageView != null) {
            mEyeImageView.setPadding(eyeIconPadding, eyeIconPadding, eyeIconPadding, eyeIconPadding);
        }
        return this;
    }

    public XInputLayout setEyeCloseDrawable(Drawable eyeCloseDrawable) {
        this.mEyeCloseDrawable = eyeCloseDrawable;
        if (isTextPassword) {
            mEyeImageView.setImageDrawable(mEyeCloseDrawable);
        }
        return this;
    }

    public XInputLayout setEyeOpenDrawable(Drawable eyeOpenDrawable) {
        this.mEyeOpenDrawable = eyeOpenDrawable;
        if (!isTextPassword) {
            mEyeImageView.setImageDrawable(mEyeOpenDrawable);
        }
        return this;
    }


    public XInputLayout setText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            mEditText.setText("");
            this.text = "";
            return this;
        }
        this.text = text;
        mEditText.setText(text);
        return this;
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    private void handleViewData() {

        if (isTextPassword) {
            hidePassword(mEditText);
            if (isEyeIconShow) {
                if (mEyeCloseDrawable != null) {
                    mEyeImageView.setImageDrawable(mEyeCloseDrawable);
                }
            }
        } else {
            showPassword(mEditText);
            if (isEyeIconShow) {
                if (mEyeOpenDrawable != null) {
                    mEyeImageView.setImageDrawable(mEyeOpenDrawable);
                }
            }
        }

        if (isClearIconShow && !TextUtils.isEmpty(mEditText.getText())) {
            mClearImageView.setVisibility(View.VISIBLE);
        } else {
            mClearImageView.setVisibility(View.GONE);
        }

        mEditText.setDisableCopyAndPaste(disableCopyAndPaste);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isClearIconShow && !TextUtils.isEmpty(s)) {
                    mClearImageView.setVisibility(View.VISIBLE);
                } else {
                    mClearImageView.setVisibility(View.GONE);
                }
            }
        });

        mEyeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextPassword) {
                    showPassword();
                } else {
                    hidePassword();
                }
            }
        });

        mClearImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                if (isClearIconShow && !TextUtils.isEmpty(mEditText.getText())) {
                    mClearImageView.setVisibility(View.VISIBLE);
                } else {
                    mClearImageView.setVisibility(View.GONE);
                }
            }
        });

    }

    public XEditText getEditText() {
        return mEditText;
    }

    public void setClearIconVisible(boolean visible) {
        this.isClearIconShow = visible;
        if (isClearIconShow && !TextUtils.isEmpty(mEditText.getText())) {
            mClearImageView.setVisibility(View.VISIBLE);
        } else {
            mClearImageView.setVisibility(View.GONE);
        }
    }

    public void setEyeIconVisible(boolean visible) {
        this.isEyeIconShow = visible;
        if (visible) {
            mEyeImageView.setVisibility(View.VISIBLE);
        } else {
            mEyeImageView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示密码
     */
    public void showPassword() {
        showPassword(mEditText);
        isTextPassword = false;
        if (mEyeOpenDrawable != null) {
            mEyeImageView.setImageDrawable(mEyeOpenDrawable);
        }
    }

    /**
     * 隐藏密码
     */
    public void hidePassword() {
        hidePassword(mEditText);
        isTextPassword = true;
        if (mEyeCloseDrawable != null) {
            mEyeImageView.setImageDrawable(mEyeCloseDrawable);
        }
    }

    /**
     * 显示密码
     *
     * @param editText
     */
    private void showPassword(EditText editText) {
        editText.setInputType(this.norInputType);
        if (editText.getText() != null) {
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 隐藏密码
     *
     * @param editText
     */
    private void hidePassword(EditText editText) {
        editText.setInputType(this.pwdInputType);
        if (editText.getText() != null) {
            editText.setSelection(editText.getText().length());
        }
    }

    public void setNormalInputType(int inputType) {
        this.norInputType = inputType;
    }

    public void setPasswordInputType(int inputType) {
        this.pwdInputType = inputType;
    }

    public int getPasswordInputType() {
        return pwdInputType;
    }

    public int getNormalInputType() {
        return norInputType;
    }

    public void addTextWatch(TextWatcher textWatcher) {
        getEditText().addTextChangedListener(textWatcher);
    }

    public void removeTextWatch(TextWatcher textWatcher) {
        getEditText().removeTextChangedListener(textWatcher);
    }
}
