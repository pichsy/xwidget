package com.pichs.app.xwidget.settings

import android.content.Intent
import com.hjq.toast.Toaster
import com.pichs.app.xwidget.MainActivity
import com.pichs.app.xwidget.aboutus.AboutUsActivity
import com.pichs.app.xwidget.base.BaseActivity
import com.pichs.app.xwidget.databinding.ActivitySettingsBinding
import com.pichs.xbase.clickhelper.fastClick
import com.pichs.xbase.utils.bounceHeaderFooter

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    override fun afterOnCreate() {

        binding.refreshLayout.bounceHeaderFooter()

        initListeners()
    }

    private fun initListeners() {

        binding.ivBack.fastClick {
            finish()
        }

        binding.clMainPage.fastClick {
            // 账号安全
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.clFontSettings.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：字体设置")
        }

        binding.clThemeSettings.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：主题设置")
        }

        binding.clAbout.fastClick {
            // 关于我们
            startActivity(Intent(this, AboutUsActivity::class.java))
        }

        binding.clFeedback.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：反馈")
        }

        binding.clCollection.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：收藏")
        }


        binding.clHistoryRecord.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：历史记录")
        }

        binding.clChildProtectionPlan.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：青少年保护计划")
        }

        binding.clPrivacyPolicy.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：隐私政策")
        }

        binding.clUserAsso.fastClick {
            // 账号安全
            Toaster.show("仅展示效果：用户协议")
        }


    }

}