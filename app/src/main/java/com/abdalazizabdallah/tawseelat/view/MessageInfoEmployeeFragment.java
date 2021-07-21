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
import com.abdalazizabdallah.tawseelat.databinding.FragmentMessageInfoEmployeeBinding;


public class MessageInfoEmployeeFragment extends Fragment {

    private FragmentMessageInfoEmployeeBinding fragmentMessageInfoEmployeeBinding;
    private NavController navControllerParent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentMessageInfoEmployeeBinding = FragmentMessageInfoEmployeeBinding.inflate(inflater, container, false);
        return fragmentMessageInfoEmployeeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navControllerParent = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        fragmentMessageInfoEmployeeBinding.myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navControllerParent.popBackStack();
            }
        });

    }
}