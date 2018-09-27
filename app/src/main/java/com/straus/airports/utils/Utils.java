package com.straus.airports.utils;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.straus.airports.AirportsApplication;

public class Utils {
    public static void forceCloseKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) AirportsApplication.getInstance().getMasterActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        AirportsApplication.getInstance().getMasterActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void forceOpenKeyboard() {
        ((InputMethodManager) AirportsApplication.getInstance().getMasterActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
