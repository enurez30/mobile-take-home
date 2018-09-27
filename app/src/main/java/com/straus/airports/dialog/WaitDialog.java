package com.straus.airports.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.straus.airports.R;

public class WaitDialog extends Dialog {

    private CamomileSpinner camomileSpinner;

    public WaitDialog(Context context) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        setContentView(R.layout.wait_dialog_small);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setCancelable(true);

        startAnimateLoading();
    }


    private void startAnimateLoading() {

        camomileSpinner = findViewById(R.id.camomileSpinner);
        camomileSpinner.start();

    }
}
