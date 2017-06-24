package com.example.ahmed_ayman.weatherforecast;

/**
 * Created by ahmed-ayman on 6/23/17.
 */
import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {

    SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity(){
        return prefs.getString("city", "cairo");
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }

}