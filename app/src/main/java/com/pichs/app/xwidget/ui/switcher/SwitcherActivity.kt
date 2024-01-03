package com.pichs.app.xwidget.ui.switcher

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class SwitcherActivity: BaseActivity<ActivityCommonBinding>(){
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, SwitcherFragment())
            .commitAllowingStateLoss()

    }
}