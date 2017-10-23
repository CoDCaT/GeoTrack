package com.codcat.geotrack.delete;

import android.location.Location;

import com.codcat.geotrack.data.MyTrack;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IModel {
    void addDBTrack(Location location, float distance);
    List<LatLng> getDBTrack();
    List<MyTrack> getTracks();
    void clearDB();
}
