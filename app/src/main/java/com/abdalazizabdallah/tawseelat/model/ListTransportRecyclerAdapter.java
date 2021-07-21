package com.abdalazizabdallah.tawseelat.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.ItemListForTransportBinding;

import java.util.List;

public class ListTransportRecyclerAdapter extends RecyclerView.Adapter<ListTransportRecyclerAdapter.ViewHolderListTransport> {

    private static final String TAG = "ListTransportRecyclerAd";
    private final List<CompanyTransportation> companyTransportations;

    public ListTransportRecyclerAdapter(List<CompanyTransportation> companyTransportations) {
        this.companyTransportations = companyTransportations;
    }

    @NonNull
    @Override
    public ViewHolderListTransport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListForTransportBinding itemListForTransportBinding = ItemListForTransportBinding.inflate(layoutInflater, parent, false);

        return new ViewHolderListTransport(itemListForTransportBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListTransport holder, int position) {
        holder.bind(companyTransportations.get(position));
    }

    @Override
    public int getItemCount() {
        return companyTransportations.size();
    }


    class ViewHolderListTransport extends RecyclerView.ViewHolder {

        private final ItemListForTransportBinding itemListForTransportBinding;
        private final Context context;


        public ViewHolderListTransport(@NonNull ItemListForTransportBinding itemListForTransportBinding) {
            // itemView is esstional item to design Layout
            super(itemListForTransportBinding.getRoot());
            context = itemListForTransportBinding.getRoot().getContext();

            this.itemListForTransportBinding = itemListForTransportBinding;

            // because the layout is imageView
            // when the layout e.g. linear you must use findViewById
            itemListForTransportBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: ", null);
                    Navigation.findNavController(v).navigate(NavGraphDirections.actionGlobalMapsFragment());

                }
            });
        }

        public void bind(CompanyTransportation companyTransportation) {
            //TODO : bind item with view
            CompanyInfo companyInfo = companyTransportation.getCompanyInfo();

            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),
                    companyInfo.getCompanyType().equals("Taxis") ? R.drawable.ic_taxi :
                            R.drawable.ic_scooter, null);


            itemListForTransportBinding.imageView.setImageDrawable(drawable);
            itemListForTransportBinding.companyNameTextview.setText(companyInfo.getCompanyName());
            itemListForTransportBinding.companyLocationTextview.setText(companyInfo.getCompanyType());

        }
    }
}
