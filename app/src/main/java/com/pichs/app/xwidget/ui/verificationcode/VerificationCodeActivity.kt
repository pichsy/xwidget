package com.pichs.app.xwidget.ui.verificationcode

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class VerificationCodeActivity : BaseActivity<ActivityCommonBinding>() {
    override fun afterOnCreate() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, VerificationCodeFragment())
            .commitAllowingStateLoss()
    }
}
