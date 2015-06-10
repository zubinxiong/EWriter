package com.zubin.ewriter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zubin on 15/6/6.
 */
public class Settings {

    public static final String XML_NAME = "settings";

    public static final String KEY_FIRST_SCREEN ="first_screen";
//    public static final String KEY_NOT_LOOP = "not_loop";

    private static Settings sInstance;
    private SharedPreferences mPrefs;

    public static Settings getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Settings(context);
        }
        return sInstance;
    }

    private Settings(Context context) {
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

}
