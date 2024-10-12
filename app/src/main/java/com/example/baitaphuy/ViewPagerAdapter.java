package com.example.baitaphuy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PlaylistFragment();
            case 1:
                return new AlbumFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return new PlaylistFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng fragment
    }
}