package com.pichs.app.xwidget.ui.nestedscroll

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentNestedScrollBinding
import com.pichs.app.xwidget.ktext.click

class NestedScrollFragment : BaseFragment<FragmentNestedScrollBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }
    }
}