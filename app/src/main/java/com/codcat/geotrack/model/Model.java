package com.codcat.geotrack.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.codcat.geotrack.service.DBHelper;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class Model implements IModel {

    private DBHelper dbHelper;
    private ContentValues cv;
    private SQLiteDatabase db;
    private Cursor c;
    private MyTrack myTrack;
    private List<MyTrack> tracks;
    String d1;
    String d2;

    public Model(DBHelper dbHelper){
        this.dbHelper = dbHelper;

        //Объект для данных
        //
        cv = new ContentValues();

        //Пдключение к базе данных
        //
        db = dbHelper.getWritableDatabase();

    }

    @Override
    public void addDBTrack(Location location, float distance) {
        long date = currentDate();
        cv.put("date", date);
        cv.put("lat", location.getLatitude());
        cv.put("lon", location.getLongitude());
        cv.put("alt", location.getAltitude());
        cv.put("distance", distance);
        cv.put("track", 1);

        db.insert("tracks", null, cv);

        dbHelper.close();
        Log.d("LOGTAG", "Добавлена точка в базу: " + date);
    }

    @Override
    public List<LatLng> getDBTrack() {
        c = db.query("tracks", null, null, null, null, null, null);
        List<LatLng> point = new ArrayList<>();

        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int latIndex = c.getColumnIndex("lat");
            int lonIndex = c.getColumnIndex("lon");


            do {

                point.add(new LatLng(c.getDouble(latIndex), c.getDouble(lonIndex)));

            }while (c.moveToNext());

        }else {
            c.close();
        }
        return point;
    }

    @Override
    public List<MyTrack> getTracks() {
        float distance = 0;
        long date1 = 0;
        long date2 = 0;

        // Параметры
        // columns – список полей, которые мы хотим получить
        // selection – строка условия WHERE
        // selectionArgs – массив аргументов для selection. В selection можно использовать знаки ?, которые будут заменены этими значениями.
        // groupBy - группировка
        // having – использование условий для агрегатных функций
        // orderBy - сортировка

        c = db.query("tracks", null, null, null, null, null, null);
        List<LatLng> point = new ArrayList<>();
        tracks = new ArrayList<>();
//
        SimpleDateFormat formating = new SimpleDateFormat("dd.MM.yyyy");
        formating.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        //Загрушка для пустой базы *****
//        for (int i = 1; i < 5; i++) {
//            myTrack = new MyTrack("0"+i+"/08/2016", i+"000 m", point);
//            tracks.add(myTrack);
//        }


        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int dateIndex = c.getColumnIndex("date");
            int latIndex = c.getColumnIndex("lat");
            int lonIndex = c.getColumnIndex("lon");
            int disIndex = c.getColumnIndex("distance");

            do {
                if (date1 == 0){
                    date1 = c.getLong(dateIndex);
                    date2 = c.getLong(dateIndex);
                }else {
                    date2 = c.getLong(dateIndex);
                }

                d1 = formating.format(new Date(date1));
                d2 = formating.format(new Date(date2));
                if (d1.equals(d2)){
                    point.add(new LatLng(c.getDouble(latIndex), c.getDouble(lonIndex)));
                    distance += c.getFloat(disIndex);

                }else {
                    myTrack = new MyTrack(d1, String.valueOf(distance), point);
                    tracks.add(myTrack);
                    date1 = date2;
                    distance = 0;
                    point = new ArrayList<>();
                }


            }while (c.moveToNext());

            myTrack = new MyTrack(d1, String.valueOf(distance), point);
            tracks.add(myTrack);


        }else {
            c.close();
        }

        return tracks;
    }

    @Override
    public void clearDB() {
        db.delete("tracks", null, null);
    }

    public long currentDate(){
        Date d = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return d.getTime();
    }
}
