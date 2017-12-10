package com.codcat.geotrack.views.map_screen;

import android.location.Location;
import com.codcat.geotrack.base.MvpView;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface MapMvpView extends MvpView {

    void showLocation(Location location);
    void drawWay(List<LatLng> list);
    void beginTrack();
    void stopTrack();
    void navigateToTrackList();

}
