package com.jtjc.EnviroSense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jtjc.EnviroSense.graphs.UpdatedLineGraph;

public class TempGraph extends AppCompatActivity {

    private final Handler handler = new Handler();
    private GraphView graph;
    private TextView text;
    private UpdatedLineGraph theGraph;
    private LineGraphSeries<DataPoint> series;
    private Runnable pointTimer;
    private Runnable updateTimer;
    private double counter = 5;

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.graph_temp);
        text = findViewById(R.id.resuming);
        graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0,1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
        });
        theGraph = new UpdatedLineGraph(graph, series);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointTimer = new Runnable() {
            @Override
            public void run() {
                DataPoint d = new DataPoint(counter, Math.random() * 5 - Math.random() * 5);
                counter += 1;
                Log.d("new point added", d.toString());
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
}

