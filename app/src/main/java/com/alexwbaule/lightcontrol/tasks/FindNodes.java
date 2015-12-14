package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;

import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by alex on 13/12/15.
 */
public class FindNodes extends AsyncTask<Void, Void, ArrayList<LightContainer>> {
    private LoadNodesListener loadNodesListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public FindNodes(LoadNodesListener loadNodesListener) {
        this.loadNodesListener = loadNodesListener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<LightContainer> doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<LightContainer> lightContainers) {
        if(loadNodesListener != null){
            loadNodesListener.onLoadNodesComplete(lightContainers);
        }
    }
}
