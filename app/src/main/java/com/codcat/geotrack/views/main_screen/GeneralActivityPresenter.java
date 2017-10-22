package com.codcat.geotrack.views.main_screen;


import android.location.Location;
import android.view.MenuItem;

import com.codcat.geotrack.App;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.model.IModel;
import com.codcat.geotrack.model.MyTrack;
import com.codcat.geotrack.presenter.IPresenter;
import com.codcat.geotrack.service.DBHelper;
import com.codcat.geotrack.service.IService;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GeneralActivityPresenter<V extends GeneralMvpView> extends BasePresenter<V> implements GeneralMvpPresenter<V>, IPresenter {

    private GeneralMvpView view;
    private IService service;
    private IModel model;
    private DBHelper dbHelper;
    List<MyTrack> myTracks;
    IRepository repository;


    GeneralActivityPresenter(V mMvpView){
        super(mMvpView);
        this.repository = App.appRepository;
    }

//    public GeneralActivityPresenter(DBHelper dbHelper){
//        this.dbHelper = dbHelper;
//        this.model = new Model(dbHelper);
//    }
//
//    public GeneralActivityPresenter(DBHelper dbHelper, GeneralMvpView view){
//        this.dbHelper = dbHelper;
//        this.model = new Model(dbHelper);
//        this.view = view;
//    }

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

    @Override
    public void onAttach(V mvpView) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickButtonMenu(MenuItem item) {
        //Обработка нажатия пунктов меню ******************
        //

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            switch (item.getItemId()) {
                case 1:
                    getMvpView().runService();

//                    locManager.removeUpdates(locListener);
                    break;
                case 2:
                    getMvpView().stopService();
//                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locListener);
                    break;
                case 3:
//                Intent allTracks = new Intent(this, GeneralActivity.class);
//                startActivity(allTracks);
                    getMvpView().navigateToTrackList();
                    break;
                case 4:
                   getMvpView().navigateToTrackList();

                    //mapFragment.getMapAsync(this);
//                presenter = new GeneralActivityPresenter(new DBHelper(this), this);
//                presenter.readFromDB();
//                presenter.getDistance();
                    break;
                case 5:
//                    presenter = new GeneralActivityPresenter(new DBHelper(this), this);
                    repository.clearDB();
                    //mMap.clear();
                    break;


            }
        }


//    }
}
