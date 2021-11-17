package com.patternkeyboard.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhijeet on 11/23/2016.
 */

public class PreferenceManager {
    // Shared preferences file name
    private static final String PREF_NAME = "patternkeyboard-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private static PreferenceManager preferenceManager;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public static PreferenceManager getInstance(Context context) {
        if(preferenceManager == null) {
            preferenceManager = new PreferenceManager(context);
        }
        return preferenceManager;
    }

    private PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

}
