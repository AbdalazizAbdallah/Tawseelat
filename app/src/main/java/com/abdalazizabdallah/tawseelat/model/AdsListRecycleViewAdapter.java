package com.abdalazizabdallah.tawseelat.model;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.R;
import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AdsListRecycleViewAdapter extends RecyclerView.Adapter<AdsListRecycleViewAdapter.AdsListViewHolder> {

    private final List<Ads> adsList;


    public AdsListRecycleViewAdapter(List<Ads> adsList) {
        this.adsList = adsList;
    }

    @NonNull
    @Override
    public AdsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycle_view, parent, false);
        return new AdsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsListViewHolder holder, int position) {
        holder.bind(adsList.get(position));
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }


    class AdsListViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewTime;
        private final MaterialTextView textViewTitle;
        private final MaterialTextView textViewPrice;
        private final AppCompatImageView imageView;

        public AdsListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_recycle_imageView);
            textViewTime = itemView.findViewById(R.id.item_recycle_textViewTime);
            textViewPrice = itemView.findViewById(R.id.item_recycle_textViewPrice);
            textViewTitle = itemView.findViewById(R.id.item_recycle_textViewtitle);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(Ads ads) {
            //TODO : bind item with view

            Glide.with(imageView)
                    .load(imageView.getContext().getResources().getDrawable(Integer.parseInt(ads.getPhotoPath())))
                    .into(imageView);

            textViewTime.setText(ads.getTime());
            textViewPrice.setText(ads.getPrice());
            textViewTitle.setText(ads.getTitle());
        }
    }
}
