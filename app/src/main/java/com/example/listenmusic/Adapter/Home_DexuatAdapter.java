package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHat;
import com.example.listenmusic.model.Trend;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Home_DexuatAdapter extends RecyclerView.Adapter<Home_DexuatAdapter.ViewHolder> {

    private List<BaiHat> baiHatList;

    public Home_DexuatAdapter(List<BaiHat> baiHatList) {
        this.baiHatList = baiHatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_dexuat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = baiHatList.get(position);
        holder.tv_tenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tv_tenNgheSi.setText(baiHat.getTenNgheSi());

        Picasso.get()
                .load(baiHat.getHinhAnh())
                .into(holder.img_baiHat);
    }

    @Override
    public int getItemCount() {
        return baiHatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenBaiHat, tv_tenNgheSi;
        ImageView img_baiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenBaiHat = itemView.findViewById(R.id.tv_song_title);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_artist_name);
            img_baiHat = itemView.findViewById(R.id.img_song);
        }
    }
}
