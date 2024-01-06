package com.pichs.app.xwidget.ui.flow

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityFlowLayoutBinding
import com.pichs.app.xwidget.ktext.click

class FlowLayoutActivity : BaseActivity<ActivityFlowLayoutBinding>() {

    override fun afterOnCreate() {
        binding.ivBack.click {
            finish()
        }

        showCode()
    }

    fun showCode() {
        binding.tvResult.text = """xml代码示例=>
            
<com.pichs.xwidget.flowlayout.XFlowLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="8dp"
    app:xp_childHorizontalSpacing="8dp"
    app:xp_childVerticalSpacing="8dp">

    ...
        """.trimIndent()


    }

}