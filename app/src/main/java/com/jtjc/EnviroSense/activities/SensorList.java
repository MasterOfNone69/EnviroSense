package com.jtjc.EnviroSense.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jtjc.EnviroSense.R;
import com.jtjc.EnviroSense.SensorData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jjoe64.graphview.GraphView;

import java.util.List;

public class SensorList extends ArrayAdapter<SensorData> {
    private Activity context;
    private List<SensorData> sensorDataList;

    public SensorList(Activity context, List<SensorData> sensorDataList){
        super(context, R.layout.graph_temp, sensorDataList);
        this.context = context;
        this.sensorDataList = sensorDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.graph_temp, null,true);
        GraphView co2 = (GraphView) listViewItem.findViewById(R.id.graph);
        SensorData sensorData = sensorDataList.get(position);

        TempGraph co2Graph = new TempGraph();
        co2Graph.onStart();
        co2Graph.sensorData = Double.parseDouble(sensorData.getCo2Sensor());

        return listViewItem;
    }
}

