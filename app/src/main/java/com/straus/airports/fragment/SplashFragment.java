package com.straus.airports.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.straus.airports.R;
import com.straus.airports.activity.MasterActivity;
import com.straus.airports.dialog.WaitDialog;
import com.straus.airports.utils.AppPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private MasterActivity masterActivity;
    private com.gmail.samehadar.iosdialog.CamomileSpinner spinner;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        masterActivity = (MasterActivity) getActivity();
        spinner = v.findViewById(R.id.camomileSpinner);
        spinner.start();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        masterActivity.executeSCVTask();
    }

}
