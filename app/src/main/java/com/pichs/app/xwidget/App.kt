package com.pichs.app.xwidget

import android.app.Application
import android.widget.Toast
import com.hjq.toast.Toaster
import com.pichs.xbase.cache.BaseMMKVHelper
import com.pichs.xbase.utils.UiKit
import com.pichs.xbase.utils.XLog
import com.pichs.xwidget.utils.XTypefaceHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UiKit.init(this)
        BaseMMKVHelper.init(this)
        XTypefaceHelper.init(this, true)
        XLog.init(XLog.getConfig().setLogEnable(true))
        Toaster.init(this)
    }
}