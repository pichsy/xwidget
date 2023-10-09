package com.pichs.xwidget.webview;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class XWebView extends WebView {
    public XWebView(@NonNull Context context) {
        this(context, null);
    }

    public XWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDefault() {
        WebSettings settings = getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
//            settings.setSupportMultipleWindows(false);
//            settings.setDefaultTextEncodingName("utf-8");
//            settings.setSupportZoom(true);
//            int screenDensity = getResources().getDisplayMetrics().densityDpi;
//            WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//            switch (screenDensity) {
//                case DisplayMetrics.DENSITY_LOW:
//                    zoomDensity = WebSettings.ZoomDensity.CLOSE;
//                    break;
//                case DisplayMetrics.DENSITY_MEDIUM:
//                    zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//                    break;
//                case DisplayMetrics.DENSITY_HIGH:
//                    zoomDensity = WebSettings.ZoomDensity.FAR;
//                    break;
//            }
//            settings.setUseWideViewPort(true);
//            settings.setDefaultZoom(zoomDensity);
//            settings.setDisplayZoomControls(false);
//            settings.setBuiltInZoomControls(true);
            //自适应屏幕
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

    }
}
