package com.lyft.android.ufotracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.lyft.android.ufotracker.databinding.ActivityMainBinding;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity that hosts fragments.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<ListRefreshListener> listRefreshListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listRefreshListeners = new ArrayList<>();

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    public interface ListRefreshListener {
        void onNewSightingAdded(Sighting sighting);
    }

    public void attachListRefreshListener(ListRefreshListener listener) {
        this.listRefreshListeners.add(listener);
    }

    public void addTestSighting(int currentTab) { // Add hardcoded Sighting objects for testing
        for (ListRefreshListener listener : listRefreshListeners) {// Notify all listeners
            if (listener != null) {
                listener.onNewSightingAdded(currentTab == 0 ?
                        new Sighting("0","March 25, 2021 @ 9:00 PM", Sighting.SightingType.LAMPSHADE, "19 knots") :
                        new Sighting("1","March 22, 2021 @ 11:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "5 knots"));
            }
        }
    }
}