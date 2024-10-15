package com.example.listenmusic.fragment;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false; // Ngăn chặn sự kiện vuốt
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false; // Ngăn chặn sự kiện vuốt
    }
}

