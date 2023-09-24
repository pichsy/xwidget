package com.pichs.common.widget.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;

import java.io.File;


/**
 * 资源帮助类
 */
public class XResHelper {

    private static TypedValue sTmpValue;

    public static float getAttrFloatValue(Context context, int attr) {
        return getAttrFloatValue(context.getTheme(), attr);
    }

    public static float getAttrFloatValue(Resources.Theme theme, int attr) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        theme.resolveAttribute(attr, sTmpValue, true);
        return sTmpValue.getFloat();
    }

    public static int getAttrDimen(Context context, int attrRes) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        context.getTheme().resolveAttribute(attrRes, sTmpValue, true);
        return TypedValue.complexToDimensionPixelSize(sTmpValue.data, XDisplayHelper.getDisplayMetrics(context));
    }

}
