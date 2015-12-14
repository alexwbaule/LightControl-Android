package com.alexwbaule.lightcontrol.callback;

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
    public static ArrayList<LightContainer> loadallStatus(RequestQueue requestQueue, String url) {
        JSONObject response = Requestor.requestJSON(requestQueue, url);
        ArrayList<LightContainer> listMovies = Parser.parseStateJSON(response);
        return listMovies;
    }
}
