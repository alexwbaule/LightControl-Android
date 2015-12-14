package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;

import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

/**
 * Created by alex on 13/12/15.
 */
public class FindNodes extends AsyncTask<DeviceAddr, Void, LightContainer> {
    private LoadNodesListener loadNodesListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public FindNodes(LoadNodesListener loadNodesListener) {
        this.loadNodesListener = loadNodesListener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected LightContainer doInBackground(DeviceAddr... params) {
        return GetFromVolley.loadallStatus(requestQueue, params[0]);
    }

    @Override
    protected void onPostExecute(LightContainer lightContainers) {
        if(loadNodesListener != null){
            loadNodesListener.onLoadNodesComplete(lightContainers);
        }
    }
}
