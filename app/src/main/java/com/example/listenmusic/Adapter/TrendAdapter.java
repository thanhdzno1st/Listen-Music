package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Models.BaiHat;
import com.example.listenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder> {

    private List<BaiHat.Trend> trendList;

    public TrendAdapter(List<BaiHat.Trend> trendList) {
        this.trendList = trendList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat.Trend trend = trendList.get(position);
        holder.tv_stt.setText(trend.getStt());
        holder.tv_tenBaiHat.setText(trend.getTenBaiHat());
        holder.tv_tenNgheSi.setText(trend.getTenNgheSi());

        Picasso.get()
                .load(trend.getHinhAnh())
                .into(holder.img_baiHat);
    }

    @Override
    public int getItemCount() {
        return trendList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stt, tv_tenBaiHat, tv_tenNgheSi;
        ImageView img_baiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stt = itemView.findViewById(R.id.tv_stt);
            tv_tenBaiHat = itemView.findViewById(R.id.tv_tenBai);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
            img_baiHat = itemView.findViewById(R.id.img_bhHinh);
        }
    }
}
