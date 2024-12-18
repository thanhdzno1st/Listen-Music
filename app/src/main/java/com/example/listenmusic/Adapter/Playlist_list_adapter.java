package com.example.listenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Activity.DanhsachbaihatActivity;
import com.example.listenmusic.Models.Playlist;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Playlist_list_adapter extends RecyclerView.Adapter<Playlist_list_adapter.ViewHolder>{
    Context context;
    ArrayList<Playlist> mangPlaylist;
    User user;
    Song song;
    public Playlist_list_adapter(Context context, ArrayList<Playlist> mangPlaylist, User user, Song song) {
        this.context = context;
        this.mangPlaylist = mangPlaylist;
        this.user = user;
        this.song = song;
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
        holder.itemView.setOnClickListener(v -> {
            if(song!=null)
                updatePlaylistData(playlist,user,song);
            else
                showPlaylistData(playlist);
        });
    }

    private void showPlaylistData(Playlist playlist) {
        // Tạo một Bundle để đóng gói dữ liệu
        Bundle bundle = new Bundle();
        bundle.putSerializable("playlist",playlist);
        bundle.putSerializable("user", user);

        // Tạo Intent và đính kèm Bundle
        Intent intent = new Intent(context, DanhsachbaihatActivity.class);
        intent.putExtras(bundle);

        // Bắt đầu Activity
        context.startActivity(intent);
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
    private void updatePlaylistData(Playlist playlist, User user, Song song) {
        // Hiển thị thông báo hoặc xử lý logic trước khi gọi API
        Toast.makeText(context, "Đang cập nhật playlist: " + playlist.getTenPlayList(), Toast.LENGTH_SHORT).show();

        Dataservice dataservice = APIservice.getService();
        Call<ResponseBody> callback = dataservice.AddSongtoPlaylist(song.getIdBaiHat(), user.getIdTaiKhoan(), playlist.getIdPlaylist());
        callback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "Thêm nhạc thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Thêm nhac thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
