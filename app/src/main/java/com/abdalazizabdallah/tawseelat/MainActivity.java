package com.abdalazizabdallah.tawseelat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abdalazizabdallah.tawseelat.heplers.LocaleHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String languageToLoad = "ar"; // your language
//        Locale locale = new Locale(languageToLoad);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());


        DataBindingUtil.setContentView(this, R.layout.activity_main);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.e(TAG, "attachBaseContext: ", null);

        Log.e(TAG, "attachBaseContext: BEFORE SUPER " + Locale.getDefault().toString(), null);
        Context context = LocaleHelper.getInstance().changeLanguageInRuntime(newBase);

        super.attachBaseContext(context);

        Log.e(TAG, "attachBaseContext: AFTER SUPER" + newBase.getResources().getConfiguration().locale, null);
        applyOverrideConfiguration(context.getResources().getConfiguration());

    }
}
