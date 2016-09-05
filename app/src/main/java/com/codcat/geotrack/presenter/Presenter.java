package com.codcat.geotrack.presenter;


import android.location.Location;
import android.test.ProviderTestCase2;

import com.codcat.geotrack.model.IModel;
import com.codcat.geotrack.model.Model;
import com.codcat.geotrack.model.MyTrack;
import com.codcat.geotrack.service.DBHelper;
import com.codcat.geotrack.service.IService;
import com.codcat.geotrack.view.IView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Presenter implements IPresenter {
    private IView view;
    private IService service;
    private IModel model;
    private DBHelper dbHelper;
    List<MyTrack> myTracks;

    public Presenter(){

    }

    public Presenter(IView view){
        this.view = view;
    }

    public Presenter(DBHelper dbHelper){
        this.dbHelper = dbHelper;
        this.model = new Model(dbHelper);
    }

    public Presenter(DBHelper dbHelper, IView view){
        this.dbHelper = dbHelper;
        this.model = new Model(dbHelper);
        this.view = view;
    }

    @Override
    public void refrashPoint(Location location) {
        if (view != null){
            view.showLocation(location);
        }
    }

    @Override
    public void writeToDB(Location location, float distance) {
        model.addDBTrack(location, distance);
    }

    @Override
    public void readFromDB() {
        view.drowWay(model.getDBTrack());
    }

    @Override
    public void clearThisDB() {
        model.clearDB();
    }

    @Override
    public List<MyTrack> getTracks() {
        myTracks = model.getTracks();
        return myTracks;
    }

    @Override
    public void onItemClick(int position) {
        List<LatLng> treck = myTracks.get(position).getPoint();

    }
}
