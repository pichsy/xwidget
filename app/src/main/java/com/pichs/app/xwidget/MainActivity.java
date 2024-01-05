package com.pichs.app.xwidget;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pichs.app.xwidget.databinding.ActivityMainBinding;
import com.pichs.app.xwidget.ui.web.WebViewActivity;
import com.pichs.xwidget.cardview.GradientOrientation;
import com.pichs.xwidget.cardview.XCardButton;
import com.pichs.xwidget.cardview.XCardConstraintLayout;
import com.pichs.xwidget.checkbox.OnCheckedChangeListener;
import com.pichs.xwidget.utils.XStatusBarHelper;
import com.pichs.xwidget.utils.XTypefaceHelper;
import com.pichs.xwidget.view.XButton;
import com.pichs.xwidget.view.XTextView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XStatusBarHelper.translucent(this);
        XStatusBarHelper.setStatusBarLightMode(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeTypeface();
        tripleColor();
        initColorfulBorder();

//        String c1 = XColorHelper.colorToString(XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT);
//        int c2 = XColorHelper.parseColor("#0000000f");

        XTextView tv = (XTextView) findViewById(R.id.tv_pressed_scale);
//        XTextView tv2 = (XTextView) findViewById(R.id.tv_pressed_scale2);

//        tv.setText("0x0000000f=>(转string)" + c1);
//
//        tv2.setText("#0000000f=>(转int)" + c2+",(0x0000000f == 15)==>"+(0x0000000f == 15));

        tv.setOnPressedStateListener(isPressed -> {
            Toast.makeText(getApplicationContext(), "isPressed:" + isPressed, Toast.LENGTH_SHORT).show();
        });

        binding.cboxIgnore2.setIgnoreRadioGroup(false);

        binding.cboxIgnore.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "切换66：isChecked:" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initColorfulBorder() {
        XCardConstraintLayout card = findViewById(R.id.cl_colorful_border);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "变色", Toast.LENGTH_SHORT).show();
                card.setBorderGradientColors(Color.WHITE, Color.BLACK, GradientOrientation.VERTICAL);
            }
        });
    }

    private void tripleColor() {
        findViewById(R.id.web_view).setOnClickListener(v -> {
            startActivity(new Intent(this, WebViewActivity.class));
        });
    }

    int color = -0xFFFFFF1;

    private void changeTypeface() {
        XCardButton btn = findViewById(R.id.btn1);

        btn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "color:" + color, Toast.LENGTH_SHORT).show();
            XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "font/smileysans.ttf");
            XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
        });

        XButton closeFont = findViewById(R.id.closeFont);
        XButton openFont = findViewById(R.id.openFont);

        closeFont.setOnClickListener(v -> {
            XTypefaceHelper.closeTypeface(this);

            closeFont.setChecked(!closeFont.isChecked());

        });

        openFont.setOnClickListener(v -> XTypefaceHelper.openTypeface(this));
    }

}