package com.pichs.app.xwidget.ui.text

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentTextBinding
import com.pichs.app.xwidget.ktext.click

class TextFragment: BaseFragment<FragmentTextBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

    }
}