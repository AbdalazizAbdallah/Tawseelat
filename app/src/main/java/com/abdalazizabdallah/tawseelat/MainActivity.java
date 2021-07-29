package com.abdalazizabdallah.tawseelat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.databinding.ActivityMainBinding;
import com.abdalazizabdallah.tawseelat.heplers.LocaleHelper;
import com.abdalazizabdallah.tawseelat.view.SearchFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private NavController navController;
    private ActivityMainBinding activityMainBinding;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(activityMainBinding.bottomNav, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.clientMainFragment
                    || destination.getId() == R.id.clientProfileFragment
            ) {
                activityMainBinding.bottomNav.setVisibility(View.VISIBLE);
            } else {
                activityMainBinding.bottomNav.setVisibility(View.GONE);
            }

            if (destination.getId() == R.id.scanQRCodeForHireEmployeeFragment
                    || destination.getId() == R.id.generatorQREmployeeFragment2) {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = 1F;
                getWindow().setAttributes(layoutParams);
            } else {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = -1F;
                getWindow().setAttributes(layoutParams);
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

    @Override
    protected void onNewIntent(Intent intent) {
        if (navController.getCurrentDestination().getId() == R.id.searchFragment) {
            ((SearchFragment) navHostFragment.getChildFragmentManager().getFragments().get(0))
                    .search_handle(intent);
        }
        super.onNewIntent(intent);
    }


}
