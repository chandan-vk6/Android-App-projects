package com.example.randomimgchange;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
        View constraint ;
        Handler handler = new Handler();

        Random random = new Random();
        Button bt;
        int[] list ={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraint = findViewById(R.id.layout);
        bt = findViewById(R.id.button);
        bt.setOnClickListener(view -> handler.postDelayed(ChangeBackground,100));
    }

    protected Runnable ChangeBackground = new Runnable() {
        @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
        @Override
        public void run() {
            try {
                int img = random.nextInt(list.length);
                constraint.setBackground(getDrawable(list[img]));
                handler.postDelayed(this,10000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    };
}
