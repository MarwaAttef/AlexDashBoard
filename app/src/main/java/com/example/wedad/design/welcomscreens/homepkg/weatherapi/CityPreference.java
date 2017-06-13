package com.example.wedad.design.welcomscreens.homepkg.weatherapi;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Marwa on 5/30/2017.
 */

public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public String getCity() {
        return prefs.getString("city", "Alexandria, EG");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}
