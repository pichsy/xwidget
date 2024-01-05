package com.pichs.app.xwidget.ui.round

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentRoundLayoutBinding
import com.pichs.app.xwidget.ktext.click

class RoundLayoutFragment: BaseFragment<FragmentRoundLayoutBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }
    }
}