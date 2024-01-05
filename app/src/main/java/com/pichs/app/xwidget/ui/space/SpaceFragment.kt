package com.pichs.app.xwidget.ui.space

import android.view.View
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentSpaceBinding
import com.pichs.app.xwidget.ktext.click

class SpaceFragment: BaseFragment<FragmentSpaceBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click{
            activity?.finish()
        }

        showCode()
    }

    fun showCode() {
        binding.tvResult.text = """xml示例=>
     
    <com.pichs.xwidget.view.XTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#8E8EF3"
        android:text="下面是一个XStatusBarSpace控件，可以占位状态栏一样的高度。" />
    
    <com.pichs.xwidget.space.XStatusBarSpace
        android:id="@+id/status_bar_space"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
    
    
    <com.pichs.xwidget.view.XTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#8E8EF3"
        android:text="下面是一个XStatusBarView控件，可以占位状态栏一样的高度。可设置背景" />
    
    <com.pichs.xwidget.view.XStatusBarView
        android:id="@+id/status_bar_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#22EA13" />
    
    <com.pichs.xwidget.view.XTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#8E8EF3"
        android:text="下面是一个XSpace控件,可自己设置高度，和Space控件一致,目前80dp" />
    
    <com.pichs.xwidget.space.XSpace
        android:layout_width="match_parent"
        android:layout_height="80dp" />      
             
         
         ...
            """.trimIndent()
    }
}