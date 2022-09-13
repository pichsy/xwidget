package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
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

import javax.crypto.Mac;

public class MainActivity extends AppCompatActivity {
    XRoundTextView xrv;
    XCheckBox xchekbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XStatusBarHelper.translucent(this);
        XStatusBarHelper.setStatusBarDarkMode(this);
        setContentView(R.layout.activity_main);
        xchekbox = findViewById(R.id.xchekbox);
        xrv = findViewById(R.id.tv_round);
        changeTypeface();
    }

    int color =-0xFFFFFF1;

    private void changeTypeface() {
        XCardButton btn = findViewById(R.id.btn1);

        XButton normalBtn = findViewById(R.id.normalBtn);
        btn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "color:" + color, Toast.LENGTH_SHORT).show();
//            XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
//            XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
            normalBtn.setBackgroundColor(color);
        });

        normalBtn.setOnClickListener(v -> XTypefaceHelper.resetTypeface(MainActivity.this));
        XButton closeFont = findViewById(R.id.closeFont);
        XButton openFont = findViewById(R.id.openFont);

        closeFont.setOnClickListener(v -> XTypefaceHelper.closeTypeface(this));
        openFont.setOnClickListener(v -> XTypefaceHelper.openTypeface(this));
    }

    public void changeRadius(View view) {
        xchekbox.setCanClick(!xchekbox.isCanClick());
        xrv.setRadius(XDisplayHelper.dp2px(this, 38));
    }

    public void changeRadiusBg(View view) {
        Toast.makeText(this, "变透明了吗", Toast.LENGTH_SHORT).show();
    }
}