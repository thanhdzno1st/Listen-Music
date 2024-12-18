package com.example.listenmusic.Adapter;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.AlbumnFragment;
import com.example.listenmusic.fragment.LikeFragment;
import com.example.listenmusic.fragment.PlaylistFragment;

public class ViewPagerAdapter_Library extends FragmentStateAdapter {

    private final User user; // Lưu đối tượng User

    public ViewPagerAdapter_Library(@NonNull FragmentActivity fragmentActivity, User user) {
        super(fragmentActivity);
        this.user = user; // Lưu dữ liệu User
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return PlaylistFragment.newInstance(user, null); // Truyền dữ liệu vào PlaylistFragment
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
        return 3; // Tổng số trang
    }
}
