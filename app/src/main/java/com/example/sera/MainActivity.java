package com.example.sera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.example.api.API;
import com.example.api.ICallback;
import com.example.api.Sensor;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API.GetInstance(this); 

        Button btnWaterPlante = findViewById(R.id.button_water_plante);
        Button btnGetWeeds = findViewById(R.id.button_weeds);
        Button btnTemperature = findViewById(R.id.button_temperature);
        Button getHumidity = findViewById(R.id.button_humidity);
        Button btnGetMoisture = findViewById(R.id.button_moisture);


        btnWaterPlante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                API.GetInstance().getSensor(5, new ICallback() {
                        @Override
                        public void onFinish(Sensor response) {
                            String mesage = "Your plants were watered!";
                            alert(mesage);
                        }

                        @Override
                        public void onError(Exception error) {

                            String mesage = "Something went wrong, try again!";
                            alert(mesage);
                        }
                });

            }

        });

        btnGetMoisture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MoistureActivity.class));
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
                startActivity(new Intent(MainActivity.this, WeedActivity.class));
            }
        });
    }

    public void alert(String mesage)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setMessage(mesage);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        if(!((Activity) this).isFinishing())
        {
            builder.show();
        }
    }
}
