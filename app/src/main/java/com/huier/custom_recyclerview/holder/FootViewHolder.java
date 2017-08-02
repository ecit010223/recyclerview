package com.huier.custom_recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huier.custom_recyclerview.R;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 */

public class FootViewHolder extends  RecyclerView.ViewHolder {
    public TextView tvFootView;
    public FootViewHolder(View view) {
        super(view);
        tvFootView=(TextView)view.findViewById(R.id.tv_foot_view);
    }
}
