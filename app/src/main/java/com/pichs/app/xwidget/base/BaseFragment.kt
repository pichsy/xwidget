package com.pichs.app.xwidget.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.binding.BindingFragment

abstract class BaseFragment<ViewBinder : ViewBinding> : BindingFragment<ViewBinder>() {

    override fun beforeOnCreateView(savedInstanceState: Bundle?) {
        super.beforeOnCreateView(savedInstanceState)
    }

}