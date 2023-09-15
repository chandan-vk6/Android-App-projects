package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
        EditText et;
        Button bt;
        TextToSpeech speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         et = findViewById(R.id.editText);
         bt = findViewById(R.id.button);

         speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
             @Override
             public void onInit(int i) {
                 if(i!=TextToSpeech.ERROR){

                 }
             }
         });
        speech.setLanguage(Locale.ENGLISH);
         bt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 speech.speak(et.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

             }

         });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        speech.stop();
    }
}