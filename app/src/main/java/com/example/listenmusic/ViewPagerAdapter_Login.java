package com.example.listenmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter_Login extends PagerAdapter {

    private List<Integer> layouts; // Danh sách các layout
    private LayoutInflater inflater;

    public ViewPagerAdapter_Login(Context context, List<Integer> layouts) {
        this.layouts = layouts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return layouts.size(); // Trả về số lượng trang
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object; // Kiểm tra xem View có phải Object không
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate layout cho từng trang
        View view = inflater.inflate(layouts.get(position), container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Xóa trang khi không cần thiết
        container.removeView((View) object);
    }
}
