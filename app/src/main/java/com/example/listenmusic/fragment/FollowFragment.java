package com.example.listenmusic.fragment;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.Follow_ChaAdapter;
import com.example.listenmusic.Adapter.Follow_GoiYAdapter;
import com.example.listenmusic.Adapter.Follow_TopAdapter;
import com.example.listenmusic.R;
import com.example.listenmusic.model.BaiHatCon;
import com.example.listenmusic.model.NgheSi;
import com.example.listenmusic.model.NgheSiCha;

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

    public FollowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fetch data if not already loaded
        fetchNgheSiData();
        fetchTopData();
        fetchChaData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch data again when coming back to this fragment
        fetchNgheSiData();
        fetchTopData();
        fetchChaData();
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
                        // Update the adapter with the new data
                        if (recycler_horizontal.getAdapter() != null) {
                            recycler_horizontal.getAdapter().notifyDataSetChanged();
                        } else {
                            recycler_horizontal.setAdapter(new Follow_GoiYAdapter(goiYNgheSiList));
                        }
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
                        // Iterate through each artist in the JSON data
                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject obj = response.getJSONObject(key);

                            // Create NgheSiCha object and set artist info
                            NgheSiCha ngheSiCha = new NgheSiCha();
                            ngheSiCha.setIdNgheSi(obj.getInt("idNgheSi"));
                            ngheSiCha.setTenNgheSi(obj.getString("tenNgheSi"));
                            ngheSiCha.setAvartar(obj.getString("avartar"));

                            // Create the list of BaiHatCon
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

                            // Set BaiHat list for NgheSiCha
                            ngheSiCha.setBaihat(baiHatConList);

                            // Add NgheSiCha to the list
                            chaNgheSiList.add(ngheSiCha);
                        }

                        // Update the adapter with the new data
                        if (recycler_cha.getAdapter() != null) {
                            recycler_cha.getAdapter().notifyDataSetChanged();
                        } else {
                            recycler_cha.setAdapter(new Follow_ChaAdapter(chaNgheSiList));
                        }

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
                        // Update the adapter with the new data
                        if (recycler_top.getAdapter() != null) {
                            recycler_top.getAdapter().notifyDataSetChanged();
                        } else {
                            recycler_top.setAdapter(new Follow_TopAdapter(topNgheSiList));
                        }
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
