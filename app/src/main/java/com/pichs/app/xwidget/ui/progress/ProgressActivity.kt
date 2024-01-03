package com.pichs.app.xwidget.ui.progress

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding
import com.pichs.app.xwidget.ui.space.SpaceFragment

class ProgressActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ProgressFragment())
            .commitAllowingStateLoss()

    }
}