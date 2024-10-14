package com.example.musicbtp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musicbtp.login_4.ViewPagerAdapter;

public class login4 extends AppCompatActivity {
    ViewPager viewpager;
    Button btnEmail;
    Button btnSdt;
    Button btnDangNhap;
    Button btnSendOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_4);
        viewpager = findViewById(R.id.login_4_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        btnEmail = findViewById(R.id.btnEmail);
        btnSdt = findViewById(R.id.btnPhone);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
                btnSdt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEDED")));
                btnEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                setupLoginButton();
            }
        });

        btnSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
                btnEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEDED")));
                btnSdt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
        });
    }
    private void setupOtpButton() {
        if (viewpager.getCurrentItem() == 0) {
            btnSendOTP = findViewById(R.id.btnSendOTP);
            btnSendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(login4.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    private void setupLoginButton() {
        if (viewpager.getCurrentItem() == 1) {
            btnDangNhap = findViewById(R.id.btnLogin);
            btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(login4.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}







