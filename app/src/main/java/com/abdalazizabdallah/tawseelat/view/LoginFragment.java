package com.abdalazizabdallah.tawseelat.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentLoginBinding;
import com.abdalazizabdallah.tawseelat.heplers.PreferenceHelper;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.hbb20.CountryCodePicker;


public class LoginFragment extends Fragment implements TextWatcher, CountryCodePicker.DialogEventsListener, View.OnClickListener, View.OnFocusChangeListener, OnChangeLanguageListener {

    private FragmentLoginBinding fragmentLoginBinding;
    private NavController navController;
    boolean loginSuccess = false;

    private static final String TAG = "LoginFragment";
    private PreferenceHelper preferenceHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = PreferenceHelper.getInstance(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        fragmentLoginBinding.ccp.registerCarrierNumberEditText(
                fragmentLoginBinding.emailOrPhoneEditText);

        Log.e(TAG, "onViewCreated: " + fragmentLoginBinding.ccp.getDefaultCountryNameCode(), null);


        fragmentLoginBinding.ccp.setDialogEventsListener(this);
        fragmentLoginBinding.countryCodeEditText.setOnClickListener(v ->
                fragmentLoginBinding.ccp.launchCountrySelectionDialog());


        fragmentLoginBinding.createAccount.setOnClickListener(this);
        fragmentLoginBinding.loginButton.setOnClickListener(this);

        fragmentLoginBinding.emailOrPhoneEditText.addTextChangedListener(this);

        fragmentLoginBinding.emailOrPhoneEditText.setOnFocusChangeListener(this);
        fragmentLoginBinding.password.setOnFocusChangeListener(this);

        fragmentLoginBinding.languageButton.setOnClickListener(this);


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() >= 3) {
            if (TextUtils.isDigitsOnly(s)) {
                fragmentLoginBinding.countryCodeEditText.setText(
                        fragmentLoginBinding.ccp.getSelectedCountryCodeWithPlus());

                fragmentLoginBinding.countryCodeLayout.setVisibility(View.VISIBLE);
                fragmentLoginBinding.emailOrPhoneLayout.setStartIconDrawable(R.drawable.img_mobile);

            } else {
                fragmentLoginBinding.countryCodeLayout.setVisibility(View.GONE);
                fragmentLoginBinding.emailOrPhoneLayout.setStartIconDrawable(R.drawable.img_email);
            }
        } else {
            fragmentLoginBinding.countryCodeLayout.setVisibility(View.GONE);
            fragmentLoginBinding.emailOrPhoneLayout.setStartIconDrawable(R.drawable.img_email);
        }
    }

    @Override
    public void onCcpDialogOpen(Dialog dialog) {

    }

    @Override
    public void onCcpDialogDismiss(DialogInterface dialogInterface) {
        fragmentLoginBinding.countryCodeEditText.setText(
                fragmentLoginBinding.ccp.getSelectedCountryCodeWithPlus());
    }

    @Override
    public void onCcpDialogCancel(DialogInterface dialogInterface) {

    }

    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentLoginBinding.createAccount.getId()) {
            NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
            navController.navigate(navDirections);
        } else if (v.getId() == fragmentLoginBinding.loginButton.getId()) {
            login();
        } else if (v.getId() == fragmentLoginBinding.languageButton.getId()) {
            navController.navigate(NavGraphDirections.actionGlobalToListLanguageDialogFragment(this));
        }
    }


    public void login() {
        //TODO : LOGIN TO ACCOUNT
        String emailOrPhoneEditText = String.valueOf(fragmentLoginBinding.emailOrPhoneEditText.getText());
        String password = String.valueOf(fragmentLoginBinding.password.getText());


        if (!PublicHelper.isConnectionInternetSuccessfully(requireContext())) {
            PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.check_your_internet));
        } else {
            // check any fields are empty or not
            if (!PublicHelper.isEmptyFields(emailOrPhoneEditText, password)) {
                if (TextUtils.isDigitsOnly(fragmentLoginBinding.emailOrPhoneEditText.getText())) {
                    //TODO : LOGIN TO ACCOUNT AS PHONE NUMBER
                    if (fragmentLoginBinding.ccp.isValidFullNumber()) {
                        //TODO : Check phone are resister in app
                        // and password correct and change loginSuccess to true
                        if (loginSuccess) {
                            if (fragmentLoginBinding.checkBoxLoginAsEmployee.isChecked()) {
                                //TODO : NAVIGATE TO MAIN EMPLOYEE'S FRAGMENT

                            } else {
                                //TODO : NAVIGATE TO MAIN CLIENT'S FRAGMENT
                            }
                            preferenceHelper.persistLoginKey(fragmentLoginBinding.ccp.getFullNumberWithPlus());
                        } else {
                            PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                                    getString(R.string.incorrect_phone_or_password));
                        }
                    } else {
                        PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                                fragmentLoginBinding.ccp.getFormattedFullNumber()
                                        + getString(R.string.number_not_valid));
                    }
                } else {
                    //TODO : LOGIN TO ACCOUNT AS EMAIL
                    if (isEmailValid(emailOrPhoneEditText)) {
                        //TODO : Check EMAIL are resister in app
                        // and password correct and change loginSuccess to true
                        //---
                        // demo
                        loginSuccess = true;

                        if (loginSuccess) {
                            if (fragmentLoginBinding.checkBoxLoginAsEmployee.isChecked()) {
                                //TODO : NAVIGATE TO MAIN EMPLOYEE'S FRAGMENT

                            } else {
                                //TODO : NAVIGATE TO MAIN CLIENT'S FRAGMENT
                            }
                            // demo

                            NavOptions navOptions = new NavOptions.Builder()
                                    .setPopUpTo(R.id.loginFragment, true)
                                    .setEnterAnim(android.R.anim.slide_in_left)
                                    .setExitAnim(android.R.anim.fade_out)
                                    .setPopEnterAnim(android.R.anim.fade_in)
                                    .setPopExitAnim(android.R.anim.slide_out_right).build();

                            navController.navigate(R.id.client_main_graph, null, navOptions);
                            preferenceHelper.persistLoginKey(emailOrPhoneEditText);
                        } else {
                            PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content), getString(R.string.incorrect_email_or_password));
                        }
                    } else {
                        PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content), getString(R.string.email_not_valid));
                    }
                }
            } else {//else for condition empty
                fragmentLoginBinding.emailOrPhoneLayout.setError(
                        PublicHelper.isEmptyFields(emailOrPhoneEditText) ? getString(R.string.required) : null);
                fragmentLoginBinding.passwordLayout.setError(
                        PublicHelper.isEmptyFields(password) ? getString(R.string.required) : null);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == fragmentLoginBinding.password.getId()) {
            if (hasFocus) {
                fragmentLoginBinding.passwordLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentLoginBinding.password.getText().toString())) {
                    fragmentLoginBinding.passwordLayout.setError(null);
                } else {
                    fragmentLoginBinding.passwordLayout.setError(getString(R.string.required));
                }

            }
        } else if (v.getId() == fragmentLoginBinding.emailOrPhoneEditText.getId()) {
            if (hasFocus) {
                fragmentLoginBinding.emailOrPhoneLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentLoginBinding.emailOrPhoneEditText.getText().toString())) {
                    fragmentLoginBinding.emailOrPhoneLayout.setError(null);
                } else {
                    fragmentLoginBinding.emailOrPhoneLayout.setError(getString(R.string.required));
                }

            }
        }

    }

    @Override
    public void onSetChangeLanguageListener(String language) {
        String persistedLanguageData = preferenceHelper.getPersistedLanguageData("");

        if (!persistedLanguageData.equals(language)) {
            preferenceHelper.persistLanguage(language);
            requireActivity().recreate();
        }
    }
}