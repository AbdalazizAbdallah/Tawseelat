package com.abdalazizabdallah.tawseelat.heplers;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class MyApplicationClass extends MultiDexApplication {

    LocaleHelper localeHelper;

    @Override
    protected void attachBaseContext(Context base) {
        localeHelper = LocaleHelper.getInstance(base);
        Context context = localeHelper.changeLanguageInRuntime();
        super.attachBaseContext(context);
    }

}
