package com.pichs.xwidget.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * 像素转换帮助类
 */
public class XDisplayHelper {
    /**
     * 是否有摄像头
     */
    private static Boolean sHasCamera = null;


    /**
     * 获取 DisplayMetrics
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        int screenHeight = getDisplayMetrics(context).heightPixels;
        if (XDeviceHelper.isXiaomi() && xiaomiNavigationGestureEnabled(context)) {
            screenHeight += getResourceNavHeight(context);
        }
        return screenHeight;
    }


    /**
     * 获取屏幕真实宽
     *
     * @param context Context
     * @return RealScreenWidth
     */
    public static int getRealScreenWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenWidth(context);
        } else {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes") Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                return displayMetrics.widthPixels;
            } catch (Exception e) {
                return getScreenWidth(context);
            }
        }
    }

    /**
     * 获取屏幕真实高
     *
     * @param context Context
     * @return RealScreenHeight
     */
    public static int getRealScreenHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenHeight(context);
        } else {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes") Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                return displayMetrics.heightPixels;
            } catch (Exception e) {
                return getScreenHeight(context);
            }
        }
    }

    /**
     * 获取屏幕物理高
     *
     * @param context Context
     * @return getPhysicalHeight
     */
    public static int getPhysicalHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenWidth(context);
        } else {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            @SuppressWarnings("rawtypes") Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked") Method method = c.getMethod("getPhysicalHeight");
                return (int) method.invoke(display);
            } catch (Exception e) {
                return getRealScreenHeight(context);
            }
        }
    }

    /**
     * 获取屏幕物理宽
     *
     * @param context Context
     * @return getPhysicalWidth
     */
    public static int getPhysicalWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenHeight(context);
        } else {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            @SuppressWarnings("rawtypes") Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked") Method method = c.getMethod("getPhysicalWidth");
                return (int) method.invoke(display);
            } catch (Exception e) {
                return getRealScreenWidth(context);
            }
        }
    }

    /**
     * 获取屏幕的真实宽高
     *
     * @param context Context
     * @return int[0] 宽，int[1] 高
     */
    public static int[] getRealScreenSize(Context context) {
        // 切换屏幕导致宽高变化时不能用 cache，先去掉 cache
        return doGetRealScreenSize(context);
    }

    private static int[] doGetRealScreenSize(Context context) {
        int[] size = new int[2];
        int widthPixels, heightPixels;
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        try {
            // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
            widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
            heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
        } catch (Exception ignored) {
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
                Point realSize = new Point();
                d.getRealSize(realSize);
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    public static boolean isNavMenuExist(Context context) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, int sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }

    /**
     * 单位转换:px -> dp
     *
     * @param px
     * @return
     */
    public static float px2dp(Context context, int px) {
        return (float) px / getDensity(context);
    }

    /**
     * 单位转换:px -> sp
     *
     * @param px
     * @return
     */
    public static float px2sp(Context context, int px) {
        return (float) px / getFontDensity(context);
    }

    /**
     * 判断是否有状态栏
     *
     * @param context
     * @return
     */
    public static boolean hasStatusBar(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        return true;
    }

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        return XStatusBarHelper.getStatusBarHeight(context);
    }

    /**
     * 获取导航栏高度，即返回键，Home键 那个 bar 的高度
     *
     * @param context context上下文
     * @return Int
     * @see #getNavMenuHeight(Context)
     * 不精准，不建议使用，请使用 {@link #getNavigationBarHeight(Activity)} 或 {@link #getNavMenuHeight(Context)}
     */
    @Deprecated
    public static int getNavigationBarHeight(Context context) {
        if (context instanceof Activity) {
            return XNavigationBarUtils.getNavigationBarHeight((Activity) context);
        }
        return getNavMenuHeight(context);
    }

    /**
     * 获取导航栏高度，即返回键，Home键 那个 bar 的高度
     *
     * @param activity Activity上下文
     * @return Int
     * @see #getNavMenuHeight(Context)
     */
    public static int getNavigationBarHeight(Activity activity) {
        return XNavigationBarUtils.getNavigationBarHeight(activity);
    }

    /**
     * 获取导航栏高度，即返回键，Home键 那个 bar 的高度
     *
     * @param context context上下文
     * @return Int
     */
    public static int getNavMenuHeight(Context context) {
        if (!isNavMenuExist(context)) {
            return 0;
        }
        int resourceNavHeight = getResourceNavHeight(context);
        if (resourceNavHeight >= 0) {
            return resourceNavHeight;
        }

        // 小米 MIX 有nav bar, 而 getRealScreenSize(context)[1] - getScreenHeight(context) = 0
        return getRealScreenSize(context)[1] - getScreenHeight(context);
    }

    private static int getResourceNavHeight(Context context) {
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    public static final boolean hasCamera(Context context) {
        if (sHasCamera == null) {
            PackageManager pckMgr = context.getPackageManager();
            boolean flag = pckMgr.hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            flag2 = flag || flag1;
            sHasCamera = flag2;
        }
        return sHasCamera;
    }

    /**
     * 是否有硬件menu
     *
     * @param context
     * @return
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag;
        if (Build.VERSION.SDK_INT < 11) flag = true;
        else if (Build.VERSION.SDK_INT >= 14) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else flag = false;
        return flag;
    }

    /**
     * 是否有网络功能
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * 判断是否存在pckName包
     *
     * @param pckName
     * @return
     */
    public static boolean isPackageExist(Context context, String pckName) {
        try {
            PackageInfo pckInfo = context.getPackageManager().getPackageInfo(pckName, 0);
            if (pckInfo != null) return true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    /**
     * 判断 SD Card 是否 ready
     *
     * @return
     */
    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取当前国家的语言
     *
     * @param context
     * @return
     */
    public static String getCurCountryLan(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        return sysLocale.getLanguage() + "-" + sysLocale.getCountry();
    }

    /**
     * 判断是否为中文环境
     *
     * @param context
     * @return
     */
    public static boolean isZhCN(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        String lang = sysLocale.getCountry();
        return lang.equalsIgnoreCase("CN");
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 判断是否全屏
     *
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }


    public static boolean isElevationSupported() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean hasNavigationBar(Context context) {
        boolean hasNav = deviceHasNavigationBar();
        if (!hasNav) {
            return false;
        }
        if (XDeviceHelper.isVivo()) {
            return vivoNavigationGestureEnabled(context);
        }
        return true;
    }

    /**
     * 判断设备是否存在NavigationBar
     *
     * @return true 存在, false 不存在
     */
    private static boolean deviceHasNavigationBar() {
        boolean haveNav = false;
        try {
            //1.通过WindowManagerGlobal获取windowManagerService
            // 反射方法：IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            Class<?> windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal");
            Method getWmServiceMethod = windowManagerGlobalClass.getDeclaredMethod("getWindowManagerService");
            getWmServiceMethod.setAccessible(true);
            //getWindowManagerService是静态方法，所以invoke null
            Object iWindowManager = getWmServiceMethod.invoke(null);

            //2.获取windowMangerService的hasNavigationBar方法返回值
            // 反射方法：haveNav = windowManagerService.hasNavigationBar();
            Class<?> iWindowManagerClass = iWindowManager.getClass();
            Method hasNavBarMethod = iWindowManagerClass.getDeclaredMethod("hasNavigationBar");
            hasNavBarMethod.setAccessible(true);
            haveNav = (Boolean) hasNavBarMethod.invoke(iWindowManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return haveNav;
    }

    // ====================== Setting ===========================
    private static final String VIVO_NAVIGATION_GESTURE = "navigation_gesture_on";
    private static final String HUAWAI_DISPLAY_NOTCH_STATUS = "display_notch_status";
    private static final String XIAOMI_DISPLAY_NOTCH_STATUS = "force_black";
    private static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";

    /**
     * 获取vivo手机设置中的"navigation_gesture_on"值，判断当前系统是使用导航键还是手势导航操作
     *
     * @param context app Context
     * @return false 表示使用的是虚拟导航键(NavigationBar)， true 表示使用的是手势， 默认是false
     */
    public static boolean vivoNavigationGestureEnabled(Context context) {
        int val = Settings.Secure.getInt(context.getContentResolver(), VIVO_NAVIGATION_GESTURE, 0);
        return val != 0;
    }


    public static boolean xiaomiNavigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
        return val != 0;
    }


    public static boolean huaweiIsNotchSetToShowInSetting(Context context) {
        // 0: 默认
        // 1: 隐藏显示区域
        int result = Settings.Secure.getInt(context.getContentResolver(), HUAWAI_DISPLAY_NOTCH_STATUS, 0);
        return result == 0;
    }
}
