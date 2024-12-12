package com.example.listenmusic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.SearchAdapter;
import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHatCon;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<BaiHatCon> baiHatList;
    RecyclerView recyclerView;
    Button btn_search;
    EditText edt_search;
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        baiHatList = new ArrayList<>();
    }

    // Hàm tìm kiếm sẽ gọi fetchSearchData
    public void searchInFragment(String keyword) {
        Log.d("SearchInFragment", "Tìm kiếm với từ khóa: " + keyword);
        fetchSearchData(keyword); // Truyền từ khóa vào phương thức fetchSearchData
    }

    private void fetchSearchData(String keyword) {
        String url = "http://musictbp.atwebpages.com/Server/SearchPHP.php?keyword=" + keyword;

        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        baiHatList.clear();  // Clear danh sách trước khi thêm dữ liệu mới
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            baiHatList.add(new BaiHatCon(
                                    obj.getInt("idBaiHat"),
                                    obj.getString("tenBaiHat"),
                                    obj.getString("hinhBaiHat"),
                                    obj.getString("tenNgheSi")
                            ));
                        }

                        // Kiểm tra recyclerView không null và thiết lập adapter
                        if (recyclerView != null) {
                            SearchAdapter adapter = new SearchAdapter(baiHatList);
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

        // Tìm và thiết lập RecyclerView
        recyclerView = view.findViewById(R.id.recycler_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_search = view.findViewById(R.id.btn_search);
        edt_search = view.findViewById(R.id.edt_search);

        // Thiết lập sự kiện cho btn_search
        btn_search.setOnClickListener(v -> {
            String keyword = edt_search.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchInFragment(keyword); // Gọi phương thức tìm kiếm với từ khóa nhập vào
            } else {
                Toast.makeText(getActivity(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
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
