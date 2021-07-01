package com.abdalazizabdallah.tawseelat.view;

import android.annotation.SuppressLint;
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
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentLoginBinding;
import com.hbb20.CountryCodePicker;


@SuppressLint("RestrictedApi")
public class LoginFragment extends Fragment implements TextWatcher, CountryCodePicker.DialogEventsListener {

    private FragmentLoginBinding fragmentLoginBinding;
    private NavController navController;

    private static final String TAG = "LoginFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        fragmentLoginBinding.fragmentLoginCcp.registerCarrierNumberEditText(
                fragmentLoginBinding.fragmentLoginEmailOrPassword);

//        fragmentLoginBinding.fragmentLoginCcp.launchCountrySelectionDialog(
//                fragmentLoginBinding.fragmentLoginCcp.getDefaultCountryNameCode());

        Log.e(TAG, "onViewCreated: " + fragmentLoginBinding.fragmentLoginCcp.getDefaultCountryNameCode(), null);

        fragmentLoginBinding.fragmentLoginEmailOrPassword.addTextChangedListener(this);

        fragmentLoginBinding.fragmentLoginCcp.setDialogEventsListener(this);
        fragmentLoginBinding.fragmentLoginCountryCodeEditText.setOnClickListener(v ->
                fragmentLoginBinding.fragmentLoginCcp.launchCountrySelectionDialog());


        fragmentLoginBinding.fragmentLoginCreateAccount.setOnClickListener(v -> {
            NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
            navController.navigate(navDirections);
        });

        fragmentLoginBinding.fragmentLoginLoginButton.setOnClickListener(v -> {
            //TODO : LOGIN TO ACCOUNT

            if (fragmentLoginBinding.fragmentLoginCheckBoxLoginAsEmployee.isChecked()) {
                //TODO : NAVIGATE TO MAIN EMPLOYEE'S FRAGMENT
            } else {
                //TODO : NAVIGATE TO MAIN CLIENT'S FRAGMENT
            }

//            if (fragmentLoginBinding.fragmentLoginCcp.isValidFullNumber()) {
//                Log.e(TAG, fragmentLoginBinding.fragmentLoginCcp.getFormattedFullNumber()
//                        + "\n number is Valid ", null);
//                Toast.makeText(getContext(), fragmentLoginBinding.fragmentLoginCcp.getFormattedFullNumber()
//                        + "\n number is Valid ", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.e(TAG, fragmentLoginBinding.fragmentLoginCcp.getFormattedFullNumber()
//                        + "\n number is not Valid ", null);
//                Toast.makeText(getContext(), fragmentLoginBinding.fragmentLoginCcp.getFormattedFullNumber() +
//                        "number is not Valid ", Toast.LENGTH_SHORT).show();
//            }


        });

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
                fragmentLoginBinding.fragmentLoginCountryCodeEditText.setText(
                        fragmentLoginBinding.fragmentLoginCcp.getSelectedCountryCodeWithPlus());

                fragmentLoginBinding.fragmentLoginCountryCodeLayout.setVisibility(View.VISIBLE);
                fragmentLoginBinding.fragmentLoginEmailOrPhoneLayout.setStartIconDrawable(R.drawable.img_mobile);

            } else {
                fragmentLoginBinding.fragmentLoginCountryCodeLayout.setVisibility(View.GONE);
                fragmentLoginBinding.fragmentLoginEmailOrPhoneLayout.setStartIconDrawable(R.drawable.img_email);
            }
        } else {
            fragmentLoginBinding.fragmentLoginCountryCodeLayout.setVisibility(View.GONE);
            fragmentLoginBinding.fragmentLoginEmailOrPhoneLayout.setStartIconDrawable(R.drawable.img_email);
        }
    }

    @Override
    public void onCcpDialogOpen(Dialog dialog) {

    }

    @Override
    public void onCcpDialogDismiss(DialogInterface dialogInterface) {
        fragmentLoginBinding.fragmentLoginCountryCodeEditText.setText(
                fragmentLoginBinding.fragmentLoginCcp.getSelectedCountryCodeWithPlus());
    }

    @Override
    public void onCcpDialogCancel(DialogInterface dialogInterface) {

    }

//    public boolean isEmailValid(CharSequence email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
//                .matches();
//    }
}