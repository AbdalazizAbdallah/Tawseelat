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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentShowEmployeesBinding;
import com.abdalazizabdallah.tawseelat.model.ClientInformation;
import com.abdalazizabdallah.tawseelat.model.Employee;
import com.abdalazizabdallah.tawseelat.model.ListEmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowInfoEmployeesFragment extends Fragment {

    private FragmentShowEmployeesBinding fragmentShowEmployeesBinding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentShowEmployeesBinding = FragmentShowEmployeesBinding.inflate(inflater, container, false);
        return fragmentShowEmployeesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentShowEmployeesBinding.toolbar, navController, appBarConfiguration);


        fragmentShowEmployeesBinding.hireEmployeeButton.setOnClickListener(v -> navController.navigate(NavGraphDirections.actionGlobalScanQRCodeForHireEmployeeFragment()));

        List<Employee> list = getList();
//        Log.e(TAG, "onViewCreated: " + list.size(), null);
//        for (int i = 0; i < list.size(); i++) {
//            Log.e(TAG, list.get(i).toString(), null);
//        }

        //List<TripsRequestsHistory> list = null;

        if (list != null) {
            fragmentShowEmployeesBinding.linearLayoutNoResultFound.setVisibility(View.GONE);
            fragmentShowEmployeesBinding.recyclerView.setVisibility(View.VISIBLE);

            ListEmployeeAdapter listTripsRecyclerAdapter = new ListEmployeeAdapter(list, () -> {
                // TODO navigate to employee trip
                navController.navigate(NavGraphDirections.actionGlobalTripsFragment());
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

            //TODO : Get list of TripsRequestsHistory from Api
            fragmentShowEmployeesBinding.recyclerView.setLayoutManager(linearLayoutManager);
            fragmentShowEmployeesBinding.recyclerView.setAdapter(listTripsRecyclerAdapter);
            fragmentShowEmployeesBinding.recyclerView.setItemViewCacheSize(5);
        } else {

            fragmentShowEmployeesBinding.linearLayoutNoResultFound.setVisibility(View.VISIBLE);
            fragmentShowEmployeesBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    private List<Employee> getList() {

        Employee employee = new Employee();
        employee.setPhoneNumber(getString(R.string.demo_phone_text));
        employee.setClientInformation(new ClientInformation(getString(R.string.demo_employee_name_text),
                null, null, null));

        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);
        employeeList.add(employee);


        return employeeList;
    }
}