package com.codcat.geotrack.data.repository;


import android.location.Location;

import com.codcat.geotrack.data.MyTrack;

import java.util.List;

public interface IRepository {

    void clearDB();
    List<MyTrack> getListTracks();
    List<MyTrack> getCurrentTrackList();
    void addTrack(Location location, float distance);
}
