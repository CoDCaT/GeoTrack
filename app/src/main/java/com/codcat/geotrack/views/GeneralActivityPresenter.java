package com.codcat.geotrack.views;


import android.view.MenuItem;
import com.codcat.geotrack.App;
import com.codcat.geotrack.base.BasePresenter;
import com.codcat.geotrack.data.repository.IRepository;

import javax.inject.Inject;

public class GeneralActivityPresenter<V extends GeneralMvpView> extends BasePresenter<V> implements GeneralMvpPresenter<V> {

    private IRepository repository;

    @Inject
    GeneralActivityPresenter(V mMvpView){
        super(mMvpView);
        this.repository = App.getApp().getAppRepository();
    }

    @Override
    public void onAttach(V mvpView) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickButtonMenu(MenuItem item) {

    }

}
