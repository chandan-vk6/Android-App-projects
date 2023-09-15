package com.example.xmlandjson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Parser {
    public ArrayList<Citys> citylist = new ArrayList<Citys>();
    Citys city;
    String text;

    public  ArrayList<Citys> parsexml(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlfactory =  XmlPullParserFactory.newInstance();
        XmlPullParser xmlparser = xmlfactory.newPullParser();
        xmlparser.setInput(is,null);

        int eventype = xmlparser.getEventType();
        while(eventype!=XmlPullParser.END_DOCUMENT){
            String name = xmlparser.getName();

            switch (eventype){
                case XmlPullParser.START_TAG:
                    if(name.equalsIgnoreCase("city")){
                         city = new Citys();
                    }
                    break;
                case XmlPullParser.TEXT:
                      text = xmlparser.getText();
                     break;

                case XmlPullParser.END_TAG:
                    if(name.equalsIgnoreCase("city")){
                        citylist.add(city);
                    } else if (name.equalsIgnoreCase("city_name")) {
                        city.setCity_name(text);
                    } else if (name.equalsIgnoreCase("latitude")) {
                        city.setLatitude(Double.parseDouble(text));
                    } else if (name.equalsIgnoreCase("langitude")) {
                        city.setLangitude(Double.parseDouble(text));
                    } else if (name.equalsIgnoreCase("Temperature")) {
                        city.setTemperature(Integer.valueOf(text));
                    } else if (name.equalsIgnoreCase("Humidity")) {
                        city.setHumidy(text);

                    }
                    break;
                default:
                    break;
            }
         eventype = xmlparser.next();
        }
        return citylist;
    }

    public Object parsejson(InputStream is){

        JSONObject object;
        String json = null;
        try {

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
          object = new JSONObject(json).getJSONObject("city");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

}
