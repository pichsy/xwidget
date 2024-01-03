package com.pichs.app.xwidget.tools

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class ToolsUsageActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ToolsUsageFragment())
            .commitAllowingStateLoss()
    }
}