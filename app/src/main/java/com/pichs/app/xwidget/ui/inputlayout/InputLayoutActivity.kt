package com.pichs.app.xwidget.ui.inputlayout

import android.annotation.SuppressLint
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityInputLayoutBinding
@SuppressLint("SetTextI18n")
class InputLayoutActivity : BaseActivity<ActivityInputLayoutBinding>() {
    override fun afterOnCreate() {

        showCode()


    }

    private fun showCode() {
        binding.tvResult.text = """xml示例=>
<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="账号:"
    android:textColor="#333"
    android:textSize="16sp" />

<com.pichs.xwidget.input.InputLayout
    android:id="@+id/input_layout_account"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:layout_margin="16dp"
    android:hint="请输入账号"
    android:paddingStart="8dp"
    android:paddingEnd="8dp"
    android:textColor="#333"
    android:textColorHint="#999"
    android:textSize="14sp"
    app:xp_borderColor="#999"
    app:xp_borderWidth="1dp"
    app:xp_clearDrawable="@drawable/ic_close_with_circle"
    app:xp_clearIconVisible="true"
    app:xp_clearIcon_padding="3dp"
    app:xp_eyeCloseDrawable="@drawable/ic_login_hide"
    app:xp_eyeIconVisible="false"
    app:xp_eyeOpenDrawable="@drawable/ic_login_show"
    app:xp_inputType_normal="text"
    app:xp_inputType_password="textPassword"
    app:xp_radius="10dp"
    app:xp_textPassword="false" />

<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:text="密码:"
    android:textColor="#333"
    android:textSize="16sp" />

<com.pichs.xwidget.input.InputLayout
    android:id="@+id/input_layout_password"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:layout_margin="16dp"
    android:hint="请输入密码"
    android:paddingStart="8dp"
    android:paddingEnd="8dp"
    android:textColor="#333"
    android:textColorHint="#999"
    android:textSize="14sp"
    app:xp_borderColor="#999"
    app:xp_borderWidth="1dp"
    app:xp_clearDrawable="@drawable/ic_close_with_circle"
    app:xp_clearIconVisible="true"
    app:xp_clearIcon_padding="3dp"
    app:xp_textPassword="true"
    app:xp_eyeCloseDrawable="@drawable/ic_login_hide"
    app:xp_eyeIconVisible="true"
    app:xp_eyeOpenDrawable="@drawable/ic_login_show"
    app:xp_inputType_normal="number"
    app:xp_inputType_password="numberPassword"
    app:xp_radius="10dp" />        
        """.trimIndent()
    }
}