package com.example.listenmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.SearchFragment;

public class SearchFragmentAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private User user;
    // Constructor
    public SearchFragmentAdapter(@NonNull FragmentManager fm, int behavior, Context context,User user) {
        super(fm, behavior);
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Trả về fragment tương ứng
        return SearchFragment.newInstance(user);
    }

    @Override
    public int getCount() {
        return 1; // Hoặc số lượng fragment của bạn
    }
}
