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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentTripsBinding;
import com.abdalazizabdallah.tawseelat.model.TripsRequestsHistory;

import java.util.ArrayList;
import java.util.List;


public class TripsFragment extends Fragment {

    private static final String TAG = "TripsFragment";
    private FragmentTripsBinding fragmentTripsBinding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTripsBinding = FragmentTripsBinding.inflate(inflater, container, false);
        return fragmentTripsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentTripsBinding.toolbar, navController, appBarConfiguration);

        List<TripsRequestsHistory> list = getList();
        Log.e(TAG, "onViewCreated: " + list.size(), null);
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, list.get(i).toString(), null);
        }


        if (list != null) {
            fragmentTripsBinding.noTrips.setVisibility(View.GONE);
            fragmentTripsBinding.recyclerView.setVisibility(View.VISIBLE);

            ListTripsRecyclerAdapter listTripsRecyclerAdapter = new ListTripsRecyclerAdapter(list, requireContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

            //TODO : Get list of TripsRequestsHistory from Api
            fragmentTripsBinding.recyclerView.setLayoutManager(linearLayoutManager);
            fragmentTripsBinding.recyclerView.setAdapter(listTripsRecyclerAdapter);
        } else {
            fragmentTripsBinding.noTrips.setVisibility(View.VISIBLE);
            fragmentTripsBinding.recyclerView.setVisibility(View.GONE);
        }


    }


    private List<TripsRequestsHistory> getList() {
        TripsRequestsHistory tripsRequestsHistory = new TripsRequestsHistory(getString(R.string.demo_source_place_text),
                getString(R.string.demo_destination_place_text),
                getString(R.string.demo_employee_name_text),
                getString(R.string.demo_client_name_text),
                getString(R.string.demo_company_name_text),
                getString(R.string.demo_date_time_text),
                getString(R.string.demo_date_time_text),
                getString(R.string.demo_nearest_place_text));

        List<TripsRequestsHistory> historyList = new ArrayList<>();
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);
        historyList.add(tripsRequestsHistory);

        return historyList;
    }
}