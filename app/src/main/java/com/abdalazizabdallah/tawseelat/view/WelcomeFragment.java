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
import com.abdalazizabdallah.tawseelat.databinding.FragmentWelcomeBinding;


public class WelcomeFragment extends Fragment {


    private final int STEP_0 = 0;
    private NavController navController;
    private FragmentWelcomeBinding fragmentWelcomeBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return fragmentWelcomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        fragmentWelcomeBinding.joinUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpAsEmployeeFragment());
            }
        });
    }
}