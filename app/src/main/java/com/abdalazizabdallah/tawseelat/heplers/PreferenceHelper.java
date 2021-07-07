package com.abdalazizabdallah.tawseelat.heplers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private static final String LANGUAGE_TO_LOAD = "languageToLoad";
    private static final String LOGIN_KEY = "LOGIN_KEY";
    private static PreferenceHelper preferenceHelper;
    private final SharedPreferences preferences;
    private final Context context;

    public PreferenceHelper(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceHelper getInstance(Context context) {
        if (preferenceHelper == null) {
            preferenceHelper = new PreferenceHelper(context);
        }
        return preferenceHelper;
    }

    public String getPersistedLanguageData(String defaultLanguage) {
        return preferences.getString(LANGUAGE_TO_LOAD, defaultLanguage);
    }

    public void persistLanguage(String language) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_TO_LOAD, language);
        editor.apply();
    }

    public String getLoginKey() {
        return preferences.getString(LOGIN_KEY, null);
    }

    public void persistLoginKey(String loginKey) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_KEY, loginKey);
        editor.apply();
    }

    public void removeLoginKey() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_KEY);
        editor.apply();
    }

}
