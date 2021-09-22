package com.lyft.android.ufotracker.ui.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lyft.android.ufotracker.R;
import com.lyft.android.ufotracker.ui.model.Sighting;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * SightingListAdapter as RecyclerView adapter.
 */
public class SightingListAdapter extends RecyclerView.Adapter<SightingListAdapter.ViewHolder> {

    private List<Sighting> mSightings;
    private Context mContext;
    private ListItemClickListener mListener;
    private int toBeRemovedPosition = -1;

    public interface ListItemClickListener {
        void onItemClicked(int position);
        void onLongClicked(int position);
        void onItemRemoved(Sighting sighting);
    }

    public SightingListAdapter(List<Sighting> sightings, Context context, ListItemClickListener listener) {
        mSightings = new ArrayList<>();
        mSightings.addAll(sightings);
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View sightingView = inflater.inflate(R.layout.row_item_sighting, parent, false);

        ViewHolder viewHolder = new ViewHolder(sightingView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data model based on position
        Sighting sighting = mSightings.get(position);

        holder.dateTextView.setText(sighting.getDate());
        holder.speedTextView.setText(sighting.getSpeed());
        holder.typeTextView.setText(sighting.getType().toString().replace("_", " "));
        holder.sightingImage.setImageDrawable(mContext.getResources().getDrawable(sighting.getType().imageId));
        if (position == toBeRemovedPosition) { // Show remove button when clicked
            holder.removeButton.setVisibility(View.VISIBLE);
        } else {
            holder.removeButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mSightings == null ? 0 : mSightings.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView dateTextView;
        public TextView speedTextView;
        public TextView typeTextView;
        public ImageView sightingImage;
        public Button removeButton;
        private WeakReference<ListItemClickListener> listenerRef;// Use WeakReference to eliminate a potential memory leak.

        public ViewHolder(View itemView, ListItemClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            dateTextView = itemView.findViewById(R.id.tv_sighting_date);
            speedTextView = itemView.findViewById(R.id.tv_sighting_speed);
            typeTextView = itemView.findViewById(R.id.tv_sighting_type);
            sightingImage = itemView.findViewById(R.id.iv_sighting_img);
            removeButton = itemView.findViewById(R.id.bt_remove);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == removeButton.getId()) {
                toBeRemovedPosition = -1; // Reset flag
//                removeAt(getAdapterPosition());
                listenerRef.get().onItemRemoved(mSightings.get(getAdapterPosition()));
            } else {
                toggleRemoveButtonVisibility(getAdapterPosition());
                listenerRef.get().onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            listenerRef.get().onLongClicked(getAdapterPosition());
            return false;
        }
    }

   /* public void removeAt(int position) {
        mSightings.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSightings.size());
    }*/

    public void toggleRemoveButtonVisibility(int position) {
        // Toggle Remove button visibility
        if (position != toBeRemovedPosition) { // show button, and hide the other remove button
            toBeRemovedPosition = position;
        } else { // hide remove button at current position
            toBeRemovedPosition = -1;
        }
        notifyDataSetChanged(); // update list
    }

    public void notifyDataChange(List<Sighting> sightings) {
        mSightings.clear();
        mSightings.addAll(sightings);
        notifyDataSetChanged();
    }
}
