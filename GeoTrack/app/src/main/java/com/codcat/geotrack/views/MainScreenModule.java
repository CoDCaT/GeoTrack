package com.codcat.geotrack.views;


import com.codcat.geotrack.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    @ActivityScope
    @Provides
    GeneralMvpView provideGeneralMvpView(GeneralActivity mainScreenActivity){
        return mainScreenActivity;
    }

    @ActivityScope
    @Provides
    GeneralActivityPresenter<GeneralMvpView> provideGeneralActivityPresenter(GeneralMvpView mvpView){
        return new GeneralActivityPresenter<>(mvpView);
    }
}
