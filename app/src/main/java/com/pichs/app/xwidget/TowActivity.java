package com.pichs.app.xwidget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pichs.common.widget.nestedscroll.XNestedBottomAreaBehavior;
import com.pichs.common.widget.nestedscroll.XNestedScrollLayout;
import com.pichs.common.widget.nestedscroll.XNestedTopAreaBehavior;
import com.pichs.common.widget.nestedscroll.XNestedTopDelegateLayout;
import com.pichs.common.widget.nestedscroll.XNestedTopRecyclerView;

public class TowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        XNestedScrollLayout nestedScrollLayout = findViewById(R.id.nested_scroll_layout);


        XNestedTopDelegateLayout mTopDelegateLayout = new XNestedTopDelegateLayout(this);

        mTopDelegateLayout.setBackgroundColor(Color.GREEN);

        XNestedTopRecyclerView topRecyclerView = new XNestedTopRecyclerView(this);
//        AppCompatTextView headerView = new AppCompatTextView(this) {
//            @Override
//            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
//                        XDisplayHelper.dp2px(getContext(), 100), MeasureSpec.EXACTLY
//                ));
//            }
//        };
//        headerView.setTextSize(17);
//        headerView.setBackgroundColor(Color.BLUE);
//        headerView.setTextColor(Color.WHITE);
//        headerView.setText("Top Header这是RecyclerView的header，但是不是直接添加的");
//        headerView.setGravity(Gravity.CENTER);
//        mTopDelegateLayout.setHeaderView(headerView);

//        AppCompatTextView footerView = new AppCompatTextView(this) {
//            @Override
//            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
//                        XDisplayHelper.dp2px(getContext(), 100), MeasureSpec.EXACTLY
//                ));
//            }
//        };
//        footerView.setTextSize(17);
//        footerView.setBackgroundColor(Color.GRAY);
//        footerView.setTextColor(Color.WHITE);
//        footerView.setGravity(Gravity.CENTER);
//        footerView.setText("Top Footer这是RecyclerView的Foote，但是不是直接添加的");
//        mTopDelegateLayout.setFooterView(footerView);

        topRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

//        mTopDelegateLayout.setDelegateView(topRecyclerView);


        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        CoordinatorLayout.LayoutParams topLp = new CoordinatorLayout.LayoutParams(matchParent, matchParent);
        topLp.setBehavior(new XNestedTopAreaBehavior(this));
        nestedScrollLayout.setTopAreaView(topRecyclerView, topLp);

//        QMUIContinuousNestedBottomRecyclerView bottomRecyclerView = new QMUIContinuousNestedBottomRecyclerView(this);
//        CoordinatorLayout.LayoutParams bottomLp = new CoordinatorLayout.LayoutParams(matchParent, matchParent);
//        nestedScrollLayout.setBottomAreaView(bottomRecyclerView,bottomLp);
//
//        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//            }
//        });

        NestedBottomView bottomView = new NestedBottomView(this);
        CoordinatorLayout.LayoutParams bottomLp = new CoordinatorLayout.LayoutParams(matchParent, matchParent);
        bottomLp.setBehavior(new XNestedBottomAreaBehavior());
        nestedScrollLayout.setBottomAreaView(bottomView,bottomLp);

        RecyclerView.Adapter<RecyclerView.ViewHolder> mTopAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(TowActivity.this).inflate(R.layout.item_layout, parent, false);
                return new RecyclerView.ViewHolder(view) {

                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 20;
            }
        };

        topRecyclerView.setAdapter(mTopAdapter);


    }
}
