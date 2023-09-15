package com.example.worldcup;

public class CountryModel {

   private String country_text,win_text;
   private int image;

    public CountryModel(String country_text, String win_text, int image) {
        this.country_text = country_text;
        this.win_text = win_text;
        this.image = image;
    }

    public void setCountry_text(String country_text) {
        this.country_text = country_text;
    }

    public void setWin_text(String win_text) {
        this.win_text = win_text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCountry_text() {
        return country_text;
    }

    public String getWin_text() {
        return win_text;
    }

    public int getImage() {
        return image;
    }
}
