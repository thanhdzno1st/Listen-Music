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
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.listenmusic.Activity.DanhsachbaihatActivity;
import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private User user;
    Context context;
    ArrayList<Banner> arrayListbanner;

    public BannerAdapter(Context context, ArrayList<Banner> arrayListbanner, User user) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
        this.user = user;
    }

    @Override
    public int getCount() {
        return arrayListbanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner, container, false);

        ImageView imgbackground = view.findViewById(R.id.imgBackground);
        ImageView imgsong = view.findViewById(R.id.imgSong);
        TextView txttitlesong = view.findViewById(R.id.txtTitleSong);
        TextView txtcontent = view.findViewById(R.id.txtContent);

        Glide.with(context).load(arrayListbanner.get(position).getHinhAnhBanner()).into(imgbackground);
        Glide.with(context).load(arrayListbanner.get(position).getHinhBaiHat()).into(imgsong);
        txttitlesong.setText(arrayListbanner.get(position).getTenBaiHat());
        txtcontent.setText(arrayListbanner.get(position).getNoiDung());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một Bundle để đóng gói dữ liệu
                Bundle bundle = new Bundle();
                bundle.putSerializable("banner", arrayListbanner.get(position)); // Đảm bảo `banner` là Serializable hoặc Parcelable
                bundle.putSerializable("user", user);

                // Tạo Intent và đính kèm Bundle
                Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                intent.putExtras(bundle);

                // Bắt đầu Activity
                context.startActivity(intent);
//                Intent intent = new Intent(context, DanhsachbaihatActivity.class);
//                intent.putExtra("banner",arrayListbanner.get(position));
//                intent.putExtra("user",user);
//                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
