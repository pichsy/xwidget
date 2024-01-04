package com.pichs.app.xwidget.ui.progress

import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentProgressBinding
import com.pichs.app.xwidget.ktext.click

class ProgressFragment : BaseFragment<FragmentProgressBinding>() {
    override fun afterOnCreateView(rootView: View?) {

        binding.ivBack.click {
            activity?.finish()
        }
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressBar1.progress = progress
                binding.progressBar2.progress = progress
                binding.progressBar3.progress = progress
                binding.progressBar4.setProgress(progress, false)
                binding.progressBar5.setProgress(progress, false)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        showCode()
    }

    private fun showCode() {
        binding.tvResult.text =
            """xml示例=>
<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="带动画"
    android:textColor="#333"
    android:textSize="16sp" />

<com.pichs.xwidget.progressbar.XProgressBar
    android:id="@+id/progress_bar1"
    android:layout_width="match_parent"
    android:layout_height="8dp"
    android:layout_marginStart="20dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="20dp"
    app:xp_backgroundColor="#14AF08"
    app:xp_maxValue="100"
    app:xp_progressColor="#DD4B07"
    app:xp_progressType="roundRect"
    app:xp_strokeRoundCap="true"
    app:xp_strokeWidth="2dp"
    app:xp_value="0" />


<com.pichs.xwidget.progressbar.XProgressBar
    android:id="@+id/progress_bar2"
    android:layout_width="match_parent"
    android:layout_height="8dp"
    android:layout_marginStart="20dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="20dp"
    app:xp_backgroundColor="#EAB1E9"
    app:xp_maxValue="100"
    app:xp_progressColor="#2C2CE4"
    app:xp_progressType="rect"
    app:xp_strokeRoundCap="true"
    app:xp_strokeWidth="2dp"
    app:xp_value="0" />


<com.pichs.xwidget.progressbar.XProgressBar
    android:id="@+id/progress_bar3"
    android:layout_width="80dp"
    android:layout_height="80dp"
    android:layout_marginStart="20dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="20dp"
    app:xp_backgroundColor="#A8A89E"
    app:xp_maxValue="100"
    app:xp_progressColor="#05DA4C"
    app:xp_progressType="circle"
    app:xp_strokeRoundCap="true"
    app:xp_strokeWidth="5dp"
    app:xp_value="0" />


<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="不带动画"
    android:textColor="#333"
    android:textSize="16sp" />

<com.pichs.xwidget.progressbar.XProgressBar
    android:id="@+id/progress_bar4"
    android:layout_width="match_parent"
    android:layout_height="8dp"
    android:layout_marginStart="20dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="20dp"
    app:xp_backgroundColor="#4808AF"
    app:xp_maxValue="100"
    app:xp_progressColor="#DDC007"
    app:xp_progressType="roundRect"
    app:xp_strokeRoundCap="true"
    app:xp_strokeWidth="2dp"
    app:xp_value="0" />


<com.pichs.xwidget.progressbar.XProgressBar
    android:id="@+id/progress_bar5"
    android:layout_width="match_parent"
    android:layout_height="8dp"
    android:layout_marginStart="20dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="20dp"
    app:xp_backgroundColor="#BBEAB1"
    app:xp_maxValue="100"
    app:xp_progressColor="#E42CBC"
    app:xp_progressType="rect"
    app:xp_strokeRoundCap="true"
    app:xp_strokeWidth="2dp"
    app:xp_value="0" />


<androidx.appcompat.widget.AppCompatSeekBar
    android:id="@+id/seekbar"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:layout_marginStart="16dp"
    android:layout_marginTop="26dp"
    android:layout_marginEnd="16dp"
    android:background="#C9C9ED"
    android:max="100" />

    """.trimIndent()
    }
}