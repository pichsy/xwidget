package com.pichs.app.xwidget.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivityHomeBinding
import com.pichs.app.xwidget.ui.fragment.HomeFragment
import com.pichs.app.xwidget.ui.fragment.MyFragment
import com.pichs.app.xwidget.ui.fragment.ToolsFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val fragments = arrayListOf<Fragment>()
    override fun afterOnCreate() {
        initView()
        initViewPager2()
        initBottomBar()
    }

    private fun initBottomBar() {
        checkBottomItem(0)
        binding.radioBottomBar.select(0)
        binding.radioBottomBar.setOnCheckedChangeListener { view, isChecked ->
            when (view.id) {
                binding.tvHome.id -> {
                    if (isChecked) {
                        checkBottomItem(0)
                    }
                }

                binding.tvTools.id -> {
                    if (isChecked) {
                        checkBottomItem(1)
                    }
                }

                binding.tvMy.id -> {
                    if (isChecked) {
                        checkBottomItem(2)
                    }
                }
            }
        }
    }

    private fun checkBottomItem(index: Int) {
        when (index) {
            0 -> {
                binding.tvHome.isChecked = true
                binding.tvTools.isChecked = false
                binding.tvMy.isChecked = false
                binding.viewpager2Components.currentItem = 0
            }

            1 -> {
                binding.tvHome.isChecked = false
                binding.tvTools.isChecked = true
                binding.tvMy.isChecked = false
                binding.viewpager2Components.currentItem = 1
            }

            2 -> {
                binding.tvHome.isChecked = false
                binding.tvTools.isChecked = false
                binding.tvMy.isChecked = true
                binding.viewpager2Components.currentItem = 2
            }
        }

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