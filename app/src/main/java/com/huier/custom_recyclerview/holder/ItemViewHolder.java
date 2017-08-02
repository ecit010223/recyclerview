package com.huier.custom_recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huier.custom_recyclerview.R;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 * 自定义的ViewHolder，持有每个Item的的所有界面元素
 */
public class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView item_tv;

    public ItemViewHolder(View view){
        super(view);
        item_tv = (TextView) view.findViewById(R.id.item_tv);
    }
}
