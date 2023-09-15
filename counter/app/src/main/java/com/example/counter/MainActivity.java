package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int counter =0;
    TextView tx;
    Button start,stop;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tx = findViewById(R.id.textView);
          start = findViewById(R.id.start);
          stop = findViewById(R.id.stop);
         start.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 handler.postDelayed(GetCounter,100);
             }
         });
    }


   private  Runnable GetCounter = new Runnable(){
        @Override
        public void run() {
            try {
                ++counter;
                tx.setText(String.valueOf(counter));

                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.removeCallbacks(GetCounter);
                    }
                });
                handler.postDelayed(this,1000);
            }catch (Exception e){
                System.out.print(" Exception is caught ");
            }

        }


   };
}