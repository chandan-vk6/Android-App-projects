package com.example.login;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<DataModel> data ;

    public Adapter(Context context, ArrayList<DataModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          DataModel model = data.get(position);

          holder.uname.setText(model.getUsername());
          holder.pass.setText(model.getPassword());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
         TextView uname,pass;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            uname = itemView.findViewById(R.id.textView2);
            pass = itemView.findViewById(R.id.text);
        }
    }
}
