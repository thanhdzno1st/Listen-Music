package com.example.listenmusic.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.Adapter.ViewPagerAdapter_Library;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LibraryFragment extends Fragment {
    private TabLayout tabLayoutLibra;
    private ViewPager2 viewPagerLibrary;

    private static final String ARG_USER = "user"; // Key cho User

    private User user; // Biến để lưu trữ User

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(User user) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user); // Truyền User qua Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER); // Lấy User từ Bundle
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Đang ở thư viện!", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        tabLayoutLibra = view.findViewById(R.id.tab_layout_library);
        viewPagerLibrary = view.findViewById(R.id.view_pager_library2);

        // Truyền User vào ViewPagerAdapter_Library
        ViewPagerAdapter_Library viewPagerAdapterLibrary = new ViewPagerAdapter_Library(getActivity(), user);
        viewPagerLibrary.setAdapter(viewPagerAdapterLibrary);

        // Kết nối TabLayout với ViewPager2 thông qua TabLayoutMediator
        new TabLayoutMediator(tabLayoutLibra, viewPagerLibrary, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Playlist");
                    break;
                case 1:
                    tab.setText("Albumn");
                    break;
                case 2:
                    tab.setText("Yêu thích");
                    break;
            }
        }).attach();

        return view;
    }
}
