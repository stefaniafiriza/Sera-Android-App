package com.example.sera;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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


import com.example.api.API;
import com.example.api.ICallback;
import com.example.api.Sensor;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Timer;
import java.util.TimerTask;

public class HumidityActivity extends AppCompatActivity {

    private TextView humidity;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    private int currentPoints = 0;
    private boolean skip = false;
    private boolean alertOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        super.setTitle("Humidity");


        humidity = findViewById(R.id.stat_2);
        graph = findViewById(R.id.graph);

        Button btnHumidity = findViewById(R.id.stat_3);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(100);
        btnHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(true);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update(false);
            }
        }, 0, 1000);

    }
    private void update(boolean force){
        if(skip && !force){
            return;
        }
        ICallback callback = new ICallback() {
            @Override
            public void onFinish(Sensor response) {
                humidity.setText(String.format("%s %%", response.getValue()));

                if(currentPoints == 100)
                {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>();
                    currentPoints = 0;
                }

                series.appendData(new DataPoint(currentPoints, response.getValue()), false, currentPoints + 1);

                if(response.getValue() >= 60)
                {
                    String mesage = "The humidity is too high for your plants! Stop watering them!";
                    alert(mesage);
                }
                else if (response.getValue()<= 0)
                {
                    String mesage = "The humidity is too low for your plants! You should water them now!";
                    alert(mesage);
                }

                graph.addSeries(series);
                currentPoints+=1;
                skip = false;
            }

            @Override
            public void onError(Exception error) {

            }
        };

        API.GetInstance().getSensor(3, callback);
        skip = true;
    }

    private void alert(String mesage)
    {
        if(alertOpened)
        {
            return;
        }
        alertOpened = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(HumidityActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Humidity alert!");
        builder.setMessage(mesage);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                alertOpened = false;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertOpened = false;

            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                alertOpened = false;

            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertOpened = false;

            }
        });
        if(!((Activity) this).isFinishing())
        {
            builder.show();
        }
    }


}
