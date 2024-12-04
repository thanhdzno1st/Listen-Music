package com.example.listenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.listenmusic.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Banner> arrayListbanner;

    public BannerAdapter(Context context, ArrayList<Banner> arrayListbanner) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
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
                Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                intent.putExtra("banner",arrayListbanner.get(position));
                context.startActivity(intent);
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
