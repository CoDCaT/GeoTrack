package com.codcat.geotrack.views.map_screen;

import android.location.Location;
import android.view.View;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.AppRepository;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.service.MyLocation;
import com.codcat.geotrack.utils.SharedPref;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MapFragmentPresenter<V extends MapMvpView> extends BasePresenter<V> implements MapMvpPresenter<V>, Observer{

    private AppRepository appRepository;

    public MapFragmentPresenter(V mvpView) {
        super(mvpView);
        this.appRepository = App.appRepository;
        appRepository.addObserver(this);
    }


    @Override
    public void myLocationChanged(Location location) {
//        getMvpView().showLocation(location);
        String text = SharedPref.readSharedSetting(App.appContext, "trackIsRunning", "false");
        if (text == "true") {
            getMvpView().drawWay(appRepository.getCurrentTrack());
        }
    }

    @Override
    public void onClickButton(View v) {
        switch (v.getId()){
            case R.id.btnStartTrack:
                getMvpView().beginTrack();
                SharedPref.saveSharedSetting(App.appContext, "trackIsRunning", "true");
                int numberTrack = Integer.parseInt(SharedPref.readSharedSetting(App.appContext, "numberTrack", "1"));
                SharedPref.saveSharedSetting(App.appContext, "numberTrack", String.valueOf(numberTrack + 1));
                break;

            case R.id.btnStopTrack:
                getMvpView().stopTrack();
                SharedPref.saveSharedSetting(App.appContext, "trackIsRunning", "false");
                break;
        }
    }

    @Override
    public void update(Observable observable, Object track) {
        if (observable instanceof AppRepository) {
            getMvpView().drawWay((List<LatLng>) track);
        }
    }
}
