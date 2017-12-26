package com.codcat.geotrack.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.codcat.geotrack.App;
import com.codcat.geotrack.utils.DBHelper;
import com.codcat.geotrack.data.MyTrack;
import com.codcat.geotrack.utils.SharedPref;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;

import javax.inject.Inject;


public class AppRepository extends Observable implements IRepository {

    private DBHelper dbHelper;
    private MyTrack myTrack;
    private List<MyTrack> myTrackList;
    private List<LatLng> currentTrack = new ArrayList<>();

    @Inject
    public AppRepository() {
        Context context = App.getApp().getAppContext();
        this.dbHelper = new DBHelper(context);
    }

    @Override
    public void clearDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("tracks", null, null);
        db.close();
    }

    @Override
    public List<MyTrack> getListTracks() {

        float distance = 0;
        int num1 = 0;
        int num2 = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("tracks", null, null, null, null, null, null);
        List<LatLng> point = new ArrayList<>();
        myTrackList = new ArrayList<>();

        SimpleDateFormat formating = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        formating.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int dateIndex = c.getColumnIndex("date");
            int latIndex = c.getColumnIndex("lat");
            int lonIndex = c.getColumnIndex("lon");
            int disIndex = c.getColumnIndex("distance");
            int numberIndex = c.getColumnIndex("track");

            String date;
            do {
                if (num1 == 0){
                    num1 = c.getInt(numberIndex);
                    num2 = c.getInt(numberIndex);
                }else {
                    num2 = c.getInt(numberIndex);
                }

                date = formating.format(new Date(c.getLong(dateIndex)));

                if (num1 == num2) {
                    point.add(new LatLng(c.getDouble(latIndex), c.getDouble(lonIndex)));
                    distance += c.getFloat(disIndex);

                } else {
                    myTrack = new MyTrack(date, String.valueOf(distance), point, num2);
                    myTrackList.add(myTrack);
                    num1 = num2;
                    distance = 0;
                    point = new ArrayList<>();
                }


            }while (c.moveToNext());

            myTrack = new MyTrack(date, String.valueOf(distance), point, num2);
            myTrackList.add(myTrack);


        }else {
            c.close();
            db.close();
        }

        return myTrackList;
    }

    @Override
    public List<MyTrack> getCurrentTrackList() {
        return myTrackList;
    }

    @Override
    public void addTrack(Location location, float distance) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        int numberTrack = Integer.parseInt(SharedPref.readSharedSetting(App.getApp().getAppContext(), "numberTrack", "1"));

        long date = new Date().getTime();
        cv.put("date", date);
        cv.put("lat", location.getLatitude());
        cv.put("lon", location.getLongitude());
        cv.put("alt", location.getAltitude());
        cv.put("distance", distance);
        cv.put("track", numberTrack); //TODO: fix() track number

        db.insert("tracks", null, cv);
        dbHelper.close();

        currentTrack.add(new LatLng(location.getLatitude(), location.getLongitude()));
        setChanged();
        notifyObservers(currentTrack);

        Log.d("LOGTAG", "Добавлена точка в базу: " + date);
    }

    @Override
    public boolean getTrackState() {
        return false;
    }

    @Override
    public void setTrackState(boolean state) {

    }

    @Override
    public List<LatLng> getTrack(int track) {

        List<MyTrack> tracks = getCurrentTrackList();
        MyTrack myTrack = tracks.get(track);

        return myTrack.getPoint();
    }

    @Override
    public List<LatLng> getCurrentTrack() {
        return currentTrack;
    }

}
