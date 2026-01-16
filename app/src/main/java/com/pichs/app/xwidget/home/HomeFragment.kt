package com.pichs.app.xwidget.home

import android.view.View
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.bean.HomeBean
import com.pichs.app.xwidget.databinding.FragmentHomeBinding
import com.pichs.app.xwidget.databinding.ItemHomeListWhiteBinding
import com.pichs.app.xwidget.utils.JumpUtils
import com.pichs.xbase.kotlinext.setItemAnimatorDisable
import com.pichs.xbase.utils.bounceHeaderFooter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mData = arrayListOf<HomeBean>()

    override fun afterOnCreateView(rootView: View?) {
        initData()
        initView()
    }

    private fun initData() {
        mData.add(HomeBean(R.drawable.ic_home_list_button, "按钮", "button"))
        mData.add(HomeBean(R.drawable.ic_home_list_font, "字体", "font"))
        mData.add(HomeBean(R.drawable.ic_home_list_image, "图片", "image"))
        mData.add(HomeBean(R.drawable.ic_home_list_card_layout, "卡片控件", "card_layout"))
        mData.add(HomeBean(R.drawable.ic_home_list_round_layout, "圆角控件", "round_layout"))
        mData.add(HomeBean(R.drawable.ic_home_list_space, "占位控件", "space"))
        mData.add(HomeBean(R.drawable.ic_home_list_radio, "单选控件", "radio"))
        mData.add(HomeBean(R.drawable.ic_home_list_checkbox, "多选控件", "checkbox"))
        mData.add(HomeBean(R.drawable.ic_home_list_rating, "星星控件", "rating"))
        mData.add(HomeBean(R.drawable.ic_home_list_flow, "流式布局", "flowlayout"))
        mData.add(HomeBean(R.drawable.ic_home_list_shining, "闪动控件", "shining"))
        mData.add(HomeBean(R.drawable.ic_home_list_progress, "进度条", "progressbar"))
        mData.add(HomeBean(R.drawable.ic_home_list_edittext, "输入框", "edittext"))
        mData.add(HomeBean(R.drawable.ic_home_list_switch, "开关控件", "switch"))
        mData.add(HomeBean(R.drawable.ic_home_list_input_layout, "输入框组合控件", "input_layout"))
        mData.add(HomeBean(R.drawable.ic_home_list_scale_layout, "缩放动画布局", "scale_layout"))
        mData.add(HomeBean(R.drawable.ic_home_list_webview, "XWebView", "xwebview"))
        mData.add(HomeBean(R.drawable.ic_home_list_nested_scroll, "顺滑滚动", "nested_scroll"))
    }

    private fun initView() {
        binding.recyclerView.grid(3).setItemAnimatorDisable().setup {
            addType<HomeBean>(R.layout.item_home_list_white)
            onBind {
                // 可以在这里处理头尾布局的逻辑
                val item = getModel<HomeBean>()
                val itemBinding = getBinding<ItemHomeListWhiteBinding>()
                itemBinding.ivIcon.setImageResource(item?.icon ?: 0)
                itemBinding.tvText.text = item?.title ?: ""
                itemBinding.root.setOnClickListener {
                    JumpUtils.jump2(item?.tag ?: "")
                }
            }
        }.models = mData

        binding.refreshLayout.bounceHeaderFooter()
    }

}

