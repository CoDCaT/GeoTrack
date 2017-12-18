package com.codcat.geotrack.views.map_screen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.service.MyLocation;
import com.codcat.geotrack.service.TrackingService;
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
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback, MapMvpView, Observer {

    @BindView(R.id.btnStartTrack) Button btnStartTrack;
    @BindView(R.id.btnStopTrack) Button btnStopTrack;
    @BindView(R.id.map) MapView mMapView;

//    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private MyLocation locationListener;
    private MapMvpPresenter mPresenter;
    PendingIntent pi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onAttachPresenter();



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_map, container, false);
        ButterKnife.bind(this, view);

        init();
        onAttachMap(savedInstanceState);

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Ловим сообщения о старте задач
        if (resultCode == 100) {
            switch (requestCode) {
                case 1:
//                    tvTask1.setText("Task1 start");
                    break;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationManager.removeUpdates(locationListener);
        locationListener.deleteObserver(this);
    }

    private void init() {
        setLocation();
        setButton();
    }

    private void onAttachMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        //mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
    }

    private void setButton() {

        btnStartTrack.setOnClickListener(this::onClickButton);
        btnStopTrack.setOnClickListener(this::onClickButton);

    }

    private void onClickButton(View v) {

        mPresenter.onClickButton(v);

    }

    @SuppressLint("MissingPermission")
    private void setLocation() {

        locationManager = (LocationManager) App.appContext.getSystemService(getActivity().LOCATION_SERVICE);
        locationListener = MyLocation.getInstance(mPresenter);
        locationListener.addObserver(this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);

    }

    private void onAttachPresenter() {
        mPresenter = new MapFragmentPresenter<>(this);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap geMap) {

        googleMap = geMap;
        googleMap.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void showLocation(Location location) {

        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        if(googleMap != null) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(point).title("Это Вы"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    @Override
    public void beginTrack() {

        Intent intent = new Intent(App.appContext, TrackingService.class).putExtra("loc", locationListener);

        getActivity().startService(intent);

        Toast.makeText(App.appContext, "Start Service", Toast.LENGTH_SHORT).show();
        //TODO: check!!!
//        locationManager.removeUpdates(locationListener);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void stopTrack() {
        getActivity().stopService(new Intent(getActivity(), TrackingService.class));
        Toast.makeText(App.appContext, "Stop Service", Toast.LENGTH_SHORT).show();
//        if(locationManager !=  null && locationListener != null){
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
//        }
    }

    @Override
    public void navigateToTrackList() {

    }

    @Override
    public void drawWay(List<LatLng> list) {

        if (list.size() == 0){
            Toast.makeText(getActivity(), "Нет данных о треке!", Toast.LENGTH_SHORT).show();
            return;
        }
        PolylineOptions line = new PolylineOptions();
        line.width(5).color(Color.BLUE);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        googleMap.clear();

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
//        int size = getResources().getDisplayMetrics().widthPixels;
//        LatLngBounds latLngBounds = latLngBuilder.build();
//        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
//        googleMap.moveCamera(track);
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

    @Override
    public void update(Observable observable, Object location) {

        if (observable instanceof MyLocation) {
            showLocation((Location) location);
        }

    }
}

