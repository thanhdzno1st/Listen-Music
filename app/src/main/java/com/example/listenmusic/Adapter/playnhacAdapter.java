package com.example.listenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;

import java.util.ArrayList;

public class playnhacAdapter extends RecyclerView.Adapter<playnhacAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> mangSong;

    public playnhacAdapter(Context context, ArrayList<Song> mangSong) {
        this.context = context;
        this.mangSong = mangSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mangSong.get(position);
        holder.txtcasi.setText(song.getTenNgheSi());
        holder.txttenbaihat.setText(song.getTenBaiHat());
        //holder.imageViewSong.setImageResource(R.drawable.img_1);
        if (song.getHinhBaiHat() != null && !song.getHinhBaiHat().isEmpty()) {
            Glide.with(context)
                    .load(song.getHinhBaiHat()) // URL ảnh từ API
                    .placeholder(R.drawable.image2) // Ảnh mặc định khi đang tải
                    .error(R.drawable.image2) // Ảnh hiển thị nếu URL không hợp lệ
                    .into(holder.imgview_song);
        } else {
            // Nếu URL null hoặc rỗng, sử dụng ảnh mặc định
            holder.imgview_song.setImageResource(R.drawable.image2);
        }
    }

    @Override
    public int getItemCount() {
        return mangSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat,txtcasi;
        ImageView imgview_song;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcasi = itemView.findViewById(R.id.tv_tentacgia);
            txttenbaihat = itemView.findViewById(R.id.tv_tenbai);
            imgview_song = itemView.findViewById(R.id.img_hinh);

        }
    }
}
