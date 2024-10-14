package com.example.listenmusic.fragment;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.listenmusic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VipFragment extends Fragment {
    private TextView tvMusicTPB,tvVIPMembership,tvVIP;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VipFragment newInstance(String param1, String param2) {
        VipFragment fragment = new VipFragment();
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
        View view= inflater.inflate(R.layout.fragment_vip, container, false);
        tvMusicTPB=view.findViewById(R.id.tvMusicTPB);
        tvVIPMembership=view.findViewById(R.id.tvVIPMembership);
        tvVIP=view.findViewById(R.id.tvVipText);

        //color gradient of tvVIPMembership
        Paint paint = tvVIPMembership.getPaint();
        float width = paint.measureText(tvVIPMembership.getText().toString());

        // Tạo LinearGradient để tạo hiệu ứng chuyển màu cho text
        Shader textShader = new LinearGradient(0, 0, width, tvVIPMembership.getTextSize(),
                new int[]{
                        Color.parseColor("#F4FFB1"), // Màu bắt đầu
                        Color.parseColor("#CBEC00")  // Màu kết thúc
                }, null, Shader.TileMode.CLAMP);

        // Áp dụng shader vào text của TextView
        tvVIPMembership.getPaint().setShader(textShader);
        // Cập nhật lại TextView để hiển thị màu gradient
        tvVIPMembership.invalidate();

        //color gradient of tvVIPMembership
        Paint paint2 = tvVIP.getPaint();
        float width2 = paint2.measureText(tvVIP.getText().toString());

        // Tạo LinearGradient để tạo hiệu ứng chuyển màu cho text
        Shader textShader2 = new LinearGradient(0, 0, width2, tvVIP.getTextSize(),
                new int[]{
                        Color.parseColor("#C1005C"), // Màu bắt đầu
                        Color.parseColor("#FF4EA3")  // Màu kết thúc
                }, null, Shader.TileMode.CLAMP);

        // Áp dụng shader vào text của TextView
        tvVIP.getPaint().setShader(textShader2);
        // Cập nhật lại TextView để hiển thị màu gradient
        tvVIP.invalidate();

        tvVIPMembership.setShadowLayer(8, 0, 5,R.color.blur);
        return view;
    }
}