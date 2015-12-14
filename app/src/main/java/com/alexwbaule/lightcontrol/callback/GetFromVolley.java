package com.alexwbaule.lightcontrol.callback;

import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.json.Parser;
import com.alexwbaule.lightcontrol.json.Requestor;
import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alex on 13/12/15.
 */

public class GetFromVolley {
    public static LightContainer loadallStatus(RequestQueue requestQueue, DeviceAddr devaddr) {
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue, "http://" + devaddr.getIpAddr() + "/state");
        lightContainers = Parser.parseJSON(response, true);
        lightContainers.setUnique_name(devaddr.getName());
        return lightContainers;
    }
    public static LightContainer ligthOn(RequestQueue requestQueue,String ipaddr) {
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue,  "http://" + ipaddr + "/state");
        lightContainers = Parser.parseJSON(response, true);
        return lightContainers;
    }
    public static LightContainer lightOff(RequestQueue requestQueue, String ipaddr) {
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue,  "http://" + ipaddr + "/state");
        lightContainers = Parser.parseJSON(response, true);
        return lightContainers;
    }
}
