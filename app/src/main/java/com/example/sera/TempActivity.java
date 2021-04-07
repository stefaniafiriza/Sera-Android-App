package com.example.sera;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.api.API;
import com.example.api.ICallback;
import com.example.api.Sensor;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Timer;
import java.util.TimerTask;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {


    private TextView alertTextView;

    private TextView temperature;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    private int currentPoints = 0;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        super.setTitle("Temperature");


        temperature = findViewById(R.id.stat_2);

        graph = findViewById(R.id.graph);

        Button btnTemperature = findViewById(R.id.stat_3);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(100);
        btnTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second

    }
    private void update(){
        ICallback callback = new ICallback() {
            @Override
            public void onFinish(Sensor response) {
                temperature.setText(String.format("%s°C", response.getValue()));

                if(currentPoints == 100)
                {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>();
                    currentPoints = 0;
                }

                series.appendData(new DataPoint(currentPoints, response.getValue()), false, currentPoints + 1);

                if(response.getValue() >= 60)
                {
                    String mesage = "The temperature is to high for your plants! Consider moving them somewhere else or cool the room proprely!";
                    alert(mesage);
                }
                else if (response.getValue()<= 0)
                {
                    String mesage = "The temperature is too low your plants! Consider moving them somewhere else or heat the room proprely!";
                    alert(mesage);
                }
                graph.addSeries(series);
                currentPoints+=1;
            }

            @Override
            public void onError(Exception error) {

            }
        };

        API.GetInstance().getSensor(4, callback);
    }
    private void alert(String mesage)
    {
        alertTextView = (TextView) findViewById(R.id.AlertTextView);

        AlertDialog.Builder builder = new AlertDialog.Builder(TempActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Temperature allert!");
        builder.setMessage(mesage);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertTextView.setVisibility(View.VISIBLE);
                    }
                });
                builder.show();
    }


}
