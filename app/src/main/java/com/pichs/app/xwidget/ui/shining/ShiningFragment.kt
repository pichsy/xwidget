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
    android:id="@+id/shine_thumb"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_margin="16dp"
    app:xp_shine_big_shine_color="#900CE1"
    app:xp_shine_checked_color="#6D05E3"
    app:xp_shine_count="5"
    app:xp_shine_icon_image="@drawable/ic_thumb_up"
    app:xp_shine_normal_color="#C3C0C0"
    app:xp_shine_size="50dp"
    app:xp_shine_small_shine_color="#0C6E8C" />

<com.pichs.xwidget.shinebutton.ShineButton
    android:id="@+id/shine_bi"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_margin="16dp"
    app:xp_shine_big_shine_color="#2196F3"
    app:xp_shine_checked_color="#FFEB3B"
    app:xp_shine_count="7"
    app:xp_shine_enable_flashing="true"
    app:xp_shine_flashing_colors="#FFEB3B,#C1AF15,#E68E0B,#E34C26"
    app:xp_shine_icon_image="@drawable/ic_coins"
    app:xp_shine_normal_color="#9E9E9E"
    app:xp_shine_size="50dp"
    app:xp_shine_small_shine_color="#FF9800" />

<com.pichs.xwidget.shinebutton.ShineButton
    android:id="@+id/shine_collect"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_margin="16dp"
    app:xp_shine_checked_color="#F6460E"
    app:xp_shine_count="10"
    app:xp_shine_enable_flashing="true"
    app:xp_shine_flashing_colors="#F6460E,#ED1A99,#F30C0C,#5933E4"
    app:xp_shine_icon_image="@drawable/ic_star"
    app:xp_shine_normal_color="#DAD7D7"
    app:xp_shine_size="50dp"/>
    
    
    ...
        """.trimIndent()

    }
}