package com.codcat.geotrack.views.tracks_screen;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.data.MyTrack;
import com.codcat.geotrack.utils.OnMyListener;
import com.codcat.geotrack.utils.SimpleItemTouchHelperCallback;
import com.codcat.geotrack.views.GeneralActivity;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;


public class TrackFragment extends DaggerFragment implements TrackMvpView, OnMyListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.rvTracks) RecyclerView rv;
    @BindView(R.id.txtEmptyTracks) TextView txtEmptyTracks;
    @BindView(R.id.rlTrackScreen) RelativeLayout rlTrackScreen;

    @Inject TrackFragmentPresenter<TrackMvpView> mPresenter;
    private AdapterTrackList adapter;
    private ItemTouchHelper mItemTouchHelper;
    private List<MyTrack> tracks;

    @Inject
    public TrackFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, null);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        setRecyclerViewList();
    }

    private void setRecyclerViewList() {

        tracks = getDataList();
        adapter = new AdapterTrackList(App.getApp().getAppContext(), tracks, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getApp().getAppContext());
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

    public List<MyTrack> getDataList(){
        return mPresenter.getTracks();
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof AdapterTrackList.ViewHolder) {
            // get the removed item name to display it in snack bar
//            String name = cartList.get(viewHolder.getAdapterPosition()).getName();
//
//            // backup of removed item for undo purpose
//            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
//            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(rlTrackScreen, "name" + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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
