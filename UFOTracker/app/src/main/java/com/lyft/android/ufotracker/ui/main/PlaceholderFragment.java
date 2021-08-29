package com.lyft.android.ufotracker.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyft.android.ufotracker.R;
import com.lyft.android.ufotracker.databinding.FragmentMainBinding;
import com.lyft.android.ufotracker.ui.model.Sighting;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.sectionLabel;
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        // Create adapter passing in the contact data
        SightingListAdapter adapter = new SightingListAdapter(createTestData(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.sightingList.setLayoutManager(layoutManager);
        // Attach the adapter to the recyclerview to populate items
        binding.sightingList.setAdapter(adapter);
        // Divider
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider_margin_left));
        binding.sightingList.addItemDecoration(mDividerItemDecoration);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Sighting> createTestData() {
        List<Sighting> testData = new ArrayList<>();
        testData.add(new Sighting("January 25, 2020 @ 6:00 AM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        testData.add(new Sighting("January 22, 2020 @ 7:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        testData.add(new Sighting("January 12, 2020 @ 6:20 AM", Sighting.SightingType.BLOB, "2 knots"));
        testData.add(new Sighting("January 25, 2020 @ 11:00 PM", Sighting.SightingType.LAMPSHADE, "14 knots"));
        testData.add(new Sighting("January 25, 2020 @ 10:00 PM", Sighting.SightingType.BLOB, "3 knots"));
        return testData;
    }
}