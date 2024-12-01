package com.example.listenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHatOffline;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private Context context;
    private List<BaiHatOffline> songList;

    public SongAdapter(Context context, List<BaiHatOffline> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.download_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        BaiHatOffline song = songList.get(position);
        holder.songTitle.setText(song.getTitle());
        holder.songArtist.setText(song.getArtist());
        // Optional: Load thumbnail if available
        // holder.songThumbnail.setImageResource(song.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView songThumbnail;
        TextView songTitle, songArtist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songThumbnail = itemView.findViewById(R.id.song_thumbnail);
            songTitle = itemView.findViewById(R.id.song_title);
            songArtist = itemView.findViewById(R.id.song_artist);
        }
    }
}
