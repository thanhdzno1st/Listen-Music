package com.example.listenmusic.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_music#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_music extends Fragment {
    public CircleImageView circle_img;
    public TextView name_music,name_casi;
    // Các tham số cho Fragment (nếu có)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fragment_music() {
        // Required empty public constructor
    }

    // Factory method để tạo một instance của Fragment với các tham số
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
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        circle_img = view.findViewById(R.id.rotate);
        name_music=view.findViewById(R.id.name_music);
        name_casi = view.findViewById(R.id.name_casi);
        startAnimation();
        return view;
    }

    // Phương thức bắt đầu hoạt ảnh quay hình ảnh
    public void startAnimation() {
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

    // Phương thức dừng hoạt ảnh quay
    public void stopAnimation() {
        circle_img.animate().cancel();
    }

    // Phương thức để tải ảnh bằng Glide
    public void Playnhac(Song song) {
        name_music.setText(song.getTenBaiHat());
        name_casi.setText(song.getTenNgheSi());
        if (isAdded() && getContext() != null) {  // Kiểm tra nếu Fragment đã được gắn vào Activity
            if (song.getHinhBaiHat() != null && !song.getHinhBaiHat().isEmpty()) {
                Glide.with(getContext())  // Sử dụng getContext() nếu Fragment đã được gắn
                        .load(song.getHinhBaiHat())  // URL ảnh từ API
                        .placeholder(R.drawable.image2)  // Ảnh mặc định khi đang tải
                        .error(R.drawable.image2)  // Ảnh hiển thị nếu URL không hợp lệ
                        .into(circle_img);
            } else {
                // Nếu URL null hoặc rỗng, sử dụng ảnh mặc định
                circle_img.setImageResource(R.drawable.image2);
            }
        }
    }

}