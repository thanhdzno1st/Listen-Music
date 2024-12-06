package com.example.listenmusic.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.listenmusic.Fragment_info;
import com.example.listenmusic.Fragment_lyrics;
import com.example.listenmusic.Fragment_music;
import com.example.listenmusic.MainActivity;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.ViewPageAdapter_dsphat;
import com.example.listenmusic.Adapter.ViewPagerAdapter_Music;
import com.example.listenmusic.fragment.Fragment_playlist;
import com.example.listenmusic.fragment.PlaylistFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Music_Activity extends AppCompatActivity {
    private ViewPager2 viewPager2Music,viewPager2dsphat;
    private TabLayout mTablayout;
    private CircleImageView circle_img;
    private ImageView btn_play,bt_previous,bt_next,btn_add_toplaylist,btn_dow,bt_random,bt_danhsach,bt_back,bt_setting,bt_repeat;
    private View indicator;
    private TextView tabTitle,durationPlayed,durationTotal;
    private View indicator1,indicator2,indicator3;
    private SeekBar seekbar;
    private Button btn_Kbps;
    private ConstraintLayout constraint_control;
    private RelativeLayout mainlayout ,layoutMenu;
    private int viewPagerMusicHeight = 0;
    private int viewPagerdsphatHeight = 0;
    private boolean isDanhSachVisible = false;
    private int currentPosition=1;
    private Integer fixSeekbar = null; // Khai báo biến để lưu giá trị đầu tiên
    public static ArrayList<Song> mangSong = new ArrayList<>();
    public static ViewPagerAdapter_Music viewPagerAdapterMusic;
    Fragment_music fragmentMusic;
    Fragment_info fragmentInfo;
    Fragment_lyrics fragmentLyrics;
    MediaPlayer mediaPlayer;
    public Integer getFixSeekbar() {
        return fixSeekbar;
    }
    public void setFixSeekbar(Integer fixSeekbar) {
        if (this.fixSeekbar == null) { // Chỉ gán giá trị nếu firstValue chưa được gán
            this.fixSeekbar = fixSeekbar;
        }
    }
    boolean isNewMusic;
    private User user;
    int positionMusic =0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;
    public Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_music), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        AnhXa();
        GetdataFromIntent();

        viewPagerAdapterMusic= new ViewPagerAdapter_Music(this);
        viewPagerAdapterMusic.AddFragment(fragmentInfo);
        viewPagerAdapterMusic.AddFragment(fragmentMusic);
        viewPagerAdapterMusic.AddFragment(fragmentLyrics);

        viewPager2Music.setAdapter(viewPagerAdapterMusic);
        viewPager2Music.setCurrentItem(1);
        new TabLayoutMediator(mTablayout, viewPager2Music,
                (tab, position) -> tab.setText(viewPagerAdapterMusic.getPageTitle(position))
        ).attach();

        ViewPageAdapter_dsphat viewPageAdapterDsphat= new ViewPageAdapter_dsphat(this);
        viewPager2dsphat.setAdapter(viewPageAdapterDsphat);

        Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Khi tab được chọn, hiển thị tiêu đề của tab đó
                tabTitle.setText(tab.getText());
                tabTitle.startAnimation(animAlpha);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần làm gì khi tab được chọn lại
            }
        });
