package com.example.baitaphuy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    // Danh sách bài hát
    private List<Song> favoriteSongs;

    public FavoritesAdapter(List<Song> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item của danh sách
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        // Gán dữ liệu cho item
        Song song = favoriteSongs.get(position);
        holder.songName.setText(song.getName());
        holder.artistName.setText(song.getArtist());
        holder.songImage.setImageResource(song.getImageResId());
        holder.favoriteIcon.setImageResource(song.isFavorite() ? R.drawable.heart : R.drawable.ic_favorite_border);
    }

    @Override
    public int getItemCount() {
        return favoriteSongs.size();
    }

    // ViewHolder để quản lý các view trong mỗi item
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView artistName;
        ImageView songImage;
        ImageView favoriteIcon;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name1);
            artistName = itemView.findViewById(R.id.artist_name1);
            songImage = itemView.findViewById(R.id.song_image1);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon1);
        }
    }
}
