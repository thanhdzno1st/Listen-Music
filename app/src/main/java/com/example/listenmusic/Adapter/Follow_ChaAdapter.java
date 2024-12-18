package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.R;
import com.example.listenmusic.Models.NgheSiCha;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Follow_ChaAdapter extends RecyclerView.Adapter<Follow_ChaAdapter.ViewHolder> {

    private List<NgheSiCha> ngheSiList;

    public Follow_ChaAdapter(List<NgheSiCha> ngheSiList) {
        this.ngheSiList = ngheSiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_cha_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NgheSiCha ngheSi = ngheSiList.get(position);
        holder.tv_tenNgheSi.setText(ngheSi.getTenNgheSi());
        Picasso.get().load(ngheSi.getAvartar()).into(holder.img_avartar);

        // Set adapter cho RecyclerView con hiển thị các bài hát
        BaiHatAdapter baiHatAdapter = new BaiHatAdapter(ngheSi.getBaihat());
        holder.recyclerViewBaiHat.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false)); // Hiển thị theo hướng dọc
        holder.recyclerViewBaiHat.setAdapter(baiHatAdapter);
    }

    @Override
    public int getItemCount() {
        return ngheSiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenNgheSi;
        ImageView img_avartar;
        RecyclerView recyclerViewBaiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
            img_avartar = itemView.findViewById(R.id.img_avartar);
            recyclerViewBaiHat = itemView.findViewById(R.id.recycler_con);

            // Cấu hình RecyclerView con cho bài hát theo hướng dọc
            recyclerViewBaiHat.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }
}
