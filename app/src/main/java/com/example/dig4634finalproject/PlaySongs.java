package com.example.dig4634finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;

import java.util.ArrayList;


public class PlaySongs extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManagerTemp;
    SensorManager sensorManagerHum;
    private Sensor tempSensor;
    private Sensor humiditySensor;
    ImageView weatherIcon;
    ImageView albumCovers;
    TextView tempText;
    TextView humidityText;
    TextView songName;
    float myTemp = 0;
    int myHumidity =0;
    Button playBtn, pauseBtn, stopBtn;
    ImageView heartBtn;
    int prevIndex  = 3;
    int count = 0;
    String getSongName = null;
    MediaPlayer mediaPlayer;

    LikedSongs likeSong;
    ArrayList<String> myList = new ArrayList<String>();



    WaveVisualizer waveVisualizer;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);
        playBtn = findViewById(R.id.playButton);
        pauseBtn = findViewById(R.id.pauseButton);
        stopBtn = findViewById(R.id.stopButton);
        heartBtn = findViewById(R.id.favButton);


        waveVisualizer = findViewById(R.id.blast);

        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        albumCovers = (ImageView) findViewById(R.id.albumCovers);
        tempText = findViewById(R.id.tempText);
        humidityText = findViewById(R.id.humidityText);
        songName = findViewById(R.id.songName);

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        weatherIcon.startAnimation(slide_down);
        Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        tempText.startAnimation(fadein);
        humidityText.startAnimation(fadein);
        albumCovers.startAnimation(fadein);
        Animation half_down_fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.half_down_fade);
        playBtn.startAnimation(half_down_fade);
        pauseBtn.startAnimation(half_down_fade);
        stopBtn.startAnimation(half_down_fade);
        //heartBtn.startAnimation(half_down_fade);

        sensorManagerTemp = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTemp.registerListener(this, sensorManagerTemp.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), sensorManagerTemp.SENSOR_DELAY_NORMAL );

        sensorManagerHum = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManagerHum.registerListener(this, sensorManagerHum.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), sensorManagerHum.SENSOR_DELAY_NORMAL );


        tempText.setText("");
        humidityText.setText("");
        songName.setText("");


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

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartBtn.setBackgroundResource(R.drawable.redheart);
                myList.add(m_getSongName() + "\n");
                Intent intent = new Intent(getApplicationContext(), LikedSongs.class);

                intent.putExtra("mylist",myList);
                startActivity(intent);


                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        heartBtn.setBackgroundResource(R.drawable.heart); //<-- put your code in here.
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 700); // <-- the "1000" is the delay time in miliseconds.
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

        if(myTemp > 100 && myHumidity > 30 ) {
            weatherIcon.setImageResource(R.drawable.hott);
            albumCovers.setImageResource(R.drawable.hotalbum);

            songName.setText("Auga Viva by iZem, Nina Miranda");
            getSongName = "★ Auga Viva by iZem, Nina Miranda";
            //mediaPlayer = MediaPlayer.create(PlaySongs.this, R.raw.hot);
            GlobalVariables.heatIndex = 5;

        }
        else if(myTemp > 80 && myHumidity > 20) {
            weatherIcon.setImageResource(R.drawable.warmm);
            albumCovers.setImageResource(R.drawable.warmalbum);
            songName.setText("Vanille fraise by L’Imperatrice");
            getSongName = "★ Vanille fraise by L’Imperatrice";
            //mediaPlayer =  MediaPlayer.create(PlaySongs.this, R.raw.warm);
            GlobalVariables.heatIndex = 4;

        }
        else if(myTemp > 65 && myHumidity > 10) {
            weatherIcon.setImageResource(R.drawable.chilll);
            albumCovers.setImageResource(R.drawable.chillalbum);
            songName.setText("There Will Be Rain by Million Eyes");
            getSongName = "★ There Will Be Rain by Million Eyes";
            //mediaPlayer =  MediaPlayer.create(PlaySongs.this, R.raw.chill);
            GlobalVariables.heatIndex = 3;

        }
        else if(myTemp > 50 && myHumidity > 0) {
            weatherIcon.setImageResource(R.drawable.coldd);
            albumCovers.setImageResource(R.drawable.coldalbum);
            songName.setText("Your Name by Bernache");
            getSongName = "★ Your Name by Bernache";
            //mediaPlayer =  MediaPlayer.create(PlaySongs.this, R.raw.cold);
            GlobalVariables.heatIndex = 2;

        }
        else if(myTemp < 50 || myHumidity < 0) {
            weatherIcon.setImageResource(R.drawable.freezingg);
            albumCovers.setImageResource(R.drawable.freezingalbum);
            songName.setText("Smile From U. by Jinsang");
            getSongName = "★ Smile From U. by Jinsang";
            //mediaPlayer = MediaPlayer.create(PlaySongs.this, R.raw.freezing);
            GlobalVariables.heatIndex = 1;

        }
        tempText.setText(""+ myTemp + "° F");
        humidityText.setText(""+ myHumidity + "% Humidity");


        if(prevIndex != GlobalVariables.heatIndex)
        {
            changeSong();
        }

        prevIndex = GlobalVariables.heatIndex;
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

    public void changeSong()
    {
        int song = 3;

        if(GlobalVariables.heatIndex == 1)
        {
            song = R.raw.freezing;
        }
        else if(GlobalVariables.heatIndex == 2)
        {
            song = R.raw.cold;
        }
        else if(GlobalVariables.heatIndex == 3)
        {
            song = R.raw.chill;
        }
        else if(GlobalVariables.heatIndex == 4)
        {
            song = R.raw.warm;
        }
        else if(GlobalVariables.heatIndex == 5)
        {
            song = R.raw.hot;
        }

        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            waveVisualizer.release();
        }

        if(mediaPlayer == null){

            mediaPlayer = MediaPlayer.create(PlaySongs.this, song);
            int audioSessionId = mediaPlayer.getAudioSessionId();
            if(audioSessionId != -1){
                waveVisualizer.setAudioSessionId(audioSessionId);

            }

        }
        mediaPlayer.start();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String m_getSongName(){


        return getSongName;

    }


}