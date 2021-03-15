package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.checkbox.XCheckBox;
import com.pichs.common.widget.roundview.XRoundTextView;
import com.pichs.common.widget.switcher.XSwitchButton;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.utils.XStatusBarHelper;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;

public class MainActivity extends AppCompatActivity {
    XRoundTextView xrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XStatusBarHelper.translucent(this);
        XStatusBarHelper.setStatusBarDarkMode(this);
        setContentView(R.layout.activity_main);
        xrv = findViewById(R.id.tv_round);


//        XCardButton btn = findViewById(R.id.btn1);
//        XCheckBox checkbox = findViewById(R.id.checkbox);
//        checkbox.setOnCheckedChangeListener(new XCheckBox.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(boolean isChecked) {
//                Toast.makeText(getApplicationContext(), "ischecked:" + isChecked, Toast.LENGTH_LONG).show();
//            }
//        });


//        XSwitchButton swb = findViewById(R.id.swb);
//        swb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    checkbox.setImageDrawable(new ColorDrawable(Color.BLACK));
//                    checkbox.setCheckedDrawable(new ColorDrawable(Color.RED));
//
//                } else {
//                    checkbox.setImageDrawable(new ColorDrawable(Color.GREEN));
//                    checkbox.setCheckedDrawable(new ColorDrawable(Color.BLUE));
//
//                }
//
//            }
//        });
//        swb.setThumbColor(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
//        swb.setBackgroundColor(Color.GREEN, Color.CYAN);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
//                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
//            }
//        });
//        XButton normalBtn = findViewById(R.id.normalBtn);

//        normalBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
////                XTypefaceHelper.clearObserver();
//
////                XTypefaceHelper.setGlobalTypeface(getApplicationContext(), XTypefaceHelper.TYPEFACE_BOLD);
//                XTypefaceHelper.resetTypeface(MainActivity.this);
////                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
//            }
//        });

    }

    public void changeRadius(View view) {
        xrv.setRadius(XDisplayHelper.dp2px(this, 38));
//        xrv.setBorderWidth(XDisplayHelper.dp2px(this, 3));
//        xrv.setBorderColor(Color.RED);
//        xrv.setPressedBorderColor(Color.BLACK);
    }

    public void changeRadiusBg(View view) {
        xrv.setPressedBackground(new ColorDrawable(Color.BLUE));
        xrv.setNormalBackground(new ColorDrawable(Color.YELLOW));
    }
}