package com.pichs.app.xwidget.home

import android.content.Intent
import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentToolsBinding
import com.pichs.app.xwidget.tools.ToolsUsageActivity
import com.pichs.xbase.clickhelper.fastClick

class ToolsFragment : BaseFragment<FragmentToolsBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.tvStyleGenerator.fastClick {
            startActivity(Intent(activity, ToolsUsageActivity::class.java))
        }
    }
}