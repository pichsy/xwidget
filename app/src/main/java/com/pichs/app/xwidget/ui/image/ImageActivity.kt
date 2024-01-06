package com.pichs.app.xwidget.ui.image

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class ImageActivity: BaseActivity<ActivityCommonBinding>(){
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ImageFragment())
            .commitAllowingStateLoss()

    }
}