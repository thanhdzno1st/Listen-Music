package com.example.listenmusic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.listenmusic.fragment.AlbumnFragment;
import com.example.listenmusic.fragment.LikeFragment;
import com.example.listenmusic.fragment.PlaylistFragment;
import com.example.listenmusic.fragment.TrendFragment;

public class ViewPagerAdapter_Library extends FragmentStateAdapter {
    public ViewPagerAdapter_Library(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PlaylistFragment();
            case 1:
                return new AlbumnFragment();
            case 2:
                return new LikeFragment();
            default:
                return new PlaylistFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Playlist";
            case 1:
                return "Albumn";
            case 2:
                return "Yêu thích";
            default:
                return null;
        }
    }
}
