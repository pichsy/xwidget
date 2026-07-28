package com.pichs.app.xwidget.ui.nestedscroll

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pichs.app.xwidget.R
import com.pichs.app.xwidget.base.BaseFragment
import com.pichs.app.xwidget.databinding.FragmentNestedScrollBinding
import com.pichs.app.xwidget.widget.NestedBottomView
import com.pichs.xwidget.nestedscroll.XNestedBottomAreaBehavior
import com.pichs.xwidget.nestedscroll.XNestedScrollLayout
import com.pichs.xwidget.nestedscroll.XNestedTopAreaBehavior
import com.pichs.xwidget.nestedscroll.XNestedTopDelegateLayout
import com.pichs.xwidget.nestedscroll.XNestedTopRecyclerView

class NestedScrollFragment : BaseFragment<FragmentNestedScrollBinding>() {
    override fun afterOnCreateView(rootView: View?) {

        val nestedScrollLayout: XNestedScrollLayout = binding.nestedScrollLayout


        val mTopDelegateLayout = XNestedTopDelegateLayout(requireContext())

        mTopDelegateLayout.setBackgroundColor(Color.GREEN)

        val topRecyclerView = XNestedTopRecyclerView(requireContext())


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
        topRecyclerView.setLayoutManager(object : LinearLayoutManager(requireContext()) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        })


        //        mTopDelegateLayout.setDelegateView(topRecyclerView);
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT


        val topLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        topLp.setBehavior(XNestedTopAreaBehavior(requireContext()))
        nestedScrollLayout.setTopAreaView(topRecyclerView, topLp)

        val bottomView = NestedBottomView(requireContext())
        val bottomLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        bottomLp.setBehavior(XNestedBottomAreaBehavior())
        nestedScrollLayout.setBottomAreaView(bottomView, bottomLp)

        val mTopAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder?> = object : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view: View = LayoutInflater.from(requireContext()).inflate(R.layout.item_layout, parent, false)
                return object : RecyclerView.ViewHolder(view) {
                }
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            }

            override fun getItemCount(): Int {
                return 20
            }
        }

        topRecyclerView.setAdapter(mTopAdapter)

    }
}