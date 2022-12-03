package com.pichs.app.xwidget;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.checkbox.XCheckBox;
import com.pichs.common.widget.roundview.XRoundTextView;
import com.pichs.common.widget.switcher.XSwitchButton;
import com.pichs.common.widget.utils.XColorHelper;
import com.pichs.common.widget.utils.XColorUtils;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.utils.XGradientHelper;
import com.pichs.common.widget.utils.XStatusBarHelper;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;
import com.pichs.common.widget.view.XLinearLayout;

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
        tripleColor();

    }

    private void tripleColor() {
//        Drawable drawable = XGradientHelper.getGradientDrawable(20, GradientDrawable.Orientation.LEFT_RIGHT, new int[]{
//                Color.RED, Color.BLUE, Color.GREEN,Color.BLACK,Color.RED,Color.GREEN
//        });
        TextView tv = findViewById(R.id.triple_color);
        XLinearLayout ll = findViewById(R.id.ll_colors);
        ll.setBackgroundGradientColors(new int[]{XColorHelper.parseColor("#fff"), XColorHelper.parseColor("#000"), Color.parseColor("purple")}, GradientOrientation.HORIZONTAL);
        ll.setPressedBackgroundGradientColors(new int[]{XColorHelper.parseColor("#f00"), XColorHelper.parseColor("#0f0")}, GradientOrientation.HORIZONTAL);
        tv.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "三色", Toast.LENGTH_SHORT).show());
//        tv.setBackground(drawable);
    }

    int color = -0xFFFFFF1;

    private void changeTypeface() {
        XCardButton btn = findViewById(R.id.btn1);

        XButton normalBtn = findViewById(R.id.normalBtn);
        btn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "color:" + color, Toast.LENGTH_SHORT).show();
            XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
            XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
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