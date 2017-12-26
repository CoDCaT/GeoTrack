package com.codcat.geotrack.views.tracks_screen;


import dagger.Module;
import dagger.Provides;

@Module
public class TrackScreenModule {

    @Provides
    TrackMvpView provideTrackMvpView(TrackFragment trackFragment){
        return trackFragment;
    }

    @Provides
    TrackFragmentPresenter<TrackMvpView> provideTrackFragmentPresenter(TrackMvpView trackMvpView){
        return new TrackFragmentPresenter<>(trackMvpView);
    }

}
