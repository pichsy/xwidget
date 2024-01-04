package com.pichs.app.xwidget.ui.flow

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityFlowLayoutBinding
import com.pichs.app.xwidget.ktext.click

class FlowLayoutActivity:BaseActivity<ActivityFlowLayoutBinding> (){

    override fun afterOnCreate() {
        binding.ivBack.click {
            finish()
        }
    }

}