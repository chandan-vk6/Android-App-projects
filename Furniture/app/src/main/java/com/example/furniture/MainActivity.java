package com.example.furniture;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  ImageView myImageView = findViewById(R.id.my_image_view);
        //ObjectAnimator animator = ObjectAnimator.ofFloat(myImageView, "scaleY", 1f, 0f);
        //animator.setDuration(1000);
        //animator.start();
        ImageView imageView = findViewById(R.id.my_image_view);
        imageView.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = getResources().getDrawable(R.drawable.mask);
        drawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        imageView.setImageDrawable(drawable);









    }}