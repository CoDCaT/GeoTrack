package com.codcat.geotrack.view;

import android.location.Location;
import android.view.LayoutInflater;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IView {
    void showLocation(Location location);
    void drowWay(List<LatLng> list);
    void showDistance(float dictance);
    void showTracks();
}
