package com.abdalazizabdallah.tawseelat.heplers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

public class LocaleHelper {

    private static final String TAG = "LocaleHelper";
    private static LocaleHelper LocaleHelper;
    private final PreferenceHelper preferenceHelper;
    private final Context context;

    private LocaleHelper(Context context) {
        preferenceHelper = PreferenceHelper.getInstance(context);
        this.context = context;
    }

    public static LocaleHelper getInstance(Context context) {
        if (LocaleHelper == null) {
            LocaleHelper = new LocaleHelper(context);
        }
        return LocaleHelper;
    }

    //----------------------------------
    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
            return context.createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

        return context;
    }

    public Context changeLanguageInRuntime() {

        String languageToLoad = preferenceHelper.getPersistedLanguageData(
                Locale.getDefault().getLanguage());

        Log.e(TAG, "changeLanguageInRuntime: " + languageToLoad, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, languageToLoad);
        }
        return updateResourcesLegacy(context, languageToLoad);
    }
}
