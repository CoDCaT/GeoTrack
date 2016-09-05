package com.codcat.geotrack.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codcat.geotrack.R;
import com.codcat.geotrack.presenter.IPresenter;
import com.codcat.geotrack.presenter.Presenter;
import com.codcat.geotrack.service.DBHelper;
import com.codcat.geotrack.service.MyLocation;
import com.codcat.geotrack.service.TrackingService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GeneralActivity extends AppCompatActivity implements IView, OnMapReadyCallback, IFragment{

    TrackFragment trackFragment;
    Fragment mapFragment;
    android.app.FragmentTransaction fragTrans;
    private LocationManager locManager;
    private MyLocation locListener = new MyLocation(this);
    private GoogleMap mMap;
    IPresenter presenter;// = new Presenter(new DBHelper(this), this);

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
        //Обработка нажатия пунктов меню ******************
        //
        fragTrans = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case 1:
                startService(new Intent(this, TrackingService.class));
                locManager.removeUpdates(locListener);
                break;
            case 2:
                stopService(new Intent(this, TrackingService.class));
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locListener);
                break;
            case 3:
//                Intent allTracks = new Intent(this, GeneralActivity.class);
//                startActivity(allTracks);
                fragTrans.replace(R.id.frameLayout, trackFragment);
                break;
            case 4:
                if(mapFragment == null) {
                    mapFragment = Fragment.instantiate(this, MapActivity.class.getName());
                }
                    //mapFragment = MapFragment.newInstance();

                //fragTrans.remove(trackFragment);
                fragTrans.replace(R.id.frameLayout, mapFragment);

                //mapFragment.getMapAsync(this);
//                presenter = new Presenter(new DBHelper(this), this);
//                presenter.readFromDB();
//                presenter.getDistance();
                break;
            case 5:
                presenter = new Presenter(new DBHelper(this), this);
                presenter.clearThisDB();
                //mMap.clear();
                break;


        }
        fragTrans.addToBackStack(null);
        fragTrans.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        fragTrans = getFragmentManager().beginTransaction();

        trackFragment = (TrackFragment) TrackFragment.instantiate(this, TrackFragment.class.getName()); //new TrackFragment();

        fragTrans.add(R.id.frameLayout, trackFragment);
        fragTrans.commit();
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
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, locListener);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Настройки карты *****
        //
        mMap.setMyLocationEnabled(true);
        UiSettings settings = mMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
    }

    @Override
    public IPresenter getPresenter() {
        return presenter;
    }
}
