package com.example.listenmusic.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.R;

public class NowPlayingFragmentBottom extends Fragment {
    ImageView nextBtn,song_art,playPauseBtn;
    TextView artist, songName;
    View view;
    public NowPlayingFragmentBottom() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now_playing_bottom, container, false);
        artist = view.findViewById(R.id.song_artist_miniPlayer);
        songName = view.findViewById(R.id.song_name_miniPlayer);
        song_art = view.findViewById(R.id.bottom_image_art);
        nextBtn = view.findViewById(R.id.skip_next_bottom);
        playPauseBtn = view.findViewById(R.id.btn_pause_miniPlayer);

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(SHOW_MINI_PLAYER){
//            if(PATH_TO_FRAG != null){
////                byte[] art = getAlbumnArt(PATH_TO_FRAG);
////                Glide.with(getContext()).load(art)
////                        .into(song_art);
////                songName.setText();
//            }
//        }
//    }
}