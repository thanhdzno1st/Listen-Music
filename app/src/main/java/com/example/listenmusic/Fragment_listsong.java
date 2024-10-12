package com.example.listenmusic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_listsong#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_listsong extends Fragment {
    private ListView lvDanhSach;
    private ArrayList<danhsach> arraybaihat;
    private DanhSachAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_listsong() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_listsong.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_listsong newInstance(String param1, String param2) {
        Fragment_listsong fragment = new Fragment_listsong();
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
        View view = inflater.inflate(R.layout.fragment_listsong, container, false);
        anhxa(view);
        adapter = new DanhSachAdapter(R.layout.dong_danh_sach_v2, getActivity(), arraybaihat);
        lvDanhSach.setAdapter(adapter);

        return view;
    }
    private void anhxa(View view) {
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