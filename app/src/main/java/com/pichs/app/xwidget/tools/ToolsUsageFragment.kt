package com.pichs.app.xwidget.tools

import android.graphics.Color
import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentToolsUsageBinding
import com.pichs.app.xwidget.dialog.ColorPickerPopup
import com.pichs.app.xwidget.ktext.click

class ToolsUsageFragment : BaseFragment<FragmentToolsUsageBinding>() {
    override fun afterOnCreateView(rootView: View?) {

        var color = Color.RED
        binding.btnColorChoose.click {

            ColorPickerPopup(activity) {
                color = it
                binding.btnColorChoose.setBackgroundColor(it)
            }.showPopupWindow()
        }

    }
}