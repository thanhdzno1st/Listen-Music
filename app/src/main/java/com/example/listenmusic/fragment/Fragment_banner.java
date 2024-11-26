package com.example.listenmusic.fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listenmusic.Adapter.BannerAdapter;
import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIRetrofitClient;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_banner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Fragment_banner() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_banner.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_banner newInstance(String param1, String param2) {
        Fragment_banner fragment = new Fragment_banner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        GetDataBanner();
        anhXa();
        return view;
    }



    private void anhXa() {
        viewPager = view.findViewById(R.id.viewpager_banner);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetDataBanner() {
        Dataservice dataservice= APIservice.getService();
        Call<List<Banner>> callback = dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                ArrayList<Banner> banners = (ArrayList<Banner>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if(currentItem >= viewPager.getAdapter().getCount())
                            currentItem =0;
                        viewPager.setCurrentItem(currentItem,true);
                        handler.postDelayed(runnable,2500);
                    }
                };
                handler.postDelayed(runnable,4500);
                Log.d("hihi", banners.get(0).getTenBaiHat());
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}