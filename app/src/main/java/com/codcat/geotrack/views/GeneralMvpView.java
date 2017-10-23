package com.codcat.geotrack.views;

import android.location.Location;

import com.codcat.geotrack.base.MvpView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface GeneralMvpView extends MvpView {
    void beginTrack();
    void stopTrack();
    void navigateToTrackList();
    void navigateToMap();
}
