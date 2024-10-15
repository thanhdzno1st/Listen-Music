package com.example.listenmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Intro_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // Đảm bảo sử dụng đúng layout

        // Sử dụng Handler để trì hoãn chuyển đổi
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển đến MainActivity (hoặc activity khác)
                Intent intent = new Intent(Intro_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish(); // Kết thúc activity intro để không quay lại nó
            }
        }, 2000); // Thời gian chờ 2 giây
    }
}
