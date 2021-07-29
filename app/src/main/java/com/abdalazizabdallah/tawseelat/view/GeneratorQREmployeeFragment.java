package com.abdalazizabdallah.tawseelat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentGeneratorQREmployeeBinding;
import com.abdalazizabdallah.tawseelat.heplers.PreferenceHelper;
import com.bumptech.glide.Glide;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GeneratorQREmployeeFragment extends Fragment {


    private static final String TAG = "GeneratorQREmployeeFrag";
    private NavController navController;
    private FragmentGeneratorQREmployeeBinding fragmentGeneratorQREmployeeBinding;
    private Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentGeneratorQREmployeeBinding = FragmentGeneratorQREmployeeBinding.inflate(inflater, container, false);

        return fragmentGeneratorQREmployeeBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentGeneratorQREmployeeBinding.toolbar, navController, appBarConfiguration);

        WindowManager manager = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3 / 4;
        Log.e(TAG, "onViewCreated: " + smallerDimension, null);
//        "SqhDRzZKIsmE9ILqkV8FWkBwj8X2GXlm11pBrsbBxdX7LzyjkcWCezJdz5VSR2hB"
        String loginKey = PreferenceHelper.getInstance(requireContext()).getLoginKey();
        QRGEncoder qrgEncoder = new QRGEncoder(loginKey, null, QRGContents.Type.TEXT, smallerDimension);
        qrgEncoder.setColorBlack(ResourcesCompat.getColor(getResources(), R.color.my_purple_Dark, null));
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            Glide.with(requireContext())
                    .load(bitmap)
                    .into(fragmentGeneratorQREmployeeBinding.qrImageView);
            //fragmentGeneratorQREmployeeBinding.qrImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

        fragmentGeneratorQREmployeeBinding.iAmManagerButton.setOnClickListener(v -> {
            // TODO : navigate to Create Company Transportation for Employee
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.generatorQREmployeeFragment2, true)
                    .setEnterAnim(android.R.anim.slide_in_left)
                    .setExitAnim(android.R.anim.fade_out)
                    .setPopEnterAnim(android.R.anim.fade_in)
                    .setPopExitAnim(android.R.anim.slide_out_right).build();

            navController.navigate(R.id.manager_graph, null, navOptions);
        });


    }
}