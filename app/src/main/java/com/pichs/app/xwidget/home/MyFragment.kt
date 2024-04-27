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


        binding.tvTestShow.setOnClickListener {
            val insetDrawable = binding.tvTest.background as InsetDrawable
            val innerDrawable = insetDrawable.drawable as StateListDrawable

            // 取出里面的drawable

            try {

//                val states = innerDrawable.state
//
//                for (state in states) {
//                    Log.e("MyFragment", "state: $state")
//                    val foucusedDrawable = innerDrawable.getStateDrawable(0)
//                    val sss = innerDrawable.getStateDrawable(1)
//                    Log.e("MyFragment", "===>state:${state} StatesDrawable: $foucusedDrawable, sss: $sss")
//                }

            } catch (e: Exception) {

            }


            // 测试
            binding.tvTest.setText("${insetDrawable}")
            Log.e(
                "MyFragment", """--
                
                
                binding.tvTest.background: ${binding.tvTest.background}
                insetDrawable: ${insetDrawable}
                innerDrawable: ${innerDrawable}
                
                --
            """.trimIndent()
            )
        }


    }
}