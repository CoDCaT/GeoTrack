package com.codcat.geotrack.service;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.codcat.geotrack.App;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.views.map_screen.MapMvpPresenter;


public class TrackingService extends Service implements IService {

    private IRepository appRepository;

    private LocationManager locationManager;
    private MyLocation locationListener;
    public boolean serviceWork = false;

    private MapMvpPresenter mPresenter;
    PendingIntent pi;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        init();

        //TODO: test repository after destroy service!!!
        appRepository = App.appRepository;

        if (isServiceWork()){
            return START_REDELIVER_INTENT;
        }else {
            setServiceWork(true);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_REDELIVER_INTENT;
        }

        //TODO: add Network provider
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);

        Log.d("LOGTAG", "Start Service!");
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("LOGTAG", "Stop Service!");
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(locationListener);
        setServiceWork(false);
    }


    private void init() {

        attachPresenter();
        setLocation();

    }

    private void setLocation() {
        if(locationManager == null) locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationListener == null) locationListener = new MyLocation(mPresenter);
    }

    private void attachPresenter() {
//        if(mPresenter == null) mPresenter = new TrackingServicePresenter (this);
    }

    public void setServiceWork(boolean serviceWork) {
        this.serviceWork = serviceWork;
    }

    public boolean isServiceWork() {
        return serviceWork;
    }


    @Override
    public boolean isServiceRun() {
        if (serviceWork){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void writeTrack(Location location, float distance) {
        appRepository.addTrack(location, distance);
    }

    @Override
    public void showOnMap(Location location) {
        Intent intent = new Intent().putExtra("loc", location);
        try {
            pi.send(TrackingService.this, 100, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }
}
