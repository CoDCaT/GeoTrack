package com.codcat.geotrack.views;


import android.location.Location;
import android.view.MenuItem;

import com.codcat.geotrack.base.MvpPresenter;

public interface GeneralMvpPresenter<V extends GeneralMvpView> extends MvpPresenter<V> {

    void onClickButtonMenu(MenuItem item);

}
