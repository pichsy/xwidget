package com.pichs.app.xwidget;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.cardview.XCardConstraintLayout;
import com.pichs.common.widget.checkbox.XCheckBox;
import com.pichs.common.widget.roundview.XRoundTextView;
import com.pichs.common.widget.utils.XColorHelper;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.utils.XGradientHelper;
import com.pichs.common.widget.utils.XStatusBarHelper;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;
import com.pichs.common.widget.view.XLinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XStatusBarHelper.translucent(this);
        XStatusBarHelper.setStatusBarDarkMode(this);
        setContentView(R.layout.activity_main);
        changeTypeface();
        tripleColor();

        initColorfulBorder();

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
        findViewById(R.id.web_view).setOnClickListener(v->{
            startActivity(new Intent(this,WebViewActivity.class));
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