package com.example.listenmusic.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.Models.User;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private User user; // Đối tượng User cần truyền
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, User user) {
        super(fm, behavior);
        this.user = user; // Gán User vào adapter
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FollowFragment();
            case 1:
                return new VipFragment();
            case 2:
                return HomeFragment.newInstance(user);
            case 3:
                return new TrendFragment();
            case 4:
                return LibraryFragment.newInstance(user); // Truyền User vào LibraryFragment
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}