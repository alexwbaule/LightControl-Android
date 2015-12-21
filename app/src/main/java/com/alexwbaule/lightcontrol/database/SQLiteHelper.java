package com.alexwbaule.lightcontrol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 19/12/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "automation_devices";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME =  "devices";
    public static final String UNIQUE_NAME = "unique_name";
    public static final String NICK_NAME = "nick_name";
    public static final String ADDR = "addr";
    public static final String TYPE = "type";
    public static final String SIGNAL = "signal";
    public static final String WIFISPOT = "wifispot";




    private static final String DATABASE_CREATE = "create table devices " +
            "(_id integer primary key autoincrement, " +
            "wifispot varchar not null, " +
            "unique_name varchar not null, " +
            "nick_name varchar not null," +
            "addr varchar not null," +
            "signal integer not null," +
            "type varchar not null)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
