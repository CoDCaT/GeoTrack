package com.codcat.geotrack.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.codcat.geotrack.views.map_screen.MapMvpPresenter;
import java.util.Observable;


public class MyLocation extends Observable implements LocationListener, Parcelable {

    private MapMvpPresenter mPresenter;
    private static MyLocation INSTANCE = null;

    public MyLocation(MapMvpPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    protected MyLocation(Parcel in) {
    }

    //for parcelable---***
    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel in) {
            return new MyLocation(in);
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };
    //---***

    public static MyLocation getInstance(MapMvpPresenter mPresenter) {
        if(INSTANCE == null) {
            INSTANCE = new MyLocation(mPresenter);
        }
        return INSTANCE;
    }

    @Override
    public void onLocationChanged(Location location) {

        // for observable java util
        setChanged();
        notifyObservers(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
