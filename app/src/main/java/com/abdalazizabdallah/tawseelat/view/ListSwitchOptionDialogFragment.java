package com.abdalazizabdallah.tawseelat.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;

public class ListSwitchOptionDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setItems(R.array.workspace_array, (dialog, which) -> {
                    ListSwitchOptionDialogFragment.this.dismiss();
                    switch (which) {
                        //TODO : navigate to Employee Workspace
                        case 0:
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).
                                    navigate(NavGraphDirections.actionGlobalMapsFragment(R.menu.menu_employee_navigation));
                            break;
                        //TODO : navigate to Manager Workspace to enter Verify ID Manager
                        case 1:
                            NavOptions navOptions = new NavOptions.Builder()
                                    .setPopUpTo(R.id.clientProfileFragment, false)
                                    .build();
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).
                                    navigate(NavGraphDirections.actionGlobalVerifyManagerIDFragment2(), navOptions);

                            break;
                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
    }

}