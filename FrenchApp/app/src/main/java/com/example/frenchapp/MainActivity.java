package com.example.frenchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void call_voice(View view) {
        Button click_bnt = (Button) view;
        MediaPlayer mp =  MediaPlayer.create(this,
                getResources().getIdentifier(click_bnt.getTag().toString(),"raw", getPackageName()));
        mp.start();
    }
}