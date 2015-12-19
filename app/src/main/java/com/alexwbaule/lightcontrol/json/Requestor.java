package com.alexwbaule.lightcontrol.json;

import com.alexwbaule.lightcontrol.Logger;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by alex on 13/12/15.
 */
public class Requestor {
    public static JSONObject requestJSON(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                (String)null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Logger.log("Requestor", "InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            Logger.log("Requestor", "ExecutionException " + e.getMessage());
        } catch (TimeoutException e) {
            Logger.log("Requestor", "TimeoutException " + e.getMessage());
        }
        return response;
    }
}