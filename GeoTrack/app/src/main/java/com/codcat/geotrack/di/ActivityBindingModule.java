package com.codcat.geotrack.di;


import com.codcat.geotrack.views.GeneralActivity;
import com.codcat.geotrack.views.MainScreenModule;
import com.codcat.geotrack.views.map_screen.MapFragment;
import com.codcat.geotrack.views.map_screen.MapScreenModule;
import com.codcat.geotrack.views.setting_screen.SettingsFragment;
import com.codcat.geotrack.views.tracks_screen.TrackFragment;
import com.codcat.geotrack.views.tracks_screen.TrackScreenModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainScreenModule.class, FragmentBindingModule.class})
    abstract GeneralActivity mainActivityInjector();

//    @ContributesAndroidInjector(modules = TrackScreenModule.class)
//    abstract TrackFragment trackFragment();
//
//    @ContributesAndroidInjector(modules = MapScreenModule.class)
//    abstract MapFragment mapFragment();

//    @ContributesAndroidInjector(modules = SettingsScreenModule.class)
//    abstract SettingsFragment settingsFragment();

}
