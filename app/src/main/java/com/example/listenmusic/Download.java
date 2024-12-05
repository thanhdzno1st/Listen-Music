package com.example.listenmusic;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import java.util.concurrent.atomic.AtomicInteger;

public class Download extends AppCompatActivity {
    RelativeLayout bt_on;
    ImageView bt_menu;
    private DrawerLayout drawerLayout;

    private RelativeLayout musicPlayerLayout;
    private TextView tvSongTitle;
    private ImageView btnPlayPause;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

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
        SongAdapter songAdapter = new SongAdapter(this, songList, new SongAdapter.OnSongClickListener() {
            @Override
            public void onSongClick(String songPath, String songTitle) {
                playSong(songPath, songTitle);
            }
        });
        recyclerDownload.setAdapter(songAdapter);

        // Ánh xạ trình phát nhạc
        musicPlayerLayout = findViewById(R.id.music_player_layout);
        tvSongTitle = findViewById(R.id.tv_song_title);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        seekBar = findViewById(R.id.seek_bar);
        tvCurrentTime = findViewById(R.id.tv_current_time); // TextView hiển thị thời gian hiện tại
        tvTotalTime = findViewById(R.id.tv_total_time); // TextView hiển thị tổng thời gian

        // Xử lý sự kiện Play/Pause
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (isPlaying) {
                        mediaPlayer.pause();
                        btnPlayPause.setImageResource(R.drawable.ic_play);
                    } else {
                        mediaPlayer.start();
                        btnPlayPause.setImageResource(R.drawable.ic_pause);
                    }
                    isPlaying = !isPlaying;
                }
            }
        });
    }

    private void playSong(String songPath, String songTitle) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
            mediaPlayer.start();

            // Hiển thị trình phát nhạc
            musicPlayerLayout.setVisibility(View.VISIBLE);
            tvSongTitle.setText(songTitle);
            btnPlayPause.setImageResource(R.drawable.ic_pause);
            isPlaying = true;

            // Cập nhật seek bar
            seekBar.setMax(mediaPlayer.getDuration());
            tvTotalTime.setText(formatTime(mediaPlayer.getDuration())); // Hiển thị tổng thời gian

            mediaPlayer.setOnCompletionListener(mp -> {
                btnPlayPause.setImageResource(R.drawable.ic_play);
                isPlaying = false;
            });

            // Khai báo AtomicInteger thay vì int thông thường
            AtomicInteger currentPosition = new AtomicInteger(0);

// Thread để cập nhật seek bar và thời gian
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        try {
                            int pos = mediaPlayer.getCurrentPosition();
                            currentPosition.set(pos);  // Cập nhật giá trị mới
                            seekBar.setProgress(currentPosition.get());
                            runOnUiThread(() -> tvCurrentTime.setText(formatTime(currentPosition.get()))); // Cập nhật thời gian hiện tại
                            Thread.sleep(1000); // Cập nhật mỗi giây
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();


            // Xử lý sự kiện khi người dùng kéo SeekBar
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm định dạng thời gian (ví dụ: 2:30 hoặc 10:15)
    private String formatTime(int timeInMilliseconds) {
        int minutes = (timeInMilliseconds / 1000) / 60;
        int seconds = (timeInMilliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public List<BaiHatOffline> getSongs() {
        List<BaiHatOffline> songs = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int titleColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int dataColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = songCursor.getString(titleColumn);
                String artist = songCursor.getString(artistColumn);
                String path = songCursor.getString(dataColumn);
                songs.add(new BaiHatOffline(title, artist, path));
            } while (songCursor.moveToNext());

            songCursor.close();
        }
        return songs;
    }
}
