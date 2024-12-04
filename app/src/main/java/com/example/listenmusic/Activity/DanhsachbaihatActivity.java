package com.example.listenmusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.listenmusic.Adapter.danhsachbaihatAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerViewDsbaihat;
    private FloatingActionButton floatingActionButton;
    private Banner banner;
    private ImageView imgdanhsachcakhuc;
    private ArrayList<Song> mangSong;
    danhsachbaihatAdapter danhsachbaihatAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);

        // Xử lý Edge-to-Edge Layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coordinatorlayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        anhxa();

        // Xử lý Intent
        DataIntent();

        // Cài đặt Toolbar
        init();

        // Hiển thị dữ liệu Banner
        if (banner != null && !banner.getTenBaiHat().isEmpty()) {
            setValueInView(banner.getTenBaiHat(), banner.getHinhBaiHat(),banner.getHinhAnhBanner());
            Getdataquangcao(banner.getIdBanner());
        }
    }

    private void Getdataquangcao(String Idquangcao) {
        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheobanner(Idquangcao);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                mangSong = new ArrayList<>(response.body());
                danhsachbaihatAdapter= new danhsachbaihatAdapter(DanhsachbaihatActivity.this,mangSong);
                recyclerViewDsbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDsbaihat.setAdapter(danhsachbaihatAdapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                // Xử lý lỗi
                t.printStackTrace();
                Toast.makeText(DanhsachbaihatActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValueInView(String ten, String hinhbaihat,String hinhbanner) {
        collapsingToolbarLayout.setTitle(ten);

        // Sử dụng Glide để tải ảnh và hiển thị trong ImageView
        Glide.with(this).load(hinhbaihat).into(imgdanhsachcakhuc);

        // Đặt nền cho CollapsingToolbarLayout (nếu cần)
        Glide.with(this).load(hinhbanner).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                collapsingToolbarLayout.setBackground(resource);

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white); // Đặt icon tùy chỉnh
        floatingActionButton.setEnabled(false);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void anhxa() {
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsach);
        recyclerViewDsbaihat = findViewById(R.id.recycler_dsbaihat);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        imgdanhsachcakhuc = findViewById(R.id.img_dscakhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("banner")) {
            banner = (Banner) intent.getSerializableExtra("banner");
        }
    }
    private void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhsachbaihatActivity.this, Music_Activity.class);
                // Tạo một Bundle để chứa các giá trị
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("cacbaihat", mangSong);  // Truyền danh sách bài hát
                bundle.putBoolean("new_music", true);  // Truyền giá trị new_music là true

                // Gửi Bundle vào Intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
