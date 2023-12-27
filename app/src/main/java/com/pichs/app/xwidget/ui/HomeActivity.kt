package com.pichs.app.xwidget.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityHomeBinding
import com.pichs.app.xwidget.databinding.ActivityHomeWhiteBinding
import com.pichs.app.xwidget.ui.fragment.HomeFragment
import com.pichs.app.xwidget.ui.fragment.MyFragment
import com.pichs.app.xwidget.ui.fragment.ToolsFragment
import com.pichs.xbase.utils.XLog

class HomeActivity : BaseActivity<ActivityHomeWhiteBinding>() {

    private val fragments = arrayListOf<Fragment>()

    override fun afterOnCreate() {
        initView()
        initViewPager2()
        initBottomBar()
    }

    private fun initBottomBar() {
        checkBottomItem(0)
        binding.radioBottomBar.select(0)
        binding.radioBottomBar.setOnRadioCheckedListener { group, checkedView, isChecked, position ->
            Toast.makeText(this, "选中${checkedView.id}:$isChecked,position:${position}", Toast.LENGTH_SHORT).show()
            XLog.d("initBottomBar：选中${checkedView.id}:$isChecked,position:${position}")
            when (checkedView.id) {
                binding.llRadioHome.id -> {
                    if (isChecked) {
                        checkBottomItem(0)
                    }
                }

                binding.llRadioTools.id -> {
                    if (isChecked) {
                        checkBottomItem(1)
                    }
                }

                binding.llRadioMy.id -> {
                    if (isChecked) {
                        checkBottomItem(2)
                    }
                }
            }
        }
    }

    private fun checkBottomItem(index: Int) {
        binding.viewpager2Components.currentItem = index
    }

    private fun initView() {

    }

    private fun initViewPager2() {

        fragments.add(HomeFragment())
        fragments.add(ToolsFragment())
        fragments.add(MyFragment())

        binding.viewpager2Components.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }

        binding.viewpager2Components.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.radioBottomBar.select(position)
            }
        })

    }

}