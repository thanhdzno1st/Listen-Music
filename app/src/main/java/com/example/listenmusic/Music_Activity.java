package com.example.listenmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import de.hdodenhof.circleimageview.CircleImageView;

public class Music_Activity extends AppCompatActivity {
    private ViewPager2 viewPager2Music,viewPager2dsphat;
    private TabLayout mTablayout;
    private CircleImageView circle_img;
    private ImageView bt_pause,btn_addmusic,btn_dow,bt_random,bt_danhsach,bt_back,bt_setting;
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


    public Integer getFixSeekbar() {
        return fixSeekbar;
    }

    public void setFixSeekbar(Integer fixSeekbar) {
        if (this.fixSeekbar == null) { // Chỉ gán giá trị nếu firstValue chưa được gán
            this.fixSeekbar = fixSeekbar;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();
//        bt_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startAnimation();
//            }
//        });

        ViewPagerAdapter_Music viewPagerAdapterMusic= new ViewPagerAdapter_Music(this);
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
                    fadeInViews(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);  // Fade in các view
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
                    fadeOutViews(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);  // Fade out các view
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
                showDialog();
            }
        });
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
        durationPlayed=findViewById(R.id.durationPlayed);
        durationTotal=findViewById(R.id.durationTotal);
        btn_addmusic=findViewById(R.id.btn_add_music);
        btn_Kbps=findViewById(R.id.btn_Kbps);
        btn_dow=findViewById(R.id.btn_dowload);
        bt_random=findViewById(R.id.btn_random);
        constraint_control=findViewById(R.id.constraint_control);
        mainlayout=findViewById(R.id.main);
        bt_danhsach=findViewById(R.id.btn_danhsachphat);
        layoutMenu = findViewById(R.id.layout_menu);
        bt_back=findViewById(R.id.btn_back);
        bt_setting=findViewById(R.id.btn_caidat);
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
                fadeOutViews(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);
            else
                fadeOutViews2(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);

        } else if (position == 1) {
            Log.d("Distance", "Trang 1");
            animateViewPagerHeightChange(viewPager2Music,viewPagerMusicHeight);
            animateViewPagerHeightChange(viewPager2dsphat,viewPagerdsphatHeight);
            fadeInViews(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);  // Fade in các view
        } else {
            Log.d("Distance", "Toi o day");
            int newHeight = viewPagerMusicHeight + getFixSeekbar() - distanceToBottomBtn;
            int newHeight2= viewPagerdsphatHeight + getFixSeekbar() - distanceToBottomBtn;
            animateViewPagerHeightChange(viewPager2Music,newHeight);
            animateViewPagerHeightChange(viewPager2dsphat,newHeight2);
            fadeOutViews(durationPlayed, durationTotal, btn_addmusic, btn_Kbps, btn_dow);  // Fade out các view
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
    private void showDialog() {
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