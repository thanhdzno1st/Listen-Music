package com.example.listenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Activity.Music_Activity;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import java.util.ArrayList;
import java.util.List;

public class Home_recommend_adapter extends RecyclerView.Adapter<Home_recommend_adapter.ViewHolder>{
    Context context;
    ArrayList<Song> mangSong;
    private User user;

    public Home_recommend_adapter(Context context, ArrayList<Song> mangSong, User user) {
        this.context = context;
        this.mangSong = mangSong;
        this.user=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_nhac,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
//        Glide.with(context).load(song.getHinhBaiHat()).into(holder.imageViewSong);
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
            imageViewSong = itemview.findViewById(R.id.img_song);
            txt_tenbaihat = itemview.findViewById(R.id.tv_tenbai);
            txt_tacgia = itemview.findViewById(R.id.tv_tentacgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, Music_Activity.class);
                    bundle.putParcelable("cakhuc",mangSong.get(getPosition()));
                    bundle.putSerializable("user", user); // Transmit the user object
                    bundle.putParcelableArrayList("cacbaihat", mangSong);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }


}
