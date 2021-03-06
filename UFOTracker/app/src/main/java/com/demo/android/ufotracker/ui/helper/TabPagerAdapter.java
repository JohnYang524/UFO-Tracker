package com.demo.android.ufotracker.ui.helper;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.demo.android.ufotracker.R;
import com.demo.android.ufotracker.ui.view.SightingListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the tabs.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_name_1, R.string.tab_name_2};
    private final Context mContext;

    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SightingListFragment (defined as a static inner class below).
        return SightingListFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}