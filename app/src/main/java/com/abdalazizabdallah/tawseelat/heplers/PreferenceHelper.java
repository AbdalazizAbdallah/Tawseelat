package com.abdalazizabdallah.tawseelat.heplers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private static final String LANGUAGE_TO_LOAD = "languageToLoad";

    public static String getPersistedLanguageData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LANGUAGE_TO_LOAD, defaultLanguage);
    }

    public static void persistLanguage(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_TO_LOAD, language);
        editor.apply();
    }


}
