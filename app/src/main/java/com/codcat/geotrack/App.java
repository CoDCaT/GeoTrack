package com.codcat.geotrack;

import android.content.Context;

import com.codcat.geotrack.data.repository.AppRepository;
import com.codcat.geotrack.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


public class App extends DaggerApplication {

    public static volatile AppRepository appRepository;
    public static volatile Context appContext;
    public static App app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        if(appContext == null) appContext = getApplicationContext();
        if (appRepository == null) appRepository = new AppRepository();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    public static App getApp() {
        return app;
    }

    public static AppRepository getAppRepository() {
        return appRepository;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
