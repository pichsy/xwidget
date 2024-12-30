package com.pichs.app.xwidget.utils

import android.content.Intent
import com.hjq.toast.Toaster
import com.pichs.app.xwidget.ui.button.ButtonActivity
import com.pichs.app.xwidget.ui.card.CardLayoutActivity
import com.pichs.app.xwidget.ui.checkbox.CheckboxActivity
import com.pichs.app.xwidget.ui.edittext.EditTextActivity
import com.pichs.app.xwidget.ui.flow.FlowLayoutActivity
import com.pichs.app.xwidget.ui.image.ImageActivity
import com.pichs.app.xwidget.ui.inputlayout.InputLayoutActivity
import com.pichs.app.xwidget.ui.nestedscroll.NestedScrollActivity
import com.pichs.app.xwidget.ui.progress.ProgressActivity
import com.pichs.app.xwidget.ui.radio.RadioActivity
import com.pichs.app.xwidget.ui.rating.RatingActivity
import com.pichs.app.xwidget.ui.round.RoundLayoutActivity
import com.pichs.app.xwidget.ui.scale.ScaleAnimationActivity
import com.pichs.app.xwidget.ui.shining.ShiningActivity
import com.pichs.app.xwidget.ui.space.SpaceActivity
import com.pichs.app.xwidget.ui.switcher.SwitcherActivity
import com.pichs.app.xwidget.ui.text.TextActivity
import com.pichs.app.xwidget.ui.web.WebViewActivity
import com.pichs.xbase.kotlinext.startActivitySafely
import com.pichs.xbase.utils.UiKit

object JumpUtils {

    fun jump2(tag: String) {
//        Toaster.show("跳转：tag:$tag")
        when (tag) {
            "button" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), ButtonActivity::class.java)
                )
            }

            "font" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), TextActivity::class.java)
                )
            }

            "image" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), ImageActivity::class.java)
                )
            }

            "card_layout" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), CardLayoutActivity::class.java)
                )
            }

            "round_layout" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), RoundLayoutActivity::class.java)
                )
            }

            "space" -> {

                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), SpaceActivity::class.java)
                )

            }

            "radio" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), RadioActivity::class.java)
                )
            }

            "checkbox" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), CheckboxActivity::class.java)
                )
            }

            "rating" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), RatingActivity::class.java)
                )
            }

            "flowlayout" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), FlowLayoutActivity::class.java)
                )
            }

            "shining" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), ShiningActivity::class.java)
                )
            }

            "progressbar" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), ProgressActivity::class.java)
                )
            }

            "edittext" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), EditTextActivity::class.java)
                )
            }

            "switch" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), SwitcherActivity::class.java)
                )
            }

            "input_layout" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), InputLayoutActivity::class.java)
                )
            }

            "scale_layout" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), ScaleAnimationActivity::class.java)
                )
            }

            "xwebview" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), WebViewActivity::class.java)
                )
            }

            "nested_scroll" -> {
                UiKit.getApplication().startActivitySafely(
                    Intent(UiKit.getApplication(), NestedScrollActivity::class.java)
                )
            }
        }
    }

}