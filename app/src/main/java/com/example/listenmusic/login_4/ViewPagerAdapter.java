package com.example.listenmusic.login_4;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
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