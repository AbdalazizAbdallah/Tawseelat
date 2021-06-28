package com.abdalazizabdallah.tawseelat.heplers;

import android.app.Application;
import android.content.Context;

public class MyApplicationClass extends Application {

    LocaleHelper localeHelper;

    @Override
    protected void attachBaseContext(Context base) {
        localeHelper = LocaleHelper.getInstance();
        Context context = localeHelper.changeLanguageInRuntime(base);
        super.attachBaseContext(context);
    }

}
