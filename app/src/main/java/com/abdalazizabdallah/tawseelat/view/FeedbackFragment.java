package com.abdalazizabdallah.tawseelat.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentFeedbackBinding;

public class FeedbackFragment extends Fragment {

    private static final String OUR_EMAIL = "abdalazizabdalluh3@gmail.com";
    private FragmentFeedbackBinding fragmentFeedbackBinding;
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);
        return fragmentFeedbackBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentFeedbackBinding.toolbar, navController, appBarConfiguration);

        fragmentFeedbackBinding.submitButton.setOnClickListener(v -> {

            String name = String.valueOf(fragmentFeedbackBinding.nameEditText.getText());
            String subject = String.valueOf(fragmentFeedbackBinding.subjectEditText.getText());
            String letter = String.valueOf(fragmentFeedbackBinding.letterEditText.getText());

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(subject) || TextUtils.isEmpty(letter)) {

                fragmentFeedbackBinding.nameInputLayout.setError(
                        TextUtils.isEmpty(name) ? getString(R.string.required) : "");
                fragmentFeedbackBinding.subjectInputLayout.setError(
                        TextUtils.isEmpty(subject) ? getString(R.string.required) : "");
                fragmentFeedbackBinding.letterInputLayout.setError(
                        TextUtils.isEmpty(letter) ? getString(R.string.required) : "");

            } else {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{OUR_EMAIL});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject + "-" + name);
                emailIntent.putExtra(Intent.EXTRA_TEXT, letter);

                try {
                    if (emailIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(Intent.createChooser(emailIntent, "choose Email client please"));
                    }
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "No Email client found!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
