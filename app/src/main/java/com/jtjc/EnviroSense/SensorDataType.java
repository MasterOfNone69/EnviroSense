package com.jtjc.EnviroSense;

public enum SensorDataType {
    CO2("CO2"),
    PRESSURE("Pressure"),
    TEMPERATURE("TEMP"),
    TVOC("TVOC"),
    UV("UV"),
    DEVICE("device"),
    TIMESTAMP("timestamp");

    public final String name;

    SensorDataType(String name) {
        this.name = name;
    }
}
