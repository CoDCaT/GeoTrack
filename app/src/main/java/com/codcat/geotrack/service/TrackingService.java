package com.codcat.geotrack.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.codcat.geotrack.App;
import com.codcat.geotrack.base.MvpView;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.views.map_screen.MapFragmentPresenter;
import com.codcat.geotrack.views.map_screen.MapMvpPresenter;
import com.codcat.geotrack.views.map_screen.MapMvpView;

import java.util.Observable;
import java.util.Observer;


public class TrackingService extends Service implements MvpView, Observer {

    private IRepository appRepository;

    private LocationManager locationManager;
    private MyLocation locationListener;
    public boolean serviceWork = false;

    private boolean startPoint = false;
    private Location locA = null;
    private Location locB;
    private float distance = 0;

    private MapMvpPresenter mPresenter;
    PendingIntent pi;

//    public TrackingService() {
//        super("TrackingService");
//    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationListener = intent.getParcelableExtra("loc");
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


        Log.d("LOGTAG", "Start Service!");
        return START_REDELIVER_INTENT;
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        locationListener = intent.getParcelableExtra("loc");
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("LOGTAG", "Stop Service!");
        super.onDestroy();
        locationListener.deleteObserver(this);
        locationManager.removeUpdates(locationListener);
        setServiceWork(false);
    }


    private void init() {

        attachPresenter();
        setLocation();

//        for (int i = 0; i < 10; i++) {
//            try {
//                Log.d("LOGTAG", "Service............." + i);
//                Thread.sleep(2000);
//                if (i == 8){
//                    Log.d("LOGTAG", "Service debug " + locationListener);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    @SuppressLint("MissingPermission")
    private void setLocation() {
        if(locationManager == null) locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(locationListener == null) locationListener = MyLocation.getInstance(mPresenter);
        locationListener.addObserver(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
    }

    private void attachPresenter() {
//        if(mPresenter == null) mPresenter = new MapFragmentPresenter((MapMvpView) this);
    }

    public void setServiceWork(boolean serviceWork) {
        this.serviceWork = serviceWork;
    }

    public boolean isServiceWork() {
        return serviceWork;
    }

    void showLocation(Location location){

        try {
            Intent intent = new Intent().putExtra("loc", location);
            pi.send(TrackingService.this, 100, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

    public boolean isServiceRun() {
        if (serviceWork){
            return true;
        }else {
            return false;
        }
    }

    public void writeTrack(Location location) {



//        SDK Рассчет растояния между точками **********************
            if (!startPoint) {
                startPoint = true;
                locA = location;
                appRepository.addTrack(location, distance);
            } else {
                locB = location;
                distance = locA.distanceTo(locB);
                locA = locB;
                if (distance > 5) appRepository.addTrack(location, distance);
            }

    }

    public void showOnMap(Location location) {
        Intent intent = new Intent().putExtra("loc", location);
        try {
            pi.send(TrackingService.this, 100, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

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

    @Override
    public void update(Observable observable, Object location) {
//        android.os.Debug.waitForDebugger();
        writeTrack((Location) location);
        Toast.makeText(App.appContext, "Write point to DB", Toast.LENGTH_LONG).show();
    }
}
