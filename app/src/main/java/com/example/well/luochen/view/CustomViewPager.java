package com.example.well.luochen.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    PointF downPoint = new PointF();
    OnSingleTouchListener onSingleTouchListener;

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        switch (evt.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时候的坐标
                downPoint.x = evt.getX();
                downPoint.y = evt.getY();
                if (this.getCurrentItem() == 0 || this.getCurrentItem() == getAdapter().getCount() - 1) { //当时第一页或者是最后一页的时候让父控件拦截
                    // 通知其父控件，现在进行的是本控件的操作，允许拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    // 通知其父控件，现在进行的是本控件的操作，允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getCurrentItem() == 0 || this.getCurrentItem() == getAdapter().getCount() - 1) { //当时第一页或者是最后一页的时候让父控件拦截
                    // 通知其父控件，现在进行的是本控件的操作，允许拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    // 通知其父控件，现在进行的是本控件的操作，允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为一个点
                if (PointF.length(evt.getX() - downPoint.x, evt.getY()
                        - downPoint.y) < (float) 5.0) {
                    onSingleTouch(this);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(evt);
    }

    public void onSingleTouch(View v) {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch(v);
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch(View v);
    }

    public void setOnSingleTouchListener(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}