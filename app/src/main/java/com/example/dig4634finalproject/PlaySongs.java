package com.example.dig4634finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PlaySongs extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManagerTemp;
    SensorManager sensorManagerHum;
    private Sensor tempSensor;
    private Sensor humiditySensor;
    ImageView weatherIcon;
    TextView tempText;
    TextView humidityText;
    float myTemp = 0;
    int myHumidity =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);

        MediaController mediaController = new MediaController(this);
        final VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("https://static.videezy.com/system/resources/previews/000/040/193/original/bg_0004.mp4");

        //mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);

        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        tempText = findViewById(R.id.tempText);
        humidityText = findViewById(R.id.humidityText);

        sensorManagerTemp = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTemp.registerListener(this, sensorManagerTemp.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), sensorManagerTemp.SENSOR_DELAY_NORMAL );

        sensorManagerHum = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerHum.registerListener(this, sensorManagerHum.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), sensorManagerHum.SENSOR_DELAY_NORMAL );


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            myTemp = (sensorEvent.values[0] * 1.8f) + 32;

        }
        else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY)
        {
            myHumidity = (int)sensorEvent.values[0];
        }

        if(myTemp > 100 && myHumidity > 30)
            GlobalVariables.heatIndex = 5;
        else if(myTemp > 80 && myHumidity > 20)
            GlobalVariables.heatIndex = 4;
        else if(myTemp > 65 && myHumidity > 10)
            GlobalVariables.heatIndex = 3;
        else if(myTemp > 50 && myHumidity > 0)
            GlobalVariables.heatIndex = 2;
        else if(myTemp < 50 || myHumidity < 0)
            GlobalVariables.heatIndex = 1;
        tempText.setText(""+ myTemp + "° F");
        humidityText.setText(""+ myHumidity + "% Humidity");

        switch(GlobalVariables.heatIndex)
        {
            case 1:
                weatherIcon.setImageResource(R.drawable.freezingg);
                break;
            case 2:
                weatherIcon.setImageResource(R.drawable.coldd);
                break;
            case 3:
                weatherIcon.setImageResource(R.drawable.chilll);
                break;
            case 4:
                weatherIcon.setImageResource(R.drawable.warmm);
                break;
            case 5:
                weatherIcon.setImageResource(R.drawable.hott);
                break;
            default:
                break;
        }
        Log.d("EXAMPLE My Temp is: "  , " TEMP: " + myTemp + " my humidity is: " + myHumidity + " HEAT INDEX IS: " + GlobalVariables.heatIndex);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}