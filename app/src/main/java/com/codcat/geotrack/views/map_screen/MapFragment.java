package com.codcat.geotrack.views.map_screen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codcat.geotrack.R;
import com.codcat.geotrack.service.MyLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback, MapMvpView {

//    @BindView(R.id.map) MapView mMapView;

    private MapView mMapView;
    private GoogleMap googleMap;
    private MapFragmentPresenter<MapMvpView> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onAttachPresenter();

    }

    private void onAttachPresenter() {
        mPresenter = new MapFragmentPresenter<>(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_map, container, false);
//        ButterKnife.bind(this, view);

        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        //mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, locListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        //       locManager.removeUpdates(locListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        //       locManager.removeUpdates(locListener);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        googleMap = mMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void showLocation(Location location) {

    }

    @Override
    public void beginTrack() {

    }

    @Override
    public void stopTrack() {

    }

    @Override
    public void navigateToTrackList() {

    }

    @Override
    public void drowWay(List<LatLng> list) {

        if (list.size() == 0){
            //Toast.makeText(this, "Нет данных о треке!", Toast.LENGTH_SHORT).show();
            return;
        }
        PolylineOptions line = new PolylineOptions();
        line.width(5).color(Color.BLUE);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                LatLng str =  list.get(i);
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(list.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_rotate));
                googleMap.addMarker(startMarkerOptions);
            } else if (i == list.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(list.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_rotate));
                googleMap.addMarker(endMarkerOptions);
            }

            line.add(list.get(i));
            latLngBuilder.include(list.get(i));
        }

        googleMap.addPolyline(line);

        //Вмещаем весь Трэк на экран ************
        int size = getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
        googleMap.moveCamera(track);
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

    }
}

