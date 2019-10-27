package com.jtjc.EnviroSense.graphs;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jtjc.EnviroSense.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper for GraphViews and LineGraphSeries to update the axes nicer when points are added.
 */
public class UpdatedLineGraph {

    private GraphView graphView;
    private LineGraphSeries<DataPoint> lineGraph;
    public int maxPoints = 100;
    private boolean useAutoView = true;
    public double viewportDefaultWidth = 5;

    private Pair<Double> xBounds;
    private Pair<Double> yBounds;
    private double viewportHeightShrinkThreshold = 0.4; // when the y difference in data points is smaller than current viewport height * this threshold

    private double xMarginRatio = 0.2;
    private double yMarginRatio = 0.2;

    private boolean graphDirty = false;
    public long updateInterval = 5000; // milliseconds

    public UpdatedLineGraph(GraphView graphView, LineGraphSeries<DataPoint> lineGraph) {
        this.lineGraph = lineGraph;
        this.graphView = graphView;
        graphView.addSeries(lineGraph);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);

        xBounds = getDefaultXBounds();
        yBounds = getDefaultYBounds();
    }

    public void updateGraph() {
//        if(graphDirty) {
        xBounds = getDefaultXBounds();
        yBounds = getDefaultYBounds();
        double xDifference = xBounds.second - xBounds.first;
        double yDifference = yBounds.second - yBounds.first;
        graphView.getViewport().setMinX(xBounds.first - xDifference * xMarginRatio);
        graphView.getViewport().setMaxX(xBounds.second + xDifference * xMarginRatio);
        graphView.getViewport().setMinY(yBounds.first - yDifference * yMarginRatio);
        graphView.getViewport().setMaxY(yBounds.second + yDifference * yMarginRatio);

        graphDirty = false;
//        }
    }

    public void addPoint(DataPoint point) {
        lineGraph.appendData(point, false, maxPoints, false);
        graphDirty = true;
    }

    private Pair<Double> getDefaultXBounds() {
        return new Pair<>(lineGraph.getHighestValueX() - viewportDefaultWidth, lineGraph.getHighestValueX());
    }

    private Pair<Double> getDefaultYBounds() {
        List<Double> yVals = new ArrayList<>();
        for (Iterator<DataPoint> it = lineGraph.getValues(xBounds.first, xBounds.second); it.hasNext(); ) {
            DataPoint p = it.next();
            yVals.add(p.getY());
        }
        if(yVals.isEmpty()) {
            return new Pair<>(0.0, 0.0);
        }
        double minY = Collections.min(yVals);
        double maxY = Collections.max(yVals);
        if(yBounds != null && minY > yBounds.first && maxY < yBounds.second && maxY - minY >= (yBounds.second - yBounds.first) * viewportHeightShrinkThreshold) {
            return yBounds;
        }
        return new Pair<>(minY, maxY);
    }
}
