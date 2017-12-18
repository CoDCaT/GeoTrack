package com.codcat.geotrack.views.tracks_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.data.MyTrack;
import com.codcat.geotrack.utils.OnMyListener;
import com.codcat.geotrack.utils.SimpleItemTouchHelperCallback;
import com.codcat.geotrack.views.GeneralActivity;
import com.codcat.geotrack.views.Router.IRouter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackFragment extends Fragment implements TrackMvpView, OnMyListener {

    @BindView(R.id.rvTracks) RecyclerView rv;
    @BindView(R.id.txtEmptyTracks) TextView txtEmptyTracks;


    private TrackFragmentPresenter<TrackMvpView> mPresenter;
    private AdapterTrackList adapter;
    public IRouter router;
    private ItemTouchHelper mItemTouchHelper;
    private List<MyTrack> tracks;


    void setRouter(IRouter router) {
        this.router = router;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void onAttachPresenter() {
        mPresenter = new TrackFragmentPresenter<>(this);
    }

    private void init() {

//        adapter = new AdapterTrackList(App.appContext, R.layout.item_list, getDataList());
        tracks = getDataList();
        adapter = new AdapterTrackList(App.appContext, tracks, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.appContext);
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);

        if (tracks.size() > 0){
            txtEmptyTracks.setVisibility(View.GONE);
        }else {
            txtEmptyTracks.setVisibility(View.VISIBLE);
        }

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, null);
        ButterKnife.bind(this, view);

        onAttachPresenter();

        init();

        return view;
    }

    public List<MyTrack> getDataList(){
        return mPresenter.getTracks();
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

    @Override
    public void goToMap(List<LatLng> points) {
        ((GeneralActivity) getActivity()).moveTo(1, points);
    }

    @Override
    public void onItemClicked(int position, View view) {
        mPresenter.onClickItemListTracks(position);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
