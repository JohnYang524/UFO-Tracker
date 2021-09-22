package com.lyft.android.ufotracker.ui.helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Utility class.
 */
public class Utils {
    public static String PREF_KEY_LAST_SYNC_TIME = "PREF_KEY_LAST_SYNC_TIME";
    public static String PREF_KEY_NEXT_DB_ID = "PREF_KEY_NEXT_DB_ID";

    /***
     * Get room DB last sync time as millisecond
     * Default value is "0".
     *
     * @param context
     * */
    public static String getLastSyncTime(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_KEY_LAST_SYNC_TIME, "0");
    }

    /***
     * Save room DB last sync time as millisecond in SharedPref
     *
     * @param context
     * @param newValue timestamp for last Room DB sync
     * */
    public static void saveLastSyncTime(Context context, String newValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(PREF_KEY_LAST_SYNC_TIME, newValue);
            editor.apply();
        }
    }

    /***
     * Method to check to see if local Room db date is up-to-date
     * by comparing dbLastSync timestamp with the timestamp saved in server
     * dbLastSyncTime > serverLastUpdateTime -> DB is up-to-date
     *
     * @param lastUpdateInServer timestamp for last sync time in local DB
     * @return whether or not we need to update Room DB
     *
     * */
//    public static boolean localDBUpToDate(Context context, String lastUpdateInServer) {
//        String lastDBSyncTime = getLastSyncTime(context);
//        return new BigInteger(lastDBSyncTime).compareTo(new BigInteger(lastUpdateInServer)) > 0;
//    }

    /***
     * Convert Sighting object to JSON string
     *
     * @param sighting
     * @return JSON string
     * */
//    public static String contactToJSONString(Sighting sighting) {
//        return new Gson().toJson(sighting);
//    }

    /***
     * Convert JSON string to Sighting[] object
     *
     * @param jsonString
     * @return Sighting[] object
     * */
//    public static Sighting[] jsonStringToContact(String jsonString) {
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(jsonString, Sighting[].class);
//    }

    /***
     * For testing purpose only. Simulating auto-incrementing ID.
     * */
    public static String getNextID(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String current = pref.getString(PREF_KEY_NEXT_DB_ID, "100");
        // increment by 1
        String next = String.valueOf(Integer.parseInt(current) + 1);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_NEXT_DB_ID, next);
        editor.apply();
        return next;
    }
}
