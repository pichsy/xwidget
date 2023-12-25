package com.pichs.app.xwidget.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.binding.BindingActivity
import com.pichs.xbase.utils.StatusBarUtils

abstract class BaseActivity<ViewBinder:ViewBinding>:BindingActivity<ViewBinder>() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun beforeOnCreate(savedInstanceState: Bundle?) {
        super.beforeOnCreate(savedInstanceState)
        StatusBarUtils.immersiveStatusBar(this)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        StatusBarUtils.immersiveStatusBar(this)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StatusBarUtils.immersiveStatusBar(this)
    }
}