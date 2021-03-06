package com.demo.android.ufotracker.ui.view;

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

import com.demo.android.ufotracker.MainActivity;
import com.demo.android.ufotracker.R;
import com.demo.android.ufotracker.db.SightingDatabase;
import com.demo.android.ufotracker.databinding.FragmentListBinding;
import com.demo.android.ufotracker.ui.helper.SightingListAdapter;
import com.demo.android.ufotracker.model.Sighting;
import com.demo.android.ufotracker.ui.viewmodel.SightlingListViewModel;

import java.util.List;

/**
 * A fragment displaying a list of Sighting items.
 */
public class SightingListFragment extends Fragment {

    private static final boolean mIsDebuggable = true;
    private static final String  TAG           = SightingListFragment.class.getName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SightlingListViewModel mViewModel;
    private FragmentListBinding binding;
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
        mViewModel = new ViewModelProvider(this).get(SightlingListViewModel.class);
        int index = Sighting.SightingCategory.STRANGE_FLYERS.tabIndex; // default tab
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        mViewModel.setTabIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getFilteredSightings().observe(getViewLifecycleOwner(), new Observer<List<Sighting>>() {
            @Override
            public void onChanged(List<Sighting> sightings) {
                if (mIsDebuggable) Log.v(TAG, "Filtered list updated. Current size: ");

            }
        });

        mViewModel.getSightingList().observe(getViewLifecycleOwner(), sightings -> {
            mViewModel.onSightingListUpdated(sightings);
            List<Sighting> filteredList = mViewModel.filterList(sightings);
            if (filteredList == null || filteredList.size() == 0) {
                displayEmptyLayout(true);// show empty layout if no data
                return;
            }
            if (listAdapter == null) {
                initSightingList(filteredList);
            } else {
                listAdapter.notifyDataChange(filteredList);
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
//            NavHostFragment.findNavController(SightingListFragment.this)
//                    .navigate(R.id.action_list_to_2nd);
        }
        @Override
        public void onLongClicked(int position) {
            if (mIsDebuggable) Log.v(TAG, "onItemClicked: " + position);
        }
        @Override
        public void onItemRemoved(Sighting sighting) {
//            if (mIsDebuggable) Log.v(TAG, "onItemRemoved: " + position);
            mViewModel.removeSightingAt(sighting);
        }
    };

    // Attach a list update listener
    private void attachListRefreshListener() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).attachListRefreshListener(new MainActivity.ListRefreshListener() {
                @Override
                public void onNewSightingAdded(Sighting sighting) {
                    mViewModel.onNewSightingAdded(sighting);
                }
            });
        }
    }

    private void displayEmptyLayout(boolean isEmpty) {
        if (isEmpty) {
            binding.sightingList.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.sightingList.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
        }
    }

}