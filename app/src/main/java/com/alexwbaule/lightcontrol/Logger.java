package com.alexwbaule.lightcontrol;

import android.util.Log;

/**
 * Created by alex on 15/12/15.
 */
public class Logger {
    public static void log(String tag, String msg){
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }
}
