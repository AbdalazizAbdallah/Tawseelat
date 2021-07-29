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
import com.abdalazizabdallah.tawseelat.databinding.FragmentMessageDialogBinding;

public class MessageFragmentDialog extends DialogFragment {


    private OnClickMyButtonDialogListener onClickMyButtonDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_message_dialog, null);
        FragmentMessageDialogBinding fragmentDialogBinding = DataBindingUtil.bind(inflateView);

        String message = MessageFragmentDialogArgs.fromBundle(getArguments()).getMessage();
        boolean isCancelable = MessageFragmentDialogArgs.fromBundle(getArguments()).getIsCancelable();


        fragmentDialogBinding.fragmentDialogTextViewMessage.setText(message);

        fragmentDialogBinding.fragmentDialogButtonRetry.setOnClickListener(v -> {
            onClickMyButtonDialogListener = MessageFragmentDialogArgs.fromBundle(getArguments()).getOnClickMyButtonDialogListener();
            onClickMyButtonDialogListener.onClickMyButtonDialogListener(MessageFragmentDialog.this);
        });

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(fragmentDialogBinding.getRoot()).create();

        if (isCancelable) {
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
        }

        return alertDialog;
    }

}
