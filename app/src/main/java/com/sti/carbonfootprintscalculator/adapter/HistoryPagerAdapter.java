package com.sti.carbonfootprintscalculator.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sti.carbonfootprintscalculator.fragment.HistoryFragment;

/**
 * Created by mykelneds on 06/03/2017.
 */

public class HistoryPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];

    public HistoryPagerAdapter(FragmentManager fm, CharSequence[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle b = new Bundle();
        b.putInt("page", position);

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
