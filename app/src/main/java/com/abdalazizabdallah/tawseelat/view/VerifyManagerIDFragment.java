package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentVerifyManagerIDBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;

public class VerifyManagerIDFragment extends Fragment {


    private NavController navController;
    private FragmentVerifyManagerIDBinding fragmentVerifyManagerIDBinding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentVerifyManagerIDBinding = FragmentVerifyManagerIDBinding.inflate(inflater, container, false);
        return fragmentVerifyManagerIDBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentVerifyManagerIDBinding.toolbar, navController, appBarConfiguration);

        fragmentVerifyManagerIDBinding.verifyIDManager.setOnClickListener(v -> {
            //TODO : Verify id Manager
            String managerID = fragmentVerifyManagerIDBinding.managerIDEditText.getText().toString();
            if (PublicHelper.isEmptyFields(managerID)) {
                fragmentVerifyManagerIDBinding.managerIDTextInputLayout.setError(
                        PublicHelper.isEmptyFields(managerID) ? getString(R.string.required) : null);
            } else {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.verifyManagerIDFragment2, true)
                        .build();
                navController.navigate(NavGraphDirections.
                        actionGlobalMapsFragment(R.menu.menu_manager_navigation), navOptions);
            }
        });

    }
}