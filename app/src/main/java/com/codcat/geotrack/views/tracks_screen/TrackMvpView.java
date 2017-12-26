package com.codcat.geotrack.views.tracks_screen;


import com.codcat.geotrack.base.MvpView;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface TrackMvpView extends MvpView{

    void goToMap(List<LatLng> points);
}
