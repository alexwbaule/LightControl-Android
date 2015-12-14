package com.alexwbaule.lightcontrol.json;

import com.alexwbaule.lightcontrol.container.LightContainer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 12/12/15.
 */
public class Parser {
    private static final String CONFIG = "config";
    private static final String NAME = "name";
    private static final String STATE = "state";

    public static LightContainer parseJSON(JSONObject response, boolean isstate) {
        LightContainer lc;
        lc = new LightContainer();
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

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }

}
