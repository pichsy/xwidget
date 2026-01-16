package com.pichs.app.xwidget.ui.wheelview

import android.view.View
import com.hjq.toast.Toaster
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentWheelviewBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xwidget.wheel.WheelView

class WheelViewFragment : BaseFragment<FragmentWheelviewBinding>() {
    
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }
        
        setupWheelViews()
    }
    
    private fun setupWheelViews() {
        // 示例1：垂直滚轮 - 3D效果
        val cities = arrayOf("北京", "上海", "广州", "深圳", "杭州", "成都", "重庆", "南京", "武汉", "西安")
        binding.wheelView1.setAdapter(object : WheelView.Adapter() {
            override fun getItemCount(): Int = cities.size
            override fun getItem(position: Int): String = cities[position]
        })
        binding.wheelView1.addOnItemSelectedListener { _, index ->
            binding.tvResult1.text = "选中城市：${cities[index]} (位置: $index)"
        }
        
        // 示例2：垂直滚轮 - 线性效果
        val weekdays = arrayOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
        binding.wheelView2.setAdapter(object : WheelView.Adapter() {
            override fun getItemCount(): Int = weekdays.size
            override fun getItem(position: Int): String = weekdays[position]
        })
        binding.wheelView2.addOnItemSelectedListener { _, index ->
            binding.tvResult2.text = "选中星期：${weekdays[index]} (位置: $index)"
        }
        
        // 示例3：水平滚轮 - 3D效果
        val hours = Array(24) { String.format("%02d:00", it) }
        binding.wheelView3.setAdapter(object : WheelView.Adapter() {
            override fun getItemCount(): Int = hours.size
            override fun getItem(position: Int): String = hours[position]
        })
        binding.wheelView3.addOnItemSelectedListener { _, index ->
            binding.tvResult3.text = "选中时间：${hours[index]}"
        }
        
        // 示例4：数字选择器
        val numbers = Array(100) { (it + 1).toString() }
        binding.wheelView4.setAdapter(object : WheelView.Adapter() {
            override fun getItemCount(): Int = numbers.size
            override fun getItem(position: Int): String = numbers[position]
        })
        binding.wheelView4.addOnItemSelectedListener { _, index ->
            binding.tvResult4.text = "选中数字：${numbers[index]}"
        }
        
        // 设置按钮点击事件
        binding.btnGetValue.click {
            val city = cities[binding.wheelView1.getCurrentItem()]
            val weekday = weekdays[binding.wheelView2.getCurrentItem()]
            val hour = hours[binding.wheelView3.getCurrentItem()]
            val number = numbers[binding.wheelView4.getCurrentItem()]
            
            val result = """
                城市：$city
                星期：$weekday
                时间：$hour
                数字：$number
            """.trimIndent()
            
            Toaster.show(result)
        }
        
        binding.btnSetValue.click {
            // 设置默认值
            binding.wheelView1.setCurrentItem(3) // 深圳
            binding.wheelView2.setCurrentItem(5) // 周六
            binding.wheelView3.setCurrentItem(14) // 14:00
            binding.wheelView4.setCurrentItem(49) // 50
            Toaster.show("已设置默认值")
        }
    }
}
