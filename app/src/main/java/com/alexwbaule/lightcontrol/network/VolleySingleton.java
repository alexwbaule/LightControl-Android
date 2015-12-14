package com.alexwbaule.lightcontrol.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.alexwbaule.lightcontrol.LightControl;

/**
 * Created by alex on 12/12/15.
 */

public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue=Volley.newRequestQueue(LightControl.getInstance());
    }
    public static VolleySingleton getInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}