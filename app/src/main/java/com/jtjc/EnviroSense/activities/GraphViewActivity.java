package com.jtjc.EnviroSense.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jtjc.EnviroSense.R;
import com.jtjc.EnviroSense.SensorData;
import com.jtjc.EnviroSense.graphs.UpdatedLineGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphViewActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private Intent intent;
    private String dataTypeToView;

    private LineGraphSeries<DataPoint> lineSeries;
    private GraphView graphView;
    private UpdatedLineGraph updatedLineGraph;

    private DatabaseReference dbRef;

    private Runnable printTimer;

    private ArrayList<SensorData> sensorDataList;
    private HashMap dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        Intent intent = getIntent();
        dataTypeToView = intent.getStringExtra("DATA_TYPE");

        graphView = findViewById(R.id.graph);

        dbRef = FirebaseDatabase.getInstance().getReference("esp8266airsensor");

        sensorDataList = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                sensorDataList.clear();
                for(DataSnapshot sensorSnapshot : dataSnapshot.getChildren()){
                    dataMap = sensorSnapshot.getValue(HashMap.class);
                    Log.d("data type", sensorSnapshot.getValue().getClass().getName());
                    Log.d("data found", sensorSnapshot.getValue().toString());
//                    Log.d("type key", dataMap.);
//                    Log.d("type value", );
//                    SensorData sensorData = sensorSnapshot.getValue(SensorData.class);

//                    sensorDataList.add(sensorData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Value cancelled", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        printTimer = new Runnable() {
            @Override
            public void run() {
//                Log.d("path", dbRef.toString());
//                Log.d("data", sensorDataList.toString());
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(printTimer);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
