package com.example.listenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Activity.Music_Activity;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import java.util.ArrayList;

public class danhsachbaihatAdapter extends RecyclerView.Adapter<danhsachbaihatAdapter.ViewHolder>{
    Context context;
    ArrayList<Song> mangSong;
    private User user;

    public danhsachbaihatAdapter(Context context, ArrayList<Song> mangSong,User user) {
        this.context = context;
        this.mangSong = mangSong;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.dong_danh_sach_verblack,parent,false);
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
                    .into(holder.imghinhbaihat);
        } else {
            // Nếu URL null hoặc rỗng, sử dụng ảnh mặc định
            holder.imghinhbaihat.setImageResource(R.drawable.image2);
        }

    }

    @Override
    public int getItemCount() {
        return mangSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat,txtcasi;
        ImageView imghinhbaihat;
        public ViewHolder(View itemView){
            super(itemView);
            txtcasi = itemView.findViewById(R.id.tv_tentacgia);
            txttenbaihat = itemView.findViewById(R.id.tv_tenbai);
            imghinhbaihat = itemView.findViewById(R.id.img_hinh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo một Bundle để đóng gói dữ liệu
                    Bundle bundle = new Bundle();

                    // Truyền bài hát được chọn
                    bundle.putParcelable("cakhuc", mangSong.get(getAdapterPosition()));
                    bundle.putSerializable("user",user);
                    // Truyền danh sách bài hát
                    bundle.putParcelableArrayList("cacbaihat", mangSong);

                    // Tạo Intent và đính kèm Bundle
                    Intent intent = new Intent(context, Music_Activity.class);
                    intent.putExtras(bundle);

                    // Chuyển sang Music_Activity
                    context.startActivity(intent);
                }
            });
        }
    }
}
