package com.codcat.geotrack.views.map_screen;

import android.location.Location;
import android.view.View;

import com.codcat.geotrack.base.MvpPresenter;


public interface MapMvpPresenter<V extends MapMvpView> extends MvpPresenter<V> {
    void myLocationChanged(Location location);

    void onClickButton(View v);
}
