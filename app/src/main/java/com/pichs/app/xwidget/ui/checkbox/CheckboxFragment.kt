package com.pichs.app.xwidget.ui.checkbox

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentCheckboxBinding
import com.pichs.app.xwidget.ktext.click

class CheckboxFragment : BaseFragment<FragmentCheckboxBinding>() {

    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        binding.ctv1.setOnClickListener {
            binding.ctv1.toggle()
        }

        binding.checkedImageView.click {
            binding.checkedImageView.toggle()
        }

        showCode()
    }

    private fun showCode() {
        binding.tvResult.text = """xml示例=>
            
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#F1F1F1"
    android:gravity="center_vertical"
    android:orientation="horizontal"
    android:padding="16dp">

<com.pichs.xwidget.view.XCheckedTextView
    android:id="@+id/ctv_1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#E4E4FD"
    android:checked="true"
    android:padding="8dp"
    android:text="XCheckedImageView:不含点击事件\nXCheckedTextView:不包含点击事件"
    android:textColor="#000"
    app:xp_checkedBackground="#6666F3" />

<com.pichs.xwidget.view.XCheckedImageView
    android:id="@+id/checked_image_view"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_marginStart="16dp"
    android:src="@drawable/ic_home_blue_unchecked"
    app:xp_checked="true"
    app:xp_checked_src="@drawable/ic_home_blue_checked"
    app:xp_radius="10dp" />

</LinearLayout>

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="#F1F1F1"
android:gravity="center_vertical"
android:orientation="horizontal"
android:padding="16dp">

<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="XCheckBox"
    android:textColor="#000" />

<com.pichs.xwidget.checkbox.XCheckBox
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_marginStart="16dp"
    android:src="@drawable/ic_my_unchecked"
    app:xp_checked="true"
    app:xp_checked_src="@drawable/ic_my_checked"
    app:xp_radius="10dp" />

</LinearLayout>

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#F1F1F1"
    android:gravity="center_vertical"
    android:orientation="horizontal"
    android:padding="16dp">

<com.pichs.xwidget.view.XTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="XSmoothCheckBox"
    android:textColor="#000" />

<com.pichs.xwidget.checkbox.XSmoothCheckBox
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_marginStart="16dp"
    app:xp_cbox_color_checked="#f00"
    app:xp_cbox_color_tick="#04DC0C"
    app:xp_cbox_color_unchecked="#fff"
    app:xp_cbox_color_unchecked_stroke="#9C9CF4"
    app:xp_cbox_stroke_width="6dp" />

</LinearLayout>

.....
            """.trimIndent()
    }

}