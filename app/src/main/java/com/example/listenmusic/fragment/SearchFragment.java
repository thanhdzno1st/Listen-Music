package com.example.listenmusic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.SearchAdapter;
import com.example.listenmusic.Activity.MainActivity;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String ARG_USER = "user"; // Key cho User
    private User user; // Biến để lưu trữ User
    private List<Song> baiHatList;  // Khai báo baiHatList
    private RecyclerView recyclerView;
    private SearchView search_view;
    private ImageView btn_back;
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(User user) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user); // Truyền User qua Bundle
        fragment.setArguments(args);
        return fragment;
    }

    public void searchInFragment(String keyword) {
        Log.d("SearchInFragment", "Tìm kiếm với từ khóa: " + keyword);
        fetchSearchData(keyword); // Truyền từ khóa vào phương thức fetchSearchData
    }

    private void fetchSearchData(String keyword) {
        // Khởi tạo baiHatList nếu chưa khởi tạo
        if (baiHatList == null) {
            baiHatList = new ArrayList<>();
        }

        String url = "http://musictbp.atwebpages.com/Server/SearchPHP.php?keyword=" + keyword;

        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        baiHatList.clear();  // Clear danh sách trước khi thêm dữ liệu mới
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            baiHatList.add(new Song(
                                    obj.getString("idBaiHat"),
                                    obj.getString("tenBaiHat"),
                                    obj.getString("hinhBaiHat"),
                                    obj.getString("tenNgheSi"),
                                    obj.getString("ngayPhatHanh"),  // New field
                                    obj.getString("linkBaiHat"),    // New field
                                    obj.getString("idDanhMuc"),        // New field
                                    obj.getString("idNgheSi")          // New field
                            ));
                        }

                        // Kiểm tra recyclerView không null và thiết lập adapter
                        if (recyclerView != null) {
                            SearchAdapter adapter = new SearchAdapter(baiHatList,user);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user"); // Lấy đối tượng User
            Log.d("Debug SearchFragment", "toi dang Lấy đối tượng User voi user la: "+user);

        }
        // Khởi tạo baiHatList nếu chưa khởi tạo
        if (baiHatList == null) {
            baiHatList = new ArrayList<>();
        }

        // Tìm và thiết lập RecyclerView
        recyclerView = view.findViewById(R.id.recycler_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        search_view = view.findViewById(R.id.edt_search);
        btn_back = view.findViewById(R.id.bt_back_search);
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng nhấn Enter, tìm kiếm với từ khóa
                if (!query.isEmpty()) {
                    searchInFragment(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tìm kiếm khi người dùng gõ vào ô tìm kiếm
                if (!newText.isEmpty()) {
                    searchInFragment(newText);
                }
                return false;
            }
        });

        btn_back.setOnClickListener(v -> {
            // Lấy đối tượng MainActivity (hoặc Activity hiện tại)
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.viewpager.setAdapter(mainActivity.adapter);
                mainActivity.viewpager.setCurrentItem(2);

                // Ẩn header
                mainActivity.layout_header.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Gọi searchInFragment() với từ khóa cần tìm kiếm từ mParam1 nếu có
        if (mParam1 != null && !mParam1.isEmpty()) {
            searchInFragment(mParam1); // Ví dụ, bạn có thể dùng mParam1 làm từ khóa tìm kiếm
        }
    }
}

