package com.pichs.app.xwidget.tools

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.bean.ColorBean
import com.pichs.app.xwidget.databinding.FragmentToolsUsageBinding
import com.pichs.app.xwidget.databinding.ItemColorRecyclerViewBinding
import com.pichs.app.xwidget.dialog.ColorPickerPopup
import com.pichs.app.xwidget.ktext.click
import com.pichs.xbase.kotlinext.dp
import com.pichs.xbase.kotlinext.gone
import com.pichs.xbase.kotlinext.invisible
import com.pichs.xbase.kotlinext.setItemAnimatorDisable
import com.pichs.xbase.kotlinext.show
import com.pichs.xwidget.cardview.GradientOrientation
import com.pichs.xwidget.cardview.XCardButton
import com.pichs.xwidget.cardview.XILayout
import com.pichs.xwidget.interpolator.XInterpolators
import com.pichs.xwidget.utils.XBackgroundHelper
import com.pichs.xwidget.utils.XColorHelper

class ToolsUsageFragment : BaseFragment<FragmentToolsUsageBinding>() {

    private var showAnimator: ObjectAnimator? = null
    private var hideAnimator: ObjectAnimator? = null
    private var currentContentViewId = 0
    private var mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_NONE
    private var gradientColors = arrayListOf<ColorBean>()
    private var mBorderColor = XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT
    private var mBorderWidth = 0
    override fun afterOnCreateView(rootView: View?) {
        currentContentViewId = binding.contentCard.id
        binding.flContentView.gone()
        binding.tvResult.show()
        cardRadius = 16
        roundRadiusRightTop = 16
        roundRadiusLeftTop = 16
        roundRadiusLeftBottom = 16
        roundRadiusRightBottom = 16
        cardShadowElevation = 8

        binding.etShadowElevation.setText("8")
        binding.etRadiusCard.setText("16")
        binding.etRadiusLeftTop.setText("16")
        binding.etRadiusLeftBottom.setText("16")
        binding.etRadiusRightTop.setText("16")
        binding.etRadiusRightBottom.setText("16")
        binding.etBorderWidth.setText("0")

        binding.ivBack.click {
            if (!binding.tvSettings.isVisible) {
                binding.tvSettings.show()
                hideMenu()
            } else {
                activity?.finish()
            }
        }

        binding.llMenu.post {
            binding.llMenu.translationY = binding.llMenu.measuredHeight.toFloat()
        }
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

        binding.tvHideCardSides.click {
            binding.tvHideCardSides.toggle()
            if (binding.tvHideCardSides.isChecked) {
                binding.tvHideCardSides.text = "隐藏边(开)"
                binding.radiusHideRadioGroup.show()
                mHideRadiusSide = getSelectedHideRadiusSide()
                binding.contentCard.hideRadiusSide = mHideRadiusSide
            } else {
                binding.tvHideCardSides.text = "隐藏边(关)开启后阴影会失效"
                binding.radiusHideRadioGroup.gone()
                mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_NONE
                binding.contentCard.hideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_NONE
            }
            refreshLayout()
        }

        initViewRadioGroup()

        initEditText()

        initColorChooser()

        initGradientColorRecyclerView()

        showCode()
    }

    private fun initGradientColorRecyclerView() {
        gradientColors.add(ColorBean(XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, "add"))
        binding.colorRecyclerView.linear(RecyclerView.HORIZONTAL).setItemAnimatorDisable().setup {
            addType<ColorBean>(R.layout.item_color_recycler_view)
            onBind {

                val item = getModel<ColorBean>()
                val itemBinding = getBinding<ItemColorRecyclerViewBinding>()
                if (item?.type == "color") {
                    itemBinding.ivDelete.show()
                    itemBinding.viewColor.setImageDrawable(ColorDrawable(item?.color ?: 0))
                } else {
                    itemBinding.ivDelete.gone()
                    itemBinding.viewColor.setImageResource(R.drawable.ic_add_color)
                }

                itemBinding.viewColor.click {
                    if (item?.type == "color") {
                        ColorPickerPopup(context) {
                            gradientColors[position].color = it
                            notifyDataSetChanged()
                            refreshLayout()
                        }.showPopupWindow()
                    } else {
                        // 选择一种颜色，添加进去
                        ColorPickerPopup(context) {
                            gradientColors.add(gradientColors.size - 1, ColorBean(it, "color"))
                            notifyDataSetChanged()
                            refreshLayout()
                        }.showPopupWindow()
                    }
                }

                itemBinding.ivDelete.click {
                    gradientColors.remove(item)
                    notifyDataSetChanged()
                    refreshLayout()
                }
            }
        }
    }

