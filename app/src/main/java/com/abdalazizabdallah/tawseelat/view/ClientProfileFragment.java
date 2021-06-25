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


public class ClientProfileFragment extends Fragment {


    private static final String TAG = "ClientProfileFragment";
    private FragmentClientProfileBinding fragmentClientProfileBinding;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        fragmentClientProfileBinding.feedbackTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ", null);
                navController.navigate(ClientProfileFragmentDirections.
                        actionClientProfileFragmentToFeedbackFragment2());

            }
        });
    }
}