package com.pichs.app.xwidget.ui.scale

import android.annotation.SuppressLint
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityScaleAnimationBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.clickhelper.fastClick

class ScaleAnimationActivity : BaseActivity<ActivityScaleAnimationBinding>() {
    @SuppressLint("SetTextI18n")
    override fun afterOnCreate() {

        binding.ivBack.click {
            finish()
        }

        binding.btnScaleFirst.fastClick {
            binding.llPressedLayout.isJustFirstAnimation = true
            updateResult()
        }

        binding.btnScaleRemoveFirst.fastClick {
            binding.llPressedLayout.isJustFirstAnimation = false
            updateResult()
        }

        binding.btnScaleIds.fastClick {
            binding.llPressedLayout.setPressedScaleChildViewIds(binding.tvBtn1.id, binding.tvBtn3.id)
            updateResult()
        }

        binding.btnScaleRemoveIds.fastClick {
            binding.llPressedLayout.setPressedScaleChildViewIds()
            updateResult()
        }

        updateResult()
    }

    @SuppressLint("SetTextI18n")
    private fun updateResult(vararg idStr: String) {
        val ids = binding.llPressedLayout.pressedScaleChildViewIds
        val idStr = StringBuilder().apply {
            for (id in ids) {
                when (id) {
                    binding.tvBtn1.id -> {
                        append("tv_btn1")
                        append(",")
                    }

                    binding.tvBtn2.id -> {
                        append("tv_btn2")
                        append(",")
                    }

                    binding.tvBtn3.id -> {
                        append("tv_btn3")
                        append(",")
                    }
                }
            }
        }.removeSuffix(",").toString()
        binding.tvResult.text = """xml代码==>
<xxx.XPressScaleLinearLayout
    android:id="@+id/ll_pressed_layout"
    android:layout_width="match_parent"
    android:layout_height="120dp"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    android:background="#BDBDF3"
    android:clickable="true"
    android:gravity="center"
    app:xp_pressedScaleChildIds="$idStr"
    app:xp_pressedScaleJustFirst="${binding.llPressedLayout.isJustFirstAnimation}"
    android:orientation="horizontal">
        """.trimIndent()
    }
}