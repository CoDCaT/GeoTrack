package com.codcat.geotrack.views.tracks_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.codcat.geotrack.R;
import com.codcat.geotrack.data.MyTrack;

import java.util.List;

import butterknife.ButterKnife;

public class TrackFragment extends ListFragment implements TrackMvpView {

    private TrackFragmentPresenter<TrackMvpView> mpPresenter;
    private AdapterTrackList adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onAttachPresenter();

        init();
    }

    private void onAttachPresenter() {
        mpPresenter = new TrackFragmentPresenter<>(this);
    }

    private void init() {
        adapter = new AdapterTrackList(getActivity(), R.layout.item_list, getDataList());
        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, null);
        ButterKnife.bind(this, view);

        return view;
    }

    public List<MyTrack> getDataList(){
        return mpPresenter.getTracks();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mpPresenter.onClickItemListTracks(position);
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@NonNull String message) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
