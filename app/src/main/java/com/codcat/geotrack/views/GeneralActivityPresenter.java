package com.codcat.geotrack.views;


import android.location.Location;
import android.view.MenuItem;
import com.codcat.geotrack.App;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;

public class GeneralActivityPresenter<V extends GeneralMvpView> extends BasePresenter<V> implements GeneralMvpPresenter<V> {

    private IRepository repository;

    GeneralActivityPresenter(V mMvpView){
        super(mMvpView);
        this.repository = App.appRepository;
    }

    @Override
    public void onAttach(V mvpView) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickButtonMenu(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
//                getMvpView().beginTrack();
                repository.setTrackState(true);
                break;
            case 2:
//                getMvpView().stopTrack();
                repository.setTrackState(false);
                break;
            case 3:
                getMvpView().navigateToTrackList();
                break;
            case 4:
                getMvpView().navigateToMap();
                break;
            case 5:
                repository.clearDB();
                break;
            }
        }

}
