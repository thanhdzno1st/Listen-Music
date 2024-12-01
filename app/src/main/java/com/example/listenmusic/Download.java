package com.example.listenmusic;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Adapter.SongAdapter;
import com.example.listenmusic.model.BaiHatOffline;

import java.util.ArrayList;
import java.util.List;

public class Download extends AppCompatActivity {
    RelativeLayout bt_on;
    ImageView bt_menu;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download);

        // Khởi tạo drawerLayout và bt_menu
        drawerLayout = findViewById(R.id.drawer_layout);
        bt_menu = findViewById(R.id.bt_menu);

        // Sự kiện khi bấm vào bt_menu
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra và mở menu nếu chưa mở
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        // Sự kiện khi bấm vào bt_on
        bt_on = findViewById(R.id.bt_on);
        bt_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Download.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Cấu hình RecyclerView
        RecyclerView recyclerDownload = findViewById(R.id.recycler_download);
        recyclerDownload.setLayoutManager(new LinearLayoutManager(this));

        List<BaiHatOffline> songList = getSongs();
        SongAdapter songAdapter = new SongAdapter(this, songList);
        recyclerDownload.setAdapter(songAdapter);


    }

    // Lấy danh sách bài hát từ bộ nhớ
    public List<BaiHatOffline> getSongs() {
        List<BaiHatOffline> songs = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int titleColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                String title = songCursor.getString(titleColumn);
                String artist = songCursor.getString(artistColumn);
                songs.add(new BaiHatOffline(title, artist));
            } while (songCursor.moveToNext());

            songCursor.close();
        }
        return songs;
    }
}
