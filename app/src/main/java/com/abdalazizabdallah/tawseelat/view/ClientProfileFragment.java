package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentClientProfileBinding;
import com.abdalazizabdallah.tawseelat.heplers.PreferenceHelper;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelperMethods;


public class ClientProfileFragment extends Fragment implements View.OnClickListener, OnChangeLanguageListener {


    private static final String TAG = "ClientProfileFragment";
    private FragmentClientProfileBinding fragmentClientProfileBinding;
    private NavController navController;
    private PreferenceHelper preferenceHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = PreferenceHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentClientProfileBinding = FragmentClientProfileBinding.inflate(inflater, container, false);
        return fragmentClientProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        fragmentClientProfileBinding.feedbackTextview.setOnClickListener(this);
        fragmentClientProfileBinding.languageTextview.setOnClickListener(this);
        fragmentClientProfileBinding.changePasswordTextview.setOnClickListener(this);
        fragmentClientProfileBinding.myInfo.setOnClickListener(this);
        fragmentClientProfileBinding.myTripsTextview.setOnClickListener(this);
        fragmentClientProfileBinding.logoutTextview.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (fragmentClientProfileBinding.languageTextview.getId() == v.getId()) {
            navController.navigate(ClientProfileFragmentDirections.
                    actionClientProfileFragmentToListLanguageDialogFragment(this));

        } else if (fragmentClientProfileBinding.feedbackTextview.getId() == v.getId()) {
            navController.navigate(ClientProfileFragmentDirections.actionClientProfileFragmentToFeedbackFragment2());
        } else if (fragmentClientProfileBinding.changePasswordTextview.getId() == v.getId()) {
            navController.navigate(ClientProfileFragmentDirections.actionClientProfileFragmentToChangePasswordFragment());
        } else if (fragmentClientProfileBinding.myInfo.getId() == v.getId()) {
            navController.navigate(ClientProfileFragmentDirections.actionClientProfileFragmentToUpdateInfoAccountFragment());
        } else if (fragmentClientProfileBinding.myTripsTextview.getId() == v.getId()) {
            navController.navigate(ClientProfileFragmentDirections.actionClientProfileFragmentToTripsFragment());
        } else if (fragmentClientProfileBinding.logoutTextview.getId() == v.getId()) {
            //TODO : remove login Key and Navigate to login fragment
            preferenceHelper.removeLoginKey();
            navController.navigate(R.id.login_flow_nav);
            Log.e(TAG, "onClick: " + preferenceHelper.getLoginKey(), null);
            PublicHelperMethods.showMessageSnackbar(requireActivity().findViewById(android.R.id.content), getString(R.string.message_logout));
        }
    }

    @Override
    public void onSetChangeLanguageListener(String language) {
        String persistedLanguageData = preferenceHelper.getPersistedLanguageData("");

        if (!persistedLanguageData.equals(language)) {
            preferenceHelper.persistLanguage(language);
            requireActivity().recreate();

        }
    }


}
