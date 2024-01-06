package com.pichs.app.xwidget.ui.web

import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    override fun afterOnCreate() {
        binding.webView.loadUrl("https://www.baidu.com")
    }

}