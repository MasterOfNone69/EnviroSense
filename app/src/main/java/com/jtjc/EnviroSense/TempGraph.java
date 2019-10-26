package com.jtjc.envirosense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TempGraph extends AppCompatActivity {

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.graph_temp);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries(new DataPoint[]{
            new DataPoint(0,1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });
        graph.addSeries(series);
    }}

