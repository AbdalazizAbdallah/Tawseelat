package com.abdalazizabdallah.tawseelat.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentSignUpAsEmployeeBinding;
import com.abdalazizabdallah.tawseelat.heplers.ImageFilePath;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.hbb20.CountryCodePicker;


public class SignUpAsEmployeeFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, CountryCodePicker.DialogEventsListener {

    private static final String TAG = "SignUpAsEmployeeFragmen";
    private static final boolean IS_ALLOW_TO_NAVIGATE = true;
    private FragmentSignUpAsEmployeeBinding fragmentSignUpAsEmployeeBinding;
    private NavController navController;
    private ActivityResultLauncher<String> mGetContent;
    private ActivityResultLauncher<String> mRequestWriteExternal;
    private int idViewPicker = 0;
    private String globalMessage;
    private Bitmap globalBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGetContent = registerForActivityResultGetContent();
        mRequestWriteExternal = registerForActivityResultRequestPermission();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSignUpAsEmployeeBinding = FragmentSignUpAsEmployeeBinding.inflate(inflater, container, false);
        return fragmentSignUpAsEmployeeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentSignUpAsEmployeeBinding.toolbar, navController, appBarConfiguration);

        fragmentSignUpAsEmployeeBinding.dobEditText.setOnFocusChangeListener(this);
        fragmentSignUpAsEmployeeBinding.idEditText.setOnFocusChangeListener(this);
        fragmentSignUpAsEmployeeBinding.genderDropDown.setOnFocusChangeListener(this);

        fragmentSignUpAsEmployeeBinding.ccp.registerCarrierNumberEditText(
                fragmentSignUpAsEmployeeBinding.phoneEditText);

        fragmentSignUpAsEmployeeBinding.ccp.setDialogEventsListener(this);
        fragmentSignUpAsEmployeeBinding.countryCodeEditText.setOnClickListener(v ->
                fragmentSignUpAsEmployeeBinding.ccp.launchCountrySelectionDialog());

        fragmentSignUpAsEmployeeBinding.countryCodeEditText.setText(
                fragmentSignUpAsEmployeeBinding.ccp.getSelectedCountryCodeWithPlus());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item,
                requireActivity().getResources().getStringArray(R.array.gender_array)
        );
        fragmentSignUpAsEmployeeBinding.genderDropDown.setAdapter(adapter);

        fragmentSignUpAsEmployeeBinding.dobEditText.setOnClickListener(this);
        fragmentSignUpAsEmployeeBinding.submitButton.setOnClickListener(this);

        fragmentSignUpAsEmployeeBinding.drivingLicenseEditText.setOnClickListener(this);
        fragmentSignUpAsEmployeeBinding.idImageEditText.setOnClickListener(this);
        fragmentSignUpAsEmployeeBinding.personalPictureEditText.setOnClickListener(this);
        fragmentSignUpAsEmployeeBinding.vehicleLicenseEditText.setOnClickListener(this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == fragmentSignUpAsEmployeeBinding.phoneEditText.getId()) {
            if (hasFocus) {
                fragmentSignUpAsEmployeeBinding.phoneTextInputLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentSignUpAsEmployeeBinding.phoneEditText.getText().toString())) {
                    fragmentSignUpAsEmployeeBinding.phoneTextInputLayout.setError(null);
                } else {
                    fragmentSignUpAsEmployeeBinding.phoneTextInputLayout.setError(getString(R.string.required));
                }
            }
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.idEditText.getId()) {
            if (hasFocus) {
                fragmentSignUpAsEmployeeBinding.idTextInputLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentSignUpAsEmployeeBinding.idEditText.getText().toString())) {
                    fragmentSignUpAsEmployeeBinding.idTextInputLayout.setError(null);
                } else {
                    fragmentSignUpAsEmployeeBinding.idTextInputLayout.setError(getString(R.string.required));
                }
            }
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.genderDropDown.getId()) {
            if (hasFocus) {
                fragmentSignUpAsEmployeeBinding.genderTextInputLayout.setError(null);
            } else {
                if (!PublicHelper.isEmptyFields(fragmentSignUpAsEmployeeBinding.genderDropDown.getText().toString())) {
                    fragmentSignUpAsEmployeeBinding.genderTextInputLayout.setError(null);
                } else {
                    fragmentSignUpAsEmployeeBinding.genderTextInputLayout.setError(getString(R.string.required));
                }
            }
        }
    }

    @Override
    public void onCcpDialogOpen(Dialog dialog) {

    }

    @Override
    public void onCcpDialogDismiss(DialogInterface dialogInterface) {
        fragmentSignUpAsEmployeeBinding.countryCodeEditText.setText(
                fragmentSignUpAsEmployeeBinding.ccp.getSelectedCountryCodeWithPlus());
    }

    @Override
    public void onCcpDialogCancel(DialogInterface dialogInterface) {

    }


    private void submitButtonClick() {

        String dob = fragmentSignUpAsEmployeeBinding.dobEditText.getText().toString();
        String phone = fragmentSignUpAsEmployeeBinding.ccp.getFullNumberWithPlus();
        String id = fragmentSignUpAsEmployeeBinding.idEditText.getText().toString();
        String gender = fragmentSignUpAsEmployeeBinding.genderDropDown.getText().toString();
        String driving = fragmentSignUpAsEmployeeBinding.drivingLicenseEditText.getText().toString();
        String idImage = fragmentSignUpAsEmployeeBinding.idImageEditText.getText().toString();
        String personalPicture = fragmentSignUpAsEmployeeBinding.personalPictureEditText.getText().toString();
        String vehicle = fragmentSignUpAsEmployeeBinding.vehicleLicenseEditText.getText().toString();

        if (PublicHelper.isEmptyFields(dob, phone, id, gender, driving, idImage, personalPicture, vehicle)) {
            fragmentSignUpAsEmployeeBinding.phoneTextInputLayout.setError(
                    PublicHelper.isEmptyFields(phone) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.dobTextInputLayout.setError(
                    PublicHelper.isEmptyFields(dob) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.idTextInputLayout.setError(
                    PublicHelper.isEmptyFields(id) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.genderTextInputLayout.setError(
                    PublicHelper.isEmptyFields(gender) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.drivingLicenseTextInputLayout.setError(
                    PublicHelper.isEmptyFields(driving) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.idImageTextInputLayout.setError(
                    PublicHelper.isEmptyFields(idImage) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.personalPictureTextInputLayout.setError(
                    PublicHelper.isEmptyFields(personalPicture) ? getString(R.string.required) : null);
            fragmentSignUpAsEmployeeBinding.vehicleLicenseTextInputLayout.setError(
                    PublicHelper.isEmptyFields(vehicle) ? getString(R.string.required) : null);
        } else {
            // TODO : create Employee object and continue next step
            if (fragmentSignUpAsEmployeeBinding.ccp.isValidFullNumber()) {
                navController.navigate(SignUpAsEmployeeFragmentDirections.actionSignUpAsEmployeeFragmentToMessageInfoEmployeeFragment());
            } else {
                PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                        fragmentSignUpAsEmployeeBinding.ccp.getFormattedFullNumber()
                                + getString(R.string.number_not_valid));
            }
        }
    }

    private ActivityResultLauncher<String> registerForActivityResultGetContent() {
        return registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    // Handle the returned Uri
                    if (uri != null) {
                        Log.e("TAG", "onActivityResult: " + uri.toString(), null);
                        String path = ImageFilePath.getPath(requireContext(), uri);
                        // TODO : Upload image to Server
                        // TODO: STORE IN CLIENT OBJECT
                        int i = path.lastIndexOf("/");
                        String substring = path.substring(i + 1);
                        if (idViewPicker == fragmentSignUpAsEmployeeBinding.drivingLicenseEditText.getId()) {

                            fragmentSignUpAsEmployeeBinding.drivingLicenseEditText.setText(substring);
                        } else if (idViewPicker == fragmentSignUpAsEmployeeBinding.vehicleLicenseEditText.getId()) {

                            fragmentSignUpAsEmployeeBinding.vehicleLicenseEditText.setText(substring);
                        } else if (idViewPicker == fragmentSignUpAsEmployeeBinding.idEditText.getId()) {

                            fragmentSignUpAsEmployeeBinding.idEditText.setText(substring);
                        } else if (idViewPicker == fragmentSignUpAsEmployeeBinding.personalPictureEditText.getId()) {

                            fragmentSignUpAsEmployeeBinding.personalPictureEditText.setText(substring);
                        }
                        Log.e("TAG", "onActivityResult: " + substring, null);

                    }
                });
    }

    @SuppressLint("StringFormatInvalid")
    private void openPickerImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    navController.navigate(NavGraphDirections.actionGlobalToMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                        @Override
                        public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                            mRequestWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            dialogFragment.dismiss();
                        }
                    }, getString(R.string.message_for_permission, "STORAGE")));
                } else {
                    mRequestWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            } else {
                showLicensesFragmentDialog();
            }
        } else {
            showLicensesFragmentDialog();
        }
    }

    private ActivityResultLauncher<String> registerForActivityResultRequestPermission() {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission()
                , isGranted -> {
                    if (isGranted) {
                        Log.e(TAG, "requestPermission: isGranted :" + isGranted, null);
                        showLicensesFragmentDialog();

                    } else {
                        Log.e(TAG, "requestPermission: Denied isGranted :" + isGranted, null);
                    }
                });

    }


    private void showLicensesFragmentDialog() {
        navController.navigate(SignUpAsEmployeeFragmentDirections.actionSignUpAsEmployeeFragmentToShowLicensesFragmentDialog(
                globalMessage,
                globalBitmap, new OnClickMyButtonDialogListener() {
                    @Override
                    public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                        dialogFragment.dismiss();
                        mGetContent.launch("image/*");
                    }
                }
        ));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentSignUpAsEmployeeBinding.submitButton.getId()) {
            submitButtonClick();
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.drivingLicenseEditText.getId()) {
            globalMessage = getString(R.string.enter_front_of_driving_license_text);
            //TODO : IF client has driving before if not set template
            globalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.driving_license_template);
            idViewPicker = v.getId();
            openPickerImage();
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.idImageEditText.getId()) {
            globalMessage = getString(R.string.enter_front_of_id_text);
            //TODO : IF client has id before if not set template
            globalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.id_template);
            idViewPicker = v.getId();
            openPickerImage();
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.personalPictureEditText.getId()) {
            globalMessage = getString(R.string.enter_front_personal_picture_text);
            //TODO : IF client has personal before if not set template
            globalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.personal_picture_temp);
            idViewPicker = v.getId();
            openPickerImage();
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.vehicleLicenseEditText.getId()) {
            globalMessage = getString(R.string.enter_front_of_vehicle_license_text);
            //TODO : IF client has vehicle before if not set template
            globalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_license_template);
            idViewPicker = v.getId();
            openPickerImage();
        } else if (v.getId() == fragmentSignUpAsEmployeeBinding.dobEditText.getId()) {
            PublicHelper.showTimePicker(
                    new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {
                            fragmentSignUpAsEmployeeBinding.dobEditText.setText(
                                    PublicHelper.formatDate(selection)
                            );
                        }
                    }, getParentFragmentManager(), requireContext());
        }
    }


}