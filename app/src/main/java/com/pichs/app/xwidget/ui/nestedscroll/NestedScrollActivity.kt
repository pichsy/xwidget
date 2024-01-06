package com.pichs.app.xwidget.ui.nestedscroll

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class NestedScrollActivity : BaseActivity<ActivityCommonBinding>() {

    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, NestedScrollFragment())
            .commitAllowingStateLoss()
    }
}