package com.abdalazizabdallah.tawseelat.heplers;

import android.app.Application;
import android.content.Context;

public class MyApplicationClass extends Application {

    LocaleHelper localeHelper;

    @Override
    protected void attachBaseContext(Context base) {
        localeHelper = LocaleHelper.getInstance(base);
        Context context = localeHelper.changeLanguageInRuntime();
        super.attachBaseContext(context);
    }

}
