package com.example.listenmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.listenmusic.Activity.Chatbox_Activity;

import me.relex.circleindicator.CircleIndicator;

import java.util.Arrays;
import java.util.List;

public class Login_Activity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter_Login adapter;
    private CircleIndicator indicator;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private Button btnDangNhap,btnDangKy;

    private Integer[] layouts = {R.layout.slide_layout_1, R.layout.slide_layout_2, R.layout.slide_layout_3}; // Danh sách layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Đảm bảo sử dụng layout chính cho Login Activity

        // Khởi tạo ViewPager và CircleIndicator
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        btnDangNhap = findViewById(R.id.loginButton);
        btnDangKy = findViewById(R.id.registerButton);

        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(Login_Activity.this, login4.class);
            startActivity(intent);
        });
        btnDangKy.setOnClickListener(view ->
        {
            Intent intent = new Intent(Login_Activity.this, register.class);
            startActivity(intent);
        });
        // Chuyển danh sách layouts sang List
        List<Integer> layoutList = Arrays.asList(layouts);

        // Khởi tạo adapter và set cho ViewPager
        adapter = new ViewPagerAdapter_Login(this, layoutList);
        viewPager.setAdapter(adapter);

        // Liên kết CircleIndicator với ViewPager
        indicator.setViewPager(viewPager);

        // Khởi tạo Handler để tự động chuyển trang
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Tính toán trang hiện tại
                if (currentPage == layoutList.size()) {
                    currentPage = 0; // Reset về trang đầu nếu đã đến trang cuối
                }

                // Chuyển sang trang tiếp theo
                viewPager.setCurrentItem(currentPage++, true);

                // Đặt lại handler sau 2 giây
                handler.postDelayed(this, 2000);
            }
        };

        // Bắt đầu chạy handler sau khi activity tạo xong
        handler.postDelayed(runnable, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Loại bỏ các callback khi activity bị hủy để tránh rò rỉ bộ nhớ
        handler.removeCallbacks(runnable);
    }
}
