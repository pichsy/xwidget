package com.pichs.xwidget.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.Field;

/**
 * 状态栏帮助类
 */
public class XStatusBarHelper {

    private final static int STATUS_BAR_DEFAULT_HEIGHT_DP = 25; // 大部分状态栏都是25dp
    // 在某些机子上存在不同的density值，所以增加两个虚拟值
    public static float sVirtualDensity = -1;
    public static float sVirtualDensityDpi = -1;

    /**
     * 沉浸式状态栏
     */
    public static void immersiveStatusBar(Activity activity) {
        if (activity == null) return;
        if (activity.getWindow() == null) return;
        transparentStatusBar(activity.getWindow());
        transparentNavigationBar(activity.getWindow());
    }


    public static void translucent(Activity activity) {
        immersiveStatusBar(activity);
    }

    public static void translucent(Window window) {
        if (window == null) return;
        transparentStatusBar(window);
        transparentNavigationBar(window);
    }

    /**
     * 透明状态栏
     */
    public static void transparentStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility = systemUiVisibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 透明导航栏
     */
    public static void transparentNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility = systemUiVisibility | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 黑色状态栏文字
     * 设置状态栏文字颜色
     *
     * @param isDark true为黑色，false为白色
     */
    public static void setStatusBarFontDark(Window window, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (window != null) {
                WindowInsetsController controller = window.getInsetsController();
                if (controller != null) {
                    if (isDark) {
                        controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    } else {
                        controller.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    }
                }
            }
        } else {
            View decor = window.getDecorView();
            if (isDark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decor.setSystemUiVisibility(decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decor.setSystemUiVisibility(decor.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }


    public static void handleDisplayCutoutMode(final Window window) {
        View decorView = window.getDecorView();
        if (ViewCompat.isAttachedToWindow(decorView)) {
            handleDisplayCutoutMode(window, decorView);
        } else {
            decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    handleDisplayCutoutMode(window, v);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });
        }
    }

    /**
     * 适配刘海屏
     *
     * @param window    Window
     * @param decorView DecorView
     */
    public static void handleDisplayCutoutMode(Window window, View decorView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && decorView.getRootWindowInsets() != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
            window.setAttributes(lp);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && decorView.getRootWindowInsets() != null && decorView.getRootWindowInsets().getDisplayCutout() != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(lp);
        }
    }

    /**
     * 适配刘海屏
     *
     * @param activity Activity
     */
    public static void handleDisplayCutoutMode(Activity activity) {
        if (activity == null) return;
        if (activity.getWindow() == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && activity.getWindow().getDecorView().getRootWindowInsets() != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
            activity.getWindow().setAttributes(lp);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && activity.getWindow().getDecorView().getRootWindowInsets() != null && activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout() != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
        }
    }


    /**
     * 设置状态栏黑色字体图标，
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     */
    public static void setStatusBarFontBlackColor(Activity activity) {
        if (activity == null) return;
        if (activity.getWindow() == null) return;
        setStatusBarFontDark(activity.getWindow(), true);
    }

    /**
     * 暗黑主题的 状态栏，即字体是白色。
     *
     * @param activity
     */
    public static void setStatusBarFontWhiteColor(Activity activity) {
        if (activity == null) return;
        if (activity.getWindow() == null) return;
        setStatusBarFontDark(activity.getWindow(), false);
    }

    /**
     * 获取是否全屏
     *
     * @return 是否全屏
     */
    public static boolean isFullScreen(Activity activity) {
        if (activity == null) return false;
        if (activity.getWindow() == null) return false;
        boolean ret = false;
        try {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            ret = (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 检测 Android 6.0 是否可以启用 window.setStatusBarColor(Color.TRANSPARENT)。
     */
    public static boolean supportTranslucentStatusBar6() {
        return !(XDeviceHelper.isZUKZ1() || XDeviceHelper.isZTKC2016());
    }

    /**
     * 获取状态栏的高度。
     */
    public static int getStatusBarHeight(Context context) {
        Resources res = Resources.getSystem();
        @SuppressLint("InternalInsetResource")
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        if (resourceId != 0) {
            height = res.getDimensionPixelSize(resourceId);
        }
        if (height == 0 && context != null) {
            height = getStatusBarHeight2(context);
        }
        return height;
    }

    public static int getStatusBarHeight() {
        return getStatusBarHeight(null);
    }

    private static int getStatusBarHeight2(Context context) {
        int sStatusbarHeight = 0;
        Class<?> clazz;
        Object obj = null;
        Field field = null;
        try {
            clazz = Class.forName("com.android.internal.R$dimen");
            obj = clazz.newInstance();
            if (XDeviceHelper.isMeizu()) {
                try {
                    field = clazz.getField("status_bar_height_large");
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (field == null) {
                field = clazz.getField("status_bar_height");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (field != null && obj != null) {
            try {
                int id = Integer.parseInt(field.get(obj).toString());
                sStatusbarHeight = context.getResources().getDimensionPixelSize(id);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (XDeviceHelper.isTablet(context)
                && sStatusbarHeight > XDisplayHelper.dp2px(context, STATUS_BAR_DEFAULT_HEIGHT_DP)) {
            //状态栏高度大于25dp的平板，状态栏通常在下方
            sStatusbarHeight = 0;
        } else {
            if (sStatusbarHeight <= 0) {
                if (sVirtualDensity == -1) {
                    sStatusbarHeight = XDisplayHelper.dp2px(context, STATUS_BAR_DEFAULT_HEIGHT_DP);
                } else {
                    sStatusbarHeight = (int) (STATUS_BAR_DEFAULT_HEIGHT_DP * sVirtualDensity + 0.5f);
                }
            }
        }
        return sStatusbarHeight;
    }

    public static void setVirtualDensity(float density) {
        sVirtualDensity = density;
    }

    public static void setVirtualDensityDpi(float densityDpi) {
        sVirtualDensityDpi = densityDpi;
    }

    /**
     * @param activity FragmentActivity
     *                 此方法必须配合 {@link #onWindowFocusChanged} 一起使用。
     *                 在onCreate中setContentView之前调用此方法
     */
    public static void setFullScreen(FragmentActivity activity) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags = attributes.flags | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(attributes);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 配合 setFullScreen 方法使用
     * 在 activit 的 onWindowFocusChanged中 调用此方法
     *
     * @param activity FragmentActivity
     * @param hasFocus hasFocus
     */
    public static void onWindowFocusChanged(FragmentActivity activity, boolean hasFocus) {
        if (activity == null) return;
        if (hasFocus) {
            Window window = activity.getWindow();
            if (window != null) {
                View decorView = window.getDecorView();
                if (decorView != null) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
                }
            }
        }
    }

    /**
     * 获取导航键的高度
     *
     * @return height
     *
     * @deprecated 此方法已过时，有些情况下获取不准，比如横条导航栏。
     * * <p>
     * 建议使用 {@link XNavigationBarUtils#getNavigationBarHeight(Activity)} 方法获取导航栏高度。
     * or {@link #getNavigationHeight(Activity)} 方法获取导航栏高度。
     * <p>
     */
    @Deprecated
    public static int getNavigationHeight() {
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        Resources res = Resources.getSystem();
        @SuppressLint("InternalInsetResource")
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = 0;
        if (resourceId != 0) {
            height = res.getDimensionPixelSize(resourceId);
        }
        return height;
    }


    /**
     * 获取导航键的高度
     *
     * @return height
     */
    public static int getNavigationHeight(Activity activity) {
        return XNavigationBarUtils.getNavigationBarHeight(activity);
    }

}
