package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
// ...

    EditText et;
    Button bt;
    TextView tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference("goldPrice").child("price");

        et = findViewById(R.id.editText);
        bt = findViewById(R.id.button);
        tx = findViewById(R.id.textView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mDatabase.setValue(et.getText().toString());
                Intent i = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(i);
            }

        });
       mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
              // tx.setText(snapshot.getValue().toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}