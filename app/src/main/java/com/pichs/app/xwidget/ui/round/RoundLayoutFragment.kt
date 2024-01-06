package com.pichs.app.xwidget.ui.round

import android.graphics.Color
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentRoundLayoutBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.app.xwidget.widget.PickerColorView
import com.pichs.app.xwidget.widget.PickerColorView.OnColorPickerChangeListener
import com.pichs.xbase.kotlinext.dp
import com.pichs.xwidget.cardview.GradientOrientation
import com.pichs.xwidget.utils.XColorHelper

class RoundLayoutFragment : BaseFragment<FragmentRoundLayoutBinding>() {

    private var startColor = 0
    private var endColor = 0
    private var borderColor = 0
    private var orientation = 0
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }
        orientation = GradientOrientation.HORIZONTAL
        startColor = Color.WHITE
        endColor = Color.WHITE
        borderColor = Color.TRANSPARENT
        binding.seekBarRadius.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cardLayout.setRadius(progress.dp)
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.startPickerColor.setOnColorPickerChangeListener(object : OnColorPickerChangeListener {
            override fun onColorChanged(picker: PickerColorView?, color: Int) {
                binding.cardLayout.setBackgroundGradientStartColor(color)
                startColor = color
                showCode()
            }

            override fun onStartTrackingTouch(picker: PickerColorView?) {
            }

            override fun onStopTrackingTouch(picker: PickerColorView?) {
            }
        })

        binding.endPickerColor.setOnColorPickerChangeListener(object : OnColorPickerChangeListener {
            override fun onColorChanged(picker: PickerColorView?, color: Int) {
                binding.cardLayout.setBackgroundGradientEndColor(color)
                endColor = color
                showCode()
            }

            override fun onStartTrackingTouch(picker: PickerColorView?) {
            }

            override fun onStopTrackingTouch(picker: PickerColorView?) {
            }
        })

        binding.borderPickerColor.setOnColorPickerChangeListener(object : OnColorPickerChangeListener {
            override fun onColorChanged(picker: PickerColorView?, color: Int) {
                binding.cardLayout.setBorderColor(color)
                borderColor = color
                showCode()
            }

            override fun onStartTrackingTouch(picker: PickerColorView?) {
            }

            override fun onStopTrackingTouch(picker: PickerColorView?) {
            }
        })

        binding.seekBarBorderWidth.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cardLayout.setBorderWidth(progress.dp)
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.rgDirection.setOnRadioCheckedListener { group, checkedView, isChecked, position ->
            when (checkedView) {
                binding.radioHorizontal -> {
                    orientation = GradientOrientation.HORIZONTAL
                    binding.cardLayout.setBackgroundGradient(startColor, endColor, GradientOrientation.HORIZONTAL)
                }

                binding.radioVertical -> {
                    orientation = GradientOrientation.VERTICAL
                    binding.cardLayout.setBackgroundGradient(startColor, endColor, GradientOrientation.VERTICAL)
                }

                binding.radioBlTr -> {
                    orientation = GradientOrientation.BL_TR
                    binding.cardLayout.setBackgroundGradient(startColor, endColor, GradientOrientation.BL_TR)
                }

                binding.radioTlBr -> {
                    orientation = GradientOrientation.TL_BR
                    binding.cardLayout.setBackgroundGradient(startColor, endColor, GradientOrientation.TL_BR)
                }
            }

            showCode()

        }

        showCode()

    }

    private fun showCode() {
        val radius = binding.seekBarRadius.progress
        val borderWidth = binding.seekBarBorderWidth.progress

        binding.tvResult.text = """xml示例=>
 .....
 
 
 <com.pichs.xwidget.roundview.XRoundFrameLayout
    android:id="@+id/card_layout"
    android:layout_width="180dp"
    android:layout_height="180dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="30dp"
    android:layout_marginBottom="30dp"
    app:xp_backgroundGradientEndColor="${XColorHelper.colorToString(endColor)}"
    app:xp_backgroundGradientOrientation="${getOrientationString(orientation)}"
    app:xp_backgroundGradientStartColor="${XColorHelper.colorToString(startColor)}"
    app:xp_borderColor="${XColorHelper.colorToString(borderColor)}"
    app:xp_borderWidth="${borderWidth}dp"
    app:xp_radius="${binding.seekBarRadius.progress}dp" />           
       
            
 .....
        """.trimIndent()
    }

    private fun getOrientationString(orientation: Int): String {
        return when (orientation) {
            GradientOrientation.HORIZONTAL -> "horizontal"
            GradientOrientation.VERTICAL -> "vertical"
            GradientOrientation.BL_TR -> "BL_TR"
            GradientOrientation.TL_BR -> "TL_BR"
            else -> "horizontal"
        }
    }

}