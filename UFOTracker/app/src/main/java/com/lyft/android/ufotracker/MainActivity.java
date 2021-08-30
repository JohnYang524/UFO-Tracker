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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<ListRefreshListener> listRefreshListeners;

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
            public void onClick(View v) { // Add hardcoded Sighting objects for testing purpose
                // Notify all listeners
                for (ListRefreshListener listener : listRefreshListeners) {
                    if (listener != null) {
                        listener.onNewSightingAdded(new Sighting("March 25, 2021 @ 9:00 PM", Sighting.SightingType.LAMPSHADE, "19 knots"));
                        listener.onNewSightingAdded(new Sighting("March 22, 2021 @ 11:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "5 knots"));
                    }
                }
            }
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