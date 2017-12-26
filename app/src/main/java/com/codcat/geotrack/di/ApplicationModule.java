package com.codcat.geotrack.di;


import android.app.Application;
import android.content.Context;

import com.codcat.geotrack.App;
import com.codcat.geotrack.data.repository.AppRepository;
import com.codcat.geotrack.data.repository.IRepository;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;

@Module
public class ApplicationModule {

//    DaggerApplication app;
//    ApplicationModule(App app){
//        this.app = app;
//    }
//
//    @Provides
//    Context provideContext(){return app;}

    @Provides
    IRepository appRepository(){
        return new AppRepository();
    }
}
