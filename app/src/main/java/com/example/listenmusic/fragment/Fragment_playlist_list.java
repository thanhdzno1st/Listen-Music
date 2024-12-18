package com.example.listenmusic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.listenmusic.Adapter.Home_recommend_adapter;
import com.example.listenmusic.Adapter.Playlist_list_adapter;
import com.example.listenmusic.Models.Playlist;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_playlist_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_playlist_list extends Fragment implements Playlist_list_adapter.OnPlaylistDeletedListener {
    View view;
    RecyclerView recyclerViewPlaylist;
    Playlist_list_adapter playlistListAdapter;
    ArrayList<Playlist> PlaylistArraylist;
    private static final String ARG_USER = "user";
    private static final String ARG_Song = "song";

    private User user;
    private Song song;

    public static Fragment_playlist_list newInstance(User user, Song song) {
        Fragment_playlist_list fragment = new Fragment_playlist_list();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user); // Truyền đối tượng User vào Bundle
        args.putParcelable(ARG_Song, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist_list, container, false);
        anhXa();

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
            song = getArguments().getParcelable(ARG_Song);
        } else {
            Log.d("AddPlaylistDebug", "Bundle bị null!");
        }

        GetData();
        return view;
    }

    private void anhXa() {
        recyclerViewPlaylist = view.findViewById(R.id.recycler_dsplaylist);
    }

    public void GetData() {
        Dataservice dataservice = APIservice.getService();
        Call<List<Playlist>> callback = dataservice.GetPlayList(user.getIdTaiKhoan());
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    // Làm mới danh sách trước
                    if (PlaylistArraylist == null) {
                        PlaylistArraylist = new ArrayList<>();
                    } else {
                        PlaylistArraylist.clear(); // Xóa dữ liệu cũ
                    }

                    // Cập nhật dữ liệu mới
                    PlaylistArraylist.addAll(response.body());

                    // Log kiểm tra
                    for (Playlist playlist : PlaylistArraylist) {
                        Log.d("hihi", "Tên Playlist: " + playlist.getTenPlayList());
                    }

                    // Nếu adapter chưa khởi tạo, tạo mới adapter và gắn vào RecyclerView
                    if (playlistListAdapter == null) {
                        playlistListAdapter = new Playlist_list_adapter(getActivity(), PlaylistArraylist, user, song, Fragment_playlist_list.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerViewPlaylist.setLayoutManager(linearLayoutManager);
                        recyclerViewPlaylist.setAdapter(playlistListAdapter);
                        recyclerViewPlaylist.invalidate();
                    } else {
                        playlistListAdapter.notifyDataSetChanged();
                    }

                    Toast.makeText(getActivity(), "Dữ liệu đã được làm mới!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("hihi", "Không có dữ liệu trả về từ API!");
                    Toast.makeText(getActivity(), "Không có danh sách playlist để hiển thị.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d("hihi", "Lỗi khi tải dữ liệu bài hát: " + t.getMessage());
                Toast.makeText(getActivity(), "Lỗi tải dữ liệu! Vui lòng kiểm tra kết nối.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức gọi lại khi playlist bị xóa
    @Override
    public void onPlaylistDeleted() {
        // Gọi lại GetData() để tải lại dữ liệu sau khi playlist bị xóa
        GetData();
    }
}
