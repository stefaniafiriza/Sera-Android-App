package com.example.sera;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.api.API;
import com.example.api.ICallback;
import com.example.api.Sensor;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Timer;
import java.util.TimerTask;

public class WeedActivity extends AppCompatActivity {

    private TextView weeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weed);

        super.setTitle("Weed Detection");

        weeds = findViewById(R.id.stat_2);

        Button btnGetWeeds = findViewById(R.id.stat_3);
        btnGetWeeds.setOnClickListener(new View.OnClickListener() {
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
                weeds.setText(String.format("%s %%", response.getValue()));

                if(response.getValue() >= 50)
                {
                    String mesage = "There are too many weeds!";
                    alert(mesage);
                }
            }

            @Override
            public void onError(Exception error) {

            }
        };

        API.GetInstance().getSensor(6, callback);
    }
    private void alert(String mesage)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(WeedActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Weed detection alert!");
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
