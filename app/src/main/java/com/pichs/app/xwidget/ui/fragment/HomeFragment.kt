package com.pichs.app.xwidget.ui.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.pichs.app.xwidget.MainActivity
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.bean.HomeBean
import com.pichs.app.xwidget.databinding.FragmentHomeBinding
import com.pichs.app.xwidget.databinding.ItemHomeListWhiteBinding
import com.pichs.xbase.kotlinext.grid
import com.pichs.xbase.kotlinext.setItemAnimatorDisable
import com.pichs.xbase.utils.UiKit
import com.pichs.xbase.utils.bounceHeaderFooter
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
                    icon = R.drawable.ic_home_blue_unchecked,
                    title = "title $i"
                )
            )
        }

    }

    private fun initView() {

        val adapter = object : BaseQuickAdapter<HomeBean, ViewBindingHolder<ItemHomeListWhiteBinding>>(mData) {

            override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemHomeListWhiteBinding> {
                return ViewBindingHolder(ItemHomeListWhiteBinding.inflate(layoutInflater, parent, false))
            }

            override fun onBindViewHolder(holder: ViewBindingHolder<ItemHomeListWhiteBinding>, position: Int, item: HomeBean?) {
                holder.viewBinder.root.setOnClickListener {
                    startActivity(Intent(mActivity, MainActivity::class.java))
                }
            }
        }

        binding.recyclerView.grid(3)
            .setItemAnimatorDisable()
            .adapter = createHelper(adapter).adapter

        binding.refreshLayout.bounceHeaderFooter()
    }


    private fun createHelper(adapter: BaseQuickAdapter<*, *>): QuickAdapterHelper {
        return QuickAdapterHelper
            .Builder(adapter)
            .build()
            .addBeforeAdapter(object : BaseQuickAdapter<String, QuickViewHolder>(arrayListOf("", "", "")) {
                override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
                }

                override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
                    return QuickViewHolder(layoutInflater.inflate(R.layout.item_home_list_white_header, parent, false))
                }
            })
            .addAfterAdapter(object : BaseQuickAdapter<String, QuickViewHolder>(arrayListOf("", "", "")) {
                override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
                }

                override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
                    return QuickViewHolder(layoutInflater.inflate(R.layout.item_home_list_white_footer, parent, false))
                }
            })
    }

}

