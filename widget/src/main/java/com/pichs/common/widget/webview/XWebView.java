package com.pichs.common.widget.webview;

import static android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK;
import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

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
            settings.setAllowContentAccess(false);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setDatabaseEnabled(true);
            settings.setAppCacheEnabled(true);
            settings.setBlockNetworkImage(true);
            settings.setBlockNetworkLoads(true);
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setCacheMode(LOAD_CACHE_ELSE_NETWORK);
            settings.setAppCachePath(getContext().getCacheDir().getAbsolutePath() + File.separator + "webview");
            settings.setSupportMultipleWindows(false);
            settings.setDefaultTextEncodingName("utf-8");
            settings.setSupportZoom(true);
            int screenDensity = getResources().getDisplayMetrics().densityDpi;
            WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
            switch (screenDensity) {
                case DisplayMetrics.DENSITY_LOW:
                    zoomDensity = WebSettings.ZoomDensity.CLOSE;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    zoomDensity = WebSettings.ZoomDensity.FAR;
                    break;
            }
            settings.setUseWideViewPort(true);
            settings.setDefaultZoom(zoomDensity);
            settings.setDisplayZoomControls(false);
            settings.setBuiltInZoomControls(true);
            //自适应屏幕
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            }
            setHorizontalScrollBarEnabled(false);//去掉webview的滚动条,水平不显示
            setScrollbarFadingEnabled(true);
            setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        // 默认简单过滤一下
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                    // 防止其他格式的file://2/wew/we/f/sd/ 格式乱入，可根据需求自行重写
                    if (url.startsWith("file") || url.startsWith("http") || url.startsWith("https") || url.startsWith("HTTP") || url.startsWith("HTTPS"))
                        view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });
    }
}