// Biến để lưu trạng thái

        bt_danhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy layout params hiện tại của layout
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) constraint_control.getLayoutParams();

                if (isDanhSachVisible) {
                    // Trả về trạng thái mặc định (ẩn danh sách phát)
                    params.addRule(RelativeLayout.BELOW, R.id.view_pager2_music);
                    constraint_control.setLayoutParams(params);
                    viewPager2dsphat.setVisibility(View.INVISIBLE);
                    viewPager2Music.setVisibility(View.VISIBLE);
                    layoutMenu.setVisibility(View.VISIBLE);
                    updateViewPager2(currentPosition);
                    updateSeekbar(currentPosition);
                    fadeInViews(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);  // Fade in các view
                    isDanhSachVisible = false;
                } else {
                    // Hiện danh sách phát
                    params.addRule(RelativeLayout.BELOW, R.id.view_pager2_dsphat);
                    constraint_control.setLayoutParams(params);
                    viewPager2dsphat.setVisibility(View.VISIBLE);
                    viewPager2Music.setVisibility(View.INVISIBLE);
                    layoutMenu.setVisibility(View.INVISIBLE);
                    updateViewPager2(3);  // Chuyển đến trang 3
                    updateSeekbar(2);  // Cập nhật seekbar
                    fadeOutViews(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);  // Fade out các view
                    isDanhSachVisible = true;
                }
            }
        });


        ViewTreeObserver viewTreeObserverMusic = viewPager2Music.getViewTreeObserver();
        viewTreeObserverMusic.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Chỉ cần lấy chiều cao của ViewPager2 sau khi layout hoàn tất
                viewPagerMusicHeight = viewPager2Music.getHeight();
                Log.d("gg", "chiều cao viewPager2Music fix cứng: " + viewPagerMusicHeight + " pixels");
                // Loại bỏ listener để không bị gọi lại nhiều lần
                viewPager2Music.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ViewTreeObserver viewTreeObserverds = viewPager2Music.getViewTreeObserver();
        viewTreeObserverds.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPagerdsphatHeight = viewPager2dsphat.getHeight();
                Log.d("gg", "chiều cao viewPager2dsphat fix cứng: " + viewPagerdsphatHeight + " pixels");
                // Loại bỏ listener để không bị gọi lại nhiều lần
                viewPager2dsphat.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        viewPager2Music.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPosition=position;


                // Cập nhật chỉ báo và SeekBar khi thay đổi trang
                updateIndicators(position);
                updateSeekbar(position);
                updateViewPager2(position);

            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Quay lại MainActivity
            }
        });
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog_setting();
            }
        });
        btn_add_toplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_playlist playlistFragment = Fragment_playlist.newInstance(user,song);
                playlistFragment.show(getSupportFragmentManager(), "PlaylistFragment");


            }
        });
        fragmentMusic= (Fragment_music) viewPagerAdapterMusic.fragmentArrayList.get(1);
        if(song != null){
            new PlayMp3().execute(song.getLinkBaiHat());
            btn_play.setImageResource(R.drawable.button_pause);
            String imageUrl = song.getHinhBaiHat(); // Đường dẫn hình ảnh
            applyBlurredBackground(imageUrl);
        }else{
//            if(mangSong.size()>0){
//                new PlayMp3().execute(mangSong.get(0).getLinkBaiHat());
//                btn_play.setImageResource(R.drawable.button_pause);
//                String imageUrl = mangSong.get(0).getHinhBaiHat(); // Đường dẫn hình ảnh
//                applyBlurredBackground(imageUrl);
//            }
        }
        positionMusic = findSongPosition(mangSong,song);
        Log.d("DEBUG", "Thong tin User: " + user);

        eventClick();

    }



    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(viewPagerAdapterMusic.fragmentArrayList.get(1)!=null){
                    if(song!=null){
                        fragmentMusic.Playnhac(song);
                        String imageUrl = song.getHinhBaiHat(); // Đường dẫn hình ảnh
                        applyBlurredBackground(imageUrl);
                        handler.removeCallbacks(this);
//                    }else
//                    if(mangSong.size()>0){
//                        fragmentMusic.Playnhac(mangSong.get(0));
//                        String imageUrl = mangSong.get(0).getHinhBaiHat(); // Đường dẫn hình ảnh
//                        applyBlurredBackground(imageUrl);
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }

            }
        },500);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btn_play.setImageResource(R.drawable.button_play);
                    fragmentMusic.stopAnimation();
                }else{
                    mediaPlayer.start();
                    btn_play.setImageResource(R.drawable.button_pause);
                    fragmentMusic.startAnimation();
                }
            }
        });
        bt_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat == false) {
                    if(checkrandom == true){
                        checkrandom = false;
                        bt_repeat.setImageResource(R.drawable.active_repeate);
                        bt_random.setImageResource(R.drawable.icon_random);
                    }
                    bt_repeat.setImageResource(R.drawable.active_repeate);
                    repeat = true;
                }else{
                    bt_repeat.setImageResource(R.drawable.repeat);
                    repeat=false;
                }
            }
        });
        bt_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkrandom == false) {
                    if(repeat == true){
                        repeat = false;
                        bt_repeat.setImageResource(R.drawable.repeat);
                        bt_random.setImageResource(R.drawable.active_random);
                    }
                    bt_random.setImageResource(R.drawable.active_random);
                    checkrandom = true;
                }else{
                    bt_random.setImageResource(R.drawable.icon_random);
                    checkrandom=false;
                }
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mangSong.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    if(positionMusic < (mangSong.size())){
                        btn_play.setImageResource(R.drawable.button_pause);
                        positionMusic++;
                        if(repeat == true){
                            if(positionMusic == 0 ){
                                positionMusic = mangSong.size();
                            }
                            positionMusic -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangSong.size());
                            if(index == positionMusic){
                                positionMusic = index - 1;
                            }
                            positionMusic = index;
                        }
                        if(positionMusic >(mangSong.size()-1)){
                            positionMusic = 0;
                        }
                        new PlayMp3().execute(mangSong.get(positionMusic).getLinkBaiHat());
                        fragmentMusic.Playnhac(mangSong.get(positionMusic));
                        String imageUrl = mangSong.get(positionMusic).getHinhBaiHat(); // Đường dẫn hình ảnh
                        applyBlurredBackground(imageUrl);
                        UpdateTime();
                    }
                }

            }
        });
        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mangSong.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    if(positionMusic < (mangSong.size())){
                        btn_play.setImageResource(R.drawable.button_pause);
                        positionMusic--;
                        if(positionMusic<0){
                            positionMusic=mangSong.size()-1;
                        }
                        if(repeat == true){

                            positionMusic += 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangSong.size());
                            if(index == positionMusic){
                                positionMusic = index - 1;
                            }
                            positionMusic = index;
                        }

                        new PlayMp3().execute(mangSong.get(positionMusic).getLinkBaiHat());
                        fragmentMusic.Playnhac(mangSong.get(positionMusic));
                        String imageUrl = mangSong.get(positionMusic).getHinhBaiHat(); // Đường dẫn hình ảnh
                        applyBlurredBackground(imageUrl);
                        UpdateTime();
                    }
                }
            }
        });
    }


    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                Log.e("PlayMp3", "Link bài hát: " + baihat);
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.e("PlayMp3", "Link bài hát lỗi: " + baihat);
                Log.e("PlayMp3", "lỗi: " + e);

                throw new RuntimeException(e);

            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        durationTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekbar.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null)
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    durationPlayed.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }
        },300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true){
                    if(positionMusic < (mangSong.size())){
                        btn_play.setImageResource(R.drawable.button_pause);
                        positionMusic++;
                        if(repeat == true){
                            if(positionMusic == 0 ){
                                positionMusic = mangSong.size();
                            }
                            positionMusic -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangSong.size());
                            if(index == positionMusic){
                                positionMusic = index - 1;
                            }
                            positionMusic = index;
                        }
                        if(positionMusic >(mangSong.size()-1)){
                            positionMusic = 0;
                        }
                        new PlayMp3().execute(mangSong.get(positionMusic).getLinkBaiHat());
                        fragmentMusic.Playnhac(mangSong.get(positionMusic));
                        String imageUrl = mangSong.get(positionMusic).getHinhBaiHat(); // Đường dẫn hình ảnh
                        applyBlurredBackground(imageUrl);
                    }
                    next = false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
    }

    private void GetdataFromIntent() {
        Bundle bundleReceive = getIntent().getExtras();
        Intent intent = getIntent();
        mangSong.clear();
        if(intent!=null)
        {
            if (intent.hasExtra("cakhuc")) {
                song = intent.getParcelableExtra("cakhuc");
                if (song != null) {
                    Log.d("DEBUG", "Nhận được một bài hát từ Intent: " + song.getTenBaiHat());
                } else {
                    Log.d("DEBUG", "Bài hát từ Intent 'cakhuc' là null.");
                }
            }

            if (intent.hasExtra("cacbaihat")) {
                ArrayList<Song> baihatArraylist = intent.getParcelableArrayListExtra("cacbaihat");
                if (baihatArraylist != null && !baihatArraylist.isEmpty()) {
                    Log.d("DEBUG", "Nhận được danh sách bài hát từ Intent: " + baihatArraylist.size() + " bài hát.");
                    for (Song s : baihatArraylist) {
                        Log.d("DEBUG", "Bài hát: " + s.toString());
                    }
                    mangSong = baihatArraylist;
                } else {
                    Log.d("DEBUG", "Danh sách bài hát từ Intent 'cacbaihat' rỗng hoặc null.");
                }
            }
            if (intent.hasExtra("user")) {
                user = (User) bundleReceive.get("user");
                if (user!=null) {
                    Log.d("DEBUG", "Da truyen user vao Music_activity"+user);
                } else {
                    Log.d("DEBUG", "Chua truyen dc user vao Music_activity");
                }
            }
            if (intent.hasExtra("new_music")) {
                isNewMusic = intent.getBooleanExtra("new_music",false);
                if (isNewMusic) {
                    Log.d("DEBUG", "Bai hat moi");
                } else {
                    Log.d("DEBUG", "Bai hat cu");
                }
            }
        }

    }

    private void AnhXa() {
        mTablayout = findViewById(R.id.tab_layout_music);
        viewPager2Music= findViewById(R.id.view_pager2_music);
        viewPager2dsphat=findViewById(R.id.view_pager2_dsphat);
        tabTitle = findViewById(R.id.tab_title);
        indicator1 = findViewById(R.id.indicator_1);
        indicator2 = findViewById(R.id.indicator_2);
        indicator3 = findViewById(R.id.indicator_3);
        seekbar = findViewById(R.id.seekbar);
        durationPlayed=findViewById(R.id.txtTimeSong);
        durationTotal=findViewById(R.id.txtTotaltimesong);
        btn_add_toplaylist=findViewById(R.id.btn_add_music_to_playlist);
        btn_Kbps=findViewById(R.id.btn_Kbps);
        btn_dow=findViewById(R.id.btn_dowload);
        bt_random=findViewById(R.id.btn_random);
        constraint_control=findViewById(R.id.constraint_control);
        mainlayout=findViewById(R.id.main_music);
        bt_danhsach=findViewById(R.id.btn_danhsachphat);
        layoutMenu = findViewById(R.id.layout_menu);
        bt_back=findViewById(R.id.btn_back);
        bt_setting=findViewById(R.id.btn_caidat);
        btn_play= findViewById(R.id.btn_play);
        fragmentMusic= new Fragment_music();
        fragmentInfo=new Fragment_info();
        fragmentLyrics=new Fragment_lyrics();
        bt_repeat = findViewById(R.id.btn_repeat);
        bt_next = findViewById(R.id.btn_next);
        bt_previous = findViewById(R.id.btn_previous);
    }
    public static int findSongPosition(ArrayList<Song> mangSong, Song targetSong) {
        for (int i = 0; i < mangSong.size(); i++) {
            if (mangSong.get(i).getTenBaiHat().equals(targetSong.getTenBaiHat())) {
                return i; // Trả về vị trí của bài hát
            }
        }
        return -1; // Không tìm thấy
    }
    // Hàm cập nhật các chỉ báo
    private void updateIndicators(int position) {
        // Reset về trạng thái ban đầu (xám và ngắn)
        resetIndicators();

        // Dựa vào vị trí trang, cập nhật chỉ báo tương ứng
        switch (position) {
            case 0:
                updateIndicator(indicator1, true); // Trang đầu tiên, cập nhật indicator1
                updateSeekbar(position);
                break;
            case 1:
                updateIndicator(indicator2, true); // Trang thứ hai, cập nhật indicator2
                break;
            case 2:
                updateIndicator(indicator3, true); // Trang thứ ba, cập nhật indicator3
                updateSeekbar(position);
                break;
        }
    }
    private void applyBlurredBackground(String imageUrl) {
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap blurredBitmap = blurBitmap(resource, 25); // 25 là mức độ blur
                        Drawable background = new BitmapDrawable(getResources(), addDarkOverlay(blurredBitmap, 0.3f));
                        mainlayout.setBackground(background);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    private Bitmap addDarkOverlay(Bitmap bitmap, float darkenFactor) {
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        // Áp dụng lớp tối (màu đen với độ trong suốt)
        int overlayColor = Color.argb((int) (darkenFactor * 255), 0, 0, 0); // darkenFactor là mức tối (0.5 = 50%)
        paint.setColor(overlayColor);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        return resultBitmap;
    }

    private Bitmap blurBitmap(Bitmap bitmap, float radius) {
        // Sử dụng RenderScript để làm mờ
        RenderScript rs = RenderScript.create(this);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        rs.destroy();
        return bitmap;
    }
    // Hàm cập nhật một indicator thành màu trắng và dài hơn
    private void updateIndicator(View indicator, boolean isSelected) {
        // Nếu là trang được chọn
        if (isSelected) {
            indicator.setBackgroundColor(getResources().getColor(R.color.white));
            ViewGroup.LayoutParams params = indicator.getLayoutParams();
            params.width = 30; // Tăng độ dài của indicator
            indicator.setLayoutParams(params);
        }
    }

    // Hàm reset tất cả indicators về trạng thái ban đầu (màu xám, ngắn)
    private void resetIndicators() {
        // Reset indicator1
        indicator1.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params1 = indicator1.getLayoutParams();
        params1.width = 15; // Trở về kích thước ngắn hơn
        indicator1.setLayoutParams(params1);

        // Reset indicator2
        indicator2.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params2 = indicator2.getLayoutParams();
        params2.width = 15;
        indicator2.setLayoutParams(params2);

        // Reset indicator3
        indicator3.setBackgroundColor(getResources().getColor(R.color.grey));
        ViewGroup.LayoutParams params3 = indicator3.getLayoutParams();
        params3.width = 15;
        indicator3.setLayoutParams(params3);
    }

    private void updateSeekbar(int position) {
        int targetWidth; // Chiều dài mới của SeekBar

        // Xác định chiều dài mới của SeekBar dựa vào vị trí trang
        if (position == 0 || position == 2 || position ==3) {
            targetWidth = 660; // Chiều dài của SeekBar ở trang 0 hoặc 2
        } else if (position == 1) {
            targetWidth = 960; // Chiều dài của SeekBar ở trang 1
        } else {
            targetWidth = seekbar.getWidth(); // Giữ nguyên nếu position không phải 0, 1, hoặc 2
        }

        // Tạo animation để thay đổi chiều dài của SeekBar
        final int initialWidth = seekbar.getWidth();
        ValueAnimator animator = ValueAnimator.ofInt(initialWidth, targetWidth);
        animator.setDuration(300); // Thời gian animation (500ms)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newWidth = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = seekbar.getLayoutParams();
                layoutParams.width = newWidth; // Cập nhật chiều rộng mới
                seekbar.setLayoutParams(layoutParams); // Áp dụng layoutParams mới
            }
        });
        animator.start(); // Bắt đầu animation
    }

    private void fadeOutViews(View... views) {
        for (View view : views) {
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            fadeOut.setDuration(300); // Thời gian animation (300ms)
            fadeOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE); // Ẩn view sau khi fade out
                }
            });
            fadeOut.start(); // Bắt đầu animation
        }
    }
    private void fadeOutViews2(View... views) {
        for (View view : views) {
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            fadeOut.setDuration(1); // Thời gian animation (300ms)
            fadeOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE); // Ẩn view sau khi fade out
                }
            });
            fadeOut.start(); // Bắt đầu animation
        }
    }
    private void fadeInViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE); // Hiện view trước khi bắt đầu animation
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeIn.setDuration(300); // Thời gian animation (300ms)
            fadeIn.start(); // Bắt đầu animation
        }
    }
    private void updateViewPager2(int position){
        // Lấy chiều cao hiện tại của ViewPager2
        int currentViewPagerHeight = viewPager2Music.getHeight();


        // Tính toán các vị trí và kích thước của các thành phần
        int[] locationOnScreen = new int[2];
        bt_random.getLocationOnScreen(locationOnScreen);
        int btnMiddle = locationOnScreen[1] + (bt_random.getHeight() / 2);
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int distanceToBottomBtn = screenHeight - btnMiddle;
        Log.d("Distance", "Khoảng cách từ giữa btn_random đến đáy màn hình: " + distanceToBottomBtn + " pixels");

        // Tính khoảng cách từ giữa SeekBar đến đáy màn hình
        int[] seekBarLocation = new int[2];
        seekbar.getLocationOnScreen(seekBarLocation);
        int seekBarMiddle = seekBarLocation[1] + (seekbar.getHeight() / 2);
        int distanceToBottomSeekBar = screenHeight - seekBarMiddle;
        setFixSeekbar(distanceToBottomSeekBar);
        Log.d("Distance", "Khoảng cách từ giữa SeekBar đến đáy màn hình: " + distanceToBottomSeekBar + " pixels");

        // Lấy chiều cao của ConstraintLayout bao bọc
        Log.d("Distance", "Chiều cao của constraint: " + constraint_control.getHeight() + " pixels");
        int layoutmenu = layoutMenu.getHeight();
        // Tính toán chiều cao mới của ViewPager2 khi chuyển đổi các trang
        if (position == 0 || position == 2) {
            int newHeight = viewPagerMusicHeight + getFixSeekbar() - distanceToBottomBtn;
            int newHeight2= viewPagerdsphatHeight + getFixSeekbar() - distanceToBottomBtn;
            animateViewPagerHeightChange(viewPager2Music,newHeight);
            animateViewPagerHeightChange(viewPager2dsphat,newHeight2);
            if (viewPager2Music.getHeight() != newHeight)
                // Fade out the specified views
                fadeOutViews(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);
            else
                fadeOutViews2(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);

        } else if (position == 1) {
            Log.d("Distance", "Trang 1");
            animateViewPagerHeightChange(viewPager2Music,viewPagerMusicHeight);
            animateViewPagerHeightChange(viewPager2dsphat,viewPagerdsphatHeight);
            fadeInViews(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);  // Fade in các view
        } else {
            Log.d("Distance", "Toi o day");
            int newHeight = viewPagerMusicHeight + getFixSeekbar() - distanceToBottomBtn;
            int newHeight2= viewPagerdsphatHeight + getFixSeekbar() - distanceToBottomBtn;
            animateViewPagerHeightChange(viewPager2Music,newHeight);
            animateViewPagerHeightChange(viewPager2dsphat,newHeight2);
            fadeOutViews(durationPlayed, durationTotal, btn_add_toplaylist, btn_Kbps, btn_dow);  // Fade out các view
        }
        Log.d("Distance", "Chiều cao của viewPagerMusic : " + viewPager2Music.getHeight() + " pixels");
        Log.d("Distance", "Chiều cao của viewPagerdsphat : " + viewPager2dsphat.getHeight() + " pixels");
    }

    /**
     * Hàm để thực hiện animation thay đổi chiều cao của ViewPager2
     */
    private void animateViewPagerHeightChange(ViewPager2 viewPager, int newHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(viewPager.getHeight(), newHeight);
        animator.setDuration(300); // 0.3 giây
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            viewPager.setLayoutParams(params);
        });
        animator.start();
    }
    private void showDialog_setting() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_setting);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}