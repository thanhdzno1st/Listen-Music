package com.example.listenmusic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter_Music extends FragmentStateAdapter {

    public ViewPagerAdapter_Music(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_info();
            case 1:
                return new Fragment_music();
            case 2:
                return new Fragment_lyrics();
            default:
                return new Fragment_music();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng trang
    }

    // Thêm phương thức để cung cấp tên cho các tab
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Thông tin";
            case 1:
                return "Bài hát gợi ý";
            case 2:
                return "Lời bài hát";
            default:
                return null;
        }
    }
}
