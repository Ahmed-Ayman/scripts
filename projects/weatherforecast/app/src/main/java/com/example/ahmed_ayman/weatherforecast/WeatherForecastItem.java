package com.example.ahmed_ayman.weatherforecast;

/**
 * Created by ahmed-ayman on 6/23/17.
 */

public class WeatherForecastItem {
    private   String city;
    private double dayTemp;
    private double nighTemp;
    private String humadity;
    private String presuure;
    private String iconDesc;
    private String iconCode;
    private int iconId;
    private String date;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(double dayTemp) {
        this.dayTemp = dayTemp;
    }

    public double getNighTemp() {
        return nighTemp;
    }

    public void setNighTemp(double nighTemp) {
        this.nighTemp = nighTemp;
    }

    public String getHumadity() {
        return humadity;
    }

    public void setHumadity(String humadity) {
        this.humadity = humadity;
    }

    public String getPresuure() {
        return presuure;
    }

    public void setPresuure(String presuure) {
        this.presuure = presuure;
    }

    public String getIconDesc() {
        return iconDesc;
    }

    public void setIconDesc(String iconDesc) {
        this.iconDesc = iconDesc;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
