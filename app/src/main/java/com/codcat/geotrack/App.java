package com.codcat.geotrack;

import android.app.Application;
import com.codcat.geotrack.data.repository.AppRepository;


public class App extends Application {

    public static volatile AppRepository appRepository;


    @Override
    public void onCreate() {
        super.onCreate();
        if (appRepository == null) appRepository = new AppRepository();
    }
}
