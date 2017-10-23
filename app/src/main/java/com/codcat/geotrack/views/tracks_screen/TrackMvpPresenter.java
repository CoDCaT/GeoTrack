package com.codcat.geotrack.views.tracks_screen;

import com.codcat.geotrack.base.MvpPresenter;
import com.codcat.geotrack.data.MyTrack;
import java.util.List;


public interface TrackMvpPresenter<V extends TrackMvpView> extends MvpPresenter<V> {

    List<MyTrack> getTracks();
    void onClickItemListTracks(int position);

}
