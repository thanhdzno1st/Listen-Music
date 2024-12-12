package com.example.listenmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.listenmusic.Fragment_info;
import com.example.listenmusic.Fragment_lyrics;
import com.example.listenmusic.Fragment_music;

import java.util.ArrayList;

public class ViewPagerAdapter_Music extends FragmentStateAdapter {
    public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public ViewPagerAdapter_Music(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Trả về Fragment tương ứng với vị trí
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    // Phương thức để thêm Fragment vào adapter
    public void AddFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
    }
    // Phương thức xóa tất cả Fragment trong danh sách

//    public ViewPagerAdapter_Music(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new Fragment_info();
//            case 1:
//                return new Fragment_music();
//            case 2:
//                return new Fragment_lyrics();
//            default:
//                return new Fragment_music();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 3; // Số lượng trang
//    }

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
