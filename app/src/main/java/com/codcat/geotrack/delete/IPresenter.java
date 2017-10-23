package com.codcat.geotrack.delete;

import android.location.Location;

import com.codcat.geotrack.data.MyTrack;

import java.util.List;


public interface IPresenter {
    void refrashPoint(Location location);
    void writeToDB(Location location, float distance);
    void readFromDB();
    void clearThisDB();
    List<MyTrack> getTracks();
    void onItemClick(int position);
}
