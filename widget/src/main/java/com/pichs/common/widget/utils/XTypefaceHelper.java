package com.pichs.common.widget.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 字体帮助类。
 */
public class XTypefaceHelper {

    public static final String TYPEFACE_DEFAULT = "DEFAULT"; // 默认字体
    public static final String TYPEFACE_BOLD = "BOLD"; // 粗体
    public static final String TYPEFACE_ITALIC = "ITALIC"; // 斜体
    public static final String TYPEFACE_BOLD_ITALIC = "BOLD_ITALIC"; // 粗斜体
    public static final String TYPEFACE_SANS_SERIF = "SANS_SERIF"; // sans——serif字体
    public static final String TYPEFACE_MONOSPACE = "MONOSPACE"; // monospace字体
    public static final String TYPEFACE_SERIF = "SERIF"; // serif字体

    public static final int NORMAL = Typeface.NORMAL;
    public static final int BOLD = Typeface.BOLD;
    public static final int ITALIC = Typeface.ITALIC;
    public static final int BOLD_ITALIC = Typeface.BOLD_ITALIC;


    private static Typeface mGlobalTypeface = null;
    // 配合KEY_TYPEFACE_FROM使用，来决定此值的价值。0：null/"",  1：assets路径, 2：文件路径， 3：系统字体(DEFAULT, BOLD, ITALIC)
    private static final String KEY_TYPEFACE_FILEPATH = "key_xw_xtf_cache_path";
    // typeface的来源，0：系统默认。 1：assets路径, 2：文件路径， 3：系统字体(KEY_TYPEFACE_FILEPATH：DEFAULT, BOLD, ITALIC)
    private static final String KEY_TYPEFACE_FROM = "key_xw_xtf_cache_file_from";
    /**
     * typeface类型。可针对三方字体进行加粗，斜体操作。
     * {@link Typeface#BOLD},
     * {@link Typeface#ITALIC},
     * {@link Typeface#BOLD_ITALIC},
     * {@link Typeface#NORMAL}
     * public static final int NORMAL = 0;
     * public static final int BOLD = 1;
     * public static final int ITALIC = 2;
     * public static final int BOLD_ITALIC = 3;
     */
    private static final String KEY_TYPEFACE_STYLE = "key_xw_xtf_cache_typeface_style";

    /**
     * 监听者队列
     */
    private final static WeakHashMap<View, GlobalTypefaceObserver> mTypefaceMap = new WeakHashMap<>();

    public static void init(Context context) {
        int typefaceFrom = XWidgetCache.getInstance(context).getInt(KEY_TYPEFACE_FROM, 0);
        String path = XWidgetCache.getInstance(context).getString(KEY_TYPEFACE_FILEPATH, null);
        int style = XWidgetCache.getInstance(context).getInt(KEY_TYPEFACE_STYLE, 0);
        if (typefaceFrom > 0 && !TextUtils.isEmpty(path)) {
            if (typefaceFrom == 1) {
                mGlobalTypeface = Typeface.createFromAsset(context.getAssets(), path);
            } else if (typefaceFrom == 2) {
                mGlobalTypeface = Typeface.createFromFile(path);
            } else if (typefaceFrom == 3) {
                mGlobalTypeface = getTypefaceFromName(path);
            }
        }
        setGlobalTypefaceStyle(context, style);
    }

    /**
     * 注册观察者
     * 具有粘性效果，首次注册时若有字体类型，则直接回调，为null时则不回调。使用系统默认就好
     *
     * @param view     view对象
     * @param observer 监听者
     */
    public static void observer(View view, GlobalTypefaceObserver observer) {
        if (view != null && observer != null) {
            mTypefaceMap.put(view, observer);
            if (mGlobalTypeface != null) {
                observer.onChanged(mGlobalTypeface);
            }
        }
    }

    /**
     * 触发监听，发送消息到观察者那
     */
    private static void post() {
        if (!mTypefaceMap.isEmpty()) {
            for (Map.Entry<View, GlobalTypefaceObserver> entry : mTypefaceMap.entrySet()) {
                if (entry != null && entry.getValue() != null) {
                    entry.getValue().onChanged(mGlobalTypeface);
                }
            }
        }
    }

    /**
     * 设置全局字体。
     *
     * @param filePath 文件路径
     */
    public static void setGlobalTypefaceFromFile(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        XWidgetCache.getInstance(context).setString(KEY_TYPEFACE_FILEPATH, filePath);
        XWidgetCache.getInstance(context).setInt(KEY_TYPEFACE_FROM, 2);
        mGlobalTypeface = Typeface.createFromFile(filePath);
        post();
    }

