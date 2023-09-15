package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
private DatabaseReference mdatabase;

ArrayList<DataModel> list = new ArrayList<DataModel>();
EditText name,pass;
Button bt;
DataModel data;
Integer counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = findViewById(R.id.editText2);
        pass = findViewById(R.id.edittext);
        bt = findViewById(R.id.button2);

      mdatabase = FirebaseDatabase.getInstance().getReference("Users");
     /* list.add(new DataModel("rock","kbhj65456"));
        list.add(new DataModel("rock","kbhj65456"));
        list.add(new DataModel("rock","kbhj65456"));
        list.add(new DataModel("rock","kbhj65456")); */
      bt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              data = new DataModel(name.getText().toString(),pass.getText().toString());
              mdatabase.setValue(data);
             /* Intent i = new Intent(getApplicationContext(),MainActivity3.class);
              startActivity(i);*/
          }
      });
    }
}