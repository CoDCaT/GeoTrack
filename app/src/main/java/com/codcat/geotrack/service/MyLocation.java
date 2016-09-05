package com.codcat.geotrack.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.codcat.geotrack.presenter.IPresenter;
import com.codcat.geotrack.presenter.Presenter;
import com.codcat.geotrack.view.IView;

import static java.lang.Math.*;


public class MyLocation implements LocationListener {

    Object context;
    IPresenter presenter;
    IService serv;
    ///////
    boolean startPoint = false;
    Location locA;
    Location locB;
    float distance = 0;

    public MyLocation(IView ctx){
        this.context = ctx;
        this.presenter = new Presenter(ctx);
    }

    public MyLocation(IService ctx){
        this.context = ctx;
        this.serv = ctx;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (presenter != null) {
            presenter.refrashPoint(location);
        }

        //Проверка что Сервис запущен
        if (serv != null && serv.isServiceRun()){

            //SDK Рассчет растояния между точками **********************
            if (!startPoint){
                startPoint = true;
                locA = location;
                serv.writeTrack(location, distance);
            }else{
                locB = location;
                distance = locA.distanceTo(locB);
                locA = locB;
                if (distance > 5)
                serv.writeTrack(location, distance);
            }


        }

        Toast.makeText((Context) context, "onLocChang", Toast.LENGTH_SHORT).show();
        Log.d("LOGTAG", "------- Change: " + location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
