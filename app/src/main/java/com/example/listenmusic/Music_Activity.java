package com.example.listenmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import de.hdodenhof.circleimageview.CircleImageView;

public class Music_Activity extends AppCompatActivity {
    private ViewPager2 viewPager2Music;
    private TabLayout mTablayout;
    private CircleImageView circle_img;
    private ImageView bt_pause;
    private View indicator;
    private TextView tabTitle;
    private View indicator1,indicator2,indicator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();
        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });

        ViewPagerAdapter_Music viewPagerAdapterMusic= new ViewPagerAdapter_Music(this);
        viewPager2Music.setAdapter(viewPagerAdapterMusic);

        new TabLayoutMediator(mTablayout, viewPager2Music,
                (tab, position) -> tab.setText(viewPagerAdapterMusic.getPageTitle(position))
        ).attach();

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Khi tab được chọn, hiển thị tiêu đề của tab đó
                tabTitle.setText(tab.getText());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần làm gì khi tab được chọn lại
            }
        });

        viewPager2Music.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Khi trang được thay đổi, cập nhật chỉ báo (indicators)
                updateIndicators(position);
            }
        });

    }

    private void AnhXa() {
        circle_img = findViewById(R.id.rotate);
        bt_pause = findViewById(R.id.btn_pause);
        mTablayout = findViewById(R.id.tab_layout_music);
        viewPager2Music= findViewById(R.id.view_pager2_music);
        tabTitle = findViewById(R.id.tab_title);
        indicator1 = findViewById(R.id.indicator_1);
        indicator2 = findViewById(R.id.indicator_2);
        indicator3 = findViewById(R.id.indicator_3);
    }
    private void startAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                circle_img.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        circle_img.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }
    // Hàm cập nhật các chỉ báo
    private void updateIndicators(int position) {
        // Reset về trạng thái ban đầu (xám và ngắn)
        resetIndicators();

        // Dựa vào vị trí trang, cập nhật chỉ báo tương ứng
        switch (position) {
            case 0:
                updateIndicator(indicator1, true); // Trang đầu tiên, cập nhật indicator1
                break;
            case 1:
                updateIndicator(indicator2, true); // Trang thứ hai, cập nhật indicator2
                break;
            case 2:
                updateIndicator(indicator3, true); // Trang thứ ba, cập nhật indicator3
                break;
        }
    }
    // Hàm cập nhật một indicator thành màu trắng và dài hơn
    private void updateIndicator(View indicator, boolean isSelected) {
        // Nếu là trang được chọn
        if (isSelected) {
            indicator.setBackgroundColor(getResources().getColor(R.color.white));
            ViewGroup.LayoutParams params = indicator.getLayoutParams();
            params.width = 30; // Tăng độ dài của indicator
            indicator.setLayoutParams(params);
        }
    }

    // Hàm reset tất cả indicators về trạng thái ban đầu (màu xám, ngắn)
    private void resetIndicators() {
        // Reset indicator1
        indicator1.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params1 = indicator1.getLayoutParams();
        params1.width = 15; // Trở về kích thước ngắn hơn
        indicator1.setLayoutParams(params1);

        // Reset indicator2
        indicator2.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params2 = indicator2.getLayoutParams();
        params2.width = 15;
        indicator2.setLayoutParams(params2);

        // Reset indicator3
        indicator3.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params3 = indicator3.getLayoutParams();
        params3.width = 15;
        indicator3.setLayoutParams(params3);
    }

}