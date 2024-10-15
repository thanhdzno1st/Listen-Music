package com.example.listenmusic.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.listenmusic.MainActivity;
import com.example.listenmusic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowFragment extends Fragment {
    private LinearLayout info_1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        // Sử dụng view thay vì getView()
        info_1 = view.findViewById(R.id.info_1);
        info_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một dialog mới
                Dialog dialog = new Dialog(getActivity()); // MainActivity.this hoặc getActivity() nếu trong Fragment

                // Đặt nội dung của dialog với layout đã tạo
                dialog.setContentView(R.layout.follow);

                // Đảm bảo dialog được hiển thị với layout mong muốn
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Đặt nền của dialog trong suốt (loại bỏ nền mặc định)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // Đảm bảo có thể hủy dialog khi nhấn ra ngoài
                dialog.setCancelable(true);

                // Hiển thị dialog
                dialog.show();
            }
        });

        return view;
    }
}