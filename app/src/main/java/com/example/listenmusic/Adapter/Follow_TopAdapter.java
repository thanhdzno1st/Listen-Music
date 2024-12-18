package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.R;
import com.example.listenmusic.Models.NgheSi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Follow_TopAdapter extends RecyclerView.Adapter<Follow_TopAdapter.ViewHolder> {

    private List<NgheSi> ngheSiList;

    public Follow_TopAdapter(List<NgheSi> ngheSiList) {
        this.ngheSiList = ngheSiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_top_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NgheSi ngheSi = ngheSiList.get(position);
        Picasso.get()
                .load(ngheSi.getAvartar())
                .into(holder.img_avartar);
    }

    @Override
    public int getItemCount() {
        return ngheSiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avartar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avartar = itemView.findViewById(R.id.img_avatar);
        }
    }
}

