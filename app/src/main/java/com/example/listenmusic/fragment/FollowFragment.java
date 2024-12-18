package com.example.listenmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.Follow_ChaAdapter;
import com.example.listenmusic.Adapter.Follow_GoiYAdapter;
import com.example.listenmusic.Adapter.Follow_TopAdapter;
import com.example.listenmusic.R;
import com.example.listenmusic.Models.BaiHatCon;
import com.example.listenmusic.Models.NgheSi;
import com.example.listenmusic.Models.NgheSiCha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FollowFragment extends Fragment {

    private RecyclerView recycler_horizontal;
    private RecyclerView recycler_top;
    private RecyclerView recycler_cha;
    private List<NgheSi> goiYNgheSiList = new ArrayList<>();
    private List<NgheSi> topNgheSiList = new ArrayList<>();
    private List<NgheSiCha> chaNgheSiList = new ArrayList<>();
    private boolean isDataLoaded = false; // Flag to check if data is already loaded

    public FollowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initially, data is not loaded
        isDataLoaded = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Only fetch data if it hasn't been loaded before
        if (!isDataLoaded) {
            fetchNgheSiData();
            fetchTopData();
            fetchChaData();
            isDataLoaded = true; // Mark data as loaded
        }
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

    private void fetchChaData() {
        String url = "http://musictbp.atwebpages.com/Server/fullBaiHat_FollowPHP.php";
        chaNgheSiList.clear();
        Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject obj = response.getJSONObject(key);
                            NgheSiCha ngheSiCha = new NgheSiCha();
                            ngheSiCha.setIdNgheSi(obj.getInt("idNgheSi"));
                            ngheSiCha.setTenNgheSi(obj.getString("tenNgheSi"));
                            ngheSiCha.setAvartar(obj.getString("avartar"));

                            List<BaiHatCon> baiHatConList = new ArrayList<>();
                            JSONArray baihatArray = obj.getJSONArray("baihat");
                            for (int j = 0; j < baihatArray.length(); j++) {
                                JSONObject baiHatObj = baihatArray.getJSONObject(j);
                                BaiHatCon baiHatCon = new BaiHatCon(
                                        baiHatObj.getInt("idbaihat"),
                                        baiHatObj.getString("tenBaiHat"),
                                        baiHatObj.getString("hinhBaiHat"),
                                        baiHatObj.getString("tenNgheSi")
                                );
                                baiHatConList.add(baiHatCon);
                            }
                            ngheSiCha.setBaihat(baiHatConList);
                            chaNgheSiList.add(ngheSiCha);
                        }
                        recycler_cha.setAdapter(new Follow_ChaAdapter(chaNgheSiList));
                        recycler_cha.getAdapter().notifyDataSetChanged();
                    } catch (JSONException e) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        recycler_horizontal = view.findViewById(R.id.recyclerView_goiy);
        recycler_horizontal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_top = view.findViewById(R.id.recycler_top);
        recycler_top.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_cha = view.findViewById(R.id.recycler_cha);
        recycler_cha.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}
