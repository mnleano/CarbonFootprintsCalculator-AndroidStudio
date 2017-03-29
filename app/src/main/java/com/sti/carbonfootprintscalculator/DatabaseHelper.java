package com.sti.carbonfootprintscalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CFC_DB";
    public static final int DB_VERSION = 3;

    private static DatabaseHelper mInstance = null;

    private DatabaseHelper(Context mContext) {
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(mContext);
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_emission_history(id varchar, emission_type integer, emission_value real, area varchar, area_id integer)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_emission_household(id varchar, appliances_name varchar, wattage integer, hours integer)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_emission_vehicle(id varchar, km integer, fuelType varchar, carType varchar, yearModel varchar)");

        /**
         * Log.d("Vehicles", "KM: " + km); Log.d("Vehicles", "Fuel Type: " +
         * fuel); Log.d("Vehicles", "Car Type: " + type); Log.d("Vehicles",
         * "Year Model: " + year); Log.d("Vehicles", "Emission Factor: " + ef);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tbl_emission_history");
        db.execSQL("DROP TABLE IF EXISTS tbl_emission_household");
        db.execSQL("DROP TABLE IF EXISTS tbl_emission_vehicle");
        onCreate(db);
    }

}
