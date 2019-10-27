package com.jtjc.EnviroSense;

public enum SensorDataType {
    CO2("CO2"),
    PRESSURE("Pressure"),
    TEMPERATURE("TEMP"),
    TVOC("TVOC"),
    UV("UV");

    public final String name;

    SensorDataType(String name) {
        this.name = name;
    }
}
