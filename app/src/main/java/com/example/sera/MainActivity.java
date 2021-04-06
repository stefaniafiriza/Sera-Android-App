package com.example.sera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView waterPlant;
    private TextView weedRatio;
    private TextView humidity;
    private TextView temperature;
    private TextView moisture;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterPlant = findViewById(R.id.text_view_result5);
        weedRatio = findViewById(R.id.text_view_result3);
        humidity = findViewById(R.id.text_view_result2);
        temperature = findViewById(R.id.text_view_result1);
        moisture = findViewById(R.id.text_view_result4);

        Button btnWaterPlante = findViewById(R.id.button_water_plante);
        Button btnGetWeeds = findViewById(R.id.button_weeds);
        Button btnTemperature = findViewById(R.id.button_temperature);
        Button getHumidity = findViewById(R.id.button_humidity);
        Button btnGetMoisture = findViewById(R.id.button_moisture);

        mQueue = Volley.newRequestQueue(this);

        btnWaterPlante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest("5", waterPlant, mQueue).jsonParse();
            }
        });

        btnGetMoisture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest("1", moisture, mQueue).jsonParse();
            }
        });

        getHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest("3", humidity, mQueue).jsonParse();
            }
        });

        btnTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest("4", temperature, mQueue).jsonParse();
            }
        });

        btnGetWeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequest("6", weedRatio , mQueue).jsonParse();
            }
        });
    }
}
