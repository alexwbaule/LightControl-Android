package com.alexwbaule.lightcontrol.json;

import com.alexwbaule.lightcontrol.container.LightContainer;

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

    /*
            getRequest("http://192.168.250.104/state", swc, tv);

        swc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getRequest("http://192.168.250.104/light/on", swc, tv);
                } else {
                    getRequest("http://192.168.250.104/light/off", swc, tv);
                }
            }
        });

        Log.d("STATUS -", "Valor is " + swc.getText().toString());


    */
    public static ArrayList<LightContainer> parseStateJSON(JSONObject response) {
        ArrayList<LightContainer> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if(contains(response, CONFIG)){
                    response.get(CONFIG);
                }
                if(contains(response, NAME)){
                    response.get(NAME);
                }
                if(contains(response, STATE)){
                    response.get(STATE);
                }
            } catch (JSONException e) {

            }
        }
        return listMovies;
    }

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }

}
