package com.abdalazizabdallah.tawseelat.model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.databinding.ItemListForEmployeeBinding;
import com.abdalazizabdallah.tawseelat.heplers.OnMyActionListener;

import java.util.List;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.ViiewHolderListEmployeeAdapter> {

    private final List<Employee> employeeList;

    private final OnMyActionListener onMyActionListener;

    public ListEmployeeAdapter(List<Employee> employeeList, OnMyActionListener onMyActionListener) {
        this.employeeList = employeeList;
        this.onMyActionListener = onMyActionListener;
    }

    @NonNull
    @Override
    public ViiewHolderListEmployeeAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListForEmployeeBinding itemListForEmployeeBinding =
                ItemListForEmployeeBinding.inflate(layoutInflater, parent, false);


        return new ViiewHolderListEmployeeAdapter(itemListForEmployeeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViiewHolderListEmployeeAdapter holder, int position) {
        holder.bind(employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    class ViiewHolderListEmployeeAdapter extends RecyclerView.ViewHolder {

        ItemListForEmployeeBinding itemView;

        public ViiewHolderListEmployeeAdapter(@NonNull ItemListForEmployeeBinding itemView) {
            // itemView is esstional item to design Layout
            super(itemView.getRoot());
            itemView.getRoot().setOnClickListener(v -> onMyActionListener.onMyAction());
            this.itemView = itemView;
            // because the layout is imageView
            // when the layout e.g. linear you must use findViewById
        }

        public void bind(Employee employee) {
            //TODO : bind item with view
            itemView.phoneTextView.setText(employee.getPhoneNumber());
            itemView.nameTextView.setText(employee.getClientInformation().getClientName());
        }
    }
}
