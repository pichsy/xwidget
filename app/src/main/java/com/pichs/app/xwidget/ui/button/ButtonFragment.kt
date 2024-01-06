package com.pichs.app.xwidget.ui.button

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentButtonBinding
import com.pichs.app.xwidget.ktext.click

class ButtonFragment: BaseFragment<FragmentButtonBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        showCode()
    }

    fun showCode(){
        binding.tvResult.text = """xml示例=》
 
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginStart="16dp"
        android:text="渐变"
        app:xp_backgroundGradientColors="#99f,#f99,#91e"
        app:xp_backgroundGradientOrientation="TL_BR"
        app:xp_radius="16dp" />

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginStart="16dp"
        android:text="渐变"
        app:xp_backgroundGradientColors="#99f,#f99,#91e"
        app:xp_backgroundGradientOrientation="BL_TR"
        app:xp_radiusBottomLeft="10dp"
        app:xp_radiusBottomRight="10dp" />

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginStart="16dp"
        android:text="渐变"
        app:xp_backgroundGradientColors="#99f,#f99,#91e"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_radiusBottomLeft="16dp"
        app:xp_radiusTopLeft="16dp" />

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginStart="16dp"
        android:text="渐变"
        app:xp_backgroundGradientColors="#99f,#f99,#91e"
        app:xp_backgroundGradientOrientation="horizontal"
        app:xp_radiusBottomRight="20dp"
        app:xp_radiusTopLeft="20dp" />
</LinearLayout>

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:shadowColor="#000"
        android:text="阴影"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="3dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:shadowColor="#000"
        android:text="红阴影"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowColor="#f00"
        app:xp_shadowElevation="5dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:shadowColor="#000"
        android:text="蓝阴影"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowColor="#00f"
        app:xp_shadowElevation="3dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:shadowColor="#000"
        android:text="绿阴影"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowColor="#0f0"
        app:xp_shadowElevation="3dp" />

</LinearLayout>

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:shadowColor="#000"
        android:text="阴影"
        app:xp_backgroundGradientEndColor="#6DDBF6"
        app:xp_backgroundGradientOrientation="horizontal"
        app:xp_backgroundGradientStartColor="#6868ED"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="3dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:text="红阴影"
        app:xp_backgroundGradientColors="#DDBFFA,#EF8C8C"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowColor="#f00"
        app:xp_shadowElevation="5dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#EABD4C"
        android:shadowColor="#000"
        android:text="边框"
        app:xp_borderColor="#F65B5B"
        app:xp_borderWidth="2dp"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowColor="#00f" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="16dp"
        android:background="#fff"
        android:text="边框渐变"
        app:xp_borderGradientEndColor="#EF0A0A"
        app:xp_borderGradientOrientation="BL_TR"
        app:xp_borderGradientStartColor="#1919F1"
        app:xp_borderWidth="3dp"
        app:xp_radius="8dp" />

</LinearLayout>

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="16dp"
        android:clickable="true"
        android:text="渐变"
        android:textSize="15sp"
        app:xp_backgroundGradientColors="#99f,#f99"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_cubeFrontHeight="4dp"
        app:xp_cubeSidesGradientColors="#E18282"
        app:xp_pressedCubeFrontHeight="1dp"
        app:xp_radius="20dp" />

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="16dp"
        android:clickable="true"
        android:text="渐变"
        android:textSize="15sp"
        app:xp_backgroundGradientColors="#f99,#99f"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_cubeFrontHeight="4dp"
        app:xp_cubeSidesGradientColors="#8080D3"
        app:xp_pressedCubeFrontHeight="1dp"
        app:xp_radius="20dp" />


    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="16dp"
        android:clickable="true"
        android:text="渐变"
        android:textSize="15sp"
        app:xp_backgroundGradientColors="#99f,#f99"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_cubeBackHeight="4dp"
        app:xp_cubeSidesGradientColors="#8080D3"
        app:xp_pressedCubeBackHeight="1dp"
        app:xp_radius="20dp" />

    <com.pichs.xwidget.roundview.XRoundButton
        android:layout_width="70dp"
        android:layout_height="40dp"
        android:layout_marginStart="16dp"
        android:clickable="true"
        android:text="渐变"
        android:textSize="15sp"
        app:xp_backgroundGradientColors="#f99,#99f"
        app:xp_backgroundGradientOrientation="vertical"
        app:xp_cubeBackHeight="4dp"
        app:xp_cubeSidesGradientColors="#E18282"
        app:xp_pressedCubeBackHeight="1dp"
        app:xp_radius="20dp" />

</LinearLayout>
            
        """.trimIndent()
    }
}