package com.codcat.geotrack.views.tracks_screen;

import com.codcat.geotrack.App;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;
import com.codcat.geotrack.data.MyTrack;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class TrackFragmentPresenter<V extends TrackMvpView> extends BasePresenter<V> implements TrackMvpPresenter<V> {

    private IRepository appRepository;

    public TrackFragmentPresenter(V mvpView) {
        super(mvpView);
        appRepository = App.appRepository;
    }

    @Override
    public List<MyTrack> getTracks() {
        return appRepository.getListTracks();
    }

    @Override
    public void onClickItemListTracks(int position) {
        List<LatLng> points = appRepository.getTrack(0);

        getMvpView().showMessage("open track screen!!");
        getMvpView().goToMap(points);
    }
}
