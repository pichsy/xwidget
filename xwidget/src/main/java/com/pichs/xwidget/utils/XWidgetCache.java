package com.pichs.xwidget.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

/**
 * 缓存帮助类，用于全局字体缓存
 */
public class XWidgetCache {

    protected WeakReference<Context> mContextWeakReference;
    protected SharedPreferences mSP;
    private final static String _spName = "xp_xw_widget_global_tf_config.conf";
    /**
     * 缓存对象
     */
    public static XWidgetCache mInstance;

    public XWidgetCache(Context context) {
        if (context == null) {
            return;
        }
        mContextWeakReference = new WeakReference<>(context.getApplicationContext());
        if (mSP == null) {
            mSP = mContextWeakReference.get().getSharedPreferences(_spName, 0);
        }
    }

    public static XWidgetCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (XWidgetCache.class) {
                if (mInstance == null) {
                    mInstance = new XWidgetCache(context);
                }
            }
        }
        return mInstance;
    }

    public String getString(String key, String defaultValue) {
        if (mContextWeakReference.get() == null) {
            return defaultValue;
        }
        if (mSP == null) {
            mSP = mContextWeakReference.get().getSharedPreferences(_spName, 0);
        }
        return mSP.getString(key, defaultValue);
    }

    public void setString(String key, String value) {
        if (mContextWeakReference.get() == null) {
            return;
        }
        if (mSP == null) {
            mSP = mContextWeakReference.get().getSharedPreferences(_spName, 0);
        }
        SharedPreferences.Editor editor = mSP.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        if (mContextWeakReference.get() == null) {
            return;
        }
        if (mSP == null) {
            mSP = mContextWeakReference.get().getSharedPreferences(_spName, 0);
        }
        SharedPreferences.Editor editor = mSP.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        if (mContextWeakReference.get() == null) {
            return defaultValue;
        }
        if (mSP == null) {
            mSP = mContextWeakReference.get().getSharedPreferences(_spName, 0);
        }
        return mSP.getInt(key, defaultValue);
    }


}
