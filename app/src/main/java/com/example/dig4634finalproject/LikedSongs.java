package com.example.dig4634finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LikedSongs extends AppCompatActivity {


    TextView text;

    String listString;

    ArrayList<String> myList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_songs);

        Intent intent = getIntent();
        //text.setText(getIntent().getStringExtra("mytext"));
        text = findViewById(R.id.textView);
        text.setText("");
        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        for(int i = 0; i < myList.size(); i++) {
            text.append(myList.get(i));
        }



    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}