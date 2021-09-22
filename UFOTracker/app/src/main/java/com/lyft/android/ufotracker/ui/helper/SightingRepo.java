
package com.lyft.android.ufotracker.ui.helper;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lyft.android.ufotracker.db.SightingDAO;
import com.lyft.android.ufotracker.db.SightingDatabase;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.List;

public class SightingRepo {
    private SightingDAO sightingDao;
    private LiveData<List<Sighting>> allSightings;

    public SightingRepo(Context context) { //application is subclass of context
        SightingDatabase database = SightingDatabase.getInstance(context);
        sightingDao = database.sightingDAO();
        allSightings = sightingDao.getAll();
    }
//
//    //methods for database operations :-
//
//
//    public void insert(Note note) {
//        new InsertNoteAsyncTask(noteDao).execute(note);
//    }
//    public void update(Note note) {
//        new UpdateNoteAsyncTask(noteDao).execute(note);
//    }
//    public void delete(Note note) {
//        new DeleteNoteAsyncTask(noteDao).execute(note);
//    }
//    public void deleteAllNotes() {
//        new DeleteAllNotesAsyncTask(noteDao).execute();
//    }
    public LiveData<List<Sighting>> getAllSightings() {
        return allSightings;
    }
}
