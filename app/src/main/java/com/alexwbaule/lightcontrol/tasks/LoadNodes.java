package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;

import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by alex on 19/12/15.
 */
public class LoadNodes extends AsyncTask<ArrayList<LightContainer>, Void, ArrayList<LightContainer>> {
    private LoadNodesListener loadNodesListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<LightContainer> lightContainers;
    public static final String TAG = "FindNodes";


    public LoadNodes(LoadNodesListener loadNodesListener) {
        this.loadNodesListener = loadNodesListener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        lightContainers = new ArrayList<>();
        Logger.log(TAG, "FindNodes Constructor");
    }

    @Override
    protected ArrayList<LightContainer> doInBackground(ArrayList<LightContainer>... params) {
        for (LightContainer devaddr: params[0]) {
            lightContainers.add(GetFromVolley.loadallStatus(requestQueue, devaddr));
        }
        return lightContainers;
    }

    @Override
    protected void onPostExecute(ArrayList<LightContainer> lightContainers) {
        if(loadNodesListener != null){
            loadNodesListener.onLoadNodesComplete(lightContainers);
        }
    }
}