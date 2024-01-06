package com.pichs.app.xwidget.base

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.binding.BindingActivity
import com.pichs.xbase.utils.StatusBarUtils


abstract class BaseActivity<ViewBinder : ViewBinding> : BindingActivity<ViewBinder>() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun beforeOnCreate(savedInstanceState: Bundle?) {
        super.beforeOnCreate(savedInstanceState)
        StatusBarUtils.transparentStatusBar(window)
        StatusBarUtils.setStatusBarFontDark(window, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
            window.attributes = lp
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        StatusBarUtils.transparentStatusBar(window)
        StatusBarUtils.setStatusBarFontDark(window, true)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StatusBarUtils.transparentStatusBar(window)
        StatusBarUtils.setStatusBarFontDark(window, true)
    }
}