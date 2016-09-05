package com.codcat.geotrack.presenter;

import android.location.Location;

import com.codcat.geotrack.model.MyTrack;
import com.codcat.geotrack.service.DBHelper;

import java.util.List;


public interface IPresenter {
    void refrashPoint(Location location);
    void writeToDB(Location location, float distance);
    void readFromDB();
    void clearThisDB();
    List<MyTrack> getTracks();
    void onItemClick(int position);
}
