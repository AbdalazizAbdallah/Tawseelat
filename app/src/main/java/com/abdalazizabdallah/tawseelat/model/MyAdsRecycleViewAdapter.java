package com.abdalazizabdallah.tawseelat.model;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.R;
import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class MyAdsRecycleViewAdapter extends RecyclerView.Adapter<MyAdsRecycleViewAdapter.MyAdsViewHolder> {

    //  private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final List<Ads> adsList;
    private final Application application;

    public MyAdsRecycleViewAdapter(Application application, List<Ads> adsList) {
        this.adsList = adsList;
        this.application = application;
        //      viewBinderHelper.setOpenOnlyOne(true);// just one open at time
    }

    @NonNull
    @Override
    public MyAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_ads_recycle_view, parent, false);
        return new MyAdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdsViewHolder holder, int position) {

        //viewBinderHelper.bind(holder.swipeRevealLayout, adsList.get(position).getTitle());// to save state
//        viewBinderHelper.closeLayout(adsList.get(position).getTitle());
        holder.bind(adsList.get(position));
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }


    class MyAdsViewHolder extends RecyclerView.ViewHolder {


        private final MaterialTextView textViewTime;
        private final MaterialTextView textViewTitle;
        private final MaterialTextView textViewPrice;
        private final AppCompatImageView imageView;

        private AppCompatImageButton imageButtonEdit;
        private AppCompatImageButton imageButtonDelete;

        //  private SwipeRevealLayout swipeRevealLayout;

        public MyAdsViewHolder(@NonNull View itemView) {
            // itemView is esstional item to design Layout
            super(itemView);
            imageView = itemView.findViewById(R.id.item_recycle_imageView);
            textViewTime = itemView.findViewById(R.id.item_recycle_textViewTime);
            textViewPrice = itemView.findViewById(R.id.item_recycle_textViewPrice);
            textViewTitle = itemView.findViewById(R.id.item_recycle_textViewtitle);
//            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
//            imageButtonEdit = itemView.findViewById(R.id.item_recycle_imageButtonEdit);
//            imageButtonDelete = itemView.findViewById(R.id.item_recycle_imageButtonDelete);

            imageButtonEdit.setOnClickListener(v -> Toast.makeText(application, "imageButtonEdit", Toast.LENGTH_SHORT).show());

            imageButtonDelete.setOnClickListener(v -> Toast.makeText(application, "imageButtonDelete", Toast.LENGTH_SHORT).show());

        }

        public void bind(Ads ads) {
            //TODO : bind item with view

            Glide.with(imageView)
                    .load(ResourcesCompat.getDrawable(imageView.getContext().getResources(), Integer.parseInt(ads.getPhotoPath()), null))
                    .into(imageView);

            textViewTime.setText(ads.getTime());
            textViewPrice.setText(ads.getPrice());
            textViewTitle.setText(ads.getTitle());
        }
    }
}
