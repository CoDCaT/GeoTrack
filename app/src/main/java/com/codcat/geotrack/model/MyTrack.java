package com.codcat.geotrack.model;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MyTrack {

    private String date;
    private String distance;
    private List<LatLng> point;

    public MyTrack(String date, String distance, List<LatLng> point) {
        this.date = date;
        this.distance = distance;
        this.point = point;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPoint(List<LatLng> point) {
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public String getDistance() {
        return distance;
    }

    public List<LatLng> getPoint() {
        return point;
    }
}
