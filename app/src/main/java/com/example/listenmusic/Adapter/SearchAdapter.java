package com.example.listenmusic.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Activity.Music_Activity;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Song> baiHatList;
    private User user;
    private ArrayList<Song> mangSong;

    public SearchAdapter(List<Song> baiHatList, User user) {
        this.baiHatList = baiHatList;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_con_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song baiHat = baiHatList.get(position);
        holder.tv_tenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tv_tenNgheSi.setText(baiHat.getTenNgheSi());

        Picasso.get()
                .load(baiHat.getHinhBaiHat())
                .into(holder.img_baiHat);
    }

    @Override
    public int getItemCount() {
        return baiHatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenBaiHat, tv_tenNgheSi;
        ImageView img_baiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenBaiHat = itemView.findViewById(R.id.songTitle);
            tv_tenNgheSi = itemView.findViewById(R.id.tv_tenNgheSi);
            img_baiHat = itemView.findViewById(R.id.songImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Song baiHat = baiHatList.get(position); // Corrected access to baiHatList
                        mangSong = new ArrayList<>();
                        mangSong.add(baiHat);


                        Intent intent = new Intent(itemView.getContext(), Music_Activity.class);
                        Bundle bundle = new Bundle();
                        Log.d("Debug SearchFragment", "toi dang o bundle voi user la: "+user);
                        bundle.putParcelable("cakhuc", baiHat); // Transmit the song object
                        bundle.putSerializable("user", user); // Transmit the user object
                        bundle.putParcelableArrayList("cacbaihat", mangSong);
                        intent.putExtras(bundle); // Set the bundle to the intent
                        itemView.getContext().startActivity(intent); // Start the Music_Activity
                    }
                }
            });
        }
    }
}
