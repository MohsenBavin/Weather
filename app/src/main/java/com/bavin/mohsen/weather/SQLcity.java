package com.bavin.mohsen.weather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLcity extends SQLiteOpenHelper {
    String TABLE_City_NAME = "city_db";

    String createTable = "" +

            "CREATE TABLE " + TABLE_City_NAME
            + "("
            + ""
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "cityName TEXT,"
            + "lon  TEXT,"
            + "lat  TEXT"
            + ""
            + ")";


    public SQLcity(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("constrac", "constractor");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertToDB(String cityName ,String lon ,String lat ) {

        String insertIntoDb = ""
                + "INSERT INTO " + TABLE_City_NAME + "( cityName ,lon , lat  )"
                + "VALUES (" + "'" + cityName + "'"
                + "," + "'" + lon + "'"
                + "," + "'" + lat + "'"
                + ")"
                + "";

        /*
        String insertIntoDb = ""
                + "INSERT INTO " + TABLE_NAME + "( name , family , grade )"
                + "VALUES (" + "'" + name + "'"
                + "," + "'" + family + "'"
                + "," + grade
                + ")"
                + "";
         */

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL( insertIntoDb );
        db.close();
    }

    public void deleteFromDb(String cityName){

    }

    public String getCityNames() {
        String names = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cityName from " + TABLE_City_NAME, null);

        while (cursor.moveToNext()){
            names += cursor.getString(0) + "\n";
        }

        db.close();
        return names;
    }




    public List<String> getLonCityArray() {
        List<String> citieslon=new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT lon from " + TABLE_City_NAME, null);

        while (cursor.moveToNext()){
            citieslon.add(cursor.getString(0));

        }

        db.close();
        return citieslon;
    }

    public List<String> getLatCityArray() {
        List<String> citieslat=new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT lat from " + TABLE_City_NAME, null);

        while (cursor.moveToNext()){
            citieslat.add(cursor.getString(0));

        }

        db.close();
        return citieslat;
    }

    public List<String> getCityArray() {
        List<String> citiesName=new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cityName from " + TABLE_City_NAME, null);

        while (cursor.moveToNext()){
            citiesName.add(cursor.getString(0));

        }

        db.close();
        return citiesName;
    }

    public void deleteCity(String nameCity){
        String deleteFromDb = ""
                + "DELETE FROM " + TABLE_City_NAME + "WHERE"
                +"cityName="
                +nameCity
                + "";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_City_NAME+" WHERE "+"cityName"+"='"+nameCity+"'");
       // db.execSQL( deleteFromDb );
        db.close();
    }
}