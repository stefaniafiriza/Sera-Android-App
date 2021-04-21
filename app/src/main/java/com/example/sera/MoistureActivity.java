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

public class MoistureActivity extends AppCompatActivity {

    private TextView moisture;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    private int currentPoints = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moist);

        super.setTitle("Moisture");

        moisture = findViewById(R.id.stat_2);

        graph = findViewById(R.id.graph);

        Button btnGetMoisture = findViewById(R.id.stat_3);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(100);
        btnGetMoisture.setOnClickListener(new View.OnClickListener() {
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
        }, 0, 2000);

    }
    private void update(){
        ICallback callback = new ICallback() {
            @Override
            public void onFinish(Sensor response) {
                moisture.setText(String.format("%s", response.getValue()));

                if(currentPoints == 100)
                {
                    graph.removeAllSeries();
                    series = new LineGraphSeries<>();
                    currentPoints = 0;
                }

                series.appendData(new DataPoint(currentPoints, response.getValue()), false, currentPoints + 1);

                if(response.getValue() > 7)
                {
                    String mesage = "The soil is too damp! Consider discarding any water excess. ";
                    alert(mesage);
                }
                else if (response.getValue()< 3)
                {
                    String mesage = "The soil is too dry! Consider adding more water to your plants.";
                    alert(mesage);
                }
                graph.addSeries(series);
                currentPoints+=1;
            }

            @Override
            public void onError(Exception error) {

            }
        };

        API.GetInstance().getSensor(1, callback);
    }
    private void alert(String mesage)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(MoistureActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Moisture alert!");
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
