package com.example.listenmusic.fragment;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenmusic.Adapter.TrendAdapter;
import com.example.listenmusic.Models.BaiHat;
import com.example.listenmusic.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrendFragment extends Fragment {

    RecyclerView recyclerView;
    List<BaiHat.Trend> trendList;
    ImageView trend_img1;
    TextView txt_title;
    TextView txt_subtitle;

    public TrendFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trendList = new ArrayList<>();
        fetchTrendData();
        fetchTrendTop1();
    }

    private void fetchTrendTop1() {
        String url = "http://musictbp.atwebpages.com/Server/TrendTop1PHP.php";

        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject obj = response.getJSONObject(0);
                            BaiHat.Trend trend = new BaiHat.Trend(
                                    obj.getString("idTrend"),
                                    obj.getString("tenBaiHat"),
                                    obj.getString("hinhBaiHat"),
                                    obj.getString("tenNgheSi")
                            );
                            txt_title.setText(trend.getTenBaiHat());
                            txt_subtitle.setText(trend.getTenNgheSi());
                            Picasso.get()
                                    .load(trend.getHinhAnh())
                                    .into(trend_img1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ));
    }

    private void fetchTrendData() {
        String url = "http://musictbp.atwebpages.com/Server/TrendPHP.php";

        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            trendList.add(new BaiHat.Trend(
                                    obj.getString("idTrend"),
                                    obj.getString("tenBaiHat"),
                                    obj.getString("hinhBaiHat"),
                                    obj.getString("tenNgheSi")
                            ));
                        }
                        recyclerView.setAdapter(new TrendAdapter(trendList));
                        recyclerView.getAdapter().notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_trend, container, false);

        recyclerView = view.findViewById(R.id.rcv_trend);
        trend_img1 = view.findViewById(R.id.trend_img1);
        txt_title = view.findViewById(R.id.txt_title);
        txt_subtitle = view.findViewById(R.id.txt_subtitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final TextView titleTextView = view.findViewById(R.id.title);

        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                int[] rainbowColors = {
                        0xFFFF0000, 0xFFFFA500, 0xFFFFFF00, 0xFF00FF00,
                        0xFF0000FF, 0xFF4B0082, 0xFF8B00FF
                };

                float width = titleTextView.getWidth();

                Shader textShader = new LinearGradient(
                        0, 0, width, 0,
                        rainbowColors, null, Shader.TileMode.CLAMP);

                titleTextView.getPaint().setShader(textShader);
                titleTextView.invalidate();
            }
        });

        return view;
    }
}
