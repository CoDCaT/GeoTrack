package com.codcat.geotrack.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;

import com.codcat.geotrack.R;
import com.codcat.geotrack.service.TrackingService;
import com.codcat.geotrack.views.map_screen.MapFragment;
import com.codcat.geotrack.views.setting_screen.SettingsFragment;
import com.codcat.geotrack.views.tracks_screen.TrackFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class GeneralActivity extends DaggerAppCompatActivity implements GeneralMvpView{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    @Inject GeneralActivityPresenter<GeneralMvpView> mPresenter;
    @Inject TrackFragment trackFragment;
    @Inject MapFragment mapFragment;
    @Inject SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.onClickButtonMenu(item);
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        ButterKnife.bind(this);

        setToolbar();
        setViewPager();
    }

    private void setViewPager() {

        PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), new Fragment[]{trackFragment, mapFragment, settingsFragment});

        mViewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_my_calendar);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_myplaces);
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_menu_edit);

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void attachPresenter() {
        mPresenter = new GeneralActivityPresenter<>(this);
    }

    public void moveTo(int position, List<LatLng> points) {
        mViewPager.setCurrentItem(position);
    }


    @Override
    public void beginTrack() {
        startService(new Intent(this, TrackingService.class));
    }

    @Override
    public void stopTrack() {
        stopService(new Intent(this, TrackingService.class));
    }

    @Override
    public void navigateToTrackList() {
    }

    @Override
    public void navigateToMap() {
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@NonNull String message) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
