package com.example.listenmusic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Adapter.Home_recommend_adapter;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_home_recommend extends Fragment {
    View view;
    RecyclerView recyclerViewSongRecommend;
    Home_recommend_adapter homeRecommendAdapter;
    private User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_recommend,container,false);
        anhXa();
        GetDataHome_recommend();
        return view;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private void anhXa() {
        recyclerViewSongRecommend = view.findViewById(R.id.recycler_dsbaihatdexuat);
    }

    private void GetDataHome_recommend() {
        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.GetSong_recommend();
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                ArrayList<Song> SongArraylist = (ArrayList<Song>) response.body();
                homeRecommendAdapter = new Home_recommend_adapter(getActivity(),SongArraylist,user);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewSongRecommend.setLayoutManager(linearLayoutManager);
                recyclerViewSongRecommend.setAdapter(homeRecommendAdapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("hihi", "Lỗi khi tải dữ liệu bài hát: " + t.getMessage());
            }
        });
    }
}