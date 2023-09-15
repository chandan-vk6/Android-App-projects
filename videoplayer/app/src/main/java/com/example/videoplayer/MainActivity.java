package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

   // VideoView vw ;
    VideoView vw2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vw2 = findViewById(R.id.videoView1);
       /* vw = findViewById(R.id.videoView);
        vw.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.mountains);
        MediaController mc = new MediaController(this);

        mc.setAnchorView(vw);
        vw.setMediaController(mc);*/






        Uri uri = Uri.parse("http://techslides.com/demos/sample-videos/small.mp4");
        vw2.setVideoURI(uri);
        MediaController mc2 = new MediaController(this);

        mc2.setAnchorView(vw2);
        mc2.setMediaPlayer(vw2);
        vw2.setMediaController(mc2);
        vw2.start();


    }
}