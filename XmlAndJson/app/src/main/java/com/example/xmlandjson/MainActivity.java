package com.example.xmlandjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8, tx9, tx10;

    ArrayList<Citys> citylist = null;
    String json = null;
    JSONObject object;
    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx1 = findViewById(R.id.textView1);
                tx2 = findViewById(R.id.textView2);
                tx3 = findViewById(R.id.textView3);
                tx4 = findViewById(R.id.textView4);
                tx5 = findViewById(R.id.textView5);


                Parser parser = new Parser();
                InputStream is = null;
                try {
                    is = getAssets().open("info.xml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    citylist = parser.parsexml(is);
                } catch (XmlPullParserException | IOException e) {
                    throw new RuntimeException(e);
                }

                tx1.setText("city_name: " + citylist.get(0).getCity_name());
                tx2.setText(String.valueOf("latitude: " + citylist.get(0).getLatitude()));
                tx3.setText(String.valueOf("longitude: " + citylist.get(0).getLangitude()));
                tx4.setText(String.valueOf("Temperature: " + citylist.get(0).getTemperature()));
                tx5.setText("Humidity: " + citylist.get(0).getHumidy());
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx6 = findViewById(R.id.textView6);
                tx7 = findViewById(R.id.textView7);
                tx8 = findViewById(R.id.textView8);
                tx9 = findViewById(R.id.textView9);
                tx10 = findViewById(R.id.textView10);

                Parser jsonparser = new Parser();
                InputStream js = null;
                try {
                    js = getAssets().open("info.json");
                    object = (JSONObject) jsonparser.parsejson(js);

                    tx6.setText("city_name: " + object.getString("city_name"));
                    tx7.setText("latitude: " + object.getString("latitude"));
                    tx8.setText("longitude: " + object.getString("longitude"));
                    tx9.setText("Temperature: " + object.getString("Temperature"));
                    tx10.setText("Humidity: " + object.getString("Humidity"));


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        });

    }
}