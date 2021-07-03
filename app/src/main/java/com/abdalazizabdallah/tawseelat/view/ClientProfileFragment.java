package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
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
import com.abdalazizabdallah.tawseelat.heplers.LocaleHelper;
import com.abdalazizabdallah.tawseelat.heplers.PreferenceHelper;


public class ClientProfileFragment extends Fragment implements View.OnClickListener, OnChangeLanguageListener {


    private static final String TAG = "ClientProfileFragment";
    private FragmentClientProfileBinding fragmentClientProfileBinding;
    private NavController navController;
    private LocaleHelper localeHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeHelper = LocaleHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        }
    }

    @Override
    public void onSetChangeLanguageListener(String language) {
        String persistedLanguageData = PreferenceHelper.getPersistedLanguageData(getContext(), "");

        if (!persistedLanguageData.equals(language)) {
            PreferenceHelper.persistLanguage(getContext(), language);
            requireActivity().recreate();

            //  Context contextChange = localeHelper.changeLanguageInRuntime(getContext());

//                        requireActivity().getBaseContext().getResources().updateConfiguration(
//                                contextChange.getResources().getConfiguration(),
//                                requireActivity().getResources().getDisplayMetrics());
        }
    }


}
