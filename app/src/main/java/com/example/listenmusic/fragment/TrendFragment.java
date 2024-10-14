package com.example.listenmusic.fragment;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.listenmusic.R;

public class TrendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrendFragment() {
        // Required empty public constructor
    }

    public static TrendFragment newInstance(String param1, String param2) {
        TrendFragment fragment = new TrendFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trend, container, false);

        // Tìm TextView có id là title
        final TextView titleTextView = view.findViewById(R.id.title);

        // Sau khi layout được render, tạo gradient
        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                // Tạo các màu gradient 7 sắc cầu vồng cho TextView
                int[] rainbowColors = {
                        0xFFFF0000, // Red
                        0xFFFFA500, // Orange
                        0xFFFFFF00, // Yellow
                        0xFF00FF00, // Green
                        0xFF0000FF, // Blue
                        0xFF4B0082, // Indigo
                        0xFF8B00FF  // Violet
                };

                // Lấy chiều rộng của TextView
                float width = titleTextView.getWidth();

                // Tạo shader cho TextView, trải đều gradient trên toàn chiều rộng TextView
                Shader textShader = new LinearGradient(
                        0, 0, width, 0, // Chiều ngang cho gradient
                        rainbowColors, null, Shader.TileMode.CLAMP);

                // Áp dụng shader cho TextView
                titleTextView.getPaint().setShader(textShader);

                // Buộc TextView vẽ lại để áp dụng gradient
                titleTextView.invalidate();
            }
        });

        return view;
    }
}
