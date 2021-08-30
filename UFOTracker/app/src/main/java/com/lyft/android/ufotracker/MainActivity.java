package com.lyft.android.ufotracker;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.lyft.android.ufotracker.ui.main.TabPagerAdapter;
import com.lyft.android.ufotracker.databinding.ActivityMainBinding;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity that holds a AppBarLayout and a TabLayout that is set up using a ViewPager.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<ListRefreshListener> listRefreshListeners;
    private int currentTab = 0; // For testing purpose, we need to save current displaying tab index for inserting corresponding test data.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Add hardcoded Sighting objects for testing
                for (ListRefreshListener listener : listRefreshListeners) {// Notify all listeners
                    if (listener != null) {
                        listener.onNewSightingAdded(currentTab == 0 ?
                                new Sighting("March 25, 2021 @ 9:00 PM", Sighting.SightingType.LAMPSHADE, "19 knots") :
                                new Sighting("March 22, 2021 @ 11:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "5 knots"));
                    }
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

        listRefreshListeners = new ArrayList<>();
    }

    public interface ListRefreshListener {
        void onNewSightingAdded(Sighting sighting);
    }

    public void attachListRefreshListener(ListRefreshListener listener) {
        this.listRefreshListeners.add(listener);
    }
}