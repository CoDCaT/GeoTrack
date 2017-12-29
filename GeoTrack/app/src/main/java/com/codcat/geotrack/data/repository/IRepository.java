package com.codcat.geotrack.data.repository;

import android.location.Location;
import com.codcat.geotrack.data.MyTrack;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;

public interface IRepository {

    void clearDB();
    List<MyTrack> getListTracks();
    List<MyTrack> getCurrentTrackList();
    void addTrack(Location location, float distance);
    boolean getTrackState();
    void setTrackState(boolean state);
    List<LatLng> getTrack(int track);
    List<LatLng> getCurrentTrack();

}
