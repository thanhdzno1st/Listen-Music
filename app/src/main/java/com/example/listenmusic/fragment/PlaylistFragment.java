package com.example.listenmusic.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listenmusic.Adapter.Playlist_list_adapter;
import com.example.listenmusic.Models.Playlist;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {
    private LinearLayout btn_playlist;
    private Dialog dialog;
    private EditText etPlaylistName;
    private Button btnAddPlaylist, btnclose;
    private User user;
    private Bundle bundle;
    private static final String ARG_USER = "user";
    private FragmentManager fragmentManager;
    private Fragment_playlist_list fragmentPlaylistList;
    private FragmentTransaction transaction;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    public static PlaylistFragment newInstance(User user) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user); // Truyền đối tượng User vào Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        btn_playlist = view.findViewById(R.id.bt_playlist);
        createDialog();

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
            if (user != null) {
                Log.d("AddPlaylistDebug", "User: " + user.toString());
            } else {
                Log.d("AddPlaylistDebug", "User bị null trong Bundle!");
            }
        } else {
            Log.d("AddPlaylistDebug", "Bundle bị null!");
        }

        // Setup fragment để hiển thị danh sách playlist
        setupPlaylistFragment();

        return view;
    }

    private void setupPlaylistFragment() {
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragmentPlaylistList = Fragment_playlist_list.newInstance(user);
        transaction.replace(R.id.fragment_container_playlist, fragmentPlaylistList);
        transaction.commit();
    }

    private void createDialog() {
        btn_playlist.setOnClickListener(view -> {
            // Tạo một dialog mới để nhập tên playlist
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.create_playlist);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);

            // Ánh xạ các thành phần trong dialog
            etPlaylistName = dialog.findViewById(R.id.Playlist_name);
            btnAddPlaylist = dialog.findViewById(R.id.btn_accept);
            btnclose = dialog.findViewById(R.id.btn_skip);

            // Sự kiện khi nhấn nút thêm playlist
            btnAddPlaylist.setOnClickListener(v -> {
                String playlistName = etPlaylistName.getText().toString();
                if (!playlistName.isEmpty()) {
                    addPlaylist(playlistName, user.getIdTaiKhoan(), "0");
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên playlist!", Toast.LENGTH_SHORT).show();
                }
            });

            // Đóng dialog khi nhấn nút đóng
            btnclose.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        });
    }

    private void addPlaylist(String tenPlayList, int idTaiKhoan, String idBaiHat) {
        Log.d("AddPlaylistDebug", "Bắt đầu gọi API thêm playlist...");
        Dataservice dataservice = APIservice.getService();
        Call<ResponseBody> callback = dataservice.AddPlaylist(tenPlayList, idTaiKhoan, idBaiHat);
        callback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handleApiResponse(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("AddPlaylistError", "Kết nối thất bại: " + t.getMessage(), t);
                Toast.makeText(getActivity(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleApiResponse(Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if (status.equals("success")) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Playlist đã được thêm: " + message, Toast.LENGTH_SHORT).show();
                    refreshPlaylist();
                } else {
                    Toast.makeText(getActivity(), "Lỗi thêm playlist: " + message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("AddPlaylistError", "Lỗi khi xử lý JSON: " + e.getMessage(), e);
                Toast.makeText(getActivity(), "Lỗi xử lý phản hồi từ server.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("AddPlaylistError", "Phản hồi thất bại. Response code: " + response.code());
            try {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "errorBody is null";
                Log.e("AddPlaylistError", "Chi tiết lỗi: " + errorBody);
                Toast.makeText(getActivity(), "Thêm playlist thất bại! Mã lỗi: " + response.code(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("AddPlaylistError", "Lỗi khi đọc errorBody: " + e.getMessage(), e);
                Toast.makeText(getActivity(), "Thêm playlist thất bại! Không thể đọc lỗi.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void refreshPlaylist() {
        if (fragmentPlaylistList != null) {
            fragmentPlaylistList.GetData();
        } else {
            Toast.makeText(getActivity(), "Không thể làm mới danh sách playlist.", Toast.LENGTH_SHORT).show();
        }
    }
}
