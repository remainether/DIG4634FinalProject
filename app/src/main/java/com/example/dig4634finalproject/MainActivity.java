package com.example.dig4634finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Button btn = (Button)findViewById(R.id.playMusicButton);
            ImageView bg= (ImageView)findViewById(R.id.mainbg);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
            bg.startAnimation(myFadeInAnimation);
            Animation half_down_fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.half_down_fade);
            btn.startAnimation(half_down_fade);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PlaySongs.class));
                }
            });



        }



}

