package com.huier.custom_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huier.custom_recyclerview.adapter.CustomRecyclerAdapter;
import com.huier.custom_recyclerview.decoration.SimpleItemDecoration;
import com.huier.custom_recyclerview.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class SwipeRefreshActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout linearTopbarBack;
    /** 添加数据按钮 **/
    private Button btnAddData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter mAdapter;
    /** 线性布局 **/
    private LinearLayoutManager mLinearLayoutManager;
    /** 最后可见的列表项 **/
    private int lastVisibleItem;

    public static void entry(Context from){
        Intent intent = new Intent(from,SwipeRefreshActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        mContext = this;

        initTopbar();
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    /** 头部控件初始化 **/
    private void initTopbar(){
        linearTopbarBack = (LinearLayout)findViewById(R.id.linear_top_bar_back);
        linearTopbarBack.setOnClickListener(this);
        //添加数据按钮
        btnAddData = (Button)findViewById(R.id.btn_add_data);
        btnAddData.setOnClickListener(this);
    }

    /** RecyclerView处理 **/
    private void initRecyclerView(){
        //开始设置RecyclerView
        mRecyclerView =(RecyclerView)this.findViewById(R.id.recyclerview_refresh);
        //设置固定大小
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局
        mLinearLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置分割线
        mRecyclerView.addItemDecoration(new SimpleItemDecoration(mContext));
        //创建带有各选项的点击回调的适配器
        mAdapter = new CustomRecyclerAdapter(mContext, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext,((TextView)view).getText().toString()+":"+position,Toast.LENGTH_SHORT).show();
                mAdapter.removeItem(position);
            }
        });
        //RecyclerView滑动监听
        mRecyclerView.setOnScrollListener(new OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mAdapter.changeMoreStatus(CustomRecyclerAdapter.LOADING_MORE);
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> newDatas = new ArrayList<String>();
                            for (int i = 0; i < 5; i++) {
                                int index = i + 1;
                                newDatas.add("上拉" + index);
                            }
                            mAdapter.addDataUp(newDatas);
                            mAdapter.changeMoreStatus(CustomRecyclerAdapter.PULLUP_LOAD_MORE);
                        }
                    },3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        //添加默认的动画效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    /** SwipeRefreshLayout处理 **/
    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        //设置刷新时的动画颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //添加下拉刷新监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //在该方法中进行网络请求最新数据，然后刷新RecyclerView列表同时设置SwipeRefreshLayout的进度Bar的隐藏或者显示效果
            @Override
            public void onRefresh() {
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> newDatas = new ArrayList<String>();
                        for (int i = 0; i < 5; i++) {
                            int index = i + 1;
                            newDatas.add("下拉" + index);
                        }
                        mAdapter.addDataDown(newDatas);
                        //隐藏刷新进度条
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(mContext, "更新了五条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_data:
                mAdapter.additem("新数据",0);
                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.linear_top_bar_back:
                finish();
                break;
        }
    }
}
