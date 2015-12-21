package com.alexwbaule.lightcontrol.callback;


import android.net.Uri;
import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.container.WifiEntry;
import com.alexwbaule.lightcontrol.json.Parser;
import com.alexwbaule.lightcontrol.json.Requestor;
import com.android.volley.RequestQueue;

import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by alex on 13/12/15.
 */

public class GetFromVolley {
    private static final String TAG = "GetFromVolley";
    public static LightContainer loadallStatus(RequestQueue requestQueue, LightContainer devaddr) {
        String url = "http://" + devaddr.getAdrress() + "/state";
        Logger.log(TAG, "URL IS -> " + url);
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue, url);
        lightContainers = Parser.parseJSON(response, false);
        if(response == null){
            lightContainers.setName(devaddr.getName());
            lightContainers.setSignal(0);
            lightContainers.setState(false);
        }
        lightContainers.setUnique_name(devaddr.getName());
        lightContainers.setAdrress(devaddr.getAdrress());

        if(!lightContainers.isConfig())
            lightContainers.setName("Sem Nome");
        return lightContainers;
    }

    public static LightContainer loadallStatus(RequestQueue requestQueue, DeviceAddr devaddr) {
        String url = "http://" + devaddr.getIpAddr() + "/state";
        Logger.log(TAG, "URL IS -> " + url);
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue, url);
        lightContainers = Parser.parseJSON(response, true);
        lightContainers.setUnique_name(devaddr.getName());
        lightContainers.setAdrress(devaddr.getIpAddr());
        if(!lightContainers.isConfig())
            lightContainers.setName("Sem Nome");
        return lightContainers;
    }
    public static LightContainer ligthOn(RequestQueue requestQueue, LightContainer devaddr) {
        String url = "http://" + devaddr.getAdrress() + "/light/on";
        Logger.log(TAG, "URL IS -> " + url);
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue,  url);
        lightContainers = Parser.parseJSON(response, false);
        lightContainers.setUnique_name(devaddr.getUnique_name());
        lightContainers.setAdrress(devaddr.getAdrress());
        return lightContainers;
    }
    public static LightContainer lightOff(RequestQueue requestQueue, LightContainer devaddr) {
        String url = "http://" + devaddr.getAdrress() + "/light/off";
        Logger.log(TAG, "URL IS -> " + url);
        LightContainer lightContainers;
        JSONObject response = Requestor.requestJSON(requestQueue,  url);
        lightContainers = Parser.parseJSON(response, false);
        lightContainers.setUnique_name(devaddr.getUnique_name());
        lightContainers.setAdrress(devaddr.getAdrress());
        return lightContainers;
    }
    public static ArrayList<WifiEntry> loadWifiList(RequestQueue requestQueue, String devaddr) {
        String url = "http://" + devaddr + "/scan";
        Logger.log(TAG, "URL IS -> " + url);
        ArrayList<WifiEntry> wifiEntries;
        JSONObject response = Requestor.requestJSON(requestQueue,  url);
        wifiEntries = Parser.parseWifiJSON(response);
        return wifiEntries;
    }
    public static boolean saveWifiConf(RequestQueue requestQueue, String addr, String name, String ssid, String passwd){
        String url = "http://" + Uri.encode(addr) + "/wifichange?n=" + Uri.encode(name) + "&p=" + Uri.encode(passwd) + "&s=" + Uri.encode(ssid);
        Logger.log(TAG, "URL IS -> " + url);
        JSONObject response = Requestor.requestJSON(requestQueue,  url);
        return Parser.parseSaveWifiJSON(response);
    }
}
