package com.pichs.app.xwidget.ui.text

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class TextActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, TextFragment())
            .commitAllowingStateLoss()

    }
}