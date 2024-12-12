package com.example.listenmusic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.AlbumnFragment;
import com.example.listenmusic.fragment.LikeFragment;
import com.example.listenmusic.fragment.PlaylistFragment;

public class ViewPagerAdapter_Library extends FragmentStatePagerAdapter {

    private final User user; // Lưu đối tượng User

    public ViewPagerAdapter_Library(@NonNull FragmentManager fm, int behavior, User user) {
        super(fm, behavior);
        this.user = user; // Lưu dữ liệu User
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AlbumnFragment();
            case 1:
                return new LikeFragment();
            case 2:
                Log.d("Debug playlist", "Adapter thu viện: PlaylistFragment");
                return PlaylistFragment.newInstance(user, null); // Truyền dữ liệu vào PlaylistFragment
            default:
                return new PlaylistFragment();
        }
    }

    @Override
    public int getCount() {
        return 3; // Tổng số trang
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Albumn";
            case 1:
                return "Yêu thích";
            case 2:
                return "Playlist";
            default:
                return null;
        }
    }
}
