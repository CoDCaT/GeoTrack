package com.codcat.geotrack.views;


import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    @Provides
    GeneralMvpView provideGeneralMvpView(GeneralActivity mainScreenActivity){
        return mainScreenActivity;
    }

    @Provides
    GeneralActivityPresenter<GeneralMvpView> provideGeneralActivityPresenter(GeneralMvpView mvpView){
        return new GeneralActivityPresenter<>(mvpView);
    }
}
