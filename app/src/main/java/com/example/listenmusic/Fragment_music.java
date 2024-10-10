package com.example.listenmusic;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_music#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_music extends Fragment {
    private CircleImageView circle_img;
    private ImageView bt_pause,bt_play;
    private MediaPlayer mediaPlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_music() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_music newInstance(String param1, String param2) {
        Fragment_music fragment = new Fragment_music();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        circle_img = view.findViewById(R.id.rotate);
        startAnimation();
        // Khởi tạo MediaPlayer
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.bachnguyequangvanotchusa); // Thay your_music_file bằng tên tệp nhạc của bạn
        playMusic();
        bt_pause = getActivity().findViewById(R.id.btn_pause);
        bt_play = getActivity().findViewById(R.id.btn_play);

        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimation();
                pauseMusic(); // Gọi hàm tạm dừng nhạc
                bt_pause.setVisibility(View.INVISIBLE); // Ẩn nút pause
                bt_play.setVisibility(View.VISIBLE); // Hiện nút play
            }
        });
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
                playMusic(); // Gọi hàm phát nhạc
                bt_play.setVisibility(View.INVISIBLE); // Ẩn nút play
                bt_pause.setVisibility(View.VISIBLE); // Hiện nút pause


            }
        });
        return view;
    }
    private void startAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                circle_img.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        circle_img.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }
    private void stopAnimation(){
        circle_img.animate().cancel();
    }
    private void playMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Phát nhạc
        }
    }

    private void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Tạm dừng nhạc
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Giải phóng MediaPlayer khi không sử dụng
            mediaPlayer = null; // Đặt mediaPlayer thành null để tránh lỗi
        }
    }

}