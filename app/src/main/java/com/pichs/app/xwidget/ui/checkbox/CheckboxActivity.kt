package com.pichs.app.xwidget.ui.checkbox

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class CheckboxActivity: BaseActivity<ActivityCommonBinding>(){
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, CheckboxFragment())
            .commitAllowingStateLoss()

    }
}