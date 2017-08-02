package com.huier.custom_recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huier.custom_recyclerview.holder.FootViewHolder;
import com.huier.custom_recyclerview.listener.OnRecyclerItemClickListener;
import com.huier.custom_recyclerview.holder.ItemViewHolder;
import com.huier.custom_recyclerview.R;

import java.util.ArrayList;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
    /** 普通Item View **/
    private static final int TYPE_ITEM = 0;
    /** 顶部FootView **/
    private static final int TYPE_FOOTER = 1;

    private LayoutInflater mInflater;
    private ArrayList<String> mTitles = new ArrayList<String>();
    /** 点击单个View时的回调 **/
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public CustomRecyclerAdapter(Context context){
        this.mInflater=LayoutInflater.from(context);
        initData();
    }

    /** 带有item点击回调函数  **/
    public CustomRecyclerAdapter(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this(context);
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private void initData(){
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles.add("item"+index);
        }
    }

    /** item显示类型 **/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if(viewType==TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_recycler, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            if (mOnRecyclerItemClickListener != null) {
                viewHolder.item_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                    }
                });
            }
            return viewHolder;
        }else if(viewType == TYPE_FOOTER){
            View foot_view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder=new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    /** 数据的绑定显示 **/
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            itemViewHolder.item_tv.setText(mTitles.get(position));
            itemViewHolder.item_tv.setTag(position);
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.tvFootView.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.tvFootView.setText("正在加载更多数据...");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /** 添加单条数据 **/
    public void additem(String data,int position){
        mTitles.add(position,data);
        //刷新界面
        notifyItemInserted(position);
    }

    /** 删除单条数据 **/
    public void removeItem(int position){
        mTitles.remove(position);
        //刷新界面
        notifyItemRemoved(position);
    }

    /** 下拉添加数据 **/
    public void addDataDown(ArrayList<String> data){
        mTitles.addAll(0,data);
        //刷新界面
        notifyDataSetChanged();
    }

    /** 上拉添加数据 **/
    public void addDataUp(ArrayList<String> data){
        mTitles.addAll(data);
        //刷新界面
        notifyDataSetChanged();
    }

    /**
     * 上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * 正在加载中
     * LOADING_MORE=1;
     * 加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
    }
}
