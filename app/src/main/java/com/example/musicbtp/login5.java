package com.example.musicbtp;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_1);
        btnDangNhap = findViewById(R.id.loginButton);
        btnDangKy = findViewById(R.id.registerButton);
        viewpager = findViewById(R.id.login_1_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(login1.this, login4.class);
            startActivity(intent);
        });
        btnDangKy.setOnClickListener(view ->
        {
            Intent intent = new Intent(login1.this, register.class);
            startActivity(intent);
        });
    }


}
