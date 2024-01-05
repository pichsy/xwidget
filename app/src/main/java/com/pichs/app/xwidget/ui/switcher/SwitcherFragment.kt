package com.pichs.app.xwidget.ui.switcher

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentSwitcherBinding
import com.pichs.app.xwidget.ktext.click

class SwitcherFragment: BaseFragment<FragmentSwitcherBinding>() {

    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        showCode()
    }

    private fun showCode(){
        binding.tvResult.text = """xml示例=>
<com.pichs.xwidget.switcher.XSwitchButton
    android:id="@+id/swb1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:checked="true"
    app:xp_swb_backgroundColor_switchOff="#999"
    app:xp_swb_backgroundColor_switchOn="#02C83A"
    app:xp_swb_thumbHeight="20dp"
    app:xp_swb_thumbRangeRatio="2.5"
    app:xp_swb_thumbWidth="20dp" />

<com.pichs.xwidget.switcher.XSwitchButton
    android:id="@+id/swb2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp"
    android:checked="true"
    android:textColor="#fff"
    app:xp_swb_backgroundColor_switchOff="#999"
    app:xp_swb_backgroundColor_switchOn="#0756F4"
    app:xp_swb_text_switchOff="打开"
    app:xp_swb_text_switchOn="关闭"
    app:xp_swb_thumbHeight="30dp"
    app:xp_swb_thumbRangeRatio="2"
    app:xp_swb_thumbWidth="80dp" />

<com.pichs.xwidget.switcher.XSwitchButton
    android:id="@+id/swb3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp"
    android:checked="true"
    android:textColor="#fff"
    app:xp_swb_backgroundColor_switchOff="#999"
    app:xp_swb_backgroundColor_switchOn="#0756F4"
    app:xp_swb_backgroundRadius="5dp"
    app:xp_swb_text_switchOff="打开"
    app:xp_swb_text_switchOn="关闭"
    app:xp_swb_thumbHeight="30dp"
    app:xp_swb_thumbRadius="5dp"
    app:xp_swb_thumbRangeRatio="2"
    app:xp_swb_thumbWidth="80dp" />
           
    ......
        """.trimIndent()

    }
}