    private fun initColorChooser() {
        binding.contentCard.click {
            ColorPickerPopup(context) {
                binding.contentCard.setBackgroundColor(it)
            }.showPopupWindow()
        }

        binding.contentRound.click {
            ColorPickerPopup(context) {
                binding.contentRound.setBackgroundColor(it)
            }.showPopupWindow()
        }

        binding.contentView.click {
            ColorPickerPopup(context) {
                binding.contentView.setBackgroundColor(it)
            }.showPopupWindow()
        }


        binding.etShadowElevation.addTextChangedListener {
            cardShadowElevation = it.toString().toIntOrNull()?.dp ?: 0
            refreshLayout()
        }

        binding.shadowPickerColor.setOnColorPickerChangeListener { picker, color ->
            cardShadowColor = color
            refreshLayout()
        }

        binding.borderPickerColor.setOnColorPickerChangeListener { picker, color ->
            mBorderColor = color
            refreshLayout()
        }


    }

    private fun getSelectedHideRadiusSide(): Int {
        when (binding.radiusHideRadioGroup.selectedView) {
            binding.radioTop -> {
                return XCardButton.HIDE_RADIUS_SIDE_TOP
            }

            binding.radioLeft -> {
                return XCardButton.HIDE_RADIUS_SIDE_LEFT
            }

            binding.radioRight -> {
                return XCardButton.HIDE_RADIUS_SIDE_RIGHT
            }

            binding.radioBottom -> {
                return XCardButton.HIDE_RADIUS_SIDE_BOTTOM
            }
        }
        return XCardButton.HIDE_RADIUS_SIDE_NONE
    }

    /**
     * 圆角
     */
//    private var gradientStartColor: Int = XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT
//    private var gradientEndColor: Int = XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT
    private var gradientOrientation: Int = GradientOrientation.HORIZONTAL

    private fun initViewRadioGroup() {
        showContentView(binding.radioTypeCard.id)

        binding.layoutTypeRadioGroup.setOnRadioCheckedListener { group, checkedView, isChecked, position ->
            showContentView(checkedView.id)
        }


        binding.rgDirection.setOnRadioCheckedListener { group, checkedView, isChecked, position ->
            when (checkedView.id) {
                binding.radioHorizontal.id -> {
                    gradientOrientation = GradientOrientation.HORIZONTAL
                }

                binding.radioVertical.id -> {
                    gradientOrientation = GradientOrientation.VERTICAL
                }

                binding.radioBlTr.id -> {
                    gradientOrientation = GradientOrientation.BL_TR
                }

                binding.radioTlBr.id -> {
                    gradientOrientation = GradientOrientation.TL_BR
                }
            }
            refreshLayout()
        }

        binding.radiusHideRadioGroup.setOnRadioCheckedListener { group, checkedView, isChecked, position ->
            when (checkedView) {
                binding.radioTop -> {
                    mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_TOP
                }

                binding.radioLeft -> {
                    mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_LEFT
                }

                binding.radioRight -> {
                    mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_RIGHT
                }

                binding.radioBottom -> {
                    mHideRadiusSide = XCardButton.HIDE_RADIUS_SIDE_BOTTOM
                }
            }
            refreshLayout()
        }
    }


    private fun showContentView(id: Int) {
        when (id) {
            binding.radioTypeCard.id -> {
                binding.contentCard.show()
                binding.contentRound.gone()
                binding.contentView.gone()
                currentContentViewId = binding.contentCard.id
                binding.llRadiusCard.show()
                binding.llRadiusRound.gone()
                binding.llShadowCard.show()
                binding.llBorder.show()
            }

            binding.radioTypeRound.id -> {
                binding.contentCard.gone()
                binding.contentRound.show()
                binding.contentView.gone()
                currentContentViewId = binding.contentRound.id
                binding.llRadiusCard.gone()
                binding.llRadiusRound.show()
                binding.llShadowCard.invisible()
                binding.llBorder.show()
            }

            binding.radioTypeNormal.id -> {
                binding.contentCard.gone()
                binding.contentRound.gone()
                binding.contentView.show()
                currentContentViewId = binding.contentView.id
                binding.llRadiusCard.gone()
                binding.llRadiusRound.invisible()
                binding.llShadowCard.invisible()
                binding.llBorder.invisible()
            }
        }
    }

