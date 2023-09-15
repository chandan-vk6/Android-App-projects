package com.example.cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GameModel> gamelist ;

    public GameAdapter(Context context, ArrayList<GameModel> gamelist) {
        this.context = context;
        this.gamelist = gamelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cell,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            GameModel model = gamelist.get(position);

            holder.game_name.setText(model.getGame_name());
            holder.image.setImageResource(model.getImge());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"This is "+ model.getGame_name(),Toast.LENGTH_SHORT ).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return gamelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView game_name ;
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            game_name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
