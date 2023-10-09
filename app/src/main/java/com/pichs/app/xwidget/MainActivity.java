package com.pichs.app.xwidget;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.cardview.XCardConstraintLayout;
import com.pichs.common.widget.utils.XBackgroundHelper;
import com.pichs.common.widget.utils.XColorHelper;
import com.pichs.common.widget.utils.XDrawableHelper;
import com.pichs.common.widget.utils.XStatusBarHelper;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.OnPressedStateListener;
import com.pichs.common.widget.view.XButton;
import com.pichs.common.widget.view.XTextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XStatusBarHelper.translucent(this);
        XStatusBarHelper.setStatusBarLightMode(this);
        setContentView(R.layout.activity_main);
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
            XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "SmileySans.ttf");
            XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
        });

        XButton closeFont = findViewById(R.id.closeFont);
        XButton openFont = findViewById(R.id.openFont);

        closeFont.setOnClickListener(v -> XTypefaceHelper.closeTypeface(this));
        openFont.setOnClickListener(v -> XTypefaceHelper.openTypeface(this));
    }

}