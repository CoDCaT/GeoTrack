package com.codcat.geotrack;

import android.app.Application;
import android.content.Context;

import com.codcat.geotrack.data.repository.AppRepository;


public class App extends Application {

    public static volatile AppRepository appRepository;
    public static volatile Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        if(appContext == null) appContext = getApplicationContext();
        if (appRepository == null) appRepository = new AppRepository();
    }
}
