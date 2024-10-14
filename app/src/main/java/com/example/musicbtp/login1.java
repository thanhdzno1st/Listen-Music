package com.example.musicbtp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musicbtp.login_1.ViewPagerAdapter;

public class login1 extends AppCompatActivity {
    ViewPager viewpager;
    Button btnDangNhap;
    Button btnDangKy;

    // Tạo một Handler để sử dụng postDelayed
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_1);
        btnDangNhap = findViewById(R.id.loginButton);
        btnDangKy = findViewById(R.id.registerButton);
        viewpager = findViewById(R.id.login_1_viewpager);

        // Cài đặt Adapter cho ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);  // Bắt đầu từ trang 0

        // Thiết lập chuyển cảnh tự động lần lượt giữa các fragment
        startAutoScroll();

        // Sự kiện cho nút Đăng Nhập
        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(login1.this, login4.class);
            startActivity(intent);
        });

        // Sự kiện cho nút Đăng Ký
        btnDangKy.setOnClickListener(view -> {
            Intent intent = new Intent(login1.this, register.class);
            startActivity(intent);
        });
    }

    // Hàm để tự động chuyển cảnh giữa các fragment
    private void startAutoScroll() {
        final int[] page = {0}; // Biến để lưu trang hiện tại

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                viewpager.setCurrentItem(page[0]); // Chuyển đến trang hiện tại

                // Tăng chỉ số trang, nếu > 2 thì reset lại về 0
                page[0]++;
                if (page[0] > 2) {
                    page[0] = 0;
                }

                // Đặt lại delay 2 giây cho lần tiếp theo
                handler.postDelayed(this, 2000); // 2 giây delay
            }
        };

        handler.post(runnable); // Bắt đầu chạy
    }
}
