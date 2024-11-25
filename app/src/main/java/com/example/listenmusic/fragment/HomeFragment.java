package com.example.listenmusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.Home_DexuatAdapter;
import com.example.listenmusic.HomeBannerViewPagerAdapter;
import com.example.listenmusic.Music_Activity;
import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHat;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private List<Integer> layoutList = new ArrayList<>();
    private RecyclerView recycler_horizontal;
    private List<BaiHat> baiHatList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        runnable = () -> {
            if (currentPage >= layoutList.size()) currentPage = 0;
            viewPager.setCurrentItem(currentPage++, true);
            handler.postDelayed(runnable, 1500);
        };
        fetchSongData();
    }

    private void fetchSongData() {
        String url = "http://musictbp.atwebpages.com/Server/DexuatPHP.php";
        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            baiHatList.add(new BaiHat(
                                    obj.getString("tenBaiHat"),
                                    obj.getString("hinhAnh"),
                                    obj.getString("tenNgheSi")
                            ));
                        }
                        recycler_horizontal.setAdapter(new Home_DexuatAdapter(baiHatList));
                        recycler_horizontal.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewpager_banner);
        recycler_horizontal = view.findViewById(R.id.recycler_horizontal);
        recycler_horizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        HomeBannerViewPagerAdapter adapter = new HomeBannerViewPagerAdapter(getActivity().getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        layoutList.add(R.drawable.img_1);
        layoutList.add(R.drawable.img_2);
        layoutList.add(R.drawable.img_3);
        handler.postDelayed(runnable, 500);
        view.findViewById(R.id.kedcchon).setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Music_Activity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) handler.removeCallbacks(runnable);
    }
}