    /**
     * 设置全局字体。
     *
     * @param file 文件
     */
    public static void setGlobalTypefaceFromFile(Context context, File file) {
        if (!file.exists()) {
            return;
        }
        XWidgetCache.getInstance(context).setString(KEY_TYPEFACE_FILEPATH, file.getAbsolutePath());
        XWidgetCache.getInstance(context).setInt(KEY_TYPEFACE_FROM, 2);
        mGlobalTypeface = Typeface.createFromFile(file);
        post();
    }

    /**
     * 设置全局字体。
     *
     * @param context      上下文
     * @param typefaceName 字体assets路径
     */
    public static void setGlobalTypefaceFromAssets(Context context, String typefaceName) {
        if (TextUtils.isEmpty(typefaceName)) {
            return;
        }
        mGlobalTypeface = Typeface.createFromAsset(context.getAssets(), typefaceName);
        XWidgetCache.getInstance(context).setString(KEY_TYPEFACE_FILEPATH, typefaceName);
        XWidgetCache.getInstance(context).setInt(KEY_TYPEFACE_FROM, 1);
        post();
    }

    /**
     * 使用系统字体，对用关系如下
     *
     * @param name {@link #TYPEFACE_DEFAULT}=>{@link Typeface#DEFAULT},
     *             {@link #TYPEFACE_ITALIC}=>{@link Typeface#ITALIC},
     *             {@link #TYPEFACE_BOLD}=>{@link Typeface#BOLD}
     *             {@link #TYPEFACE_BOLD_ITALIC}=>{@link Typeface#BOLD_ITALIC}
     *             {@link #TYPEFACE_SANS_SERIF}=>{@link Typeface#SANS_SERIF}
     *             {@link #TYPEFACE_SERIF}=>{@link Typeface#SERIF}
     *             {@link #TYPEFACE_MONOSPACE}=>{@link Typeface#MONOSPACE}
     */
    public static void setGlobalTypeface(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        mGlobalTypeface = getTypefaceFromName(name);
        if (mGlobalTypeface != null) {
            XWidgetCache.getInstance(context).setString(KEY_TYPEFACE_FILEPATH, name);
            XWidgetCache.getInstance(context).setInt(KEY_TYPEFACE_FROM, 3);
            post();
        }
    }

    /**
     * 根据系统文件名，来定义字体规格
     *
     * @param name 存储名字
     */
    private static Typeface getTypefaceFromName(String name) {
        if (TYPEFACE_BOLD.equals(name)) {
            return Typeface.DEFAULT_BOLD;
        } else if (TYPEFACE_BOLD_ITALIC.equals(name)) {
            return Typeface.create((Typeface) null, Typeface.BOLD_ITALIC);
        } else if (TYPEFACE_ITALIC.equals(name)) {
            return Typeface.create((Typeface) null, Typeface.ITALIC);
        } else if (TYPEFACE_DEFAULT.equals(name)) {
            return Typeface.DEFAULT;
        } else if (TYPEFACE_SERIF.equals(name)) {
            return Typeface.SERIF;
        } else if (TYPEFACE_SANS_SERIF.equals(name)) {
            return Typeface.SANS_SERIF;
        } else if (TYPEFACE_MONOSPACE.equals(name)) {
            return Typeface.MONOSPACE;
        }
        return null;
    }


    /**
     * 设置字体 粗、斜 体
     *
     * @param style {@link #NORMAL},
     *              {@link #BOLD},
     *              {@link #ITALIC},
     *              {@link #BOLD_ITALIC}
     */
    public static void setGlobalTypefaceStyle(Context context, int style) {
        // 样式必须在规则内。
        if (style < NORMAL || style > BOLD_ITALIC) {
            return;
        }
        XWidgetCache.getInstance(context).setInt(KEY_TYPEFACE_STYLE, style);
        mGlobalTypeface = Typeface.create(mGlobalTypeface, style);
        post();
    }

    /**
     * 由此来控制全局字体，获取全局字体的类型
     */
    private static Typeface getGlobalTypeface() {
        return mGlobalTypeface;
    }

    /**
     * 重新设置字体。
     */
    public static void resetTypeface() {
        mGlobalTypeface = null;
        post();
    }

    /**
     * 清空字体监听器
     */
    public static void clearObserver() {
        mTypefaceMap.clear();
    }

    /**
     * 全局的字体监听
     */
    public interface GlobalTypefaceObserver {
        void onChanged(@Nullable Typeface typeface);
    }
}
