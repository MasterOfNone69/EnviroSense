package com.jtjc.EnviroSense;

public class Sensor {
    String co2Sensor;
    String pressureSensor;
    String tempSensor;
    String tvocSensor;
    String uvSensor;
    String deviceNode;
    String timeStamp;

    public Sensor(){

    }

    public Sensor(String co2Sensor, String pressureSensor, String tempSensor, String tvocSensor, String uvSensor, String deviceNode, String timeStamp) {
        this.co2Sensor = co2Sensor;
        this.pressureSensor = pressureSensor;
        this.tempSensor = tempSensor;
        this.tvocSensor = tvocSensor;
        this.uvSensor = uvSensor;
        this.deviceNode = deviceNode;
        this.timeStamp = timeStamp;
    }

    public String getCo2Sensor() {
        return co2Sensor;
    }

    public String getPressureSensor() {
        return pressureSensor;
    }

    public String getTempSensor() {
        return tempSensor;
    }

    public String getTvocSensor() {
        return tvocSensor;
    }

    public String getUvSensor() {
        return uvSensor;
    }

    public String getDeviceNode() {
        return deviceNode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
