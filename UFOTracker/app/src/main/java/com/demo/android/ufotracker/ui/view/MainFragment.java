package com.demo.android.ufotracker.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demo.android.ufotracker.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.demo.android.ufotracker.databinding.FragmentMainBinding;
import com.demo.android.ufotracker.ui.helper.TabPagerAdapter;

/**
 * Main fragment that holds a AppBarLayout and a TabLayout that is set up using a ViewPager.
 */
public class MainFragment extends Fragment {

    private static final boolean mIsDebuggable = true;
    private static final String  TAG           = MainFragment.class.getName();

    private FragmentMainBinding binding;
    private int currentTab = 0; // For testing purpose, we need to save current displaying tab index for inserting corresponding test data.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity(), getChildFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Add hardcoded Sighting objects for testing
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity)getActivity()).addTestSighting(currentTab);
                }
            }
        });
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Save current selected tab
                currentTab = tab.getPosition();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}