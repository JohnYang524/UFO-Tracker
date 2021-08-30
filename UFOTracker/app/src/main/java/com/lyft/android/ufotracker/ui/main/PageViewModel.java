package com.lyft.android.ufotracker.ui.main;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private static final boolean mIsDebuggable = true;
    private static final String  TAG           = PageViewModel.class.getName();

    MutableLiveData<List<Sighting>> mSightingsList; // The list that contains all sightings
    private MutableLiveData<Integer> mTabIndex = new MutableLiveData<>();
    // The transformations aren't calculated unless an observer is observing the returned LiveData object.(Lazy calculation)
    private LiveData<List<Sighting>> filteredSightings = Transformations.map(mTabIndex, new Function<Integer, List<Sighting>>() {
        @Override
        public List<Sighting> apply(Integer input) {
            if (mIsDebuggable)
                Log.v(TAG, "Tab selected: " + input + ". Updating filtered list.");
            if (mSightingsList == null || mSightingsList.getValue().size() == 0)
                return mSightingsList.getValue();
            // filter list data based on tab index
            List<Sighting> filteredList = new ArrayList<>();
            for (Sighting sighting : mSightingsList.getValue()) {
                if (sighting.getType().category.tabIndex == input) {
                    filteredList.add(sighting);
                }
            }
            return filteredList;
        }
    });

    public PageViewModel() {
        mSightingsList = new MutableLiveData<>();
        // call Rest API in init method
        init();
    }

    private void init(){
        // Call to get data here. For testing purpose, the list is populated with sample data.
        mSightingsList.setValue(createTestData());
    }

    public void setTabIndex(int index) {
        mTabIndex.setValue(index);
    }

    public LiveData<List<Sighting>> getFilteredSightings() {
        return filteredSightings;
    }

    public MutableLiveData<List<Sighting>> getUserMutableLiveData() {
        return mSightingsList;
    }

    private List<Sighting> createTestData() {
        List<Sighting> testData = new ArrayList<>();
        testData.add(new Sighting("January 25, 2020 @ 6:00 AM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        testData.add(new Sighting("January 22, 2020 @ 7:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        testData.add(new Sighting("January 12, 2020 @ 6:20 AM", Sighting.SightingType.BLOB, "2 knots"));
        testData.add(new Sighting("January 13, 2020 @ 6:20 AM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "22 knots"));
        testData.add(new Sighting("January 25, 2020 @ 11:00 PM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        testData.add(new Sighting("January 25, 2020 @ 10:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        testData.add(new Sighting("January 14, 2020 @ 11:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "24 knots"));
        testData.add(new Sighting("January 15, 2020 @ 10:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "23 knots"));
        return testData;
    }
}