package com.pichs.app.xwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pichs.xwidget.nestedscroll.XNestedBottomDelegateLayout;
import com.pichs.xwidget.nestedscroll.XNestedBottomRecyclerView;
import com.pichs.xwidget.utils.XDisplayHelper;

public class NestedBottomView extends XNestedBottomDelegateLayout {
    public NestedBottomView(Context context) {
        super(context);
    }

    public NestedBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    protected View onCreateHeaderView() {
        View view = inflate(getContext(),R.layout.item_layout_sticky,null);
        return view;
    }


    @Override
    protected int getHeaderHeightLayoutParam() {
        return XDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    protected int getHeaderStickyHeight() {
        return XDisplayHelper.dp2px(getContext(), 50);
    }


    @NonNull
    @Override
    protected View onCreateContentView() {

        XNestedBottomRecyclerView bottomRecyclerView = new XNestedBottomRecyclerView(getContext());

        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        RecyclerView.Adapter<RecyclerView.ViewHolder> mBottomAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 20;
            }
        };

        bottomRecyclerView.setAdapter(mBottomAdapter);
        return bottomRecyclerView;
    }
}
