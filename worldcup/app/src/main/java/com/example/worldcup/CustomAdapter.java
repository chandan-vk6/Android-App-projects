package com.example.worldcup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyviewHolder> {

    CountryModel[] modelslist;

    public CustomAdapter(CountryModel[] modelslist) {
        this.modelslist = modelslist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        View datalist = inflator.inflate(R.layout.my_list_view,parent,false);
        return new MyviewHolder(datalist);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, @SuppressLint("RecyclerView") int position) {
         final CountryModel model = modelslist[position];
        holder.country_text.setText(modelslist[position].getCountry_text());
        holder.win_text.setText(modelslist[position].getWin_text());
        holder.img.setImageResource(modelslist[position].getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        view.getContext(), "You clicked" + modelslist[position].getCountry_text(),Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelslist.length ;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder

    {

        public TextView country_text, win_text;
        public ImageView img;

        public MyviewHolder(@NonNull View itemView) {
        super(itemView);
        this.country_text = itemView.findViewById(R.id.textView);
        this.win_text = itemView.findViewById(R.id.textview2);
        this.img = itemView.findViewById(R.id.imageView2);
        }

    }


}
