package com.abdalazizabdallah.tawseelat.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentConfirmGoToTripDialogBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;

public class ConfirmGoToTripDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_confirm_go_to_trip_dialog, null);
        FragmentConfirmGoToTripDialogBinding fragmentConfirmGoToTripDialogBinding = DataBindingUtil.bind(inflateView);

        OnClickMyButtonDialogListenerForConfirmDetail onClickButtonsListener = ConfirmGoToTripDialogArgs.fromBundle(getArguments()).getOnClickButtonsListener();

        fragmentConfirmGoToTripDialogBinding.confirmActionButton.setOnClickListener(v -> {
            String details = fragmentConfirmGoToTripDialogBinding.detailsEditText.getText().toString();
            if (!PublicHelper.isEmptyFields(details)) {
                onClickButtonsListener.onClickMyButtonDialogListener(ConfirmGoToTripDialog.this,
                        details
                );
            } else {
                fragmentConfirmGoToTripDialogBinding.detailsTextInputLayout.setError(inflateView.getContext().getString(R.string.required));
            }
        });

        fragmentConfirmGoToTripDialogBinding.cancelActionButton.setOnClickListener(v -> onClickButtonsListener.onClickMyButtonDialogListener(ConfirmGoToTripDialog.this));

        return new AlertDialog.Builder(getContext())
                .setView(fragmentConfirmGoToTripDialogBinding.getRoot()).create();
    }

}