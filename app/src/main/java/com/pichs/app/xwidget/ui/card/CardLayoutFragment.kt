package com.pichs.app.xwidget.ui.card

import android.view.View
import android.widget.SeekBar
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentCardLayoutBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.app.xwidget.widget.PickerColorView
import com.pichs.xbase.kotlinext.dp
import com.pichs.xwidget.utils.XColorHelper

class CardLayoutFragment : BaseFragment<FragmentCardLayoutBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        binding.pickerColor.setOnColorPickerChangeListener(object : PickerColorView.OnColorPickerChangeListener {
            override fun onColorChanged(view: PickerColorView?, color: Int) {
                binding.cardLayout.shadowColor = color
                showCode()
            }

            override fun onStartTrackingTouch(picker: PickerColorView?) {
            }

            override fun onStopTrackingTouch(picker: PickerColorView?) {
            }

        })

        binding.seekBarRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cardLayout.radius = progress.dp
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.seekBarElevation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cardLayout.shadowElevation = progress.dp
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        binding.bgPickerColor.setOnColorPickerChangeListener(object : PickerColorView.OnColorPickerChangeListener {
            override fun onColorChanged(view: PickerColorView?, color: Int) {
                binding.cardLayout.setNormalBackgroundColor(color)
                showCode()
            }

            override fun onStartTrackingTouch(picker: PickerColorView?) {
            }

            override fun onStopTrackingTouch(picker: PickerColorView?) {
            }

        })

        showCode()
    }

    private fun showCode() {

        val color = binding.pickerColor.color
        val colorStr = XColorHelper.colorToString(color)

        val radius = binding.seekBarRadius.progress
        val elevation = binding.seekBarElevation.progress

        binding.tvResult.text = """xml示例=》
<com.pichs.xwidget.cardview.XCardFrameLayout
    android:id="@+id/card_layout"
    android:layout_width="180dp"
    android:layout_height="180dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="30dp"
    android:layout_marginBottom="30dp"
    android:background="#fff"
    app:xp_radius="${radius}dp"
    app:xp_shadowColor="$colorStr"
    app:xp_shadowElevation="${elevation}dp" />     
            
        """.trimIndent()
    }
}