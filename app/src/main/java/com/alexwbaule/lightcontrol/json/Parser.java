package com.alexwbaule.lightcontrol.json;

import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.container.WifiEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alex on 12/12/15.
 */
public class Parser {
    private static final String CONFIG = "config";
    private static final String NAME = "name";
    private static final String STATE = "state";
    private static final String SSIDS = "apssid";
    private static final String SAVED = "saved";

    public static LightContainer parseJSON(JSONObject response, boolean isstate) {
        LightContainer lc = new LightContainer();

        if (response != null && response.length() > 0) {
            try {
                if(contains(response, CONFIG)){
                    lc.setConfig((response.get(CONFIG).equals("true") ? true : false));
                }
                if(contains(response, NAME)){
                    lc.setName((String)response.get(NAME));
                }
                if(contains(response, STATE)){
                    lc.setState((response.get(STATE).equals("on") ? true : false));
                }
            } catch (JSONException e) {

            }
        }
        if(!isstate){
            lc.setConfig(true);
        }
        return lc;
    }

    public static ArrayList<WifiEntry> parseWifiJSON(JSONObject response){
        ArrayList<WifiEntry> wifiEntries = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if(contains(response, SSIDS)){
                    JSONArray obj = response.getJSONArray(SSIDS);
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject wifi = obj.getJSONObject(i);
                        String name = wifi.getString("ssid");
                        String mac = wifi.getString("mac");
                        int signal = wifi.getInt("signal");
                        wifiEntries.add(new WifiEntry(name,mac,signal));
                    }
                }
            } catch (JSONException e) {

            }
        }
        return wifiEntries;
    }

    public static boolean parseSaveWifiJSON(JSONObject response) {
        boolean done = false;

        if (response != null && response.length() > 0) {
            try {
                if(contains(response, SAVED)){
                    done = response.getBoolean(SAVED);
                }
            } catch (JSONException e) {

            }
        }
        return done;
    }

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }

}
