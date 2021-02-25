package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.switcher.XSwitchButton;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XCardButton btn = findViewById(R.id.btn1);
        XSwitchButton swb = findViewById(R.id.swb);

        swb.setThumbColor(Color.BLACK, Color.BLACK,  Color.BLACK, Color.BLACK );
        swb.setBackgroundColor(Color.GREEN, Color.CYAN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
            }
        });
        XButton normalBtn = findViewById(R.id.normalBtn);

        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
//                XTypefaceHelper.clearObserver();

//                XTypefaceHelper.setGlobalTypeface(getApplicationContext(), XTypefaceHelper.TYPEFACE_BOLD);
                XTypefaceHelper.resetTypeface(MainActivity.this);
//                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
            }
        });

    }
}