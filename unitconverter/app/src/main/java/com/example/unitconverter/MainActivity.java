package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tx,tx2;
    Button bt;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tx = findViewById(R.id.textView);
        tx2 = findViewById(R.id.textview2);
        bt = findViewById(R.id.button);
        et = findViewById(R.id.editText);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double unit = Double.parseDouble(et.getText().toString());
                tx2.setText(""+unit_pound(unit)+"lb");
            }
        });
    }

    public double unit_pound(double unit ){
        return unit * 2.20462;
    }
}