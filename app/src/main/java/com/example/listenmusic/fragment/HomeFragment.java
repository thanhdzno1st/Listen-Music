package com.example.listenmusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.listenmusic.HomeBannerViewPagerAdapter;
import com.example.listenmusic.Music_Activity;
import com.example.listenmusic.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private List<Integer> layoutList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
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

        // Thiết lập ViewPager và Adapter
        viewPager = view.findViewById(R.id.viewpager_banner);
        HomeBannerViewPagerAdapter adapter = new HomeBannerViewPagerAdapter(getActivity().getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        // Khởi tạo danh sách layout cho ViewPager
        layoutList.add(R.drawable.img_1); // Bạn có thể thay thế bằng các ID của layout khác
        layoutList.add(R.drawable.img_2);
        layoutList.add(R.drawable.img_3);

        // Bắt đầu tự động chuyển ảnh
        handler.postDelayed(runnable, 500);

        // Xử lý sự kiện khi click vào layout
        View layoutdcchon = view.findViewById(R.id.kedcchon);
        layoutdcchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Music_Activity.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

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
