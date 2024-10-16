package com.example.listenmusic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.fragment.BanFragment;
import com.example.listenmusic.fragment.FollowFragment;
import com.example.listenmusic.fragment.HelpFragment;
import com.example.listenmusic.fragment.HistoryFragment;
import com.example.listenmusic.fragment.HomeFragment;
import com.example.listenmusic.fragment.LibraryFragment;
import com.example.listenmusic.fragment.NotifyFragment;
import com.example.listenmusic.fragment.SettingFragment;
import com.example.listenmusic.fragment.TrendFragment;
import com.example.listenmusic.fragment.VipFragment;

public class ViewPagerAdapter_LeftMenu extends FragmentStatePagerAdapter {
    public ViewPagerAdapter_LeftMenu(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new BanFragment();
            case 2:
                return new NotifyFragment();
            case 3:
                return new HelpFragment();
            case 4:
                return new SettingFragment();
            default:
                return new SettingFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
