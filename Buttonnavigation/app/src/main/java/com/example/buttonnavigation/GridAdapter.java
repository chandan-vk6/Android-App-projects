package com.example.buttonnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends ArrayAdapter<Gridmodel> {
    Context context;
    ArrayList<Gridmodel> modellist = new ArrayList<Gridmodel>();


    public GridAdapter(Context context,ArrayList<Gridmodel> modellist){
        super(context,0,modellist);
        this.context = context;
        this.modellist = modellist;
    }

    public static class ViewHolder{
        TextView name;
        ImageView image;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getitem data for position
        Gridmodel model = getItem(position);
           ViewHolder  viewholder;
        //check reuse and inflate the view
        final View result;
        if(convertView == null){
              viewholder = new  ViewHolder();
             convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
             viewholder.name = convertView.findViewById(R.id.name);
             viewholder.image = convertView.findViewById(R.id.image);
             result = convertView;
             convertView.setTag(viewholder);
        }
        else{
            viewholder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewholder.name.setText(model.getName());
        viewholder.image.setImageResource(model.getImg());
        return convertView;
    }
}

