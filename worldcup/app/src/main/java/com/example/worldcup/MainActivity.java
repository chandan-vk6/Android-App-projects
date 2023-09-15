package com.example.worldcup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
       RecyclerView rw;

       //data source
    CountryModel[] model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
///        lw = findViewById(R.id.listview);
        //Adapter

         setupdata();
         setuplist();

   //     lw.setAdapter(adapter);


    }

    public void  setupdata(){
     model = new CountryModel[]{new CountryModel("brazil","2",R.drawable.brazil),
                                new CountryModel("spain","4",R.drawable.spain),
             new CountryModel("uk","4",R.drawable.unitedkingdom),
             new CountryModel("us","4",R.drawable.unitedstates),
             new CountryModel("uae","4",R.drawable.saudiarabia)};
    }
    public void setuplist(){
        rw = findViewById(R.id.recycler);

        CustomAdapter adapter = new CustomAdapter(model);
        rw.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rw.setAdapter(adapter);

    }
}