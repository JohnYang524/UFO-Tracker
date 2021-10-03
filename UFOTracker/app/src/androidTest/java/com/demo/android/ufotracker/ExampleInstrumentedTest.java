package com.demo.android.ufotracker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.demo.android.ufotracker.db.SightingDatabase;
import com.demo.android.ufotracker.ui.helper.Utils;
import com.demo.android.ufotracker.model.Sighting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.demo.android.ufotracker", appContext.getPackageName());
    }

    @Test
    public void db_testDAOfunctions() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SightingDatabase appDb = SightingDatabase.getInstance(getAppContext());
                Sighting sighting = appDb.sightingDAO().findById("100");
                if (sighting != null) {
                    Assert.assertEquals(sighting.id, "100");
                    appDb.sightingDAO().delete(sighting);
                }
                Sighting testContact = appDb.sightingDAO().findById("100");
                Assert.assertEquals(testContact, null);

                appDb.sightingDAO().insertAll(new Sighting[]{sighting});
                testContact = appDb.sightingDAO().findById("100");
                Assert.assertNotEquals(testContact, null);
            }
        });
    }

    @Test
    public void db_SequentialIDShouldBeAutoIncrementing() {
        String firstID = Utils.getNextID(getAppContext());
        String secondID = Utils.getNextID(getAppContext());
        Assert.assertTrue(Integer.parseInt(secondID) - Integer.parseInt(firstID) == 1);
    }

    public Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}