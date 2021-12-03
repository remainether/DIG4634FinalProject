package com.example.dig4634finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;


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
    Button playBtn, pauseBtn, stopBtn;

    MediaPlayer mediaPlayer;

    WaveVisualizer waveVisualizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);
        playBtn = findViewById(R.id.playButton);
        pauseBtn = findViewById(R.id.pauseButton);
        stopBtn = findViewById(R.id.stopButton);
        waveVisualizer = findViewById(R.id.blast);

        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        tempText = findViewById(R.id.tempText);
        humidityText = findViewById(R.id.humidityText);

            sensorManagerTemp = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTemp.registerListener(this, sensorManagerTemp.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), sensorManagerTemp.SENSOR_DELAY_NORMAL );

        sensorManagerHum = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerHum.registerListener(this, sensorManagerHum.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), sensorManagerHum.SENSOR_DELAY_NORMAL );

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);

        }

        playBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(PlaySongs.this, R.raw.hot);
                    int audioSessionId = mediaPlayer.getAudioSessionId();
                    if(audioSessionId != -1){
                        waveVisualizer.setAudioSessionId(audioSessionId);
                    }

                }
                mediaPlayer.start();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    mediaPlayer.pause();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                    waveVisualizer.release();
                }

            }
        });



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

        if(myTemp > 100 && myHumidity > 30 )
            weatherIcon.setImageResource(R.drawable.hott);
        else if(myTemp > 80 && myHumidity > 20)
            weatherIcon.setImageResource(R.drawable.warmm);
        else if(myTemp > 65 && myHumidity > 10)
            weatherIcon.setImageResource(R.drawable.chilll);
        else if(myTemp > 50 && myHumidity > 0)
            weatherIcon.setImageResource(R.drawable.coldd);
        else if(myTemp < 50 || myHumidity < 0)
            weatherIcon.setImageResource(R.drawable.freezingg);
        tempText.setText(""+ myTemp + "° F");
        humidityText.setText(""+ myHumidity + "% Humidity");

        /*
        switch(GlobalVariables.heatIndex)
        {
            case 1:
                weatherIcon.setImageResource(R.drawable.freezingg);
                //break;
            case 2:
                weatherIcon.setImageResource(R.drawable.coldd);
               // break;
            case 3:
                weatherIcon.setImageResource(R.drawable.chilll);
                //break;
            case 4:
                weatherIcon.setImageResource(R.drawable.warmm);
               // break;
            case 5:
                weatherIcon.setImageResource(R.drawable.hott);
                //break;
            default:
                break;
        }

         */
        Log.d("EXAMPLE My Temp is: "  , " TEMP: " + myTemp + " my humidity is: " + myHumidity + " HEAT INDEX IS: " + GlobalVariables.heatIndex);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}