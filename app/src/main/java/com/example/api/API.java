package com.example.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class API {

    private String url = "http://192.168.100.9:5000/api/";
    private RequestQueue mQueue;
    private static API instance;


    // Singleton
    public static API GetInstance(Context context){
        if(instance == null){
            instance = new API(context);
        }
        return instance;
    }

    public static API GetInstance(){
        return GetInstance(null);
    }


    private API (Context context) {
        mQueue = Volley.newRequestQueue(context);
    }


    public void getSensor(int sensor, final ICallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + sensor, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject sensors = response.getJSONObject("sensors");
                            String sensorName = sensors.getString("sensor");
                            double value = sensors.getDouble("value");
                            callback.onFinish(new Sensor(sensorName, value));
                        } catch (JSONException e) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        mQueue.add(request);
    }
}
