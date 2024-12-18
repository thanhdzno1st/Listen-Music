package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.R;
import com.example.listenmusic.model.NgheSi;

import java.util.List;

public class Follow_GoiYAdapter extends RecyclerView.Adapter<Follow_GoiYAdapter.ViewHolder> {

    private List<NgheSi> ngheSiList;

    public Follow_GoiYAdapter(List<NgheSi> ngheSiList) {
        this.ngheSiList = ngheSiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goiy_follow_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NgheSi ngheSi = ngheSiList.get(position);
        holder.tv_tenNgheSi.setText(ngheSi.getTenNgheSi());

        String avatarUrl = ngheSi.getAvartar();

        // Kiểm tra nếu URL không rỗng hoặc null
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Glide.with(holder.img_avartar.getContext())
                    .load(avatarUrl)
                    .into(holder.img_avartar);
        } else {
            // Nếu URL rỗng hoặc null, tải hình ảnh mặc định
            Glide.with(holder.img_avartar.getContext())
                    .load(R.drawable.downloadicon) // Hình ảnh mặc định
                    .into(holder.img_avartar);
        }
    }

    @Override
    public int getItemCount() {
        return ngheSiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenNgheSi;
        ImageView img_avartar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
            img_avartar = itemView.findViewById(R.id.img_avatar);
        }
    }
}
