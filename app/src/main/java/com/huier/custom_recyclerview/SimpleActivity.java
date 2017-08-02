package com.huier.custom_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huier.custom_recyclerview.adapter.CustomRecyclerAdapter;
import com.huier.custom_recyclerview.decoration.AdvanceItemDecoration;
import com.huier.custom_recyclerview.listener.OnRecyclerItemClickListener;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout linearTopbarBack;
    /** 添加数据按钮 **/
    private Button btnAddData;
    private RecyclerView recyclerViewSimple;
    private CustomRecyclerAdapter mAdapter;
    /** 线性布局 **/
    private LinearLayoutManager mLinearLayoutManager;
    /** 网络布局 **/
    private GridLayoutManager mGridLayoutManager;
    /** 瀑布布局 **/
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public static void entry(Context from){
        Intent intent = new Intent(from,SimpleActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mContext = this;
        initTopbar();
        initRecyclerView();
    }

    /** 头部控件 **/
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
        recyclerViewSimple =(RecyclerView)this.findViewById(R.id.recyclerview_simple);
        //设置固定大小
        recyclerViewSimple.setHasFixedSize(true);
        //创建线性布局
        mLinearLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //创建网格布局
        mGridLayoutManager = new GridLayoutManager(mContext,4);
        //创建瀑布布局
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
//        recyclerViewSimple.setLayoutManager(mLinearLayoutManager);
        recyclerViewSimple.setLayoutManager(mGridLayoutManager);
//        recyclerViewSimple.setLayoutManager(mStaggeredGridLayoutManager);
        //设置分割线
//        recyclerViewSimple.addItemDecoration(new SimpleItemDecoration(mContext));
        recyclerViewSimple.addItemDecoration(new AdvanceItemDecoration(mContext,OrientationHelper.VERTICAL));
        //创建适配器
//        mAdapter = new CustomRecyclerAdapter(mContext);
        //创建带有各选项的点击回调的适配器
        mAdapter = new CustomRecyclerAdapter(mContext, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext,((TextView)view).getText().toString()+":"+position,Toast.LENGTH_SHORT).show();
                mAdapter.removeItem(position);
            }
        });
        //添加默认的动画效果
        recyclerViewSimple.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSimple.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_data:
                mAdapter.additem("新数据",0);
                break;
            case R.id.linear_top_bar_back:
                finish();
                break;
        }
    }
}
