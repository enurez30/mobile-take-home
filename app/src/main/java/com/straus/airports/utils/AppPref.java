package com.straus.airports.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.straus.airports.AirportsApplication;

public class AppPref {

    private static SharedPreferences getSharedPrefs() {

        return AirportsApplication.getInstance().getSharedPreferences(" com.straus.airports", Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getPrefsEditor() {
        return getSharedPrefs().edit();
    }

    public static void setFirstTime(boolean status){
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putBoolean("firstTime", status).apply();
    }

    public static boolean isFirstTime(){
        return getSharedPrefs().getBoolean("firstTime", true);
    }


}
