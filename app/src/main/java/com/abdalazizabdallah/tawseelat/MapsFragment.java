package com.abdalazizabdallah.tawseelat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.databinding.FragmentMapsBinding;
import com.abdalazizabdallah.tawseelat.heplers.PermissionsHelper;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.abdalazizabdallah.tawseelat.view.OnClickMyButtonDialogListenerForConfirmDetail;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapsFragment";
    private final int DESTINATION_STATE = 0;
    private final int SOURCE_STATE = 1;
    private final int CONFIRM_TO_GO_STATE = 2;
    private GoogleMap mMap;
    private NavController navController;
    private FragmentMapsBinding fragmentMapsBinding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<Marker> markerArrayListEmployee;
    private int actionBarHeight = 0;
    private Marker markerGlobal;
    private LatLng latLngSource;
    private LatLng latLngDistention;
    private int currentState = SOURCE_STATE;
    private int menuResources;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuResources = MapsFragmentArgs.fromBundle(getArguments()).getMenuResources();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
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

        NavigationUI.setupWithNavController(fragmentMapsBinding.navView, navController);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.supportMapFragment);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        fragmentMapsBinding.navButtonMenu.setOnClickListener(this::onClick);
        fragmentMapsBinding.closeButton.setOnClickListener(this::onClick);
    }


    @SuppressLint("MissingPermission")
    private void setUpGeneralSettings() {
        UiSettings uiSettings = mMap.getUiSettings();
        mMap.clear();
        mMap.setTrafficEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        mMap.setMaxZoomPreference(20f);
        mMap.setMinZoomPreference(10f);
        mMap.setMyLocationEnabled(true);
        getLastLocation();
        mMap.setOnMyLocationButtonClickListener(() -> {
            getLastLocation();
            return false;
        });

        TypedValue tv = new TypedValue();
        if (requireActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        mMap.setPadding(0, actionBarHeight, 0, 0);

    }

    private void configurationMap() {
        setUpGeneralSettings();

        TypedValue tv = new TypedValue();
        if (requireActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        int height = fragmentMapsBinding.frame.getHeight();
        mMap.setPadding(0, actionBarHeight, 0, height);

        mMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(fragmentMapsBinding.frameLayoutButton, new AutoTransition());
                }
                fragmentMapsBinding.frameLayoutButton.setVisibility(View.VISIBLE);
                int height1 = fragmentMapsBinding.frame.getHeight();
                mMap.setPadding(0, actionBarHeight, 0, height1);
            }
        });


        mMap.setOnCameraMoveListener(() -> {
            if (markerGlobal != null) {
                markerGlobal.remove();
            }
            fragmentMapsBinding.markerDot.setVisibility(View.VISIBLE);
        });

        mMap.setOnCameraIdleListener(() -> {
            if (currentState != CONFIRM_TO_GO_STATE) {
                LatLng latLng = mMap.getCameraPosition().target;
                String lngUnknown = formatLatLngUnknown(latLng);
                fragmentMapsBinding.markerDot.setVisibility(View.GONE);
                fragmentMapsBinding.textViewLatlng.setText(lngUnknown);
                if (markerGlobal != null) {
                    markerGlobal.remove();
                }
                markerGlobal = mMap.addMarker(createMarkerDefault(latLng));
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;
        if (menuResources == R.menu.menu_client_navigation) {
            configurationMap();
            fragmentMapsBinding.confirmButton.setOnClickListener(this::onClick);
            fragmentMapsBinding.backStatusButton.setOnClickListener(this::onClick);
            fragmentMapsBinding.goButton.setOnClickListener(this::onClick);
            fragmentMapsBinding.navView.inflateMenu(menuResources);

        } else if (menuResources == R.menu.menu_employee_navigation || menuResources == R.menu.menu_manager_navigation) {
            setUpGeneralSettings();
            fragmentMapsBinding.navView.inflateMenu(menuResources);
            fragmentMapsBinding.frame.setVisibility(View.GONE);
            fragmentMapsBinding.markerDot.setVisibility(View.GONE);
            markerGlobal = mMap.addMarker(createMarkerEmployee(mMap.getCameraPosition().target));
        }
        addMarkersEmployee();

    }


    @Override
    public void onResume() {
        Log.e(TAG, "onResume: ", null);
        if (PermissionsHelper.checkIsLocationGrand(requireContext())) {
            final LocationManager manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            } else {
                // navController.navigate(NavGraphDirections.actionGlobalMapsFragment());
            }
        } else {
            navController.popBackStack();
        }
        super.onResume();
    }

    private void getLastLocation() {
        @SuppressLint("MissingPermission") Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnSuccessListener(location -> {
            if (location != null) {
                // TODO : handle with last location
                moveCamera(location);
            }
        });
    }

    private void moveCamera(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                location.getLatitude(), location.getLongitude()
        ), 18f));
    }

    //--------------------------------------------------------------------------------
    private MarkerOptions createMarkerEmployee(LatLng latLng) {
        return new MarkerOptions()
                .position(latLng)
                .rotation((float) (Math.random() * 361.0))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon));
    }

    private void addMarkersEmployee() {
        markerArrayListEmployee = new ArrayList<>();
        List<LatLng> listLatLng = getListLatLngFromEmployee();
        for (LatLng latLng :
                listLatLng) {
            Marker marker = mMap.addMarker(createMarkerEmployee(latLng));
            marker.setTag(markerArrayListEmployee.size());
            markerArrayListEmployee.add(marker);
        }
    }

    private List<LatLng> getListLatLngFromEmployee() {
        ArrayList<LatLng> latLngArrayList = new ArrayList<>();
        latLngArrayList.add(new LatLng(31.4968087, 34.4574352));
        latLngArrayList.add(new LatLng(31.4954627, 34.4579163));

        latLngArrayList.add(new LatLng(31.4977362, 34.4580577));
        latLngArrayList.add(new LatLng(31.4968469, 34.4572422));

        latLngArrayList.add(new LatLng(31.4968469, 34.4572422));
        latLngArrayList.add(new LatLng(31.5150063, 34.4403482));

        latLngArrayList.add(new LatLng(31.5150063, 34.4403482));

        latLngArrayList.add(new LatLng(31.5164911, 34.4367567));
        latLngArrayList.add(new LatLng(31.51587, 34.4311895));

        latLngArrayList.add(new LatLng(31.5168779, 34.4247126));
        latLngArrayList.add(new LatLng(31.5168779, 34.4247126));
        latLngArrayList.add(new LatLng(31.5238899, 34.4428046));

        return latLngArrayList;
    }

    //--------------------------------------------------------------------------------
    private MarkerOptions createMarkerDefault(LatLng latLng) {
        return new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(R.drawable.ic_marker2));
    }

    private MarkerOptions createMarkerWithTitle(LatLng latLng, String title, String snippet) {
        return new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(bitmapDescriptorFromVector(R.drawable.ic_marker2));
    }

    private PolylineOptions createPolyline() {
        List<LatLng> latLngs = PublicHelper.decodePoly("ewf_E}eiqEqCBaI{FeEmCsHxLaBlCaKsE{CrDaErFaDdGyFbRwGhN_I~LgIjRaIdPgCtE");
        mMap.addMarker(createMarkerWithTitle(latLngs.get(0), "time reach", "3 mins"))
                .showInfoWindow();
        mMap.addMarker(createMarkerWithTitle(latLngs.get(latLngs.size() - 1), "time reach", "5:50")).showInfoWindow();

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(ResourcesCompat.getColor(getResources(), R.color.my_purple_Dark, null));
        polylineOptions.geodesic(true);
        polylineOptions.addAll(latLngs);
        return polylineOptions;
    }

    private void addDirections() {
        PolylineOptions polylineOptions = createPolyline();
        mMap.addPolyline(polylineOptions);
    }

    @SuppressLint("DefaultLocale")
    private String formatLatLngUnknown(LatLng latLng) {
        return "[" + String.format("%.2f", latLng.latitude) + " , " + String.format("%.2f", latLng.longitude) + "]";
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void removeEmployeeMarkers() {
        for (Marker marker :
                markerArrayListEmployee) {
            marker.remove();
        }
    }

    public void onClick(View v) {
        if (v.getId() == fragmentMapsBinding.navButtonMenu.getId()) {
            fragmentMapsBinding.drawerLayout.open();
        } else if (v.getId() == fragmentMapsBinding.closeButton.getId()) {
            navController.popBackStack();
        } else if (v.getId() == fragmentMapsBinding.confirmButton.getId()) {
            if (currentState == SOURCE_STATE) {
                getLatLngSourceAndChangeStates();
            } else if (currentState == DESTINATION_STATE) {
                getLatLngDestinationAndChangeStates();
            }
        } else if (v.getId() == fragmentMapsBinding.backStatusButton.getId()) {
            if (currentState == DESTINATION_STATE) {
                backToLatLngSourceAndChangeStates();
            } else if (currentState == CONFIRM_TO_GO_STATE) {
                backToLatLngDestinationAndChangeStates();
            }
        } else if (v.getId() == fragmentMapsBinding.goButton.getId()) {
            navController.navigate(MapsFragmentDirections.actionMapsFragmentToConfirmGoToTripDialog(new OnClickMyButtonDialogListenerForConfirmDetail() {
                @Override
                public void onClickMyButtonDialogListener(DialogFragment dialogFragment, String details) {
                    //TODO: Make Request
                    dialogFragment.dismiss();
                }

                @Override
                public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                    backToLatLngDestinationAndChangeStates();
                    dialogFragment.dismiss();
                }
            }));
        }
    }

    private void getLatLngSourceAndChangeStates() {
        latLngSource = mMap.getCameraPosition().target;
        fragmentMapsBinding.confirmButton.setText(getString(R.string.confirm_drop_off_text));
        fragmentMapsBinding.titleMessageTextView.setText(getString(R.string.where_do_you_want_text));
        fragmentMapsBinding.textViewLatlng.setText(getString(R.string.pick_up_distention_text));
        getLastLocation();
        fragmentMapsBinding.navButtonMenu.setVisibility(View.GONE);
        fragmentMapsBinding.closeButtonCard.setVisibility(View.GONE);
        fragmentMapsBinding.backStatusCard.setVisibility(View.VISIBLE);
        fragmentMapsBinding.iconStats.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.stepper_destination_shape_bg, null));

        currentState = DESTINATION_STATE;
    }

    private void backToLatLngSourceAndChangeStates() {
        latLngSource = null;
        fragmentMapsBinding.confirmButton.setText(getString(R.string.confirm_pick_up_text));
        fragmentMapsBinding.titleMessageTextView.setText(getString(R.string.pick_up_launch_point_text));
        fragmentMapsBinding.textViewLatlng.setText(getString(R.string.pick_source_text));
        getLastLocation();
        fragmentMapsBinding.navButtonMenu.setVisibility(View.VISIBLE);
        fragmentMapsBinding.closeButtonCard.setVisibility(View.VISIBLE);
        fragmentMapsBinding.backStatusCard.setVisibility(View.GONE);
        fragmentMapsBinding.iconStats.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.stepper_source_layerlist_bg, null));
        currentState = SOURCE_STATE;
    }


    private void getLatLngDestinationAndChangeStates() {
        currentState = CONFIRM_TO_GO_STATE;
        latLngDistention = mMap.getCameraPosition().target;

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        fragmentMapsBinding.markerDot.setVisibility(View.GONE);
        fragmentMapsBinding.pickersLinearLayout.setVisibility(View.GONE);
        fragmentMapsBinding.sourceTripTextView.setText(formatLatLngUnknown(latLngSource));
        fragmentMapsBinding.destinationTripTextView.setText(formatLatLngUnknown(latLngSource));

        fragmentMapsBinding.goToTripCard.setVisibility(View.VISIBLE);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5150063, 34.4403482), 13f));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(fragmentMapsBinding.goToTripCard, new AutoTransition());
        }
        int height1 = fragmentMapsBinding.frame.getHeight();
        mMap.setPadding(0, actionBarHeight, 0, height1);

        mMap.clear();
        addDirections();
    }

    private void backToLatLngDestinationAndChangeStates() {
        latLngDistention = null;
        mMap.clear();
        addMarkersEmployee();
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        markerGlobal = mMap.addMarker(createMarkerDefault(mMap.getCameraPosition().target));
        fragmentMapsBinding.pickersLinearLayout.setVisibility(View.VISIBLE);
        fragmentMapsBinding.goToTripCard.setVisibility(View.GONE);
        fragmentMapsBinding.markerDot.setVisibility(View.VISIBLE);
        getLastLocation();
        currentState = DESTINATION_STATE;
    }

}