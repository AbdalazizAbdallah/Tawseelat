package com.abdalazizabdallah.tawseelat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.databinding.ActivityMainBinding;
import com.abdalazizabdallah.tawseelat.heplers.LocaleHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    NavController navController;
    ActivityMainBinding activityMainBinding;

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
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(activityMainBinding.bottomNav, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.clientMainFragment
                        || destination.getId() == R.id.clientProfileFragment
                ) {
                    activityMainBinding.bottomNav.setVisibility(View.VISIBLE);
                } else {
                    activityMainBinding.bottomNav.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.e(TAG, "attachBaseContext: ", null);

        Log.e(TAG, "attachBaseContext: BEFORE SUPER " + Locale.getDefault().toString(), null);
        Context context = LocaleHelper.getInstance(newBase).changeLanguageInRuntime();

        super.attachBaseContext(context);

        Log.e(TAG, "attachBaseContext: AFTER SUPER" + newBase.getResources().getConfiguration().locale, null);
        applyOverrideConfiguration(context.getResources().getConfiguration());

    }
}
