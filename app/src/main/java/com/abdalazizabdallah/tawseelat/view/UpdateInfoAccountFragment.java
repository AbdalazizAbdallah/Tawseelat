package com.abdalazizabdallah.tawseelat.view;

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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateInfoAccountFragment extends Fragment {


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

        fragmentUpdateAccountBinding.dobEditText.setOnClickListener(v -> showTimePicker());


        //TODO : UPDATE ACCOUNT FETCH DATA FROM DATABASE
    }

    private String formatDate(long currentTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(currentTime));
    }

    private void showTimePicker() {

        Calendar instance = Calendar.getInstance(Locale.getDefault());
        long timeInMillis = instance.getTimeInMillis();

        CalendarConstraints calendarConstraints = new CalendarConstraints.Builder().
                setValidator(DateValidatorPointBackward.now()).build();

        MaterialDatePicker<Long> longBuilder =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText(getString(R.string.select_dob))
                        .setCalendarConstraints(calendarConstraints)
                        .setSelection(timeInMillis)
                        .build();

        longBuilder.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                fragmentUpdateAccountBinding.dobEditText.setText(
                        formatDate(selection)
                );
            }
        });
        longBuilder.show(getParentFragmentManager(), "MaterialDatePicker");
    }

}
