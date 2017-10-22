package com.codcat.geotrack.views.map_screen;

import com.codcat.geotrack.base.BasePresenter;


public class MapActivityPresenter<V extends MapMvpView> extends BasePresenter<V> {

    public MapActivityPresenter(V mvpView) {
        super(mvpView);
    }
}
