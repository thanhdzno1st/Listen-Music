package com.example.listenmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.listenmusic.fragment.Fragment_listsong;

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
