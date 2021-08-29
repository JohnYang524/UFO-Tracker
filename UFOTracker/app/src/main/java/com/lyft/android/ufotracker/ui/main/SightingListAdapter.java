package com.lyft.android.ufotracker.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lyft.android.ufotracker.R;
import com.lyft.android.ufotracker.ui.model.Sighting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SightingListAdapter extends RecyclerView.Adapter<SightingListAdapter.ViewHolder> {

    private List<Sighting> mSightings;
    private Context context;

    public SightingListAdapter(List<Sighting> sightings, Context context) {
        mSightings = new ArrayList<>();
        mSightings.addAll(sightings);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View sightingView = inflater.inflate(R.layout.row_item_sighting, parent, false);

        ViewHolder viewHolder = new ViewHolder(sightingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data model based on position
        Sighting sighting = mSightings.get(position);

        TextView nameTextView = holder.dateTextView;
        TextView phoneTextView = holder.speedTextView;
        TextView typeTextView = holder.typeTextView;
        ImageView sightingImage = holder.sightingImage;

        nameTextView.setText(sighting.getDate());
        phoneTextView.setText(sighting.getSpeed());
        typeTextView.setText(sighting.getType().toString());
        sightingImage.setImageDrawable(context.getResources().getDrawable(sighting.getType().imageId));
    }

    @Override
    public int getItemCount() {
        return mSightings == null ? 0 : mSightings.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView speedTextView;
        public TextView typeTextView;
        public ImageView sightingImage;

        public ViewHolder(View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.tv_sighting_date);
            speedTextView = itemView.findViewById(R.id.tv_sighting_speed);
            typeTextView = itemView.findViewById(R.id.tv_sighting_type);
            sightingImage = itemView.findViewById(R.id.iv_sighting_img);

        }
    }

//    public void notifyDataChange(List<Contact> contacts) {
//        mSightings.clear();
//        mSightings.addAll(contacts);
//        notifyDataSetChanged();
//    }
}
