package com.example.sera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.api.API;
import com.example.api.ICallback;
import com.example.api.Sensor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API.GetInstance(this); // create the Singleton

        Button btnWaterPlante = findViewById(R.id.button_water_plante);
        Button btnGetWeeds = findViewById(R.id.button_weeds);
        Button btnTemperature = findViewById(R.id.button_temperature);
        Button getHumidity = findViewById(R.id.button_humidity);
        Button btnGetMoisture = findViewById(R.id.button_moisture);



        btnWaterPlante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICallback callback = new ICallback() {
                    @Override
                    public void onFinish(Sensor response) {
                    }

                    @Override
                    public void onError(Exception error) {

                    }
                };

                API.GetInstance().getSensor(5, callback);

            }
        });

        btnGetMoisture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICallback callback = new ICallback() {
                    @Override
                    public void onFinish(Sensor response) {
                    }

                    @Override
                    public void onError(Exception error) {

                    }
                };

                API.GetInstance().getSensor(1, callback);
            }
        });

        getHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HumidityActivity.class));
            }
        });

        btnTemperature.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TempActivity.class));
                    }
        });

        btnGetWeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICallback callback = new ICallback() {
                    @Override
                    public void onFinish(Sensor response) {
                    }

                    @Override
                    public void onError(Exception error) {

                    }
                };

                API.GetInstance().getSensor(6, callback);
            }
        });
    }
}
