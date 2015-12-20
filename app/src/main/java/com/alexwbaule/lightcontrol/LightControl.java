package com.alexwbaule.lightcontrol;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.database.SQLiteHelper;

import java.util.ArrayList;


/**
 * Created by alex on 12/12/15.
 */
public class LightControl extends Application {
    private static final String TAG = "LightControlApplication";
    private static LightControl instance = null;
    public static final String NAME = "NAME";
    public static final String ADDR = "ADDR";
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;


    public static LightControl getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            dbHelper = new SQLiteHelper(instance);
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            database = null;
        }
        Logger.log(TAG, "LightControlApplication.onCreate()");
    }

    @Override
    public void onTerminate() {
        dbHelper.close();
        Logger.log(TAG, "MeuCarroApplication.onTerminate()");
        super.onTerminate();
    }

    public int getWifiSignalImage(int signal){
        if (signal >= -67) {
            return R.drawable.wifi_full;
        } else if (signal >= -70) {
            return R.drawable.wifi_3;
        } else if (signal >= -80) {
            return R.drawable.wifi_2;
        } else if (signal >= -90) {
            return R.drawable.wifi_min;
        }
        return R.drawable.wifi_min;
    }

    public void SaveCacheDiscovery(ArrayList<LightContainer> lista){
        database.delete(SQLiteHelper.TABLE_NAME, null,null);
        for (LightContainer lgts : lista) {
            ContentValues values = new ContentValues();

            values.put(SQLiteHelper.UNIQUE_NAME, lgts.getUnique_name());
            values.put(SQLiteHelper.NICK_NAME, lgts.getName());
            values.put(SQLiteHelper.ADDR, lgts.getAdrress());
            values.put(SQLiteHelper.SIGNAL, lgts.getSignal());
            values.put(SQLiteHelper.TYPE, "light");

            try {
                database.insertOrThrow(SQLiteHelper.TABLE_NAME, null, values);
            } catch (SQLiteConstraintException e) {
            }
        }
    }

    public ArrayList<LightContainer> getCacheDiscovery(){
        ArrayList<LightContainer> lightContainers = new ArrayList<>();

        Cursor cursor;

        cursor = database.query(SQLiteHelper.TABLE_NAME,new String[]{SQLiteHelper.UNIQUE_NAME,SQLiteHelper.NICK_NAME, SQLiteHelper.ADDR, SQLiteHelper.SIGNAL},null, null, null, null, null);

        while (cursor.moveToNext()){
                lightContainers.add(
                        new LightContainer(
                                cursor.getString(cursor.getColumnIndex(SQLiteHelper.UNIQUE_NAME)),
                                cursor.getString(cursor.getColumnIndex(SQLiteHelper.NICK_NAME)),
                                cursor.getString(cursor.getColumnIndex(SQLiteHelper.ADDR)),
                                cursor.getInt(cursor.getColumnIndex(SQLiteHelper.SIGNAL))
                        )
                );
        }
        cursor.close();

        return lightContainers;
    }
}
