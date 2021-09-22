
package com.lyft.android.ufotracker.ui.helper;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.lyft.android.ufotracker.db.SightingDAO;
import com.lyft.android.ufotracker.db.SightingDatabase;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SightingRepo {
    private SightingDAO sightingDao;
    private LiveData<List<Sighting>> allSightings;
    private Application application;
    private Executor executor;

    public SightingRepo(Application application) { //application is subclass of context
        SightingDatabase database = SightingDatabase.getInstance(application);
        sightingDao = database.sightingDAO();
        allSightings = sightingDao.getAll();
        this.application = application;
    }

    // For first time user, populate list with test data
    public void populateList() {
        if (Utils.getLastSyncTime(application).equals("0")) { // first time
            insert(createTestData());
        }
    }

   //methods for database operations

    public void insert(Sighting[] sightings) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sightingDao.insertAll(sightings);// insert new data
                Utils.saveLastSyncTime(application, "1"); // save current timestamp. TODO: change value
            }
        });
    }

    public void update(Sighting sighting) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Sighting oldSighting = sightingDao.findById(sighting.id);
                sightingDao.delete(oldSighting);// delete old data
                sightingDao.insertAll(new Sighting[]{sighting});
            }
        });
    }

    public void delete(Sighting sighting) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sightingDao.delete(sighting);// insert new data
            }
        });
    }

    public LiveData<List<Sighting>> getAllSightings() {
        return allSightings;
    }

    private Sighting[] createTestData() {
        List<Sighting> dataList = new ArrayList<>();
        dataList.add(new Sighting(Utils.getNextID(application), "January 25, 2020 @ 6:00 AM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 22, 2020 @ 7:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 12, 2020 @ 6:20 AM", Sighting.SightingType.BLOB, "2 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 13, 2020 @ 6:20 AM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "22 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 25, 2020 @ 11:00 PM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 25, 2020 @ 10:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 14, 2020 @ 11:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "24 knots"));
        dataList.add(new Sighting(Utils.getNextID(application), "January 15, 2020 @ 10:00 PM", Sighting.SightingType.MYSTERIOUS_LIGHTS, "23 knots"));
        Sighting[] data = new Sighting[dataList.size()];
        data = dataList.toArray(data);
        return data;
    }

    /**
     * Calling from non-UI thread to refresh UI
     */
//    private void notifyUI() {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                // do UI changes after background work
//                if (mEventListener != null) {
//                    mEventListener.onContactListLoaded();
//                }
//            }
//        });
//    }
}
