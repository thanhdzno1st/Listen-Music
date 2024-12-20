package com.example.listenmusic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.listenmusic.Adapter.Home_tiktok_adapter;
import com.example.listenmusic.Adapter.SearchAdapter;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private List<Integer> layoutList = new ArrayList<>();


    public static HomeFragment newInstance(User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user); // Truyền đối tượng User vào Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo handler và runnable để tự động chuyển trang
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Tính toán trang hiện tại
                if (currentPage > layoutList.size()) {
                    currentPage = 0; // Reset về trang đầu nếu đã đến trang cuối
                }

                // Chuyển sang trang tiếp theo
                viewPager.setCurrentItem(currentPage++, true);

                // Đặt lại handler sau 2 giây
                handler.postDelayed(this, 1500);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

// Lấy đối tượng Fragment_banner từ id
        Fragment_banner fragmentBanner = (Fragment_banner) getChildFragmentManager().findFragmentById(R.id.fragment_banner);

        // Truyền dữ liệu vào Fragment_banner
        if (fragmentBanner != null) {
            User user =  (User) getArguments().getSerializable("user"); // Lấy đối tượng User
            fragmentBanner.setUser(user); // Gọi hàm để truyền User
        }
        // Truyền user vào fragment_home_recommend
        fragment_home_recommend homeRecommendFragment = (fragment_home_recommend) getChildFragmentManager().findFragmentById(R.id.fragment_home_recommend);
        if (homeRecommendFragment != null) {
            User user = (User) getArguments().getSerializable("user");  // Lấy đối tượng user
            homeRecommendFragment.setUser(user);  // Truyền user vào fragment_home_recommend
        }

        // Truyền user vào fragment_home_tiktok
        Fragment_home_tiktok fragmentHomeTiktok = (Fragment_home_tiktok) getChildFragmentManager().findFragmentById(R.id.fragment_home_tiktok);
        if (homeRecommendFragment != null) {
            User user = (User) getArguments().getSerializable("user");  // Lấy đối tượng user
            fragmentHomeTiktok.setUser(user);  // Truyền user vào fragment_home_recommend
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable); // Hủy handler khi Fragment bị hủy
        }
    }
}
