package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentSplashScreenBinding;

import java.lang.ref.WeakReference;

public class SplashScreenFragment extends Fragment {

    private static final String REMAINING_TIME = "REMAINING_TIME";
    private static final String TAG = "SplashScreenFragment";
    private boolean isRunning = true;
    private long remainingTime = 2000;
    private long currentTime;
    private MyHandle myHandle;
    private NavController navController;
    private FragmentSplashScreenBinding fragmentSplashScreenBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTime = System.currentTimeMillis();

        if (savedInstanceState != null) {
            remainingTime = savedInstanceState.getLong(REMAINING_TIME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(getLayoutInflater(), container, false);
        return fragmentSplashScreenBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        animation.setDuration(remainingTime);
        fragmentSplashScreenBinding.imageViewBrand.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        animation.setDuration(remainingTime);
        fragmentSplashScreenBinding.textViewBrand.startAnimation(animation);


        myHandle = new MyHandle(new WeakReference<>(this));
        Message message = myHandle.obtainMessage();
        myHandle.sendMessageDelayed(message, remainingTime);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        remainingTime -= (System.currentTimeMillis() - currentTime);
        Log.e(TAG, "onSaveInstanceState : " + remainingTime, null);
        outState.putLong(REMAINING_TIME, (2000 - remainingTime));
    }

    static class MyHandle extends Handler {
        private final WeakReference<SplashScreenFragment> splashScreenFragmentWeakReference;

        public MyHandle(WeakReference<SplashScreenFragment> splashScreenFragmentWeakReference) {
            this.splashScreenFragmentWeakReference = splashScreenFragmentWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            SplashScreenFragment splashScreenFragment = splashScreenFragmentWeakReference.get();
            if (splashScreenFragment != null && splashScreenFragment.isRunning) {
                splashScreenFragment.navController.navigate(SplashScreenFragmentDirections
                        .actionSplashScreenFragmentToLoginFragment());
            }
        }
    }

}