    private var cardRadius = 0
    private var cardShadowElevation = 0
    private var cardShadowColor = 0

    private var roundRadiusLeftTop = 0
    private var roundRadiusRightBottom = 0
    private var roundRadiusLeftBottom = 0
    private var roundRadiusRightTop = 0

    private fun initEditText() {
        binding.etRadiusLeftTop.addTextChangedListener {
            roundRadiusLeftTop = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etRadiusRightTop.addTextChangedListener {
            roundRadiusRightTop = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etRadiusLeftBottom.addTextChangedListener {
            roundRadiusLeftBottom = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etRadiusRightBottom.addTextChangedListener {
            roundRadiusRightBottom = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etRadiusCard.addTextChangedListener {
            cardRadius = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etShadowElevation.addTextChangedListener {
            cardShadowElevation = it.toString().toIntOrNull() ?: 0
            refreshLayout()
        }

        binding.etBorderWidth.addTextChangedListener {
            val borderWidth = it.toString().toIntOrNull() ?: 0
            mBorderWidth = borderWidth
            refreshLayout()
        }
    }

    /**
     * 刷新布局
     */
    private fun refreshLayout() {
        binding.contentCard.apply {
            setRadius(cardRadius.dp, mHideRadiusSide)
            if (gradientColors.isNotEmpty()) {
                setBackgroundGradientColors(covertGradientColorsIntArray(), gradientOrientation)
            } else {
                setBackgroundGradient(XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, gradientOrientation)
            }
            shadowColor = cardShadowColor
            shadowElevation = cardShadowElevation.dp
            setBorderColor(mBorderColor)
            setBorderWidth(mBorderWidth.dp)
        }

        binding.contentRound.apply {
            setRadius(roundRadiusLeftTop.dp, roundRadiusRightTop.dp, roundRadiusLeftBottom.dp, roundRadiusRightBottom.dp)
            if (gradientColors.isNotEmpty()) {
                setBackgroundGradientColors(covertGradientColorsIntArray(), gradientOrientation)
            } else {
                setBackgroundGradient(XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, gradientOrientation)
            }
            setBorderColor(mBorderColor)
            setBorderWidth(mBorderWidth.dp)
        }

        binding.contentView.apply {
            if (gradientColors.isNotEmpty()) {
                setBackgroundGradientColors(covertGradientColorsIntArray(), gradientOrientation)
            } else {
                setBackgroundGradient(XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT, gradientOrientation)
            }
        }
    }


    private fun covertGradientColorsIntArray(): IntArray {
        val colors = arrayListOf<Int>()
        gradientColors.filterNot { it.type != "color" }.forEach {
            colors.add(it.color)
        }
        return colors.toIntArray()
    }

    private fun showMenu() {
        showAnimator = ObjectAnimator.ofFloat(binding.llMenu, "translationY", binding.llMenu.measuredHeight.toFloat(), 0f)
        showAnimator?.duration = 100
        showAnimator?.interpolator = XInterpolators.linear
        showAnimator?.start()
        binding.flContentView.show()
        binding.tvResult.gone()
    }

    private fun hideMenu() {
        hideAnimator = ObjectAnimator.ofFloat(binding.llMenu, "translationY", 0f, binding.llMenu.measuredHeight.toFloat())
        hideAnimator?.duration = 100
        hideAnimator?.interpolator = XInterpolators.linear
        hideAnimator?.start()
        binding.flContentView.gone()
        binding.tvResult.show()
        showCode()
    }

    fun onBackPressed() {
        if (!binding.tvSettings.isVisible) {
            binding.tvSettings.show()
            hideMenu()
        } else {
            activity?.finish()
        }
    }


    private fun showCode() {
        when (currentContentViewId) {
            binding.contentCard.id -> {
                showCardCode()
            }

            binding.contentRound.id -> {
                showRoundCode()
            }

            binding.contentView.id -> {
                showNormalCode()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showNormalCode() {

        val orientation = when (gradientOrientation) {
            GradientOrientation.HORIZONTAL -> "horizontal"
            GradientOrientation.VERTICAL -> "vertical"
            GradientOrientation.BL_TR -> "bl_tr"
            GradientOrientation.TL_BR -> "tl_br"
            else -> "horizontal"
        }

        val colors = arrayListOf<String>()
        gradientColors.filterNot { it.type != "color" }.forEach {
            colors.add(XColorHelper.colorToString(it.color ?: 0))
        }
        val colorStr = colors.joinToString(",")

        binding.tvResult.text = """代码参考：
            
<com.pichs.xwidget.view.XButton
    android:id="@+id/content_view"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="46dp"
    android:background="#FFFFFF"
    android:text="XView系列"
    android:textColor="#333"
    android:textSize="22sp"
    app:xp_backgroundGradientColors="$colorStr"
    app:xp_backgroundGradientOrientation="$orientation"/>  
             
    ...
        """.trimIndent()
    }

    @SuppressLint("SetTextI18n")
    private fun showRoundCode() {

        val orientation = when (gradientOrientation) {
            GradientOrientation.HORIZONTAL -> "horizontal"
            GradientOrientation.VERTICAL -> "vertical"
            GradientOrientation.BL_TR -> "bl_tr"
            GradientOrientation.TL_BR -> "tl_br"
            else -> "horizontal"
        }

        val colors = arrayListOf<String>()
        gradientColors.filterNot { it.type != "color" }.forEach {
            colors.add(XColorHelper.colorToString(it.color ?: 0))
        }
        val colorStr = colors.joinToString(",")

        binding.tvResult.text = """代码参考：
            
<com.pichs.xwidget.roundview.XRoundButton
    android:id="@+id/content_round"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="46dp"
    android:background="#0072FF"
    android:text="XRound系列"
    android:textColor="#333"
    android:textSize="22sp"
    app:xp_backgroundGradientColors="$colorStr"
    app:xp_backgroundGradientOrientation="$orientation"
    app:xp_radiusTopLeft="${roundRadiusLeftTop}dp"
    app:xp_radiusTopRight="${roundRadiusRightTop}dp" 
    app:xp_radiusBottomLeft="${roundRadiusLeftBottom}dp"
    app:xp_radiusBottomRight="${roundRadiusRightBottom}dp"
    app:xp_borderColor="${XColorHelper.colorToString(mBorderColor)}"
    app:xp_borderWidth="${mBorderWidth}dp"
    />
    
    ...
        """.trimIndent()

    }

    @SuppressLint("SetTextI18n")
    private fun showCardCode() {

        val orientation = when (gradientOrientation) {
            GradientOrientation.HORIZONTAL -> "horizontal"
            GradientOrientation.VERTICAL -> "vertical"
            GradientOrientation.BL_TR -> "bl_tr"
            GradientOrientation.TL_BR -> "tl_br"
            else -> "horizontal"
        }

        val colors = arrayListOf<String>()
        gradientColors.filterNot { it.type != "color" }.forEach {
            colors.add(XColorHelper.colorToString(it.color ?: 0))
        }
        val colorStr = colors.joinToString(",")

        val shadowColorStr = XColorHelper.colorToString(cardShadowColor)

        val hideRadiusSideStr = when (mHideRadiusSide) {
            XILayout.HIDE_RADIUS_SIDE_NONE -> "none"
            XILayout.HIDE_RADIUS_SIDE_TOP -> "top"
            XILayout.HIDE_RADIUS_SIDE_RIGHT -> "right"
            XILayout.HIDE_RADIUS_SIDE_BOTTOM -> "bottom"
            XILayout.HIDE_RADIUS_SIDE_LEFT -> "left"
            else -> "none"
        }

        binding.tvResult.text = """代码参考：
            
 <com.pichs.xwidget.cardview.XCardButton
    android:id="@+id/content_card"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="46dp"
    android:background="#FFFFFF"
    android:text="XCard系列"
    android:textSize="22sp"
    android:visibility="visible"
    app:xp_radius="${cardRadius}dp"
    app:xp_backgroundGradientColors="$colorStr"
    app:xp_backgroundGradientOrientation="$orientation"
    app:xp_hideRadiusSide="${hideRadiusSideStr}"
    app:xp_shadowColor="${shadowColorStr}"
    app:xp_shadowAlpha="1"
    app:xp_shadowElevation="${cardShadowElevation}dp"
    app:xp_borderColor="${XColorHelper.colorToString(mBorderColor)}"
    app:xp_borderWidth="${mBorderWidth}dp"
    />  
     
     ...
        """.trimIndent()
    }

}