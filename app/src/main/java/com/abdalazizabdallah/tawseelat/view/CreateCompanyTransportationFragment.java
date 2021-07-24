package com.abdalazizabdallah.tawseelat.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.abdalazizabdallah.tawseelat.databinding.FragmentCreateCompanyTransportationBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;


public class CreateCompanyTransportationFragment extends Fragment implements View.OnFocusChangeListener {

    private FragmentCreateCompanyTransportationBinding fragmentCreateCompanyTransportationBinding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCreateCompanyTransportationBinding = FragmentCreateCompanyTransportationBinding.inflate(inflater, container, false);
        return fragmentCreateCompanyTransportationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item,
                requireActivity().getResources().getStringArray(R.array.type_transportation_array)
        );
        fragmentCreateCompanyTransportationBinding.companyTypeDropDown.setAdapter(adapter);

        fragmentCreateCompanyTransportationBinding.companyTypeDropDown.setSelection(0);
        fragmentCreateCompanyTransportationBinding.companyTypeDropDown.setListSelection(0);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentCreateCompanyTransportationBinding.toolbar, navController, appBarConfiguration);
        fragmentCreateCompanyTransportationBinding.companyNameEditText.setOnFocusChangeListener(this);
        fragmentCreateCompanyTransportationBinding.companyLocationEditText.setOnFocusChangeListener(this);


        fragmentCreateCompanyTransportationBinding.createCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyLocation = fragmentCreateCompanyTransportationBinding.companyLocationEditText.getText().toString();
                String companyName = fragmentCreateCompanyTransportationBinding.companyNameEditText.getText().toString();
                String companyType = fragmentCreateCompanyTransportationBinding.companyTypeDropDown.getText().toString();

                if (PublicHelper.isEmptyFields(companyName, companyLocation, companyType)) {
                    fragmentCreateCompanyTransportationBinding.companyLocationTextInputLayout.setError(
                            PublicHelper.isEmptyFields(companyLocation) ? getString(R.string.required) : null);
                    fragmentCreateCompanyTransportationBinding.companyNameTextInputLayout.setError(
                            PublicHelper.isEmptyFields(companyName) ? getString(R.string.required) : null);

                    if (PublicHelper.isEmptyFields(companyType)) {
                        PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                                getString(R.string.type_transportation_text) + " " + getString(R.string.required));
                    }

                } else {
                    // TODO : CREATE COMPANY IN Data base and generate manager id
                    //  navigate to congratulation
                    clickOnJointUsButton();
                }
            }
        });

    }

    private void clickOnJointUsButton() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage(getString(R.string.please_wait));

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                navController.navigate(CreateCompanyTransportationFragmentDirections
                        .actionCreateCompanyTransportationFragmentToCongratulationCreateCompanyFragment());

            }
        }.start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == fragmentCreateCompanyTransportationBinding.companyNameEditText.getId()) {
            if (hasFocus) {
                fragmentCreateCompanyTransportationBinding.companyNameTextInputLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentCreateCompanyTransportationBinding.companyNameEditText.getText().toString())) {
                    fragmentCreateCompanyTransportationBinding.companyNameTextInputLayout.setError(null);
                } else {
                    fragmentCreateCompanyTransportationBinding.companyNameTextInputLayout.setError(getString(R.string.required));
                }
            }
        } else if (v.getId() == fragmentCreateCompanyTransportationBinding.companyLocationEditText.getId()) {
            if (hasFocus) {
                fragmentCreateCompanyTransportationBinding.companyLocationTextInputLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentCreateCompanyTransportationBinding.companyLocationEditText.getText().toString())) {
                    fragmentCreateCompanyTransportationBinding.companyLocationTextInputLayout.setError(null);
                } else {
                    fragmentCreateCompanyTransportationBinding.companyLocationTextInputLayout.setError(getString(R.string.required));
                }
            }
        }
    }
}