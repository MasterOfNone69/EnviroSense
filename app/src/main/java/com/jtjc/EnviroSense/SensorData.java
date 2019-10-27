package com.jtjc.EnviroSense;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class SensorData {
    public final long co2Sensor;
    public final double pressureSensor;
    public final double tempSensor;
    public final long tvocSensor;
    public final long uvSensor;
    public final String deviceNode;
    public final String timeStamp;

    public SensorData(long co2Sensor, double pressureSensor, double tempSensor, long tvocSensor, long uvSensor, String deviceNode, String timeStamp) {
        this.co2Sensor = co2Sensor;
        this.pressureSensor = pressureSensor;
        this.tempSensor = tempSensor;
        this.tvocSensor = tvocSensor;
        this.uvSensor = uvSensor;
        this.deviceNode = deviceNode;
        this.timeStamp = timeStamp;
    }

//    public String getCo2Sensor() {
//        return co2Sensor;
//    }
//
//    public String getPressureSensor() {
//        return pressureSensor;
//    }
//
//    public String getTempSensor() {
//        return tempSensor;
//    }
//
//    public String getTvocSensor() {
//        return tvocSensor;
//    }
//
//    public String getUvSensor() {
//        return uvSensor;
//    }
//
//    public String getDeviceNode() {
//        return deviceNode;
//    }
//
//    public String getTimeStamp() {
//        return timeStamp;
//    }

    @NonNull
    @Override
    public String toString() {
        return String.format("(%d, %d, %d, %d, %d, %s, %s)", co2Sensor, pressureSensor, tempSensor, tvocSensor, uvSensor, deviceNode, timeStamp);
    }

    public long parseTime() throws ParseException {
        String date = timeStamp.substring(0, 10);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = format.parse(date);
        long dateLong = dateObj.getTime();
        String time = timeStamp.substring(11, 19);
        Time t = Time.valueOf(time);
        long timeLong = t.getTime();

        return dateLong + timeLong;
    }
}
