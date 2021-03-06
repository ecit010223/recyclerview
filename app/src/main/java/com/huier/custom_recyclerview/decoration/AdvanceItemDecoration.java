package com.huier.custom_recyclerview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：张玉辉
 * 时间：2017/8/2.
 */

public class AdvanceItemDecoration extends RecyclerView.ItemDecoration {
    //采用系统内置的风格的分割线
    private static final int[] attrs=new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int mOrientation;

    public AdvanceItemDecoration(Context context, int orientation){
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHDecoration(c,parent);
//        drawVDecoration(c,parent);
    }

    /** 划水平线 **/
    private void drawHDecoration(Canvas c, RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for(int i=0; i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams  layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    /** 划垂直线 **/
    private void drawVDecoration(Canvas c, RecyclerView parent){
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount= parent.getChildCount();
        for(int i = 0; i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(OrientationHelper.HORIZONTAL == mOrientation){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }else{
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }
    }
}
