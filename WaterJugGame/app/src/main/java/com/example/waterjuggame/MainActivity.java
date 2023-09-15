package com.example.waterjuggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private int leftJugWaterAmount = 4;
    private int rightJugWaterAmount = 5;

    private ImageView leftJugImageView;
    private ImageView rightJugImageView;
    private Button pourButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get references to views
        leftJugImageView = findViewById(R.id.left_jug);
        rightJugImageView = findViewById(R.id.right_jug);
        pourButton = findViewById(R.id.pour_button);


                // Set click listener on pour button
                pourButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle pour button click
                        // Calculate pour amount
                        int maxPourAmount = Math.min(leftJugWaterAmount, 10 - rightJugWaterAmount);
                        int pourAmount = Math.min(maxPourAmount, 5);

                        // Update water amounts
                        leftJugWaterAmount -= pourAmount;
                        rightJugWaterAmount += pourAmount;

                        // Update jug images
                        if (leftJugWaterAmount == 0) {
                            leftJugImageView.setImageResource(R.drawable.jug_empty);
                        } else if (leftJugWaterAmount == 5) {
                            leftJugImageView.setImageResource(R.drawable.jug_half);
                        } else {
                            leftJugImageView.setImageResource(R.drawable.jug_full);
                        }

                        if (rightJugWaterAmount == 0) {
                            rightJugImageView.setImageResource(R.drawable.jug_empty);
                        } else if (rightJugWaterAmount == 5) {
                            rightJugImageView.setImageResource(R.drawable.jug_half);
                        } else {
                            rightJugImageView.setImageResource(R.drawable.jug_full);
                        }

                    }
                });
            }

}