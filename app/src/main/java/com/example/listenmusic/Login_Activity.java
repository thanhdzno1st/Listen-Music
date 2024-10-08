package com.example.listenmusic;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Arrays;
import java.util.List;

public class Login_Activity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private Integer[] layouts = {R.layout.slide_layout_1, R.layout.slide_layout_2, R.layout.slide_layout_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout_1); // Đảm bảo sử dụng đúng layout

        // Khởi tạo ViewPager
        viewPager = findViewById(R.id.viewPager);

        // Chuyển danh sách layouts sang List
        List<Integer> layoutList = Arrays.asList(layouts);

        // Khởi tạo adapter và set cho ViewPager
        adapter = new ViewPagerAdapter(this, layoutList);
        viewPager.setAdapter(adapter);

        // Thêm chuyển cảnh tự động sau 3 giây (nếu cần)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển cảnh sang slide tiếp theo nếu có
                if (viewPager.getCurrentItem() < layoutList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        }, 3000); // 3 giây
    }
}
