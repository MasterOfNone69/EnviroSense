package com.jtjc.EnviroSense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jtjc.EnviroSense.graphs.UpdatedLineGraph;

public class TempGraph extends AppCompatActivity {

    private final Handler handler = new Handler();
    private GraphView graph;
    DatabaseReference databaseSensor;
    private TextView text;
    private UpdatedLineGraph theGraph;
    private LineGraphSeries<DataPoint> series;
    private Runnable pointTimer;
    private Runnable updateTimer;
    protected double sensorData;
    private double counter = 5;

    @Override
    protected void onStart(){
        setTheme(R.style.AppTheme);
        super.onStart();
        setContentView(R.layout.graph_temp);
        text = findViewById(R.id.resuming);
        graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0,0)
        });
        theGraph = new UpdatedLineGraph(graph, series);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointTimer = new Runnable() {
            @Override
            public void run() {
                DataPoint d = new DataPoint(counter, sensorData);
                counter += 1;
                //Log.d("new point added", d.toString());
                theGraph.addPoint(d);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(pointTimer, 1000);

        updateTimer = new Runnable() {
            @Override
            public void run() {
                theGraph.updateGraph();
                handler.postDelayed(this, theGraph.updateInterval);
            }
        };
        handler.post(updateTimer);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(pointTimer);
        handler.removeCallbacks(updateTimer);
        super.onPause();
    }
}

