package com.pichs.app.xwidget.ui.fragment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.bean.HomeBean
import com.pichs.app.xwidget.databinding.FragmentHomeBinding
import com.pichs.app.xwidget.databinding.ItemHomeListBinding
import com.pichs.xbase.kotlinext.grid
import com.pichs.xbase.kotlinext.setItemAnimatorDisable
import com.pichs.xbase.viewholder.ViewBindingHolder

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mData = arrayListOf<HomeBean>()

    override fun afterOnCreateView(rootView: View?) {
        initData()
        initView()
    }

    private fun initData() {

        for (i in 0..20) {
            mData.add(
                HomeBean(
                    icon = R.mipmap.ic_launcher,
                    title = "title $i"
                )
            )
        }

    }

    private fun initView() {
        binding.recyclerView.grid(3).setItemAnimatorDisable().adapter = object : BaseQuickAdapter<HomeBean, ViewBindingHolder<ItemHomeListBinding>>(mData) {

            override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemHomeListBinding> {
                return ViewBindingHolder(ItemHomeListBinding.inflate(layoutInflater, parent, false))
            }

            override fun onBindViewHolder(holder: ViewBindingHolder<ItemHomeListBinding>, position: Int, item: HomeBean?) {

            }
        }
    }
}