package com.pichs.app.xwidget.ui.shining

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentShiningBinding

class ShiningFragment: BaseFragment<FragmentShiningBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

        showCode()
    }

    fun showCode(){
        binding.tvResult.text ="""xml代码示例=>
<com.pichs.xwidget.shinebutton.ShineButton
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:id="@+id/shine_button"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="16dp"
    app:xp_shine_big_shine_color="#0EF161"
    app:xp_shine_checked_color="#FFEB3B"
    app:xp_shine_count="8"
    app:xp_shine_distance_multiple="2"
    app:xp_shine_enable_flashing="true"
    app:xp_shine_icon_image="@drawable/ic_start"
    app:xp_shine_normal_color="#DAD7D7"
    app:xp_shine_size="80dp"
    app:xp_shine_small_shine_color="#D55F06" />
        """.trimIndent()

    }
}