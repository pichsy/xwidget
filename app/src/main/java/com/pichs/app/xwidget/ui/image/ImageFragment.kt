package com.pichs.app.xwidget.ui.image

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentImageBinding
import com.pichs.app.xwidget.ktext.click

class ImageFragment : BaseFragment<FragmentImageBinding>() {

    private var isChanged = false
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }


        binding.ivCircle.click {
            isChanged = !isChanged

            if (isChanged) {
                Toast.makeText(context, "XCircleImageVie, 编角 30", Toast.LENGTH_SHORT).show()
                binding.ivCircle.setRadius(
                    30, 30, 0, 0
                )
            } else {
                Toast.makeText(context, "XCircleImageVie, 编角 0", Toast.LENGTH_SHORT).show()
                binding.ivCircle.setRadius(0)
            }

        }

        binding.ivRocket.click {
            Toast.makeText(context, "XRoundImageView, 编角", Toast.LENGTH_SHORT).show()
            binding.ivRocket.setNormalBackgroundColor(Color.TRANSPARENT)
            binding.ivRocket.setRadius(
                30, 30, 0, 0
            )
        }

        showCode()
    }

    private fun showCode() {
        binding.tvResult.text = """xml示例=>
            
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


    <com.pichs.xwidget.cardview.XCardImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_borderColor="#f00"
        app:xp_borderWidth="3dp"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="0dp" />
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
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radius="10dp" />


    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radiusBottomLeft="25dp"
        app:xp_radiusBottomRight="25dp"
        app:xp_radiusTopLeft="25dp"
        app:xp_radiusTopRight="25dp" />

    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_radiusBottomLeft="0dp"
        app:xp_radiusBottomRight="10dp"
        app:xp_radiusTopLeft="10dp"
        app:xp_radiusTopRight="0dp" />


    <com.pichs.xwidget.roundview.XRoundImageView
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:background="#fff"
        android:src="@drawable/panda_logo"
        app:xp_borderColor="#f00"
        app:xp_borderWidth="2dp"
        app:xp_radiusBottomLeft="0dp"
        app:xp_radiusBottomRight="10dp"
        app:xp_radiusTopLeft="10dp"
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
       
        
    ....
        """.trimIndent()
    }


}