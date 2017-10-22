package com.codcat.geotrack.views.main_screen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codcat.geotrack.R;
import com.codcat.geotrack.service.TrackingService;
import com.codcat.geotrack.views.map_screen.MapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GeneralActivity extends AppCompatActivity implements GeneralMvpView, OnMapReadyCallback {

    private TrackFragment trackFragment;
    private Fragment mapFragment;
    private FragmentTransaction fragTrans;
//    private LocationManager locManager;
//    private MyLocation locListener = new MyLocation(this);
    private GoogleMap mMap;

    GeneralActivityPresenter<GeneralMvpView> mPresenter;

//    IPresenter presenter;// = new GeneralActivityPresenter(new DBHelper(this), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        attachPresenter();

        init();

    }

    private void init() {

        attachFragment();

    }

    private void attachFragment() {
//        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fragTrans = getSupportFragmentManager().beginTransaction();
        trackFragment = new TrackFragment();
        fragTrans.add(R.id.frameLayout, trackFragment, "FRAG_TAG");
        fragTrans.commit();
    }

    private void attachPresenter() {
        mPresenter = new GeneralActivityPresenter<>(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        //слушатели изменения Геопозиции по GPS и интернету **********************************
        //Параметры: -Тип провайдера,
        //           -Минимальное время получения координат,
        //           -Минимальное расстояне получения координат,
        //           -Слушатль
        //
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, locListener);
    }

    @Override
    public void showLocation(Location location) {

    }

    @Override
    public void drowWay(List<LatLng> list) {

    }

    @Override
    public void showDistance(float dictance) {

    }

    @Override
    public void showTracks() {

    }

    @Override
    public void runService() {
        startService(new Intent(this, TrackingService.class));
    }

    @Override
    public void stopService() {
        stopService(new Intent(this, TrackingService.class));
    }

    @Override
    public void navigateToTrackList() {
        fragTrans = getSupportFragmentManager().beginTransaction();

        fragTrans.replace(R.id.frameLayout, trackFragment);

        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    @Override
    public void navigateToMap() {

        fragTrans = getSupportFragmentManager().beginTransaction();

        if (mapFragment == null) {
            mapFragment = Fragment.instantiate(this, MapActivity.class.getName());
        }
        fragTrans.replace(R.id.frameLayout, mapFragment);

        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Настройки карты *****
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        UiSettings settings = mMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
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
