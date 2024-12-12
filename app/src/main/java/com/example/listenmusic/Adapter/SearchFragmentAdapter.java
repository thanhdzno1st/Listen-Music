package com.example.listenmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.fragment.SearchFragment;

public class SearchFragmentAdapter extends FragmentStatePagerAdapter {
    private Context context;

    // Constructor
    public SearchFragmentAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Trả về fragment tương ứng
        return new SearchFragment();
    }

    @Override
    public int getCount() {
        return 1; // Hoặc số lượng fragment của bạn
    }
}
