package com.pichs.app.xwidget.ui.verificationcode

import android.view.View
import com.hjq.toast.Toaster
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentVerificationCodeBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.utils.ThreadUtils
import com.pichs.xwidget.edittext.XVerificationCodeEditText

class VerificationCodeFragment : BaseFragment<FragmentVerificationCodeBinding>() {

    override fun afterOnCreateView(rootView: View?) {
        binding.ivBack.click { activity?.finish() }

        // 下划线样式：输满后模拟加载，正确密码=123456
        binding.vcUnderline.setTextChangedListener(object : XVerificationCodeEditText.TextChangedListener {
            override fun textChanged(changeText: CharSequence?) {
                binding.tvStatusUnderline.text = "已输入 ${changeText?.length ?: 0} 位"
            }

            override fun textCompleted(text: CharSequence?) {
                binding.tvStatusUnderline.text = "验证中..."
                binding.vcUnderline.startLoading()
                ThreadUtils.postDelay(1500) {
                    val success = text.toString() == "123456"
                    binding.vcUnderline.stopLoading(success)
                    binding.tvStatusUnderline.text = if (success) "✓ 验证成功" else "✗ 验证失败，请重试（正确：123456）"
                }
            }

            override fun errorCompleted() {
                binding.vcUnderline.clearText()
                binding.tvStatusUnderline.text = "请重新输入"
            }
        })

        // 实心样式：输满即提示
        binding.vcSolid.setTextChangedListener(object : XVerificationCodeEditText.TextChangedListener {
            override fun textChanged(changeText: CharSequence?) {
                binding.tvStatusSolid.text = "已输入 ${changeText?.length ?: 0} 位"
            }

            override fun textCompleted(text: CharSequence?) {
                binding.tvStatusSolid.text = "✓ 输入完成：$text"
                Toaster.show("输入完成：$text")
            }

            override fun errorCompleted() {}
        })

        // 空心样式：输满即提示
        binding.vcHollow.setTextChangedListener(object : XVerificationCodeEditText.TextChangedListener {
            override fun textChanged(changeText: CharSequence?) {
                binding.tvStatusHollow.text = "已输入 ${changeText?.length ?: 0} 位"
            }

            override fun textCompleted(text: CharSequence?) {
                binding.tvStatusHollow.text = "✓ 输入完成"
                Toaster.show("密码输入完成")
            }

            override fun errorCompleted() {}
        })
    }
}
