package com.abdalazizabdallah.tawseelat.heplers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class PublicHelperMethods {

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
//    private static boolean isInternetAvailable() {
//        try {
//            InetAddress address = InetAddress.getByName("www.google.com");
//            return !address.equals("");
//        } catch (UnknownHostException e) {
//            // Log error
//        }
//        return false;
//    }

    public static boolean isConnectionInternetSuccessfully(Context context) {
        return isNetworkAvailable(context);
    }

    public static void showMessageSnackbar(View view, String string) {
        Snackbar.make(view,
                string,
                BaseTransientBottomBar.LENGTH_SHORT).show();
    }


    public static boolean isEmptyFields(String... strings) {
        for (String s : strings) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
