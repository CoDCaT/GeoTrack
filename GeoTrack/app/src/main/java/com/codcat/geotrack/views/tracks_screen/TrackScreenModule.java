package com.codcat.geotrack.views.tracks_screen;


import com.codcat.geotrack.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TrackScreenModule {

    @FragmentScope
    @Provides
    TrackMvpView provideTrackMvpView(){
        return new TrackFragment();
    }

    @FragmentScope
    @Provides
    TrackFragmentPresenter<TrackMvpView> provideTrackFragmentPresenter(TrackMvpView trackMvpView){
        return new TrackFragmentPresenter<>(trackMvpView);
    }

}
