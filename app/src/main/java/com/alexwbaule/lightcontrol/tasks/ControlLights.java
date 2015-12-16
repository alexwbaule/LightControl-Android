package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Switch;

import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.LoadLightResult;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

/**
 * Created by alex on 12/14/15.
 */
public class ControlLights extends AsyncTask<LightContainer, Void, LightContainer> {
    private LoadLightResult loadLightResult;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String TAG = "ControlLights";


    public ControlLights(LoadLightResult loadNodesListener) {
        this.loadLightResult = loadNodesListener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Logger.log(TAG, "ControlLights Constructor");
    }

    public ControlLights() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Logger.log(TAG, "ControlLights Constructor");
    }

    @Override
    protected LightContainer doInBackground(LightContainer... params) {
        LightContainer lgt = params[0];
        if(lgt.isState()){
            return GetFromVolley.lightOff(requestQueue, lgt);
        }else {
            return GetFromVolley.ligthOn(requestQueue, lgt);
        }
    }

    @Override
    protected void onPostExecute(final LightContainer lightContainers) {
        if(loadLightResult != null){
            Logger.log(TAG, "Ok, atualizando item");
            loadLightResult.onLightsOk(lightContainers);
        }
    }
}
