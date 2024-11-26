package com.example.listenmusic.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.Follow_GoiYAdapter;
import com.example.listenmusic.Adapter.Follow_TopAdapter;
import com.example.listenmusic.R;
import com.example.listenmusic.model.NgheSi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends Fragment {

    private RecyclerView recycler_horizontal;
    private RecyclerView recycler_top;
    private List<NgheSi> goiYNgheSiList = new ArrayList<>();
    private List<NgheSi> topNgheSiList = new ArrayList<>();
    private LinearLayout info_1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FollowFragment() {
    }

    public static FollowFragment newInstance(String param1, String param2) {
        FollowFragment fragment = new FollowFragment();
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
        fetchNgheSiData();
        fetchTopData();
    }

    private void fetchNgheSiData() {
        String url = "http://musictbp.atwebpages.com/Server/GoiYNgheSiPHP.php";
        goiYNgheSiList.clear();
        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            goiYNgheSiList.add(new NgheSi(
                                    obj.getString("idNgheSi"),
                                    obj.getString("tenNgheSi"),
                                    obj.getString("avartar")
                            ));
                        }
                        recycler_horizontal.setAdapter(new Follow_GoiYAdapter(goiYNgheSiList));
                        recycler_horizontal.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ));
    }

    private void fetchTopData() {
        String url = "http://musictbp.atwebpages.com/Server/TopFollowPHP.php";
        topNgheSiList.clear();
        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            topNgheSiList.add(new NgheSi(
                                    obj.getString("idNgheSi"),
                                    obj.getString("tenNgheSi"),
                                    obj.getString("avartar")
                            ));
                        }
                        recycler_top.setAdapter(new Follow_TopAdapter(topNgheSiList));
                        recycler_top.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        recycler_horizontal = view.findViewById(R.id.recyclerView_goiy);
        recycler_horizontal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_top = view.findViewById(R.id.recycler_top);
        recycler_top.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        info_1 = view.findViewById(R.id.info_1);
        info_1.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.follow);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.show();
        });

        return view;
    }
}

