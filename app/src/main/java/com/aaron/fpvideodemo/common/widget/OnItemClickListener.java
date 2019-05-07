package com.aaron.fpvideodemo.common.widget;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 实现recycleview 条目点击事件
 * <p>
 * 定义一个OnItemClickListener继承自RecycleView.OnItemTouchListener
 * ,里面持有一个RecycleView和一个GestureDetectorCompat
 * Created by Aaron on 17/2/28.
 */

public abstract class OnItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnItemClickListener(RecyclerView view) {
        this.recyclerView = view;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
//                int position = recyclerView.indexOfChild(child);
                onItemClick(recyclerView.getChildViewHolder(child), recyclerView.getChildAdapterPosition(child));
            }
            return true;
//            return super.onSingleTapUp(e);
        }


//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            return super.onSingleTapUp(e);
//        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                int position = recyclerView.indexOfChild(child);
                OnItemClickListener.this.onLongPress(recyclerView.getChildViewHolder(child), position);
            }
        }

    }

    public abstract void onItemClick(RecyclerView.ViewHolder holder, int position);

    public void onLongPress(RecyclerView.ViewHolder holder, int position) {
    }
}
