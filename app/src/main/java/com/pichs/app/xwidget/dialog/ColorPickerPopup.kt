package com.pichs.app.xwidget.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.databinding.PopupColorPickerBinding
import com.pichs.xbase.clickhelper.fastClick
import razerdp.basepopup.BasePopupWindow

class ColorPickerPopup(context: Context?, private val onColorSelected: (Int) -> Unit) : BasePopupWindow(context) {

    private lateinit var binding: PopupColorPickerBinding

    private var mCurrentSelectColor = Color.RED

    init {
        setContentView(R.layout.popup_color_picker)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        binding = PopupColorPickerBinding.bind(contentView)
        binding.pickerColor.setOnColorChangeListener { color ->
            mCurrentSelectColor = color
        }

        binding.btnConfirm.fastClick {
            onColorSelected.invoke(mCurrentSelectColor)
            dismiss()
        }
    }


}