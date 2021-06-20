package com.abdalazizabdallah.tawseelat;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abdalazizabdallah.tawseelat.model.Ads;
import com.abdalazizabdallah.tawseelat.model.MyNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    List<MyNotification> myNotificationList;

    AutoCompleteTextView autoCompleteTextView;
    private List<Ads> adsList;

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
}
