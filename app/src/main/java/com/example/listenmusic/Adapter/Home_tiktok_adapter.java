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

public class Home_tiktok_adapter extends RecyclerView.Adapter<Home_tiktok_adapter.ViewHolder>{
    Context context;
    ArrayList<Song> mangSong;

    public Home_tiktok_adapter(Context context, ArrayList<Song> mangSong) {
        this.context = context;
        this.mangSong = mangSong;
    }

    @NonNull
    @Override
    public Home_tiktok_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_tiktok,parent,false);
        return new Home_tiktok_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_tiktok_adapter.ViewHolder holder, int position) {
        Song song = mangSong.get(position);
        holder.txt_tenbaihat.setText(song.getTenBaiHat());
        holder.txt_tacgia.setText(song.getTenNgheSi());
        //holder.imageViewSong.setImageResource(R.drawable.img_1);
        if (song.getHinhBaiHat() != null && !song.getHinhBaiHat().isEmpty()) {
            Glide.with(context)
                    .load(song.getHinhBaiHat()) // URL ảnh từ API
                    .placeholder(R.drawable.image2) // Ảnh mặc định khi đang tải
                    .error(R.drawable.image2) // Ảnh hiển thị nếu URL không hợp lệ
                    .into(holder.imageViewSong);
        } else {
            // Nếu URL null hoặc rỗng, sử dụng ảnh mặc định
            holder.imageViewSong.setImageResource(R.drawable.image2);
        }
    }

    @Override
    public int getItemCount() {
        return mangSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewSong;
        TextView txt_tenbaihat,txt_tacgia;
        public ViewHolder(View itemview){
            super(itemview);
            imageViewSong = itemview.findViewById(R.id.image_tiktok);
            txt_tenbaihat = itemview.findViewById(R.id.tv_tenbaihat_tiktok);
            txt_tacgia = itemview.findViewById(R.id.tv_tennghesi_tiktok);
        }
    }
}
