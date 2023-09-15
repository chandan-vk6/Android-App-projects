package com.example.callingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callingapp.MainActivity;
import com.example.callingapp.R;
import com.example.callingapp.db.entity.Contacts;

import java.util.ArrayList;

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    ArrayList<Contacts> contactlist ;
    MainActivity mainActivity;

        public Adapter(Context context, ArrayList<Contacts> contactlist, MainActivity mainActivity) {
            this.context = context;
            this.contactlist = contactlist;
            this.mainActivity = mainActivity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return  new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
              Contacts contact = contactlist.get(position);

              holder.name.setText(contact.getName());
              holder.email.setText(contact.getEmail());
              holder.img.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      mainActivity.call(contact,position);
                  }
              });
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      mainActivity.editandsave(true,contact,position);
                  }
              });
        }

        @Override
        public int getItemCount() {
            return contactlist.size();
        }

        public class ViewHolder  extends RecyclerView.ViewHolder{
         private TextView name;
         private TextView email;
         private ImageButton img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tx1);
            this.email = itemView.findViewById(R.id.tx2);
            this.img = itemView.findViewById(R.id.imgbt);
        }
    }



}
