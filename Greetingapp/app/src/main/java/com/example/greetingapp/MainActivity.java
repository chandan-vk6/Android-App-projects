package com.example.greetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tx;
    Button bt;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.textview);
        bt = findViewById(R.id.button);
        et = findViewById(R.id.editText);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et.getText().toString();
                Toast.makeText(MainActivity.this,"Hello "+name, Toast.LENGTH_LONG).show();
            }
        });

    }
}

