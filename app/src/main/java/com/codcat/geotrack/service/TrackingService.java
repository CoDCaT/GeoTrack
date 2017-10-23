package com.codcat.geotrack.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.codcat.geotrack.App;
import com.codcat.geotrack.data.repository.IRepository;


public class TrackingService extends Service implements IService {

    private IRepository appRepository;

    private LocationManager locationManager;
    private MyLocation locationListener;
    public boolean serviceWork = false;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        appRepository = App.appRepository;
        locationListener = new MyLocation(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isServiceWork()){
            return START_REDELIVER_INTENT;
        }else {
            setServiceWork(true);
        }

        if(locationManager == null){
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_REDELIVER_INTENT;
        }
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
}
