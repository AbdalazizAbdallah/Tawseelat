package com.abdalazizabdallah.tawseelat.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentUpdateAccountBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.hbb20.CountryCodePicker;

public class UpdateInfoAccountFragment extends Fragment implements CountryCodePicker.DialogEventsListener {


    private FragmentUpdateAccountBinding fragmentUpdateAccountBinding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentUpdateAccountBinding = FragmentUpdateAccountBinding.inflate(inflater, container, false);
        return fragmentUpdateAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentUpdateAccountBinding.toolbar, navController, appBarConfiguration);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item,
                requireActivity().getResources().getStringArray(R.array.gender_array)
        );
        fragmentUpdateAccountBinding.genderDropDown.setAdapter(adapter);

        fragmentUpdateAccountBinding.ccp.registerCarrierNumberEditText(
                fragmentUpdateAccountBinding.phoneEditText);

        fragmentUpdateAccountBinding.ccp.setDialogEventsListener(this);
        fragmentUpdateAccountBinding.countryCodeEditText.setOnClickListener(v ->
                fragmentUpdateAccountBinding.ccp.launchCountrySelectionDialog());

        fragmentUpdateAccountBinding.countryCodeEditText.setText(
                fragmentUpdateAccountBinding.ccp.getSelectedCountryCodeWithPlus());


        fragmentUpdateAccountBinding.dobEditText.setOnClickListener(v -> PublicHelper.showTimePicker(
                new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        fragmentUpdateAccountBinding.dobEditText.setText(
                                PublicHelper.formatDate(selection)
                        );
                    }
                }, getParentFragmentManager(), requireContext()
        ));

        //TODO : UPDATE ACCOUNT FETCH DATA FROM DATABASE
    }

    @Override
    public void onCcpDialogOpen(Dialog dialog) {

    }

    @Override
    public void onCcpDialogDismiss(DialogInterface dialogInterface) {
        fragmentUpdateAccountBinding.countryCodeEditText.setText(
                fragmentUpdateAccountBinding.ccp.getSelectedCountryCodeWithPlus());
    }

    @Override
    public void onCcpDialogCancel(DialogInterface dialogInterface) {

    }


}
