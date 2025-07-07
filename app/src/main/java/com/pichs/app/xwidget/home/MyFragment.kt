package com.pichs.app.xwidget.home

import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pichs.app.xwidget.TestActivity
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentMyBinding
import com.pichs.app.xwidget.settings.SettingsActivity
import com.pichs.xbase.clickhelper.ComboClickHelper
import com.pichs.xbase.clickhelper.MultiClickHelper
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


        MultiClickHelper.clicks(binding.flTest).setTimeMills(1000).setLockedTime(1000).call {
            if (it == 1) {
                binding.flTest.setRadius(40)
                Toast.makeText(mAppCompatActivity, "点击次数：$it, setRadius(20)", Toast.LENGTH_SHORT).show()
            } else if (it == 2) {
                binding.flTest.setRadius(50, 0, 0, 50)
                Toast.makeText(mAppCompatActivity, "点击次数：$it, setRadius(40)", Toast.LENGTH_SHORT).show()
            } else if (it == 3) {
                binding.flTest.setRadius(60)
                Toast.makeText(mAppCompatActivity, "点击次数：$it, setRadius(0, 50, 0, 50)", Toast.LENGTH_SHORT).show()
            } else if (it == 4) {
                binding.flTest.setRadius(0, 0, 80, 0)

                Toast.makeText(mAppCompatActivity, "点击次数：$it, setRadius(0, 0, 80, 0)", Toast.LENGTH_SHORT).show()
            } else if (it == 5) {
                binding.flTest.setRadius(30, 50, 60, 80)

                Toast.makeText(mAppCompatActivity, "点击次数：$it, setRadius(30, 50, 60, 80)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}