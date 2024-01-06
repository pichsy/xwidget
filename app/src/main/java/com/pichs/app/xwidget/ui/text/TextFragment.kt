package com.pichs.app.xwidget.ui.text

import android.view.View
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentTextBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.utils.UiKit
import com.pichs.xbase.utils.XLog
import com.pichs.xwidget.utils.XIdsHelper
import com.pichs.xwidget.utils.XTypefaceHelper

class TextFragment : BaseFragment<FragmentTextBinding>() {
    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click {
            activity?.finish()
        }

        binding.tvLeihong.click {
            XLog.d("点击了雷鸿字体:fontName:${XIdsHelper.getResourceNameById(UiKit.getApplication(), R.font.leihong)}")
            XTypefaceHelper.openTypeface(UiKit.getApplication())
            XTypefaceHelper.setGlobalTypefaceFromFontRes(UiKit.getApplication(), R.font.leihong)
            XTypefaceHelper.setGlobalTypefaceStyle(UiKit.getApplication(), XTypefaceHelper.NONE)
        }

        binding.tvSmielysans.click {
            XLog.d("点击了得意黑字体:fontName:${XIdsHelper.getResourceNameById(UiKit.getApplication(), R.font.smileysans)}")
            XTypefaceHelper.openTypeface(UiKit.getApplication())
            XTypefaceHelper.setGlobalTypefaceFromFontRes(UiKit.getApplication(), R.font.smileysans)
            XTypefaceHelper.setGlobalTypefaceStyle(UiKit.getApplication(), XTypefaceHelper.NONE)
        }


        binding.tvRecovery.click {
            XTypefaceHelper.closeTypeface(UiKit.getApplication())
        }


        showCode()

    }


    private fun showCode() {
        binding.tvResult.text = """=========kotlin代码=======>

binding.tvLeihong.click {
    XTypefaceHelper.openTypeface(UiKit.getApplication())
    XTypefaceHelper.setGlobalTypefaceFromFontRes(UiKit.getApplication(), R.font.leihong)
    XTypefaceHelper.setGlobalTypefaceStyle(UiKit.getApplication(), XTypefaceHelper.NONE)
}

binding.tvSmielysans.click {
    XTypefaceHelper.openTypeface(UiKit.getApplication())
    XTypefaceHelper.setGlobalTypefaceFromFontRes(UiKit.getApplication(), R.font.smileysans)
    XTypefaceHelper.setGlobalTypefaceStyle(UiKit.getApplication(), XTypefaceHelper.NONE)
}


binding.tvRecovery.click {
    XTypefaceHelper.closeTypeface(UiKit.getApplication())
}
            
            
==========xml文件=============>
            
 <com.pichs.xwidget.view.XTextView
    android:id="@+id/tv_title"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="修改XTextView的字体文件" />

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical"
    android:orientation="horizontal">

    <com.pichs.xwidget.cardview.XCardButton
        android:id="@+id/tv_smielysans"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_margin="16dp"
        android:background="#047BFA"
        android:fontFamily="@font/smileysans"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:text="得意黑字体"
        android:textColor="#fff"
        android:textSize="18sp"
        app:xp_ignoreGlobalTypeface="true"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="3dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:id="@+id/tv_leihong"
        android:background="#047BFA"
        android:fontFamily="@font/leihong"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:text="雷鸿字体"
        android:textColor="#fff"
        android:textSize="20sp"
        app:xp_ignoreGlobalTypeface="true"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="3dp" />

    <com.pichs.xwidget.cardview.XCardButton
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_marginStart="16dp"
        android:id="@+id/tv_recovery"
        android:background="#047BFA"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:text="恢复默认"
        android:textColor="#fff"
        android:textSize="15sp"
        app:xp_isRadiusAdjustBounds="true"
        app:xp_shadowElevation="3dp" />


</LinearLayout>

<com.pichs.xwidget.view.XTextView
    android:id="@+id/tv_ignore_global_typeface"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:text="忽略字体文件变化"
    app:xp_ignoreGlobalTypeface="true"
    android:textSize="20sp"
    android:textStyle="bold" />
    
 <com.pichs.xwidget.textview.XVerticalTextView
    android:id="@+id/tv_vertical"
    android:layout_width="match_parent"
    android:layout_height="180dp"
    android:layout_marginStart="16dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="16dp"
    android:letterSpacing="0.25"
    android:lineSpacingExtra="10dp"
    android:text="竖着的TextView, 春眠不觉晓，花落又一村,天涯何处不相逢，冲个首冲也不亏，天大地大谁能挡，点个star行不行？"
    android:textSize="20sp"
    android:textStyle="bold"
    app:xp_backgroundGradientEndColor="#0f1"
    app:xp_backgroundGradientOrientation="BL_TR"
    app:xp_backgroundGradientStartColor="#99f" />
             
                           
                           
        """.trimIndent()
    }
}