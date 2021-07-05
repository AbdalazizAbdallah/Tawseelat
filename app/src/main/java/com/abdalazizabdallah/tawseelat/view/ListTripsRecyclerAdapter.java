package com.abdalazizabdallah.tawseelat.view;

import android.content.Context;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.ItemListForTripsBinding;
import com.abdalazizabdallah.tawseelat.model.TripsRequestsHistory;

import java.util.List;

public class ListTripsRecyclerAdapter extends RecyclerView.Adapter<ListTripsRecyclerAdapter.ListTripsRecyclerAdapterViewHolder> {

    private final List<TripsRequestsHistory> myTrips;

    private final Context context;

    public ListTripsRecyclerAdapter(List<TripsRequestsHistory> myTrips, Context context) {
        this.myTrips = myTrips;
        this.context = context;
    }

    @NonNull
    @Override
    public ListTripsRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListForTripsBinding itemListForTripsBinding = ItemListForTripsBinding.inflate(layoutInflater, parent, false);


        return new ListTripsRecyclerAdapterViewHolder(itemListForTripsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTripsRecyclerAdapterViewHolder holder, int position) {
        holder.bind(myTrips.get(position));

    }

    @Override
    public int getItemCount() {
        return myTrips.size();
    }


    class ListTripsRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemListForTripsBinding itemListForTripsBinding;

        public ListTripsRecyclerAdapterViewHolder(@NonNull ItemListForTripsBinding itemListForTripsBinding) {
            super(itemListForTripsBinding.getRoot());

            this.itemListForTripsBinding = itemListForTripsBinding;

            itemListForTripsBinding.moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListForTripsBinding.detailsConstraintLayout.getVisibility() == View.GONE) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            TransitionManager.beginDelayedTransition(itemListForTripsBinding.detailsConstraintLayout, new AutoTransition());
                        }
                        itemListForTripsBinding.detailsConstraintLayout.setVisibility(View.VISIBLE);
                        itemListForTripsBinding.finishedDate2.setVisibility(View.INVISIBLE);
                        itemListForTripsBinding.finishedDateTextView2.setText(context.getString(R.string.details_text));

                        itemListForTripsBinding.moreButton.setIcon(
                                AppCompatResources.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_up_24));

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            TransitionManager.beginDelayedTransition(itemListForTripsBinding.detailsConstraintLayout, new AutoTransition());
                        }
                        itemListForTripsBinding.detailsConstraintLayout.setVisibility(View.GONE);
                        itemListForTripsBinding.finishedDate2.setVisibility(View.VISIBLE);
                        itemListForTripsBinding.finishedDateTextView2.setText(context.getString(R.string.finished_date_text));

                        itemListForTripsBinding.moreButton.setIcon(
                                AppCompatResources.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_down_24));

                    }
                }
            });
        }

        public void bind(TripsRequestsHistory trips) {
            //TODO : bind item with view

            itemListForTripsBinding.acceptedDate.setText(trips.getAcceptedDate());
            itemListForTripsBinding.finishedDate.setText(trips.getFinishDate());
            itemListForTripsBinding.finishedDate2.setText(trips.getFinishDate());

            itemListForTripsBinding.sourceTripTextView.setText(trips.getSource());
            itemListForTripsBinding.destinationTripTextView.setText(trips.getDestination());


            itemListForTripsBinding.nearestPlace.setText(trips.getNearestPlace());
            itemListForTripsBinding.companyName.setText(trips.getCompanyName());
            itemListForTripsBinding.clientName.setText(trips.getClientName());
            itemListForTripsBinding.employeeName.setText(trips.getEmployeeName());


        }
    }


}
