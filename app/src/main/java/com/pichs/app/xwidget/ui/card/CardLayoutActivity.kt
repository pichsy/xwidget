package com.pichs.app.xwidget.ui.card

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class CardLayoutActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, CardLayoutFragment())
            .commitAllowingStateLoss()

    }
}