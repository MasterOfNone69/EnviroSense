package com.jtjc.EnviroSense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);}

    public void startGraph(View view) {
        android.content.Intent graph1 = new Intent(this, TempGraph.class);
        startActivity(graph1);
    }
    }

