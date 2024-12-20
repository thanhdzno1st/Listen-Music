package com.example.listenmusic.Activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.listenmusic.R;
import com.example.listenmusic.Adapter.ViewPagerAdapter_login_2;

public class login4 extends AppCompatActivity {
    ViewPager viewpager;
    Button btnEmail;
    Button btnSdt;
    Button btnDangNhap;
    Button btnSendOTP;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_4);
        viewpager = findViewById(R.id.login_4_viewpager);
        ViewPagerAdapter_login_2 adapter = new ViewPagerAdapter_login_2(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
            }
        });

//        Intent intent = new Intent(login4.this, MainActivity.class);
//        startActivity(intent);
        btnSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
                btnEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEDED")));
                btnSdt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
        });
    }


}
