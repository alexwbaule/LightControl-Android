package com.alexwbaule.lightcontrol;

import android.app.Application;
import android.util.Log;

import com.alexwbaule.lightcontrol.dns_sd.NsdHelper;

/**
 * Created by alex on 12/12/15.
 */
public class LightControl extends Application {
    private static final String TAG = "LightControlApplication";
    private static LightControl instance = null;
    public static NsdHelper mNsdHelper;


    public static LightControl getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d(TAG, "LightControlApplication.onCreate()");
        mNsdHelper = new NsdHelper(LightControl.getInstance());
        mNsdHelper.initializeNsd();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "MeuCarroApplication.onTerminate()");
    }
}
