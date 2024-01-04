package com.pichs.app.xwidget.ui.edittext

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentEditTextBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.utils.ThreadUtils
import com.pichs.xbase.utils.XLog
import com.pichs.xwidget.edittext.XVerificationCodeEditText

class EditTextFragment : BaseFragment<FragmentEditTextBinding>() {

    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        binding.verificationCodeEditText.setTextChangedListener(object : XVerificationCodeEditText.TextChangedListener {
            override fun textChanged(changeText: CharSequence?) {
            }

            override fun textCompleted(text: CharSequence?) {
                XLog.d("文本输入完毕：textCompleted: $text")
                binding.verificationCodeEditText.startLoading()
                ThreadUtils.postDelay(2000) {
                    binding.verificationCodeEditText.stopLoading(text.toString() == "123456")
                }
            }

            override fun errorCompleted() {
            }
        })


        showCode()
    }

    private fun showCode() {
        binding.tvResult.text = """xml示例=>
<com.pichs.xwidget.view.XEditText
    android:id="@+id/et_input"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    android:background="#8BC34A"
    android:cursorVisible="true"
    android:padding="16dp"
    android:text="我是输入框"
    android:textCursorDrawable="@drawable/cursor_drawable"
    app:xp_disableCopyAndPaste="true" />

<com.pichs.xwidget.edittext.XVerificationCodeEditText
    android:id="@+id/verificationCodeEditText"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginStart="26dp"
    android:layout_marginTop="26dp"
    android:layout_marginEnd="26dp"
    android:background="#FFFFFF"
    android:cursorVisible="true"
    android:inputType="numberPassword"
    android:textColor="#255BFA"
    app:xp_borderColor="#255BFA"
    app:xp_borderErrorColor="#EF067E"
    app:xp_borderLoadingColor="#FFC107"
    app:xp_borderNoTextColor="#333"
    app:xp_borderWidth="4dp"
    app:xp_boxType="underline"
    app:xp_cursorColor="#D65151"
    app:xp_cursorDuration="1000"
    app:xp_cursorWidth="2dp"
    app:xp_horizontalSpacing="10dp"
    app:xp_maxNumber="6"
    app:xp_radius="8dp"
    app:xp_showCursor="true" />


<com.pichs.xwidget.edittext.XVerificationCodeEditText
    android:id="@+id/verificationCodeEditText2"
    android:layout_width="300dp"
    android:layout_height="40dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginStart="26dp"
    android:layout_marginTop="36dp"
    android:layout_marginEnd="26dp"
    android:background="#FFFFFF"
    android:cursorVisible="true"
    android:inputType="numberPassword"
    android:textColor="#fff"
    app:xp_blockColor="#28CBE8"
    app:xp_boxType="solid"
    app:xp_cursorColor="#99f"
    app:xp_cursorDuration="500"
    app:xp_cursorWidth="2dp"
    app:xp_horizontalSpacing="8dp"
    app:xp_maxNumber="6"
    app:xp_radius="8dp"
    app:xp_showCursor="true" />


<com.pichs.xwidget.edittext.XVerificationCodeEditText
    android:id="@+id/verificationCodeEditText3"
    android:layout_width="300dp"
    android:layout_height="40dp"
    android:layout_gravity="center_horizontal"
    android:layout_margin="36dp"
    android:layout_marginStart="26dp"
    android:layout_marginTop="36dp"
    android:layout_marginEnd="26dp"
    android:background="#FFFFFF"
    android:cursorVisible="true"
    android:inputType="numberPassword"
    android:textColor="#255BFA"
    app:xp_borderColor="#255BFA"
    app:xp_borderNoTextColor="#333"
    app:xp_borderWidth="2dp"
    app:xp_boxType="hollow"
    app:xp_cursorColor="#255BFA"
    app:xp_cursorDuration="500"
    app:xp_cursorWidth="2dp"
    app:xp_horizontalSpacing="10dp"
    app:xp_maxNumber="6"
    app:xp_radius="0dp"
    app:xp_showCursor="true" />
            
        """.trimIndent()
    }

}