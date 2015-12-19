package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;

import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.container.WifiConf;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

/**
 * Created by alex on 19/12/15.
 */
public class SaveWifiConf extends AsyncTask<WifiConf, Void, Boolean>{
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String TAG = "SaveWifiConf";

    public SaveWifiConf() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Logger.log(TAG, "ControlLights Constructor");
    }

    @Override
    protected Boolean doInBackground(WifiConf... params) {
        return GetFromVolley.saveWifiConf(requestQueue, params[0].getAddr(),params[0].getName(),params[0].getSsid(),params[0].getPasswd());
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

    }
}
