package com.abdalazizabdallah.tawseelat.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalazizabdallah.tawseelat.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NotificationListRecycleViewAdapter extends RecyclerView.Adapter<NotificationListRecycleViewAdapter.NotificationListViewHolder> {

    private final List<MyNotification> notificationList;


    public NotificationListRecycleViewAdapter(List<MyNotification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_recycle_view, parent, false);
        return new NotificationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {
        holder.bind(notificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    class NotificationListViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewTitle;
        private final MaterialTextView textViewBody;


        public NotificationListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.item_notification_recycle_textViewTitle);
            textViewBody = itemView.findViewById(R.id.item_notification_recycle_textViewBody);
        }

        public void bind(MyNotification myNotification) {
            //TODO : bind item with view
            textViewTitle.setText(myNotification.getTitleNotification());
            textViewBody.setText(myNotification.getBodyNotification());
        }
    }
}