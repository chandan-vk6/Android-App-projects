package com.example.buttonnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //adapter view
    GridView gridView;
    ArrayList<Gridmodel> model = new ArrayList<Gridmodel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      gridView = findViewById(R.id.grid_view);
      // data
      model.add(new Gridmodel("car",R.drawable.course1));
      model.add(new Gridmodel("bike",R.drawable.course2));

      //adapter
        GridAdapter adapter = new GridAdapter(getApplicationContext(),model);
        gridView.setAdapter(adapter);
    }
}