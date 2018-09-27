package com.straus.airports;

import android.app.Application;
import android.util.Log;

import com.straus.airports.activity.MasterActivity;

public class AirportsApplication extends Application {

    static AirportsApplication instance;
    private MasterActivity masterActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        AirportsApplication.instance = this;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                //implementation of uncaught exception
                Log.d("", "");
            }
        });
    }

    public static AirportsApplication getInstance() {
        return instance;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }
}
