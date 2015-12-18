package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;

import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.WifiLoadListener;
import com.alexwbaule.lightcontrol.container.WifiEntry;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by alex on 17/12/15.
 */
public class FindWifiList extends AsyncTask<String, Void, ArrayList<WifiEntry>> {
    WifiLoadListener callback;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String TAG = "FindWifiList";

    public FindWifiList(WifiLoadListener callback) {
        this.callback = callback;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<WifiEntry> doInBackground(String... params) {
        return GetFromVolley.loadWifiList(requestQueue, params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<WifiEntry> wifiEntries) {
        if(callback != null){
            callback.onLoadComplete(wifiEntries);
        }
    }
}
