package com.pichs.app.xwidget.ui.shining

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding
import com.pichs.app.xwidget.ui.space.SpaceFragment

class ShiningActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ShiningFragment())
            .commitAllowingStateLoss()

    }
}