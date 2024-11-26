package com.example.listenmusic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Adapter.Home_recommend_adapter;
import com.example.listenmusic.Adapter.Home_tiktok_adapter;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_home_tiktok extends Fragment {
    View view;
    RecyclerView recycler_dsbaihattiktok;
    Home_tiktok_adapter homeTiktokAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_tiktok,container,false);
        anhXa();
        GetDataHome_tiktok();
        return view;
    }

    private void anhXa() {
        recycler_dsbaihattiktok = view.findViewById(R.id.recycler_dsbaihattiktok);

    }

    private void GetDataHome_tiktok() {
        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.GetSong_hottiktok();
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                ArrayList<Song> fullSongList = (ArrayList<Song>) response.body();
                if (fullSongList != null && fullSongList.size() >= 9) {

                    // Chia danh sách bài hát thành 3 phần mỗi phần 3 bài hát
                    ArrayList<Song> list1 = new ArrayList<>(fullSongList.subList(0, 3));
                    ArrayList<Song> list2 = new ArrayList<>(fullSongList.subList(3, 6));
                    ArrayList<Song> list3 = new ArrayList<>(fullSongList.subList(6, 9));

                    // Gộp các danh sách này lại thành một danh sách lớn
                    ArrayList<Song> combinedList = new ArrayList<>();
                    combinedList.addAll(list1);
                    combinedList.addAll(list2);
                    combinedList.addAll(list3);

                    // Thiết lập adapter cho RecyclerView
                    homeTiktokAdapter = new Home_tiktok_adapter(getActivity(), combinedList);

                    // Sử dụng GridLayoutManager để tạo layout kiểu 3 cột
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.HORIZONTAL, false);
                    recycler_dsbaihattiktok.setLayoutManager(gridLayoutManager);
                    recycler_dsbaihattiktok.setAdapter(homeTiktokAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("hihi", "Lỗi khi tải dữ liệu bài hát: " + t.getMessage());
            }
        });
    }
}
