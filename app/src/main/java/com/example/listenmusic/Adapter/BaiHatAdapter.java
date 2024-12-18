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

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.BaiHatViewHolder> {

    private List<BaiHatCon> baiHatList;

    public BaiHatAdapter(List<BaiHatCon> baiHatList) {
        this.baiHatList = baiHatList;
    }

    @NonNull
    @Override
    public BaiHatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_con_item, parent, false);
        return new BaiHatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiHatViewHolder holder, int position) {
        BaiHatCon baiHat = baiHatList.get(position);
        holder.tenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tenNgheSi.setText(baiHat.getTenNgheSi());
        Picasso.get().load(baiHat.getHinhBaiHat()).into(holder.hinhBaiHat);
    }

    @Override
    public int getItemCount() {
        return baiHatList.size();
    }

    public static class BaiHatViewHolder extends RecyclerView.ViewHolder {
        TextView tenBaiHat;
        ImageView hinhBaiHat;
        TextView tenNgheSi;
        public BaiHatViewHolder(View itemView) {
            super(itemView);
            tenBaiHat = itemView.findViewById(R.id.songTitle);
            hinhBaiHat = itemView.findViewById(R.id.songImage);
            tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
        }
    }
}
