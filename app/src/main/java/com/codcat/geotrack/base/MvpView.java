package com.codcat.geotrack.base;


import android.support.annotation.NonNull;

public interface MvpView {

    boolean isNetworkConnected();

    void showLoading();

    void hideLoading();

    void onError(@NonNull String message);

    void showMessage(@NonNull String message);

}
