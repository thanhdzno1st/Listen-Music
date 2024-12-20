package com.example.listenmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.fragment.Login_4_1Fragment;
import com.example.listenmusic.fragment.Login_4_2Fragment;

public class ViewPagerAdapter_login_2 extends FragmentStatePagerAdapter {
    public ViewPagerAdapter_login_2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Login_4_1Fragment();
            case 1:
                return new Login_4_2Fragment();
            default:
                return new Login_4_1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}