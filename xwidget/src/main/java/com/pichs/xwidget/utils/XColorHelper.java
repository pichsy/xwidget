

package com.pichs.xwidget.utils;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.Size;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * 颜色帮助类
 */
public class XColorHelper {

    public static int setColorAlpha(@ColorInt int color, float alpha) {
        return setColorAlpha(color, alpha, true);
    }

    /**
     * 设置颜色的alpha值
     *
     * @param color    需要被设置的颜色值
     * @param alpha    取值为[0,1]，0表示全透明，1表示不透明
     * @param override 覆盖原本的 alpha
     * @return 返回改变了 alpha 值的颜色值
     */
    public static int setColorAlpha(@ColorInt int color, float alpha, boolean override) {
        int origin = override ? 0xff : (color >> 24) & 0xff;
        return color & 0x00ffffff | (int) (alpha * origin) << 24;
    }

    /**
     * 根据比例，在两个color值之间计算出一个color值
     * <b>注意该方法是ARGB通道分开计算比例的</b>
     *
     * @param fromColor 开始的color值
     * @param toColor   最终的color值
     * @param fraction  比例，取值为[0,1]，为0时返回 fromColor， 为1时返回 toColor
     * @return 计算出的color值
     */
    public static int computeColor(@ColorInt int fromColor, @ColorInt int toColor, float fraction) {
        fraction = XLangHelper.constrain(fraction, 0f, 1f);

        int minColorA = Color.alpha(fromColor);
        int maxColorA = Color.alpha(toColor);
        int resultA = (int) ((maxColorA - minColorA) * fraction) + minColorA;

        int minColorR = Color.red(fromColor);
        int maxColorR = Color.red(toColor);
        int resultR = (int) ((maxColorR - minColorR) * fraction) + minColorR;

        int minColorG = Color.green(fromColor);
        int maxColorG = Color.green(toColor);
        int resultG = (int) ((maxColorG - minColorG) * fraction) + minColorG;

        int minColorB = Color.blue(fromColor);
        int maxColorB = Color.blue(toColor);
        int resultB = (int) ((maxColorB - minColorB) * fraction) + minColorB;

        return Color.argb(resultA, resultR, resultG, resultB);
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色值
     * @return 转换后的字符串
     */
    public static String colorToString(@ColorInt int color) {
        return String.format("#%08X", color);
    }

    /**
     * 颜色解析：
     * 支持：
     * #RGB
     * #ARGB
     * #RRGGBB
     * #AARRGGBB
     *
     * @param colorString 颜色String
     * @return ColorInt
     * @throws RuntimeException 异常报错
     */
    @ColorInt
    public static int parseColor(@Size(min = 1) String colorString) throws RuntimeException {
        if (colorString == null) {
            throw new RuntimeException("您设置的颜色值：为 null，请用#开头，如#rgb,#argb,#rrggbb,#aarrggbb");
        }
        if (colorString.trim().isEmpty()) {
            throw new RuntimeException("您设置的颜色值：为空字符串，请用#开头，如#rgb,#argb,#rrggbb,#aarrggbb");
        }
        if (!isColorStringFormat(colorString)) {
            throw new RuntimeException("您设置的颜色值：" + colorString + ": 颜色值格式不正确，请用#开头，如#rgb,#argb,#rrggbb,#aarrggbb");
        }
        String colorFinal = colorString;
        if (colorString.length() == 4) {
            colorFinal = "#" + colorString.charAt(1) + colorString.charAt(1) + colorString.charAt(2) + colorString.charAt(2) + colorString.charAt(3) + colorString.charAt(3);
        } else if (colorString.length() == 5) {
            colorFinal = "#" + colorString.charAt(1) + colorString.charAt(1) + colorString.charAt(2) + colorString.charAt(2) + colorString.charAt(3) + colorString.charAt(3) + colorString.charAt(4) + colorString.charAt(4);
        }
        try {
            return Color.parseColor(colorFinal);
        } catch (Exception e) {
            throw new RuntimeException("最终装换的颜色值：" + colorFinal + " 颜色值格式不正确，请用#开头，如#rgb,#argb,#rrggbb,#aarrggbb\n ex:" + e);
        }
    }

    private final static Pattern mColorPattern = Pattern.compile("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");


    /**
     * 判断是否是颜色值
     * 请用#开头，如#rgb,#argb,#rrggbb,#aarrggbb
     *
     * @param colorString 颜色值
     * @return true 是颜色值
     */
    public static boolean isColorStringFormat(String colorString) {
        if (colorString == null || colorString.trim().isEmpty()) {
            return false;
        }
        return mColorPattern.matcher(colorString.trim()).matches();
    }



    public static int[] toIntArray(ArrayList<Integer> list) {
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}
