package com.example.xmlandjson;

public class Citys {
    private String city_name;
    private double latitude;
    private double langitude;
    private int Temperature;
    private String Humidy;


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }

    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public String getHumidy() {
        return Humidy;
    }

    public void setHumidy(String humidy) {
        Humidy = humidy;
    }
}
