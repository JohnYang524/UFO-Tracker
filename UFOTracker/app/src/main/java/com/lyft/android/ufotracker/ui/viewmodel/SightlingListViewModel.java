package com.lyft.android.ufotracker.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.lyft.android.ufotracker.ui.helper.SightingRepo;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;

/**
 * SightlingListViewModel for keeping business logic and handling data updates.
 */
public class SightlingListViewModel extends AndroidViewModel {

    private static final boolean mIsDebuggable = true;
    private static final String  TAG           = SightlingListViewModel.class.getName();

    private SightingRepo mRepo;
    private LiveData<List<Sighting>> mSightingsList; // The list that contains all sightings

    private MutableLiveData<Integer> mTabIndex = new MutableLiveData<>();
    // The filter logic is not calculated unless an observer is observing the returned LiveData object.(Lazy calculation)
    private LiveData<List<Sighting>> filteredSightings = Transformations.map(mTabIndex, new Function<Integer, List<Sighting>>() {
        @Override
        public List<Sighting> apply(Integer input) {
            if (mIsDebuggable)
                Log.v(TAG, "Tab selected: " + input + ". Updating filtered list.");
            if (mSightingsList.getValue() == null || mSightingsList.getValue().size() == 0)
                return mSightingsList.getValue();
            // filter list data based on tab index
            List<Sighting> filteredList = new ArrayList<>();
            for (Sighting sighting : mSightingsList.getValue()) {
                if (sighting.getType().category.tabIndex == input) {
                    filteredList.add(sighting);
                }
            }
            Log.v(TAG, "filteredList size " + filteredList.size());
            return filteredList;
        }
    });

    public SightlingListViewModel(Application application) {
        super(application);
        mRepo = new SightingRepo(application);
        mSightingsList = mRepo.getAllSightings();
        populateSightingsList();
    }

    public void populateSightingsList() {
//        if (mSightingsList == null) {
//            mSightingsList = new MutableLiveData<>();
//            // Call to get data here. For testing purpose, the list is populated with sample data.
//            mSightingsList.setValue(createTestData());
//        }
//        return mSightingsList;
        mRepo.populateList();
    }

    public void setTabIndex(int index) {
        mTabIndex.setValue(index);
    }

    public LiveData<List<Sighting>> getFilteredSightings() {
        return filteredSightings;
    }

    public void onNewSightingAdded(Sighting sighting) {
        // For testing purpose, add only the data that matches current displaying category.
        if (sighting.getType().category.tabIndex == mTabIndex.getValue()) {
//            List<Sighting> dataList = mSightingsList.getValue();
//            dataList.add(0, sighting);
            mRepo.insert(new Sighting[]{sighting});
//            mSightingsList.setValue(dataList);
            refreshFilteredList();
        }
    }

    // filteredSightings is mapped with mTabIndex
    private void refreshFilteredList() {
        mTabIndex.setValue(mTabIndex.getValue());
    }

    // Remove the sighting object at corresponding index of current category list.
    public void removeSightingAt(Sighting sighting) {
//        List<Sighting> sightingList = mSightingsList.getValue();
//        if (sightingList.size() > pos) {
//            for (Sighting sighting : sightingList) { // Remove corresponding sighting from list
//                if (sighting.getType().category.tabIndex == mTabIndex.getValue()) {
//                    if (pos == 0) {
//                        sightingList.remove(sighting);
//                        break;
//                    } else {
//                        pos--;
//                    }
//                }
//            }
//            refreshFilteredList();
//        }
        mRepo.delete(sighting);
    }

    // When list of sighting is updated
    public void onSightingListUpdated(List<Sighting> sightings) {
        refreshFilteredList();
    }

    public List<Sighting> filterList(List<Sighting> sightings) {
        // filter list data based on tab index
        List<Sighting> filteredList = new ArrayList<>();
        for (Sighting sighting : sightings) {
            if (sighting.getType().category.tabIndex == mTabIndex.getValue()) {
                filteredList.add(sighting);
            }
        }
        if (mIsDebuggable)  Log.v(TAG, "filteredList size " + filteredList.size());
        return filteredList;
    }

}