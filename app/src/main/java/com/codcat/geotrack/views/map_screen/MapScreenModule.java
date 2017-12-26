package com.codcat.geotrack.views.map_screen;


import dagger.Module;
import dagger.Provides;

@Module
public class MapScreenModule {

    @Provides
    MapMvpView provideMapMvpView(MapFragment mapFragment){
        return mapFragment;
    }

    @Provides
    MapFragmentPresenter<MapMvpView> provideMapFragmentPresenter(MapMvpView mapMvpView){
        return new MapFragmentPresenter<>(mapMvpView);
    }

}
