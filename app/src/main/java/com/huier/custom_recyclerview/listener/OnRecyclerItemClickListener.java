package com.huier.custom_recyclerview.listener;

import android.view.View;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 * 自定义RecyclerView 中item view点击回调方法
 */

public interface OnRecyclerItemClickListener {
    /** item view 回调方法 **/
    void onItemClick(View view, int position);
}
