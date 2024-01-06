package com.pichs.app.xwidget.ui.rating

import android.annotation.SuppressLint
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentRatingBinding
import com.pichs.xbase.kotlinext.dp
import com.pichs.xbase.kotlinext.px2dp

class RatingFragment : BaseFragment<FragmentRatingBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

        binding.seekBarRadius.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ratingBar.starCornerRadius = progress
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.seekBarStarCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ratingBar.starCount = progress
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.seekBarStarSeparation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ratingBar.starsSeparation = progress.dp
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        binding.seekBarStarSize.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ratingBar.starSize = progress.dp.toFloat()
                binding.ratingBar.maxStarSize = progress.dp.toFloat()
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.seekBarStarStep.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ratingBar.starStep = progress.toFloat() / 2
                showCode()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        binding.ratingBar.setOnRatingBarChangeListener { XRatingBar, rating, fromUser ->
            showCode()
        }

        showCode()
    }

    @SuppressLint("SetTextI18n")
    fun showCode() {
        val startCount = binding.ratingBar.starCount
        val starSize = binding.ratingBar.starSize.px2dp.toInt()
        val starSeparation = binding.ratingBar.starsSeparation.toFloat().px2dp.toInt()
        val starCornerRadius = binding.ratingBar.starCornerRadius.toFloat().px2dp.toInt()
        val maxStarSize = binding.ratingBar.maxStarSize.px2dp.toInt()
        val progress = binding.ratingBar.progress
        val startStep = binding.ratingBar.starStep
        binding.tvResult.text = """xml示例=>
 <com.pichs.xwidget.ratingbar.XRatingBar
    android:id="@+id/ratingBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:padding="16dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:xp_borderColor="#EAA599"
    app:xp_maxStarSize="${maxStarSize}dp"
    app:xp_starCornerRadius="${starCornerRadius}dp"
    app:xp_starCount="$startCount"
    app:xp_starNormalColor="#DFDADA"
    app:xp_starProgress="$progress"
    app:xp_starSelectedColor="#EC0690"
    app:xp_starSize="${starSize}dp"
    app:xp_starStep="$startStep"
    app:xp_starsSeparation="${starSeparation}dp" />
        """.trimIndent()
    }
}