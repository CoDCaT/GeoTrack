package com.codcat.geotrack.service;


import android.location.Location;

public interface IService {
    boolean isServiceRun();
    void writeTrack(Location location, float distance);
}
