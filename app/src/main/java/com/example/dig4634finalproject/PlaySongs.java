package com.example.dig4634finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class PlaySongs extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManagerTemp;
    SensorManager sensorManagerHum;
    private Sensor tempSensor;
    private Sensor humiditySensor;
    int myTemp = 0;
    int myHumidity =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);

        sensorManagerTemp = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTemp.registerListener(this, sensorManagerTemp.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), sensorManagerTemp.SENSOR_DELAY_NORMAL );

        sensorManagerHum = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerHum.registerListener(this, sensorManagerHum.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), sensorManagerHum.SENSOR_DELAY_NORMAL );


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            myTemp = (int)sensorEvent.values[0];
        }
        else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY)
        {
            myHumidity = (int)sensorEvent.values[0];
        }


        Log.d("EXAMPLE My Temp is: "  , " TEMP: " + myTemp + " my humidity is: " + myHumidity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}