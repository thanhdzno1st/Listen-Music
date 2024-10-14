package com.example.musicbtp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class testbottom extends AppCompatActivity {
    ViewPager viewpager;
    Button btnDangNhap;
    Button btnDangKy;

    // Tạo một Handler để sử dụng postDelayed
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.bottomsheet);

    }
}
