package com.pichs.app.xwidget.tools

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.view.isVisible
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentToolsUsageBinding
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.kotlinext.dp
import com.pichs.xbase.kotlinext.gone
import com.pichs.xbase.kotlinext.show
import com.pichs.xwidget.interpolator.XInterpolators

class ToolsUsageFragment : BaseFragment<FragmentToolsUsageBinding>() {

    private var showAnimator: ObjectAnimator? = null
    private var hideAnimator: ObjectAnimator? = null

    override fun afterOnCreateView(rootView: View?) {

        binding.ivBack.click {
            if (!binding.tvSettings.isVisible) {
                binding.tvSettings.show()
                hideMenu()
            } else {
                activity?.finish()
            }
        }

        binding.llMenu.translationX = 340.dp.toFloat()
        binding.tvSettings.click {
            binding.tvSettings.gone()
            showMenu()
        }

        binding.llRoot.click {
            if (!binding.tvSettings.isVisible) {
                binding.tvSettings.show()
                hideMenu()
            }
        }

        binding.closeMenu.click {
            binding.tvSettings.show()
            hideMenu()
        }
    }

    private fun showMenu() {
        showAnimator = ObjectAnimator.ofFloat(binding.llMenu, "translationX", 340.dp.toFloat(), 0f)
        showAnimator?.duration = 100
        showAnimator?.interpolator = XInterpolators.linear
        showAnimator?.start()
    }

    private fun hideMenu() {
        hideAnimator = ObjectAnimator.ofFloat(binding.llMenu, "translationX", 0f, 340.dp.toFloat())
        hideAnimator?.duration = 100
        hideAnimator?.interpolator = XInterpolators.linear
        hideAnimator?.start()
    }

    fun onBackPressed() {
        if (!binding.tvSettings.isVisible) {
            binding.tvSettings.show()
            hideMenu()
        } else {
            activity?.finish()
        }
    }

}