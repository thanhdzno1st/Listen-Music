package com.example.listenmusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listenmusic.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
