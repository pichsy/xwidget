package com.pichs.app.xwidget.ui.round

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class RoundLayoutActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, RoundLayoutFragment())
            .commitAllowingStateLoss()

    }
}