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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentCongratulationCreateCompanyBinding;


public class CongratulationCreateCompanyDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_congratulation_create_company, null);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        FragmentCongratulationCreateCompanyBinding fragmentDialogBinding = DataBindingUtil.bind(inflateView);

        fragmentDialogBinding.joinUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(CongratulationCreateCompanyDialogDirections.actionCongratulationCreateCompanyDialogToVerifyManagerIDFragment2());
                dismiss();
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(fragmentDialogBinding.getRoot()).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}