package com.pichs.app.xwidget.ui.radio

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class RadioActivity: BaseActivity<ActivityCommonBinding>(){
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, RadioFragment())
            .commitAllowingStateLoss()

    }
}