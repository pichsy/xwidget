package com.pichs.app.xwidget.home

import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.util.Log
import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentMyBinding
import com.pichs.app.xwidget.settings.SettingsActivity
import com.pichs.xbase.clickhelper.fastClick

class MyFragment : BaseFragment<FragmentMyBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        initListener()
    }

    private fun initListener() {
        binding.clSettings.fastClick {
            // 跳转到设置界面
            startActivity(Intent(activity, SettingsActivity::class.java))
        }



    }
}