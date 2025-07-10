package com.pichs.xwidget.utils;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.view.WindowInsets;

public class XNavigationBarUtils {

    public static int getNavigationBarHeight(Activity activity) {
        int hei = getBottomNavBarHeight(activity);
        if (hei > 0) {
            return hei;
        }
        return XDisplayHelper.getNavMenuHeight(activity);
    }

    /**
     * 获取手势导航条高度
     * 这个判断必须放在 UI加载完毕以后。
     * 建议：ViewCompat.setOnApplyWindowInsetsListener(window.decorView, (v, insets) -> {
     *     isGestureBarVisible(this);
     * });
     */
    public static boolean isGestureBarVisible(Activity activity) {
        if (activity == null) {
            return false;
        }

        int bn = getBottomNavBarHeight(activity);

        if (bn > 0) {
            // 说明是 屏幕内三键 导航
            return true;
        }

        int gestureInsets = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsets insets = activity.getWindow().getDecorView().getRootWindowInsets();
            if (insets != null) {
                gestureInsets = insets.getInsets(WindowInsets.Type.systemGestures()).bottom;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowInsets insets = activity.getWindow().getDecorView().getRootWindowInsets();
            if (insets != null) {
                gestureInsets = insets.getSystemGestureInsets().bottom;
            }
        }

        if (gestureInsets > 0) {
            return true;
        }

        // 小米特殊判断
        if ("xiaomi".equalsIgnoreCase(Build.BRAND)) {
            try {
                int value = Settings.Global.getInt(activity.getContentResolver(), "force_fsg_nav_bar", 0);
                return value == 1;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    private static int getBottomNavBarHeight(Activity activity) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsets insets = activity.getWindow().getDecorView().getRootWindowInsets();
            if (insets != null) {
                return insets.getInsets(WindowInsets.Type.navigationBars()).bottom;
            }
        } else {
            WindowInsets insets = activity.getWindow().getDecorView().getRootWindowInsets();
            if (insets != null) {
                return insets.getStableInsetBottom();
            }
        }
        return 0;
    }
}