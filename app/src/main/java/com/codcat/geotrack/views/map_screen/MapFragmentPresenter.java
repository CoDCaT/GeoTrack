package com.codcat.geotrack.views.map_screen;

import android.location.Location;
import android.view.View;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;


public class MapFragmentPresenter<V extends MapMvpView> extends BasePresenter<V> implements MapMvpPresenter<V> {

    private IRepository appRepository;

    public MapFragmentPresenter(V mvpView) {
        super(mvpView);
        this.appRepository = App.appRepository;
    }


    @Override
    public void myLocationChanged(Location location) {
        getMvpView().showLocation(location);
    }

    @Override
    public void onClickButton(View v) {
        switch (v.getId()){
            case R.id.btnStartTrack:
                getMvpView().beginTrack();
                break;

            case R.id.btnStopTrack:
                getMvpView().stopTrack();
                break;
        }
    }
}
