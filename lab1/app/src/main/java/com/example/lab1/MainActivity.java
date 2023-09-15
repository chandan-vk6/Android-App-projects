package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView tx;
    ImageView img;
    Button bt;
    Animation anim,animat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tx = findViewById(R.id.tx1);
         img = findViewById(R.id.img);
         bt = findViewById(R.id.btn);

         anim = AnimationUtils.loadAnimation(this,R.anim.text_anime);

         tx.setAnimation(anim);
         img.setAnimation(anim);

         animat  = AnimationUtils.loadAnimation(this,R.anim.buttom_anim);
          bt.setAnimation(animat);

    }
}