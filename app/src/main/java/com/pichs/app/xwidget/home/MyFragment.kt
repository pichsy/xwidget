package com.pichs.app.xwidget.home

import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.util.Log
import android.view.View
import com.pichs.app.xwidget.TestActivity
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentMyBinding
import com.pichs.app.xwidget.settings.SettingsActivity
import com.pichs.xbase.clickhelper.ComboClickHelper
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


        ComboClickHelper.clicks(binding.ivAvatar).setIntervalTime(1000).call {
            if (it == 5) {
                startActivity(Intent(activity, TestActivity::class.java))
            }
        }
    }
}