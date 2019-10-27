package com.jtjc.EnviroSense.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jtjc.EnviroSense.R;
import com.jtjc.EnviroSense.SensorData;
import com.jtjc.EnviroSense.SensorDataType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseSensor;
    GraphView graphView;
    List<SensorData> sensorDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Firebase Connection Success", Toast.LENGTH_LONG).show();
        sensorDataList = new ArrayList<>();
        databaseSensor = FirebaseDatabase.getInstance().getReference("esp8266airsensor");
        graphView = (GraphView)findViewById(R.id.graph);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSensor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensorDataList.clear();
                for(DataSnapshot sensorSnapshot : dataSnapshot.getChildren()){
//                    SensorData sensorData = sensorSnapshot.getValue(SensorData.class);

//                    sensorDataList.add(sensorData);
                }
                SensorList adapter = new SensorList(MainActivity.this, sensorDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void startTemp(View view) {
        Intent g = new Intent(this, GraphViewActivity.class);
        g.putExtra("DATA_TYPE", SensorDataType.TEMPERATURE.name);
        startActivity(g);
    }

    public void startUVGraph(View view) {
        Intent g = new Intent(this, GraphViewActivity.class);
        g.putExtra("DATA_TYPE", SensorDataType.UV.name);
        startActivity(g);
    }

    public void startECO2(View view) {
        Intent g = new Intent(this, GraphViewActivity.class);
        g.putExtra("DATA_TYPE", SensorDataType.CO2.name);
        startActivity(g);
    }

    public void startTVOC(View view) {
        Intent g = new Intent(this, GraphViewActivity.class);
        g.putExtra("DATA_TYPE", SensorDataType.TVOC.name);
        startActivity(g);
    }

    public void startPRESSURE(View view) {
        Intent g = new Intent(this, GraphViewActivity.class);
        g.putExtra("DATA_TYPE", SensorDataType.PRESSURE.name);
        startActivity(g);
    }
}

