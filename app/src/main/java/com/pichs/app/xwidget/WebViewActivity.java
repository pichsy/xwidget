package com.pichs.app.xwidget;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pichs.common.widget.webview.XWebView;

public class WebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        XWebView webView = findViewById(R.id.web_view);

        webView.loadUrl("https://baike.baidu.com/item/%E6%95%B0%E7%8B%AC/74847?fromModule=lemma_search-box");

    }
}
