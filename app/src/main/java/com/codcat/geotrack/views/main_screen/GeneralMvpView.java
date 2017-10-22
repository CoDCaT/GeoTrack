package com.codcat.geotrack.views.main_screen;

import android.location.Location;

import com.codcat.geotrack.base.MvpView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface GeneralMvpView extends MvpView {
    void showLocation(Location location);
    void drowWay(List<LatLng> list);
    void showDistance(float dictance);
    void showTracks();
    void runService();
    void stopService();
    void navigateToTrackList();
    void navigateToMap();
}
