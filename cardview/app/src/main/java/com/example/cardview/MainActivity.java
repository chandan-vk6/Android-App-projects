package com.example.cardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Data
    public ArrayList<GameModel> gamelist;

    //Adapterview

    RecyclerView recyclerView;

   //Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dataset();
        intialset();


    }

    private void intialset() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        GameAdapter adapter = new GameAdapter(this,gamelist);
        recyclerView.setAdapter(adapter);

    }

    private void Dataset() {
        gamelist = new ArrayList<>();
        gamelist.add(new GameModel("Car race",R.drawable.card1));
        gamelist.add(new GameModel("Pubg race",R.drawable.card2));
        gamelist.add(new GameModel("Head bolly",R.drawable.card3));
        gamelist.add(new GameModel("hooked",R.drawable.card4));
        gamelist.add(new GameModel("fifa",R.drawable.card5));
    }
}