package com.abdalazizabdallah.tawseelat.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentChangePasswordBinding;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private static final String TAG = "ChangePasswordFragment";
    private FragmentChangePasswordBinding fragmentChangePasswordBinding;
    private NavController navController;
    private ColorStateList defaultBoxStroke;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentChangePasswordBinding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return fragmentChangePasswordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defaultBoxStroke = fragmentChangePasswordBinding.confirmNewPassTextInputLayout.getBoxStrokeErrorColor();

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentChangePasswordBinding.toolbar, navController, appBarConfiguration);


        fragmentChangePasswordBinding.changePasswordButton.setOnClickListener(this);

        fragmentChangePasswordBinding.confirmNewPassEditText.addTextChangedListener(this);

        fragmentChangePasswordBinding.confirmNewPassEditText.setOnFocusChangeListener(this);
        fragmentChangePasswordBinding.newPassEditText.setOnFocusChangeListener(this);
        fragmentChangePasswordBinding.currentPassEditText.setOnFocusChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        String newPass = fragmentChangePasswordBinding.newPassEditText.getText().toString();
        String confirmNewPass = fragmentChangePasswordBinding.confirmNewPassEditText.getText().toString();
        String currentPass = fragmentChangePasswordBinding.currentPassEditText.getText().toString();

        if (!(newPass.isEmpty() || confirmNewPass.isEmpty() || currentPass.isEmpty())) {
            if (confirmNewPass.equals(newPass)) {
                //TODO : CHECK CURRENT PASS
                // AND CHANGE PASSWORD
            }
        } else {
            fragmentChangePasswordBinding.currentPassTextInputLayout
                    .setError(currentPass.isEmpty() ? getString(R.string.required) : null);

            fragmentChangePasswordBinding.confirmNewPassTextInputLayout
                    .setError(confirmNewPass.isEmpty() ? getString(R.string.required) : null);

            fragmentChangePasswordBinding.newPassTextInputLayout
                    .setError(newPass.isEmpty() ? getString(R.string.required) : null);
        }


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (hasFocus) {
            if (id == fragmentChangePasswordBinding.confirmNewPassEditText.getId()) {
                fragmentChangePasswordBinding.confirmNewPassTextInputLayout.setError(null);

            } else if (id == fragmentChangePasswordBinding.currentPassEditText.getId()) {
                fragmentChangePasswordBinding.currentPassTextInputLayout.setError(null);
            } else {
                fragmentChangePasswordBinding.newPassTextInputLayout.setError(null);
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String newPass = fragmentChangePasswordBinding.newPassEditText.getText().toString();
        String confirmNewPass = s.toString();

        if (newPass.equals(confirmNewPass)) {
            fragmentChangePasswordBinding.confirmNewPassTextInputLayout.setError(null);
        } else {
            fragmentChangePasswordBinding.confirmNewPassTextInputLayout.setError(getText(R.string.not_matching));
            fragmentChangePasswordBinding.confirmNewPassTextInputLayout.setBoxStrokeErrorColor(defaultBoxStroke);
        }

    }

}