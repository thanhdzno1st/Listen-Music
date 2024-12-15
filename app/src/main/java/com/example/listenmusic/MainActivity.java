package com.example.listenmusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.listenmusic.Activity.ChatBoxDialogFragment;
import com.example.listenmusic.Activity.Music_Activity;
import com.example.listenmusic.Adapter.SearchFragmentAdapter;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.fragment.LibraryFragment;
import com.example.listenmusic.fragment.PlaylistFragment;
import com.example.listenmusic.fragment.SearchFragment;
import com.example.listenmusic.fragment.ViewPagerAdapter;
import com.example.listenmusic.widget.CustomViewPager;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewpager, viewPagerLeftMenu;
    private BottomNavigationBar bottomNavigationBar;
    private DrawerLayout drawerLayout;
    private ImageView btnOpenDrawer;
    private NavigationView navigationView;
    private View view;
    private RelativeLayout bt_dowload;
    private TextView tv_user, tv_email;
    private User user = null;
    private ImageView btn_search;
    private TextView searchView;
    private RelativeLayout bt_1;
    private RelativeLayout bt_2;
    private RelativeLayout bt_3;
    private ImageView btn_close_search;
    private TextInputEditText querryEditText;
    private ImageView sendQuerry,logo,appIcon;
    FloatingActionButton buttonshowDialog;
    private ProgressBar progressBar;
    private LinearLayout chatResponse;
    private ChatFutures chatModel;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_dowload = findViewById(R.id.bt_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tv_user = headerView.findViewById(R.id.txt_User);
        tv_email = headerView.findViewById(R.id.txt_Email);
        btn_search = findViewById(R.id.btn_search);
        btnOpenDrawer = findViewById(R.id.bt_menu);
        bt_1 = findViewById(R.id.bt_1);
        bt_2 = findViewById(R.id.bt_2);
        bt_3 = findViewById(R.id.bt_3);
        btn_close_search = findViewById(R.id.btn_close_search);

        buttonshowDialog = findViewById(R.id.showMessageDialog);

        buttonshowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChatDialog();
            }
        });

        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            user = (User) bundleReceive.get("object_user");
            if (user != null) {
                tv_user.setText(user.getHoTen());
                tv_email.setText(user.getEmail());
            }
        }
        if (user == null) {
            user = (User) getIntent().getSerializableExtra("object_user");
            tv_user.setText(user.getHoTen());
            tv_email.setText(user.getEmail());
        }

        bt_dowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Download.class);
                intent.putExtra("object_user", user);
                startActivity(intent);
            }
        });

        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });


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
                .setFirstSelectedPosition(2)
                .initialise();

        viewpager = findViewById(R.id.viewPager);
        viewpager.setPagingEnabled(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, user);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(2);

        viewPagerLeftMenu = findViewById(R.id.viewPagerMenuLeft);
        viewPagerLeftMenu.setPagingEnabled(false);
        ViewPagerAdapter_LeftMenu adapterLeftMenu = new ViewPagerAdapter_LeftMenu(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerLeftMenu.setAdapter(adapterLeftMenu);
        viewPagerLeftMenu.setVisibility(View.GONE);
        viewPagerLeftMenu.setPageTransformer(false, null);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_close_search.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.GONE);

                SearchFragmentAdapter searchadapter = new SearchFragmentAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, MainActivity.this);
                viewpager.setAdapter(searchadapter);
                viewpager.setCurrentItem(1);
            }
        });

        btn_close_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_search.setVisibility(View.VISIBLE);
                btn_close_search.setVisibility(View.GONE);
                viewpager.setAdapter(adapter);
                viewpager.setCurrentItem(2);
            }
        });




        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewpager.setCurrentItem(position);
                viewPagerLeftMenu.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(int position) {}

            @Override
            public void onTabReselected(int position) {}
        });

        viewpager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
                viewPagerLeftMenu.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                viewPagerLeftMenu.setVisibility(View.VISIBLE);
                if (id == R.id.nav_history) {
                    viewPagerLeftMenu.setCurrentItem(0);
                } else if (id == R.id.nav_ban) {
                    viewPagerLeftMenu.setCurrentItem(1);
                } else if (id == R.id.nav_notify) {
                    viewPagerLeftMenu.setCurrentItem(2);
                } else if (id == R.id.nav_help) {
                    viewPagerLeftMenu.setCurrentItem(3);
                } else if (id == R.id.nav_setting) {
                    viewPagerLeftMenu.setCurrentItem(4);
                } else if (id == R.id.nav_student) {
                    viewPagerLeftMenu.setCurrentItem(5);
                } else if (id == R.id.nav_logout) {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void closeCurrentFragment() {
        viewPagerLeftMenu.setVisibility(View.GONE);
    }
    private void showChatDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChatBoxDialogFragment chatDialogFragment = new ChatBoxDialogFragment();
        chatDialogFragment.show(fragmentManager, "ChatBoxDialogFragment");
    }
}
