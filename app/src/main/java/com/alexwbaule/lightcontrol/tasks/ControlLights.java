package com.alexwbaule.lightcontrol.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.LoadLightResult;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.android.volley.RequestQueue;

/**
 * Created by alex on 12/14/15.
 */
public class ControlLights extends AsyncTask<LightContainer, Void, LightContainer> {
    private View switcher;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String TAG = "FindNodes";


    public ControlLights(View loadNodesListener) {
        this.switcher = loadNodesListener;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Log.d(TAG, "FindNodes Constructor");
    }

    public ControlLights() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Log.d(TAG, "FindNodes Constructor");
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
        if(switcher != null){
            Log.e(TAG, "Ok, atualizando item");
            switcher.post(new Runnable() {
                @Override
                public void run() {
                    Switch l = (Switch) switcher;
                    l.setText(lightContainers.getStatename());
                }
            });
        }
    }
}
