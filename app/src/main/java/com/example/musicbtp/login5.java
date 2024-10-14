package com.example.musicbtp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class login5 extends AppCompatActivity {
    Button btnVerifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_5);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        btnVerifyOTP.setOnClickListener(v -> {
            Intent intent = new Intent(login5.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
