package com.example.sera;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SendRequest {

    //    server local pt testare functionalitati
    private String url = "http://192.168.100.9:5000/api/"; //ale
    //  http://192.168.1.9:5000/api/ stefi

    private TextView textView;
    private RequestQueue mqueue;

    public SendRequest(String id, TextView textView, RequestQueue mqueue) {
        this.url += id;
        this.textView = textView;
        this.mqueue = mqueue;
    }


    public void jsonParse() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject sensors = response.getJSONObject("sensors");
                            String sensorName = sensors.getString("sensor");
                            double value = sensors.getDouble("value");
                            if (sensorName.equals("Detect weeds"))
                                textView.setText(String.format("%s, %s%%", sensorName, value));
                            else {
                                if (sensorName.equals("temperature - air"))
                                    if (value < 20 || value > 40)
                                        textView.setText("Wrong temperature! Please try again!");
                                    else
                                        textView.setText(String.format("%s, %s°C", sensorName, value));
                                else {
                                    if (sensorName.equals("moisture")) {
                                        if (value > 2.5)
                                            textView.setText("The plant need water!");
                                        else
                                            textView.setText("The plant is wet!");

                                    } else
                                        textView.setText(String.format("%s, %s", sensorName, value));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mqueue.add(request);
    }
}
