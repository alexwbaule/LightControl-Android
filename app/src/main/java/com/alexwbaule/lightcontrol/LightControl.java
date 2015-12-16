package com.alexwbaule.lightcontrol;

import android.app.Application;


/**
 * Created by alex on 12/12/15.
 */
public class LightControl extends Application {
    private static final String TAG = "LightControlApplication";
    private static LightControl instance = null;
    public static final String NAME = "NAME";
    public static final String ADDR = "ADDR";


    public static LightControl getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.log(TAG, "LightControlApplication.onCreate()");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.log(TAG, "MeuCarroApplication.onTerminate()");
    }
}
