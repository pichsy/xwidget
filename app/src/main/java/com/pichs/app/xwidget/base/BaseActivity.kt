package com.pichs.app.xwidget.base

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.binding.BindingActivity
import com.pichs.xbase.utils.StatusBarUtils
import com.pichs.xwidget.utils.XStatusBarHelper


abstract class BaseActivity<ViewBinder : ViewBinding> : BindingActivity<ViewBinder>() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun beforeOnCreate(savedInstanceState: Bundle?) {
        super.beforeOnCreate(savedInstanceState)
        StatusBarUtils.transparentStatusBar(window)
        XStatusBarHelper.setStatusBarFontBlackColor(this)
        XStatusBarHelper.handleDisplayCutoutMode(window)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        StatusBarUtils.transparentStatusBar(window)
        XStatusBarHelper.setStatusBarFontBlackColor(this)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StatusBarUtils.transparentStatusBar(window)
        XStatusBarHelper.setStatusBarFontBlackColor(this)
    }
}