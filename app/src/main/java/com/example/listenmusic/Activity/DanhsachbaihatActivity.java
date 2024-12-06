package com.example.listenmusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.listenmusic.Models.Playlist;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
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
    private User user;
    private Playlist playlist;
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
        }else{
            GetdataPlaylist(playlist.getIdPlaylist(),user.getIdTaiKhoan());

        }
    }

    private void GetdataPlaylist(String idPlaylist, int idTaiKhoan) {
        Log.d("GetdataPlaylist", "Bắt đầu gọi API với idPlaylist: " + idPlaylist + " và idTaiKhoan: " + idTaiKhoan);

        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheoplaylist(idPlaylist, idTaiKhoan);

        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d("GetdataPlaylist", "API trả về phản hồi: " + response.code() + ", dữ liệu: " + (response.body() != null ? response.body().size() : 0) + " bài hát.");

                if (response.isSuccessful() && response.body() != null) {
                    mangSong = new ArrayList<>(response.body());
                    Log.d("GetdataPlaylist", "Dữ liệu bài hát đã được tải thành công. Số lượng bài hát: " + mangSong.size());

                    if (!mangSong.isEmpty()) {
                        danhsachbaihatAdapter = new danhsachbaihatAdapter(DanhsachbaihatActivity.this, mangSong, user);
                        recyclerViewDsbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                        recyclerViewDsbaihat.setAdapter(danhsachbaihatAdapter);
                        Log.d("GetdataPlaylist", "Adapter đã được thiết lập và RecyclerView đã được cập nhật.");
                    } else {
                        Log.d("GetdataPlaylist", "Playlist không có bài hát nào.");
                        Toast.makeText(DanhsachbaihatActivity.this, "Không có bài hát nào trong playlist của bạn!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("GetdataPlaylist", "Lỗi khi gọi API. Mã lỗi: " + response.code());
                    Toast.makeText(DanhsachbaihatActivity.this, "Không thể tải dữ liệu từ playlist. Mã lỗi: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.e("GetdataPlaylist", "Gọi API thất bại. Lỗi: " + t.getMessage(), t);
                t.printStackTrace();
                Toast.makeText(DanhsachbaihatActivity.this, "Không thể tải dữ liệu từ playlist. Chi tiết lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void Getdataquangcao(String Idquangcao) {
        if (Idquangcao == null || Idquangcao.isEmpty()) {
            Toast.makeText(this, "ID quảng cáo không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheobanner(Idquangcao);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangSong = new ArrayList<>(response.body());
                    Log.d("API_RESPONSE", "Danh sách bài hát: " + mangSong.toString());
                    Log.d("API_RESPONSE", "bài hát banner: " + mangSong.get(0));

                    if (!mangSong.isEmpty()) {
                        String idNgheSi = mangSong.get(0).getIdNgheSi();
                        if (idNgheSi != null && !idNgheSi.isEmpty()) {
                            GetdatadsBaihattheoNghesi(idNgheSi);
                        } else {
                            Toast.makeText(DanhsachbaihatActivity.this, "Không có nghệ sĩ liên quan!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DanhsachbaihatActivity.this, "Không có bài hát từ quảng cáo!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DanhsachbaihatActivity.this, "API trả về lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DanhsachbaihatActivity.this, "Không thể tải dữ liệu từ quảng cáo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetdatadsBaihattheoNghesi(String IdNghesi) {
        if (IdNghesi == null || IdNghesi.isEmpty()) {
            Toast.makeText(this, "ID nghệ sĩ không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheonghesi(IdNghesi);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangSong = new ArrayList<>(response.body());

                    if (!mangSong.isEmpty()) {
                        danhsachbaihatAdapter = new danhsachbaihatAdapter(DanhsachbaihatActivity.this, mangSong,user);
                        recyclerViewDsbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                        recyclerViewDsbaihat.setAdapter(danhsachbaihatAdapter);
                    } else {
                        Toast.makeText(DanhsachbaihatActivity.this, "Không có bài hát nào từ nghệ sĩ!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DanhsachbaihatActivity.this, "API trả về lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DanhsachbaihatActivity.this, "Không thể tải dữ liệu từ nghệ sĩ", Toast.LENGTH_SHORT).show();
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
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            banner = (Banner) bundleReceive.get("banner");
            user = (User) bundleReceive.get("user");
            playlist = (Playlist) bundleReceive.get("playlist");
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
