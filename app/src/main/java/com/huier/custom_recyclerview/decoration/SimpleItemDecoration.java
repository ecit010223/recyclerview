package com.huier.custom_recyclerview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 * 画横分割线
 */

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    //采用系统内置的风格的分割线
    private static final int[] attrs=new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public SimpleItemDecoration(Context context){
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    /** 进行自定义绘制 **/
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for(int i=0; i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams  layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            //getIntrinsicWidth()取得Drawable的固有的宽度，单位为dp
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设置绘制整个区域范围
        outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
    }
}
