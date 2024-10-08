package com.example.listenmusic;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class info_activity extends AppCompatActivity {
    ListView lvDanhSach;
    ArrayList<danhsach> arraybaihat;
    DanhSachAdapter adapter;
    ImageView button_cmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        adapter = new DanhSachAdapter(R.layout.dong_danh_sach,this, arraybaihat);
        lvDanhSach.setAdapter(adapter);

        button_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void anhxa(){
        button_cmt = findViewById(R.id.btn_comment);
        lvDanhSach = (ListView) findViewById(R.id.list_dsbaihat);
        lvDanhSach.setDivider(null);
        arraybaihat = new ArrayList<>();
        arraybaihat.add(new danhsach("Lạc Trôi","Sơn Tùng MTP",R.drawable.baihat_1));
        arraybaihat.add(new danhsach("Chúng Ta Của Hiện Tại","Sơn Tùng MTP",R.drawable.baihat_2));
        arraybaihat.add(new danhsach("Nắng Ấm Xa Dần","Sơn Tùng MTP",R.drawable.baihat_3));
        arraybaihat.add(new danhsach("Hãy Trao Cho Anh","Sơn Tùng MTP",R.drawable.baihat_4));
        arraybaihat.add(new danhsach("Bình yên những phút giây","Sơn Tùng MTP",R.drawable.baihat_5));
        arraybaihat.add(new danhsach("Making My Way","Sơn Tùng MTP",R.drawable.baihat_6));
        arraybaihat.add(new danhsach("Muộn rồi mà sao còn","Sơn Tùng MTP",R.drawable.baihat_7));
    }
}