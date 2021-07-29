package com.abdalazizabdallah.tawseelat.heplers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.view.OnClickMyButtonDialogListener;

public class PermissionsHelper {

    private static final String TAG = "PermissionsHelper";

    public static boolean checkIsLocationGrand(Context context) {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public static void checkPermissionLocationAndAction(@NonNull Activity activity,
                                                        @NonNull OnMyActionListener myActionListener,
                                                        @NonNull ActivityResultLauncher<String> registerRequestPermissionLocation) {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageFragmentDialog(navController, (OnClickMyButtonDialogListener) dialogFragment -> {

                    registerRequestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    dialogFragment.dismiss();
                }, activity.getString(R.string.message_for_permission), true);
            } else {
                registerRequestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }

        } else {
            myActionListener.onMyAction();
        }
    }


    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static void checkPermissionStorageAndAction(@NonNull Activity activity,
                                                       @NonNull OnMyActionListener myActionListener,
                                                       @NonNull ActivityResultLauncher<String> requestPermissionWriteExternal) {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showMessageFragmentDialog(navController, (OnClickMyButtonDialogListener) dialogFragment -> {

                        requestPermissionWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        dialogFragment.dismiss();

                    }, activity.getString(R.string.message_for_permission), false);
                } else {
                    requestPermissionWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            } else {
                myActionListener.onMyAction();
            }
        } else {
            myActionListener.onMyAction();
        }
    }


    private static void showMessageFragmentDialog(NavController navController,
                                                  OnClickMyButtonDialogListener onClickMyButtonDialogListener,
                                                  String message,
                                                  boolean isCancelable
    ) {
        navController.navigate(NavGraphDirections.actionGlobalToMessageFragmentDialog(
                onClickMyButtonDialogListener, message, isCancelable));
    }

    @SuppressLint("SupportAnnotationUsage")
    @RequiresPermission
    public static void checkPermissionAndAction(@NonNull Activity activity,
                                                @NonNull String permission,
                                                @NonNull OnMyActionListener myActionListener,
                                                @NonNull ActivityResultLauncher<String> registerRequestPermission) {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        if (ContextCompat.checkSelfPermission(activity,
                permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageFragmentDialog(navController, (OnClickMyButtonDialogListener) dialogFragment -> {

                    registerRequestPermission.launch(permission);
                    dialogFragment.dismiss();

                }, activity.getString(R.string.message_for_permission), false);
            } else {
                registerRequestPermission.launch(permission);
            }

        } else {
            myActionListener.onMyAction();
        }
    }


}
