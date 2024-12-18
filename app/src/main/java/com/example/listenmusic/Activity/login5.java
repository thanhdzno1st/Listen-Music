package com.example.listenmusic.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listenmusic.R;

public class login5 extends AppCompatActivity {
    Button btnVerifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_5);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        btnVerifyOTP.setOnClickListener(v -> {
            Toast.makeText(login5.this, "Chức năng đang được cập nhật, vui lòng đăng nhập bằng email: thanh261220@gmail.com và pass: 1234", Toast.LENGTH_LONG).show();
            finish();

        });
    }
}
