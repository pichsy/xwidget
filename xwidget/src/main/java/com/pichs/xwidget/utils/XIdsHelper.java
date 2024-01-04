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


}
