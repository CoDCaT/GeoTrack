package com.codcat.geotrack.service;


import android.location.Location;
import android.util.Log;
import android.view.View;

import com.codcat.geotrack.App;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.views.map_screen.MapMvpPresenter;
import com.codcat.geotrack.views.map_screen.MapMvpView;

public class TrackingServicePresenter<V extends IService & MapMvpView> extends BasePresenter<V> implements MapMvpPresenter<V> {

    private IRepository appRepository;
    private IService iService;
    private boolean startPoint = false;
    private Location locA;
    private Location locB;
    private float distance = 0;


    public TrackingServicePresenter(V iService) {
        super(iService);
        this.appRepository = App.appRepository;
        this.iService = iService;
    }


    @Override
    public void myLocationChanged(Location location) {


        if (iService.isServiceRun()) {
//        if (trackIsRun()) {

            //SDK Рассчет растояния между точками **********************
            if (!startPoint) {
                startPoint = true;
                locA = location;
                appRepository.addTrack(location, distance);
            } else {
                locB = location;
                distance = locA.distanceTo(locB);
                locA = locB;
                if (distance > 5) appRepository.addTrack(location, distance);
            }

        }

        getMvpView().showOnMap(location);

        Log.d("LOGTAG", "------- onLocChang: " + location);

    }

    @Override
    public void onClickButton(View v) {

    }


    private boolean trackIsRun() {
        return appRepository.getTrackState();
    }

    @Override
    public void onAttach(V mvpView) {

    }

    @Override
    public void onDetach() {

    }
}
