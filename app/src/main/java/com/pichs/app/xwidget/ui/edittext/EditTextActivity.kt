package com.pichs.app.xwidget.ui.edittext

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class EditTextActivity: BaseActivity<ActivityCommonBinding>(){
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, EditTextFragment())
            .commitAllowingStateLoss()

    }
}