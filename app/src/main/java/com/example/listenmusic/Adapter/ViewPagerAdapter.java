package com.example.listenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<Integer> layouts;  // Danh sách các layout
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<Integer> layouts) {
        this.layouts = layouts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng trang
        View view = inflater.inflate(layouts.get(viewType), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bạn có thể thêm các hành động khác nếu cần
    }

    @Override
    public int getItemCount() {
        return layouts.size();  // Trả về số lượng trang
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
