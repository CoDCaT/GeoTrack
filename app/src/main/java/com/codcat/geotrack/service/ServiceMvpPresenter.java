package com.codcat.geotrack.service;

import android.location.Location;

import com.codcat.geotrack.base.MvpPresenter;


public interface ServiceMvpPresenter<V extends IService> {

    void myLocationChanged(Location location);
}
