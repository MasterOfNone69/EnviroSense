package com.jtjc.EnviroSense;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.jtjc.EnviroSense.TempGraph;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jjoe64.graphview.GraphView;

import java.util.List;

public class SensorList extends ArrayAdapter<Sensor> {
    private Activity context;
    private List<Sensor> sensorList;

    public SensorList(Activity context, List<Sensor> sensorList){
        super(context, R.layout.graph_temp, sensorList);
        this.context = context;
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.graph_temp, null,true);
        GraphView co2 = (GraphView) listViewItem.findViewById(R.id.graph);
        Sensor sensor = sensorList.get(position);

        TempGraph co2Graph = new TempGraph();
        co2Graph.onStart();
        co2Graph.sensorData = Double.parseDouble(sensor.getCo2Sensor());

        return listViewItem;
    }
}

