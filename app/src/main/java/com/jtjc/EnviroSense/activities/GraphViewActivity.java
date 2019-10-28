package com.jtjc.EnviroSense.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
import com.jtjc.EnviroSense.SensorDataType;
import com.jtjc.EnviroSense.graphs.UpdatedLineGraph;

import java.text.ParseException;
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
    private Runnable graphUpdater;

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

        Toast.makeText(GraphViewActivity.this,"Firebase Connection Success", Toast.LENGTH_LONG).show();

        sensorDataList = new ArrayList<>();

        lineSeries = new LineGraphSeries<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensorDataList.clear();
                for(DataSnapshot sensorSnapshot : dataSnapshot.getChildren()){
                    dataMap = (HashMap) sensorSnapshot.getValue();
//                    Log.d("data type", sensorSnapshot.getValue().getClass().getName());
                    Log.d("data found", sensorSnapshot.getValue().toString());
//                    Log.d("co2type value", dataMap.get("CO2").getClass().toString());
//                    Log.d("devtype value", dataMap.get("device").getClass().toString());
//                    Log.d("timetype value", dataMap.get("timestamp").getClass().toString());

                    SensorData snapshotData = new SensorData(
                            (long) dataMap.get(SensorDataType.CO2.name),
                            (double) dataMap.get(SensorDataType.PRESSURE.name),
                            (double) dataMap.get(SensorDataType.TEMPERATURE.name),
                            (long) dataMap.get(SensorDataType.TVOC.name),
                            (long) dataMap.get(SensorDataType.UV.name),
                            (String) dataMap.get(SensorDataType.DEVICE.name),
                            (String) dataMap.get(SensorDataType.TIMESTAMP.name));

                    sensorDataList.add(snapshotData);
                }

                ArrayList<DataPoint> dataPoints = new ArrayList<>();

                if(dataTypeToView.equals(SensorDataType.CO2.name)) {
                    for(SensorData sd : sensorDataList) {
                        try {
                            dataPoints.add(new DataPoint(sd.parseTime(), sd.co2Sensor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(dataTypeToView.equals(SensorDataType.PRESSURE.name)) {
                    for(SensorData sd : sensorDataList) {
                        try {
                            dataPoints.add(new DataPoint(sd.parseTime(), sd.pressureSensor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(dataTypeToView.equals(SensorDataType.TEMPERATURE.name)) {
                    for(SensorData sd : sensorDataList) {
                        try {
                            dataPoints.add(new DataPoint(sd.parseTime(), sd.tempSensor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(dataTypeToView.equals(SensorDataType.TVOC.name)) {
                    for(SensorData sd : sensorDataList) {
                        try {
                            dataPoints.add(new DataPoint(sd.parseTime(), sd.tvocSensor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(dataTypeToView.equals(SensorDataType.UV.name)) {
                    for(SensorData sd : sensorDataList) {
                        try {
                            dataPoints.add(new DataPoint(sd.parseTime(), sd.uvSensor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                DataPoint[] points = new DataPoint[dataPoints.size()];
                lineSeries.resetData(dataPoints.toArray(points));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Value cancelled", "loadPost:onCancelled", databaseError.toException());
            }
        });

        updatedLineGraph = new UpdatedLineGraph(graphView, lineSeries);
        updatedLineGraph.viewportDefaultWidth = 300;
        updatedLineGraph.updateInterval = 2000;
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

        graphUpdater = new Runnable() {
            @Override
            public void run() {
                Log.d("update", "attempting to update graph");
                updatedLineGraph.updateGraph();
                handler.postDelayed(this, updatedLineGraph.updateInterval);
            }
        };
        handler.post(graphUpdater);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(printTimer);
        handler.removeCallbacks(graphUpdater);
        super.onPause();
    }
}
