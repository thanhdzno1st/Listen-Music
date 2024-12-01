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
import com.example.listenmusic.Models.Playlist;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;

import java.util.ArrayList;

public class Playlist_list_adapter extends RecyclerView.Adapter<Playlist_list_adapter.ViewHolder>{
    Context context;
    ArrayList<Playlist> mangPlaylist;

    public Playlist_list_adapter(Context context, ArrayList<Playlist> mangPlaylist) {
        this.context = context;
        this.mangPlaylist = mangPlaylist;
    }

    @NonNull
    @Override
    public Playlist_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_playlist,parent,false);
        return new Playlist_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Playlist_list_adapter.ViewHolder holder, int position) {
        Playlist playlist = mangPlaylist.get(position);
        holder.txt_tenplaylist.setText(playlist.getTenPlayList());
        holder.txt_tacgia.setText("Tạo bởi "+playlist.getHoTen());
        //holder.imageViewSong.setImageResource(R.drawable.img_1);
        if (playlist.getHinhPlayList() != null && !playlist.getHinhPlayList().isEmpty()) {
            Glide.with(context)
                    .load(playlist.getHinhPlayList()) // URL ảnh từ API
                    .placeholder(R.drawable.default_img) // Ảnh mặc định khi đang tải
                    .error(R.drawable.default_img) // Ảnh hiển thị nếu URL không hợp lệ
                    .into(holder.imageViewPlaylist);
        } else {
            // Nếu URL null hoặc rỗng, sử dụng ảnh mặc định
            holder.imageViewPlaylist.setImageResource(R.drawable.default_img);
        }
    }

    @Override
    public int getItemCount() {
        return mangPlaylist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewPlaylist;
        TextView txt_tenplaylist,txt_tacgia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlaylist = itemView.findViewById(R.id.img_playlist);
            txt_tenplaylist = itemView.findViewById(R.id.tv_playlistname);
            txt_tacgia = itemView.findViewById(R.id.tv_authorPlaylist);
        }
    }
}
