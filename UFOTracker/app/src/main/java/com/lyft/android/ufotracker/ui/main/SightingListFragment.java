package com.lyft.android.ufotracker.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyft.android.ufotracker.MainActivity;
import com.lyft.android.ufotracker.R;
import com.lyft.android.ufotracker.databinding.FragmentMainBinding;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.List;

/**
 * A fragment displaying a list of sighting items.
 */
public class SightingListFragment extends Fragment {

    private static final boolean mIsDebuggable = true;
    private static final String  TAG           = PageViewModel.class.getName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;
    private SightingListAdapter listAdapter;

    public static SightingListFragment newInstance(int index) {
        SightingListFragment fragment = new SightingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = Sighting.SightingCategory.STRANGE_FLYERS.tabIndex; // default tab
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setTabIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pageViewModel.getFilteredSightings().observe(getViewLifecycleOwner(), new Observer<List<Sighting>>() {
            @Override
            public void onChanged(List<Sighting> sightings) {
                if (listAdapter == null)
                    initSightingList(sightings);
                else {
                    listAdapter.notifyDataChange(sightings);
                }
            }
        });

        attachListRefreshListener();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initSightingList(List<Sighting> sightingsList) {

        listAdapter = new SightingListAdapter(sightingsList, getContext(), clickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.sightingList.setLayoutManager(layoutManager);
        binding.sightingList.setAdapter(listAdapter);
        // Divider
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider_margin_left));
        binding.sightingList.addItemDecoration(mDividerItemDecoration);
    }

    SightingListAdapter.ListItemClickListener clickListener = new SightingListAdapter.ListItemClickListener() {
        @Override
        public void onItemClicked(int position) {
            if (mIsDebuggable) Log.v(TAG, "onItemClicked: " + position);
        }
        @Override
        public void onLongClicked(int position) {
            if (mIsDebuggable) Log.v(TAG, "onItemClicked: " + position);
        }
        @Override
        public void onItemRemoved(int position) {
            if (mIsDebuggable) Log.v(TAG, "onItemRemoved: " + position);
            pageViewModel.removeSightingAt(position);
        }
    };

    // Attach a list update listener
    private void attachListRefreshListener() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).attachListRefreshListener(new MainActivity.ListRefreshListener() {
                @Override
                public void onNewSightingAdded(Sighting sighting) {
                    pageViewModel.onNewSightingAdded(sighting);
                }
            });
        }
    }

}