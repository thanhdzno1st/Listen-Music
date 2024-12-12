package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHatCon;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<BaiHatCon> baiHatList;

    public SearchAdapter(List<BaiHatCon> baiHatList) {
        this.baiHatList = baiHatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_con_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHatCon baiHat = baiHatList.get(position);
        holder.tv_tenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tv_tenNgheSi.setText(baiHat.getTenNgheSi());

        Picasso.get()
                .load(baiHat.getHinhBaiHat())
                .into(holder.img_baiHat);
    }

    @Override
    public int getItemCount() {
        return baiHatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tv_tenBaiHat, tv_tenNgheSi;
        ImageView img_baiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenBaiHat = itemView.findViewById(R.id.songTitle);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
            img_baiHat = itemView.findViewById(R.id.songImage);
        }
    }
}
