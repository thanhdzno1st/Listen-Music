package com.example.listenmusic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter_dsphat extends FragmentStateAdapter {
    public ViewPageAdapter_dsphat(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new Fragment_listsong();

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
