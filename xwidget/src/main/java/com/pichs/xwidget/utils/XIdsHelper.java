package com.pichs.xwidget.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class XIdsHelper {


    @SuppressLint("DiscouragedApi")
    public static int getIdByName(Context context, String name) {
        try {
            return context.getResources().getIdentifier(name, "id", context.getPackageName());
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressLint("DiscouragedApi")
    public static int getFontResIdByName(Context context, String name) {
        try {
            return context.getResources().getIdentifier(name, "font", context.getPackageName());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据Id，获取资源名称
     */
    public static String getResourceNameById(Context context, int id) {
        try {
            return context.getResources().getResourceName(id);
        } catch (Exception e) {
            return "";
        }
    }


}
