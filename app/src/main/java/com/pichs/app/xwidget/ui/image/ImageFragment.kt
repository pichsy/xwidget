package com.pichs.app.xwidget.ui.image

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentImageBinding
import com.pichs.app.xwidget.ktext.click

class ImageFragment : BaseFragment<FragmentImageBinding>() {

    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        showCode()
    }

    private fun showCode() {
        binding.tvResult.text = """xml示例=>
            
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="XCardImageView："
    android:textStyle="bold" />

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.pichs.xwidget.cardview.XCardImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radius="10dp"
        app:xp_shadowElevation="5dp" />

    <com.pichs.xwidget.cardview.XCardImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="5dp" />


</LinearLayout>

<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:text="XRoundImageView："
    android:textStyle="bold" />

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radius="20dp" />

    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radiusBottomLeft="20dp"
        app:xp_radiusBottomRight="20dp"
        app:xp_radiusTopLeft="0dp"
        app:xp_radiusTopRight="0dp" />

    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radiusBottomLeft="0dp"
        app:xp_radiusBottomRight="20dp"
        app:xp_radiusTopLeft="20dp"
        app:xp_radiusTopRight="0dp" />
</LinearLayout>


<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:text="XCircleImageView："
    android:textStyle="bold" />

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.pichs.xwidget.roundview.XCircleImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:src="@drawable/panda_logo" />

</LinearLayout>

<com.pichs.xwidget.view.XTextView
    android:id="@+id/tv_result"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    android:layout_marginTop="60dp"
    android:background="#464547"
    android:text="代码"
    android:textColor="#FFEB3B"
    android:textSize="14sp" />
</LinearLayout>
       
        
    ....
        """.trimIndent()
    }


}