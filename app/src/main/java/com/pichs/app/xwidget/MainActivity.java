package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv1);
        XCardButton btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.BOLD_ITALIC);
            }
        });


        tv.setTextColor(Color.BLACK);

        tv.setTextSize(20);

        Typeface tf = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC);

        tf = Typeface.createFromAsset(getAssets(), "leihong.ttf");


//        tf = Typeface.SERIF;

//        tf = Typeface.SANS_SERIF;


        tf = Typeface.create(tf, Typeface.BOLD_ITALIC);


        tv.setTypeface(tf);


    }
}