package com.abdalazizabdallah.tawseelat.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentClientMainBinding;
import com.abdalazizabdallah.tawseelat.heplers.OnMyActionListener;
import com.abdalazizabdallah.tawseelat.heplers.PermissionsHelper;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.abdalazizabdallah.tawseelat.model.CompanyInfo;
import com.abdalazizabdallah.tawseelat.model.CompanyTransportation;
import com.abdalazizabdallah.tawseelat.model.ListTransportRecyclerAdapter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class ClientMainFragment extends Fragment implements Toolbar.OnMenuItemClickListener, OnMyActionListener {

    private static final String TAG = "ClientMainFragment";
    private FragmentClientMainBinding fragmentClientMainBinding;
    private NavController navController;
    private String defaultFilter = "";
    private ActivityResultLauncher<String> mRequestLocation;
    private ActivityResultLauncher<IntentSenderRequest> mResultActivity;
    private boolean isLocationGrand = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestLocation = registerResultRequestLocation();
        mResultActivity = registerResultActivity();
        defaultFilter = getString(R.string.all_text);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentClientMainBinding = FragmentClientMainBinding.inflate(inflater, container, false);
        return fragmentClientMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        fragmentClientMainBinding.toolbar.setOnMenuItemClickListener(this);

        requestPermissionLocation();

        List<CompanyTransportation> list = getList();
        Log.e(TAG, "onViewCreated: " + list.size(), null);
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, list.get(i).toString(), null);
        }

        ListTransportRecyclerAdapter listTransportRecyclerAdapter =
                new ListTransportRecyclerAdapter(list, this);
        fragmentClientMainBinding.mainFragmentRecycleView.setLayoutManager(
                new LinearLayoutManager(requireContext()));

        fragmentClientMainBinding.mainFragmentRecycleView.setAdapter(listTransportRecyclerAdapter);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                navController.navigate(ClientMainFragmentDirections.actionClientMainFragmentToSearchFragment());
                break;
            case R.id.all_filter:
                if (!defaultFilter.equals(getString(R.string.all_text))) {
                    // TODO all filter company taxis
                }
                break;
            case R.id.cities_filter:
                if (!defaultFilter.equals(getString(R.string.cities_text))) {
                    // TODO cities filter company taxis
                }
                break;
            case R.id.taxis_filter:
                if (!defaultFilter.equals(getString(R.string.taxis_text))) {
                    // TODO taxis filter company taxis
                }
                break;
            case R.id.delivery_filter:
                if (!defaultFilter.equals(getString(R.string.delivery_text))) {
                    // TODO delivery filter company taxis
                }
                break;
        }
        return false;
    }

    private List<CompanyTransportation> getList() {
        CompanyTransportation companyTransportation = new CompanyTransportation(new CompanyInfo(
                "مطعم فهد",
                "غزة",
                "Delivery"
        ));
        CompanyTransportation companyTransportation2 = new CompanyTransportation(new CompanyInfo(
                getString(R.string.demo_company_name_text),
                getString(R.string.demo_city_text),
                "Taxis"
        ));

        List<CompanyTransportation> listCompanyTransportations = new ArrayList<>();
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);

        return listCompanyTransportations;
    }


    @SuppressLint("MissingPermission")
    private ActivityResultLauncher<String> registerResultRequestLocation() {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrand -> {
            if (isGrand) {
                //mMap.setMyLocationEnabled(true);
                // take location
                isLocationGrand = true;
            } else {
                showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                    @Override
                    public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                        dialogFragment.dismiss();
                        mRequestLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }, getString(R.string.message_for_permission), false);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void requestPermissionLocation() {
        PermissionsHelper.checkPermissionLocationAndAction(requireActivity(),
                new OnMyActionListener() {
                    @Override
                    public void onMyAction() {
                        //mMap.setMyLocationEnabled(true);
                        // take location
                        isLocationGrand = true;
                    }
                }, mRequestLocation);
    }

    private ActivityResultLauncher<IntentSenderRequest> registerResultActivity() {
        return registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    navController.navigate(NavGraphDirections.actionGlobalMapsFragment(R.menu.menu_client_navigation));
                    Log.e(TAG, "onActivityResult: ", null);
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    PublicHelper.showMessageSnackbar(
                            requireActivity().findViewById(android.R.id.content),
                            "Sorry you accept to get your location "
                    );
                }
            }
        });
    }

    private void showMessageFragmentDialog(OnClickMyButtonDialogListener onClickMyButtonDialogListener, String message, boolean isCancelable) {
        navController.navigate(NavGraphDirections.actionGlobalToMessageFragmentDialog(
                onClickMyButtonDialogListener, message, isCancelable));
    }

    @Override
    public void onMyAction() {
        if (isLocationGrand) {
            final LocationManager manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showDialogToEnableLocationService();
            } else {
                navController.navigate(NavGraphDirections.actionGlobalMapsFragment(R.menu.menu_client_navigation));
            }
        } else {
            PublicHelper.showMessageSnackbarWithButton(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.message_location_permission),
                    R.string.request_location_text,
                    v -> requestPermissionLocation()
            );
        }
    }

//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }


    private void showDialogToEnableLocationService() {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest).build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(requireActivity());
        Task<LocationSettingsResponse> locationSettingsResponseTask = settingsClient.checkLocationSettings(locationSettingsRequest);

        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);

                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                IntentSenderRequest build = new IntentSenderRequest.Builder(resolvable.getResolution()).build();
                                mResultActivity.launch(build);

                            } catch (ClassCastException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            PublicHelper.showMessageSnackbar(
                                    requireActivity().findViewById(android.R.id.content),
                                    "Sorry Location settings are not" +
                                            " satisfied and we have no way to fix this"
                            );
                            break;
                    }
                }
            }
        });
    }

}