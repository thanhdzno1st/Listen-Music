package com.example.listenmusic;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.ViewPagerAdapter;
import com.example.listenmusic.widget.CustomViewPager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewpager, viewPagerLeftMenu;
    private BottomNavigationBar bottomNavigationBar;
    private DrawerLayout drawerLayout;
    private ImageView btnOpenDrawer;
    private NavigationView navigationView;
    private View view;
    private RelativeLayout bt_dowload;
    private TextView tv_user,tv_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_dowload= findViewById(R.id.bt_2);
        // Tham chiếu tới DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tv_user= headerView.findViewById(R.id.txt_User);
        tv_email= headerView.findViewById(R.id.txt_Email);
        // Tham chiếu tới nút mở menu
        btnOpenDrawer = findViewById(R.id.bt_menu);
        view = findViewById(R.id.viewtablet);
        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive!=null){
            User user = (User) bundleReceive.get("object_user");
            if(user != null){
                tv_user.setText(user.getHoTen());
                tv_email.setText(user.getEmail());
            }
        }
        bt_dowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Download.class);
                startActivity(intent);
            }
        });
        // Đóng dialog khi nhấn vào bên ngoài

        // Cài đặt sự kiện cho nút mở menu trái
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra và mở menu nếu chưa mở
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Music_Activity.class); // Thay MainActivity bằng tên Activity hiện tại của bạn
                startActivity(intent); // Bắt đầu Activity mới
            }
        });
        // Cấu hình BottomNavigationBar
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.menu_follow_ic, "Follow")
                        .setActiveColorResource(R.color.colorActiveFollow)
                        .setInActiveColorResource(R.color.colorInactive))
                .addItem(new BottomNavigationItem(R.drawable.menu_vip_ic, "Vip")
                        .setActiveColorResource(R.color.colorActiveVip)
                        .setInActiveColorResource(R.color.colorInactive))
                .addItem(new BottomNavigationItem(R.drawable.menu_home_ic, "Home")
                        .setActiveColorResource(R.color.colorActiveHome)
                        .setInActiveColorResource(R.color.colorInactive))
                .addItem(new BottomNavigationItem(R.drawable.menu_trend_ic, "Trend")
                        .setActiveColorResource(R.color.colorActiveTrend)
                        .setInActiveColorResource(R.color.colorInactive))
                .addItem(new BottomNavigationItem(R.drawable.menu_storage_ic, "Library")
                        .setActiveColorResource(R.color.colorActiveLibrary)
                        .setInActiveColorResource(R.color.colorInactive))
                .setFirstSelectedPosition(2)  // Đặt tab "Home" là mặc định
                .initialise();

        // Thiết lập ViewPager và Adapter
        viewpager = findViewById(R.id.viewPager);
        viewpager.setPagingEnabled(false); // Tắt tính năng vuốt
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(2);

        // Thiết lập ViewPagerMenuLeft và ẩn nó ban đầu
        viewPagerLeftMenu = findViewById(R.id.viewPagerMenuLeft);
        viewPagerLeftMenu.setPagingEnabled(false);
        ViewPagerAdapter_LeftMenu adapterLeftMenu = new ViewPagerAdapter_LeftMenu(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerLeftMenu.setAdapter(adapterLeftMenu);
        viewPagerLeftMenu.setVisibility(View.GONE); // Ẩn khi khởi động
        viewPagerLeftMenu.setPageTransformer(false, null);



        // Liên kết BottomNavigationBar với ViewPager
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                // Di chuyển đến fragment tương ứng khi tab được chọn
                viewpager.setCurrentItem(position);
                viewPagerLeftMenu.setVisibility(View.GONE); // Ẩn ViewPagerMenuLeft khi chọn tab
            }

            @Override
            public void onTabUnselected(int position) {
                // Xử lý khi tab không được chọn nếu cần
            }

            @Override
            public void onTabReselected(int position) {
                // Xử lý khi tab được chọn lại nếu cần
            }
        });

        // Đồng bộ hóa trạng thái khi trang được thay đổi qua swipe (nếu được kích hoạt)
        viewpager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Không cần xử lý
            }

            @Override
            public void onPageSelected(int position) {
                // Đặt tab tương ứng trên BottomNavigationBar khi trang được thay đổi
                bottomNavigationBar.selectTab(position);
                viewPagerLeftMenu.setVisibility(View.GONE); // Ẩn ViewPagerMenuLeft khi đổi tab
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Không cần xử lý
            }
        });

        // Thiết lập sự kiện chọn item cho NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Hiển thị ViewPagerMenuLeft và chuyển đến trang tương ứng
                viewPagerLeftMenu.setVisibility(View.VISIBLE); // Hiển thị khi chọn item
                if (id == R.id.nav_history) {
                    viewPagerLeftMenu.setCurrentItem(0); // Chuyển đến trang 0
                } else if (id == R.id.nav_ban) {
                    viewPagerLeftMenu.setCurrentItem(1); // Chuyển đến trang 1
                } else if (id == R.id.nav_notify) {
                    viewPagerLeftMenu.setCurrentItem(2); // Chuyển đến trang 2
                } else if (id == R.id.nav_help) {
                    viewPagerLeftMenu.setCurrentItem(3); // Chuyển đến trang 3
                } else if (id == R.id.nav_setting) {
                    viewPagerLeftMenu.setCurrentItem(4); // Chuyển đến trang 4
                } else if (id == R.id.nav_logout) {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }

                // Đóng Drawer sau khi item được chọn
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    // Hàm xử lý khi người dùng nhấn nút Back để đóng Drawer nếu đang mở
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void closeCurrentFragment() {
            viewPagerLeftMenu.setVisibility(View.GONE); // Ẩn ViewPagerMenuLeft nếu đã ở fragment đầu tiên

    }
}