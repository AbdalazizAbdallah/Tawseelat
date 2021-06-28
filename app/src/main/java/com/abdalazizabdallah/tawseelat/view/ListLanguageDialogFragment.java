package com.abdalazizabdallah.tawseelat.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.abdalazizabdallah.tawseelat.R;

public class ListLanguageDialogFragment extends DialogFragment {


    OnChangeLanguageListener onChangeLanguageListener;

    public ListLanguageDialogFragment(OnChangeLanguageListener onChangeLanguageListener) {
        this.onChangeLanguageListener = onChangeLanguageListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setItems(R.array.languages_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (onChangeLanguageListener != null) {
                            if (which == 0) {
                                onChangeLanguageListener.onSetChangeLanguageListener("en");
                            } else {
                                onChangeLanguageListener.onSetChangeLanguageListener("ar");
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    interface OnChangeLanguageListener {
        void onSetChangeLanguageListener(String language);
    }

}
