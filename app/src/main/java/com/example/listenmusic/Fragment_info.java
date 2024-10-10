package com.example.listenmusic;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_info extends Fragment {
    private ListView lvDanhSach;
    private ArrayList<danhsach> arraybaihat;
    private DanhSachAdapter adapter;
    private ImageView button_cmt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_info newInstance(String param1, String param2) {
        Fragment_info fragment = new Fragment_info();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        anhxa(view);
        adapter = new DanhSachAdapter(R.layout.dong_danh_sach, getActivity(), arraybaihat);
        lvDanhSach.setAdapter(adapter);

        button_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void anhxa(View view) {
        button_cmt = getActivity().findViewById(R.id.btn_comment);
        lvDanhSach = view.findViewById(R.id.list_dsbaihat);
        lvDanhSach.setDivider(null);
        arraybaihat = new ArrayList<>();
        arraybaihat.add(new danhsach("Lạc Trôi", "Sơn Tùng MTP", R.drawable.baihat_1));
        arraybaihat.add(new danhsach("Chúng Ta Của Hiện Tại", "Sơn Tùng MTP", R.drawable.baihat_2));
        arraybaihat.add(new danhsach("Nắng Ấm Xa Dần", "Sơn Tùng MTP", R.drawable.baihat_3));
        arraybaihat.add(new danhsach("Hãy Trao Cho Anh", "Sơn Tùng MTP", R.drawable.baihat_4));
        arraybaihat.add(new danhsach("Bình yên những phút giây", "Sơn Tùng MTP", R.drawable.baihat_5));
        arraybaihat.add(new danhsach("Making My Way", "Sơn Tùng MTP", R.drawable.baihat_6));
        arraybaihat.add(new danhsach("Muộn rồi mà sao còn", "Sơn Tùng MTP", R.drawable.baihat_7));
    }
}