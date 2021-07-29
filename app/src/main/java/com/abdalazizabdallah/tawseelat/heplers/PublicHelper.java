package com.abdalazizabdallah.tawseelat.heplers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.abdalazizabdallah.tawseelat.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PublicHelper {

    private static final String TAG = "PublicHelper";

    private static boolean isInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            Log.e(TAG, "isInternet: " + exitValue + "", null);
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) return false;

        switch (activeNetwork.getType()) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_MOBILE:
                if ((activeNetwork.getState() == NetworkInfo.State.CONNECTED ||
                        activeNetwork.getState() == NetworkInfo.State.CONNECTING) &&
                        isInternet())
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }


    public static boolean isConnectionInternetSuccessfully(Context context) {
        return isInternetAvailable(context);
    }

    public static void showMessageSnackbar(View view, String string) {
        Snackbar.make(view,
                string,
                BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    public static void showMessageSnackbarWithButton(View view, String string, int resIdForTextButton, View.OnClickListener onClickListener) {
        Snackbar.make(view,
                string,
                BaseTransientBottomBar.LENGTH_SHORT)
                .setAction(resIdForTextButton, onClickListener)
                .show();
    }

    public static boolean isEmptyFields(String... strings) {
        for (String s : strings) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static String formatDate(long currentTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(currentTime));
    }

    public static void showTimePicker(MaterialPickerOnPositiveButtonClickListener<Long> materialPickerOnPositiveButtonClickListener,
                                      FragmentManager fragmentManager, Context context
    ) {

        Calendar instance = Calendar.getInstance(Locale.getDefault());
        long timeInMillis = instance.getTimeInMillis();

        CalendarConstraints calendarConstraints = new CalendarConstraints.Builder().
                setValidator(DateValidatorPointBackward.now()).build();

        MaterialDatePicker<Long> longBuilder =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText(context.getString(R.string.select_dob))
                        .setCalendarConstraints(calendarConstraints)
                        .setSelection(timeInMillis)
                        .build();

        longBuilder.addOnPositiveButtonClickListener(materialPickerOnPositiveButtonClickListener);
        longBuilder.show(fragmentManager, "MaterialDatePicker");
    }

    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}
