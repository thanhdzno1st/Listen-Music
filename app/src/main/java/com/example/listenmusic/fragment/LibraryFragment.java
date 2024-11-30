package com.example.listenmusic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.ViewPagerAdapter_Library;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LibraryFragment extends Fragment {
    private TabLayout tabLayoutLibra;
    private ViewPager2 viewPager2Library;

    private static final String ARG_USER = "user"; // Key cho User

    private User user; // Biến để lưu trữ User

    public LibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Phương thức newInstance để nhận đối tượng User.
     *
     * @param user Đối tượng User cần truyền.
     * @return Instance của LibraryFragment.
     */
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        tabLayoutLibra = view.findViewById(R.id.tab_layout_library);
        viewPager2Library = view.findViewById(R.id.view_pager2_library);

        // Truyền User vào ViewPagerAdapter_Library
        ViewPagerAdapter_Library viewPagerAdapterLibrary = new ViewPagerAdapter_Library(getActivity(), user);
        viewPager2Library.setAdapter(viewPagerAdapterLibrary);
        viewPager2Library.setCurrentItem(0);

        new TabLayoutMediator(tabLayoutLibra, viewPager2Library,
                (tab, position) -> tab.setText(viewPagerAdapterLibrary.getPageTitle(position))
        ).attach();

        return view;
    }
}
