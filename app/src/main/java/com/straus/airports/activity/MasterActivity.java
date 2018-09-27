package com.straus.airports.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.straus.airports.AirportsApplication;
import com.straus.airports.task.CSVParsingTask;
import com.straus.airports.R;
import com.straus.airports.fragment.MapFragment;
import com.straus.airports.fragment.SplashFragment;
import com.straus.airports.utils.AppPref;
import com.straus.airports.utils.DialogUtils;
import com.straus.airports.utils.Listener;

public class MasterActivity extends AppCompatActivity {

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_master);
        AirportsApplication.getInstance().setMasterActivity(this);
        if (AppPref.isFirstTime()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();
        } else {
            showMapFragment();
        }
    }

    public void executeSCVTask() {
        CSVParsingTask csvParsingTask = new CSVParsingTask(this);
        csvParsingTask.execute();
    }

    public void showMapFragment() {
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
    }

    @Override
    public void onBackPressed() {
        mapFragment.backPressed();
    }

    public void submitExit() {
        DialogUtils.showExitDialog(this, new Listener() {
            @Override
            public void onPositiveActionSelected() {
                finish();
            }

            @Override
            public void onNegativeActionSelected() {
                // nothing
            }
        });
    }
}
