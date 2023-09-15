package com.example.callingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.callingapp.Adapter.Adapter;
import com.example.callingapp.db.ContactsDatabase;
//import com.example.callingapp.db.DatabaseHelper;
import com.example.callingapp.db.entity.Contacts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private RecyclerView recyle;
    private ArrayList<Contacts> list = new ArrayList<Contacts>();
    private ContactsDatabase contactsdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("welcome to contacts");

        recyle = findViewById(R.id.recyle);
        contactsdb = Room.databaseBuilder(getApplicationContext(), ContactsDatabase.class,"Contacts_db").allowMainThreadQueries().build();


        adapter = new Adapter(this,list,MainActivity.this);
        RecyclerView.LayoutManager  layout = new LinearLayoutManager(getApplicationContext());
        recyle.setLayoutManager(layout);
        recyle.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editandsave(false,null,-1);

            }
        });


    }

    public void editandsave(Boolean isupdated,Contacts contacts,int position){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_licenseplate,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        TextView tex = view.findViewById(R.id.textVS2);
        EditText et1 = view.findViewById(R.id.et1);
        EditText et2 = view.findViewById(R.id.et2);

        tex.setText(!isupdated ? "Add New Contact": "Edit Contact");

        if(isupdated && contacts != null){
            et1.setText(contacts.getName());
            et2.setText(contacts.getEmail());
        }

        builder.setCancelable(false).setPositiveButton(isupdated ? "update" : "save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isupdated){
                            deletecontact(contacts,position);
                        }
                        else{
                            dialogInterface.cancel();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et1.getText().toString())){
                    Toast.makeText(MainActivity.this, "Enter The Name", Toast.LENGTH_SHORT).show();
                    return;

                }
                else{
                    alertDialog.dismiss();
                }
                if(isupdated && contacts != null){
                    updatecontact(et1.getText().toString(),et2.getText().toString(),position);
                }
                else{
                    createcontact(et1.getText().toString(),et2.getText().toString());
                }
            }

        });

    }

    public void  deletecontact(Contacts contacts, int position){
        list.remove(position);
        contactsdb.GetDao().DeleteContact(contacts);
        adapter.notifyDataSetChanged();
    }

    public  void updatecontact(String name,String email,int position){
        Contacts contacts = list.get(position);
        contacts.setName(name);
        contacts.setEmail(email);
        contactsdb.GetDao().UpdateContact(contacts);
        adapter.notifyDataSetChanged();
    }

    public void createcontact(String name, String email){
        long id = contactsdb.GetDao().CreateContact(new Contacts(0,name,email));
        Contacts contacts = contactsdb.GetDao().GetContact(id);

        if(contacts!=null){
            list.add(0,contacts);
        }
       adapter.notifyDataSetChanged();
    }


    public void call(Contacts contacts,int position){
        Uri u = Uri.parse("tel:" + contacts.getEmail());
        Intent i = new Intent(Intent.ACTION_DIAL,u);
        startActivity(i);
    }

    public  void running(){
    ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                list.addAll(contactsdb.GetDao().GetAllContacts());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }

}