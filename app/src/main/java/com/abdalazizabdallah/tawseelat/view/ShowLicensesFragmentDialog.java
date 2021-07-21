package com.abdalazizabdallah.tawseelat.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abdalazizabdallah.tawseelat.databinding.FragmentShowLicenseDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShowLicensesFragmentDialog extends BottomSheetDialogFragment {

    private FragmentShowLicenseDialogBinding fragmentShowLicenseDialogBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentShowLicenseDialogBinding = FragmentShowLicenseDialogBinding.inflate(inflater, container, false);
        return fragmentShowLicenseDialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String message = ShowLicensesFragmentDialogArgs.fromBundle(getArguments()).getMessage();
        Bitmap srcImageBitmap = ShowLicensesFragmentDialogArgs.fromBundle(getArguments()).getSrcImageBitmap();
        OnClickMyButtonDialogListener onClickMyButtonDialogListener = ShowLicensesFragmentDialogArgs.fromBundle(getArguments()).getOnClickMyButtonDialogListener();

        fragmentShowLicenseDialogBinding.messageTextview.setText(message);
        fragmentShowLicenseDialogBinding.imageView.setImageBitmap(srcImageBitmap);
        fragmentShowLicenseDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLicensesFragmentDialog.this.dismiss();
            }
        });

        fragmentShowLicenseDialogBinding.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMyButtonDialogListener.onClickMyButtonDialogListener(ShowLicensesFragmentDialog.this);
            }
        });
    }
}
