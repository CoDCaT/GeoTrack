package com.codcat.geotrack.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.codcat.geotrack.views.map_screen.MapFragment;
import com.codcat.geotrack.views.tracks_screen.TrackFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public MyPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);

        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments[position];

    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}