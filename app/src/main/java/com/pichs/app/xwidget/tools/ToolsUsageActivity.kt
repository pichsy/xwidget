package com.pichs.app.xwidget.tools

import android.content.res.Configuration
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityCommonBinding

class ToolsUsageActivity : BaseActivity<ActivityCommonBinding>() {
    private var fragment: ToolsUsageFragment? = null
    override fun afterOnCreate() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            XStatusBarHelper.setStatusBarFontDark(window, false)
//        }
//        binding.llRoot.setBackgroundColor(XColorHelper.parseColor("#505050"))
        fragment = ToolsUsageFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment!!)
            .commitAllowingStateLoss()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            XStatusBarHelper.setStatusBarFontDark(window, false)
//        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            XStatusBarHelper.setStatusBarFontDark(window, false)
//        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        fragment?.onBackPressed() ?: super.onBackPressed()
    }
}