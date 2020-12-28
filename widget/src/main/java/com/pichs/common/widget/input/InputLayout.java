package com.pichs.common.widget.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.pichs.common.widget.R;
import com.pichs.common.widget.cardview.XCardLinearLayout;
import com.pichs.common.widget.view.XEditText;
import com.pichs.common.widget.view.XImageView;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/10/9$ 17:35$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/9$ 17:35$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class InputLayout extends XCardLinearLayout {

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

    public InputLayout(Context context) {
        this(context, null);
    }

    public InputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.widget_input_layout, this);
        mEditText = findViewById(R.id.et_content);
        mClearImageView = findViewById(R.id.iv_clear);
        mEyeImageView = findViewById(R.id.iv_eye);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputLayout, defStyleAttr, 0);
        int indexCount = ta.getIndexCount();
        int maxLength = 0;
        int textColor = -1, textHintColor = -1, textSize = 0;
        String text = null, bindText = null;
        String hintText = "";
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.InputLayout_xp_clearIconVisible) {
                isClearIconShow = ta.getBoolean(index, false);
            } else if (index == R.styleable.InputLayout_xp_eyeIconVisible) {
                isEyeIconShow = ta.getBoolean(index, false);
            } else if (index == R.styleable.InputLayout_xp_textPassword) {
                isTextPassword = ta.getBoolean(index, false);
            } else if (index == R.styleable.InputLayout_xp_clearDrawable) {
                mClearDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.InputLayout_xp_eyeOpenDrawable) {
                mEyeOpenDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.InputLayout_xp_eyeCloseDrawable) {
                mEyeCloseDrawable = ta.getDrawable(index);
            } else if (index == R.styleable.InputLayout_android_text) {
                text = ta.getString(index);
            } else if (index == R.styleable.InputLayout_bindText) {
                bindText = ta.getString(index);
            } else if (index == R.styleable.InputLayout_android_hint) {
                hintText = ta.getString(index);
            } else if (index == R.styleable.InputLayout_android_textSize) {
                textSize = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.InputLayout_android_textColorHint) {
                textHintColor = ta.getColor(index, -1);
            } else if (index == R.styleable.InputLayout_android_textColor) {
                textColor = ta.getColor(index, -1);
            } else if (index == R.styleable.InputLayout_android_maxLength) {
                maxLength = ta.getInt(index, 0);
            } else if (index == R.styleable.InputLayout_xp_inputType_password) {
                pwdInputType = ta.getInt(index, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (index == R.styleable.InputLayout_xp_inputType_normal) {
                norInputType = ta.getInt(index, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            } else if (index == R.styleable.InputLayout_xp_disableCopyAndPaste) {
                disableCopyAndPaste = ta.getBoolean(index, false);
            }
        }
        ta.recycle();
        if (!TextUtils.isEmpty(text)) {
            mEditText.setText(text);
        }

        if (!TextUtils.isEmpty(bindText)) {
            mEditText.setText(bindText);
        }

        if (!TextUtils.isEmpty(hintText)) {
            mEditText.setHint(hintText);
        }

        if (textSize != 0) {
            mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        if (textColor != -1) {
            mEditText.setTextColor(textColor);
        }

        if (textHintColor != -1) {
            mEditText.setHintTextColor(textHintColor);
        }

        if (maxLength != 0) {
            setMaxLength(mEditText, maxLength);
        }

        initView();
    }

    /**
     * 设置输入字符的个数最大不超过maxLength
     */
    public static void setMaxLength(EditText editText, int maxLength) {
        editText.setFilters(getLengthFilters(maxLength));
    }

    public static InputFilter[] getLengthFilters(int maxLength) {
        return new InputFilter[]{
                new InputFilter.LengthFilter(maxLength)
        };
    }


    public void setText(CharSequence text) {
        mEditText.setText(text);
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    private void initView() {
        if (mClearDrawable != null) {
            mClearImageView.setImageDrawable(mClearDrawable);
        }

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

        setClearIconVisible(!TextUtils.isEmpty(mEditText.getText()));
        setEyeIconVisible(isEyeIconShow);

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
                if (TextUtils.isEmpty(s)) {
                    if (isClearIconShow) {
                        mClearImageView.setVisibility(GONE);
                    }
                } else {
                    if (isClearIconShow) {
                        mClearImageView.setVisibility(VISIBLE);
                    }
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
                setClearIconVisible(false);
            }
        });

    }

    public XEditText getEditText() {
        return mEditText;
    }

    public void setClearIconVisible(boolean visible) {
        if (visible) {
            mClearImageView.setVisibility(VISIBLE);
        } else {
            mClearImageView.setVisibility(GONE);
        }
    }

    public void setEyeIconVisible(boolean visible) {
        if (visible) {
            mEyeImageView.setVisibility(VISIBLE);
        } else {
            mEyeImageView.setVisibility(GONE);
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
