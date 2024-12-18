package com.example.listenmusic.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.FollowFragment;
import com.example.listenmusic.fragment.HomeFragment;
import com.example.listenmusic.fragment.LibraryFragment;
import com.example.listenmusic.fragment.SearchFragment;
import com.example.listenmusic.fragment.TrendFragment;
import com.example.listenmusic.fragment.VipFragment;

public class ViewPagerAdapter_Menu extends FragmentStatePagerAdapter {
    private User user; // Đối tượng User cần truyền
    public ViewPagerAdapter_Menu(@NonNull FragmentManager fm, int behavior, User user) {
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
                Log.d("Debug HomeFragment", "toi dang o adapter HomeFragment voi user:" +user);
                return HomeFragment.newInstance(user);
            case 3:
                return new TrendFragment();
            case 4:
                Log.d("Debug playlist", "toi dang o adapter thu vien voi user:" +user);
                return LibraryFragment.newInstance(user); // Truyền User vào LibraryFragment
//            case 5:
//                Log.d("Debug SearchFragment", "toi dang o SearchFragment voi user"+user);
//                return SearchFragment.newInstance(user);
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}