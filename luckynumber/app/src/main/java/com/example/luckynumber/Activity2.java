package com.example.luckynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Activity2 extends AppCompatActivity {
      TextView tx,tx2;
      Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticvity2);

        tx = findViewById(R.id.textView2);
        tx2 = findViewById(R.id.textView3);
        bt = findViewById(R.id.button2);

       Intent i = getIntent();
       String usrname = i.getStringExtra("name");
        //Toast.makeText(Activity2.this,"welcome "+usrname,Toast.LENGTH_SHORT).show();
       int value = generator();
        tx2.setText(""+value);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharing(usrname,value);
            }
        });

    }
    public int  generator(){
        Random random = new Random();
        return random.nextInt(1000);
    }

    public void sharing(String usrname,int num){
        String number = Integer.toString(num);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT,usrname + " got lucky today!");
        i.putExtra(Intent.EXTRA_TEXT,"His lucky number is "+number);

        startActivity(Intent.createChooser(i,"Choose your app" ));

    }
}

