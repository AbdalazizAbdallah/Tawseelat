package com.abdalazizabdallah.tawseelat.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentClientMainBinding;
import com.abdalazizabdallah.tawseelat.model.CompanyInfo;
import com.abdalazizabdallah.tawseelat.model.CompanyTransportation;
import com.abdalazizabdallah.tawseelat.model.ListTransportRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClientMainFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "ClientMainFragment";
    private FragmentClientMainBinding fragmentClientMainBinding;
    private NavController navController;
    private String defaultFilter = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultFilter = getString(R.string.all_text);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentClientMainBinding = FragmentClientMainBinding.inflate(inflater, container, false);
        return fragmentClientMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        fragmentClientMainBinding.toolbar.setOnMenuItemClickListener(this);

        List<CompanyTransportation> list = getList();
        Log.e(TAG, "onViewCreated: " + list.size(), null);
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, list.get(i).toString(), null);
        }


        ListTransportRecyclerAdapter listTransportRecyclerAdapter = new ListTransportRecyclerAdapter(list);
        fragmentClientMainBinding.mainFragmentRecycleView.setLayoutManager(
                new LinearLayoutManager(requireContext()));

        fragmentClientMainBinding.mainFragmentRecycleView.setAdapter(listTransportRecyclerAdapter);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                navController.navigate(ClientMainFragmentDirections.actionClientMainFragmentToSearchFragment());
                break;
            case R.id.all_filter:
                if (!defaultFilter.equals(getString(R.string.all_text))) {
                    // TODO all filter company taxis
                }
                break;
            case R.id.cities_filter:
                if (!defaultFilter.equals(getString(R.string.cities_text))) {
                    // TODO cities filter company taxis
                }
                break;
            case R.id.taxis_filter:
                if (!defaultFilter.equals(getString(R.string.taxis_text))) {
                    // TODO taxis filter company taxis
                }
                break;
            case R.id.delivery_filter:
                if (!defaultFilter.equals(getString(R.string.delivery_text))) {
                    // TODO delivery filter company taxis
                }
                break;
        }
        return false;
    }

    private List<CompanyTransportation> getList() {
        CompanyTransportation companyTransportation = new CompanyTransportation(new CompanyInfo(
                "مطعم فهد",
                "غزة",
                "Delivery"
        ));
        CompanyTransportation companyTransportation2 = new CompanyTransportation(new CompanyInfo(
                getString(R.string.demo_company_name_text),
                getString(R.string.demo_city_text),
                "Taxis"
        ));

        List<CompanyTransportation> listCompanyTransportations = new ArrayList<>();
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);
        listCompanyTransportations.add(companyTransportation);
        listCompanyTransportations.add(companyTransportation2);

        return listCompanyTransportations;
    }


}