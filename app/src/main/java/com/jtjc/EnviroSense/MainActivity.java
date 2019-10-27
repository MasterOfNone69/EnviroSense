package com.jtjc.EnviroSense;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseSensor;
    GraphView graphView;
    List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Firebase Connection Success", Toast.LENGTH_LONG).show();
        sensorList = new ArrayList<>();
        databaseSensor = FirebaseDatabase.getInstance().getReference("esp8266airsensor");
        graphView = (GraphView)findViewById(R.id.graph);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSensor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensorList.clear();
                for(DataSnapshot sensorSnapshot : dataSnapshot.getChildren()){
                    Sensor sensor = sensorSnapshot.getValue(Sensor.class);

                    sensorList.add(sensor);

                }
                SensorList adapter = new SensorList(MainActivity.this, sensorList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void startGraph(View view) {
        android.content.Intent graph1 = new Intent(this, TempGraph.class);
        startActivity(graph1);
    }
}

