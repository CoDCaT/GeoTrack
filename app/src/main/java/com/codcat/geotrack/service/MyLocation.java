package com.codcat.geotrack.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.codcat.geotrack.views.GeneralMvpPresenter;
import com.codcat.geotrack.views.map_screen.MapMvpPresenter;


public class MyLocation implements LocationListener {

//    private IService service;
//    private boolean startPoint = false;
//    private Location locA;
//    private Location locB;
//    private float distance = 0;
    private MapMvpPresenter mPresenter;

    public MyLocation(MapMvpPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onLocationChanged(Location location) {

        mPresenter.myLocationChanged(location);

//        if (service != null && service.isServiceRun()) {
//
//            //SDK Рассчет растояния между точками **********************
//            if (!startPoint) {
//                startPoint = true;
//                locA = location;
//                service.writeTrack(location, distance);
//            } else {
//                locB = location;
//                distance = locA.distanceTo(locB);
//                locA = locB;
//                if (distance > 5) service.writeTrack(location, distance);
//            }
//
//        }
//
//        Log.d("LOGTAG", "------- onLocChang: " + location);
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
