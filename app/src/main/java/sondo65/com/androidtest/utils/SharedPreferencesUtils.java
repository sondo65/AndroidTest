package sondo65.com.androidtest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static SharedPreferences mSharedPref;

    public static final String SHOULD_INSERT = "PREF_KEY_SHOULD_INSERT";

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static boolean getShouldInsert(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void setShouldInsert(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }
}
