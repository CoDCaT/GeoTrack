package com.codcat.geotrack.service;


import android.location.Location;

import com.codcat.geotrack.views.map_screen.MapMvpView;

public interface IService {
    boolean isServiceRun();
    void writeTrack(Location location, float distance);

    void showOnMap(Location location);
}
