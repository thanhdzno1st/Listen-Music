package com.example.listenmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.fragment.home_banner1;
import com.example.listenmusic.fragment.home_banner2;
import com.example.listenmusic.fragment.home_banner3;
import com.example.listenmusic.fragment.home_banner4;

public class HomeBannerViewPagerAdapter extends FragmentStatePagerAdapter {
    public HomeBannerViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new home_banner1();
            case 1:
                return new home_banner2();
            case 2:
                return new home_banner3();
            case 3:
                return new home_banner4();
            default:
                return new home_banner1();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}