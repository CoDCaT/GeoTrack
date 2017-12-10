package com.codcat.geotrack.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codcat.geotrack.R;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.service.TrackingService;
import com.codcat.geotrack.views.Router.IRouter;
import com.codcat.geotrack.views.map_screen.MapFragment;
import com.codcat.geotrack.views.tracks_screen.TrackFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralActivity extends AppCompatActivity implements GeneralMvpView{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    private GeneralActivityPresenter<GeneralMvpView> mPresenter;
    private PagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        attachPresenter();

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
        menu.add(1, 1, 0, "Начать запись");
        menu.add(1, 2, 0, "Завершить запись");
        menu.add(1, 3, 0, "Маршруты");
        menu.add(1, 4, 0, "Карта");
        menu.add(1, 5, 0, "Очистить базу");
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

        Fragment trackFragment = new TrackFragment();

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), new Fragment[]{trackFragment, new MapFragment(), new SettingsFragment()});

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
//        fragTrans = getSupportFragmentManager().beginTransaction();
//
//        fragTrans.replace(R.id.frameLayout, trackFragment);
//
//        fragTrans.addToBackStack(null);
//        fragTrans.commit(); //TODO: check the second commit
    }

    @Override
    public void navigateToMap() {

//        fragTrans = getSupportFragmentManager().beginTransaction();
//
//        if (mapFragment == null) {
//            mapFragment = new MapFragment(); //Fragment.instantiate(this, MapFragment.class.getName());
//        }
//        fragTrans.replace(R.id.frameLayout, mapFragment);
//
//        fragTrans.addToBackStack(null);
//        fragTrans.commit();
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

    public void moveTo(int position, List<LatLng> points) {
        mViewPager.setCurrentItem(position);

        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.pager);
//        mapFragment.drawWay(points);
    }
}
