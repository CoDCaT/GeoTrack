package com.codcat.geotrack.views;

import com.codcat.geotrack.base.MvpView;

public interface GeneralMvpView extends MvpView {
    void beginTrack();
    void stopTrack();
    void navigateToTrackList();
    void navigateToMap();
}
