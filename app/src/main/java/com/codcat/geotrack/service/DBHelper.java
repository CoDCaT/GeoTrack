package com.codcat.geotrack.service;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "dbGeoTrack", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tracks ("
                + "id integer primary key autoincrement,"
                + "date long,"
                + "lat double,"
                + "lon double,"
                + "alt double,"
                + "distance float,"
                + "track integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE tracks ADD COLUMN distance float, alt double;");
    }
}
