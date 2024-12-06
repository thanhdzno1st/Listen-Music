package com.example.listenmusic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    Button btnDangKy;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);
        btnDangKy = findViewById(R.id.registerButton);
        tvRegister = findViewById(R.id.tvRegister);
        btnDangKy.setOnClickListener(view ->
        {
            Toast.makeText(register.this, "Chức năng đang được cập nhật, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(register.this, Login_Activity.class);
            startActivity(intent);
        });
        tvRegister.setOnClickListener(view ->
        {
            Intent intent = new Intent(register.this, Login_Activity.class);
            startActivity(intent);
        });
    }
}







