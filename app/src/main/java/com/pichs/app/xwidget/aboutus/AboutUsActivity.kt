package com.pichs.app.xwidget.aboutus

import android.annotation.SuppressLint
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityAboutUsBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.utils.SysOsUtils

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding>() {

    @SuppressLint("SetTextI18n")
    override fun afterOnCreate() {
        binding.ivBack.click {
            finish()
        }

        binding.tvVersion.text = "版本：v${SysOsUtils.getVersionName(this)}"
    }
}