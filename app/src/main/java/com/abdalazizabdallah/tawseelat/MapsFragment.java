package com.abdalazizabdallah.tawseelat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.databinding.FragmentMapsBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

//    private static final int PERMISSION_LOCATION_REQUEST = 2020;

    //    private GoogleMap mMap;
//
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
    private static final String TAG = "MapsFragment";
    private NavController navController;
    private DialogFragment mDialogFragment;
    private FragmentMapsBinding fragmentMapsBinding;
//    private LocationCallback locationCallback;
//    private LocationRequest locationRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        //makeLocationCallBack();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapsBinding = FragmentMapsBinding.inflate(inflater, container, false);
        return fragmentMapsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        if (!PublicHelper.isLocationPermissionGranted(requireContext())) {
            //navController.navigate(
            // NavGraphDirections.actionGlobalToFragmentDialog(new,getString(R.string.message_for_location)));
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.supportMapFragment);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

//        this.mMap = googleMap;
        // requestPermission();

        // googleMap.setMyLocationEnabled(true);

    }


    @Override
    public void onPause() {
        super.onPause();
        //   fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        // checkSettingLocationAndRequest();
        Log.e(TAG, "onResume: checkSettingLocationAndRequest", null);
    }
/*
    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);

            } else {

                // No explanation needed, we can request the permission.

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }
    }
*/
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION) {
//            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mMap.setMyLocationEnabled(true);
//                Log.e(TAG, "onRequestPermissionsResult: ", null);
//            }
//        }
//    }
/*
    private void makeLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    // TODO : handle with update location
                }
            }
        };

    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkSettingLocationAndRequest() {
     if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                FragmentDialog fragmentDialog = new FragmentDialog("you need to accept location permission to complete process", MapsFragment.this);
                fragmentDialog.show(getParentFragmentManager(), "fragmentDialog");
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST);
            }

        } else {
            getLastLocation();
            createLocationRequest();
            LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest).build();

            SettingsClient settingsClient = LocationServices.getSettingsClient(requireActivity());
            Task<LocationSettingsResponse> locationSettingsResponseTask = settingsClient.checkLocationSettings(locationSettingsRequest);

            locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                        requestLocationUpdate();

                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;

                                    resolvable.startResolutionForResult(requireActivity(), PERMISSION_LOCATION_REQUEST);

                                } catch (IntentSender.SendIntentException e) {

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

    @SuppressLint("MissingPermission")
    private void requestLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, requireActivity().getMainLooper());
    }

    private void getLastLocation() {
        @SuppressLint("MissingPermission") Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // TODO : handle with last location
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSettingLocationAndRequest();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PERMISSION_LOCATION_REQUEST){
            if (resultCode==Activity.RESULT_OK){
                checkSettingLocationAndRequest();
                Log.e(TAG, "onActivityResult: ",null);
            }else if (resultCode== Activity.RESULT_CANCELED){
                PublicHelper.showMessageSnackbar(
                        requireActivity().findViewById(android.R.id.content),
                        "Sorry you accept to get your location "
                );
            }
        }
    }
*/

}