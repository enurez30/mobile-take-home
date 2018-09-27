package com.straus.airports.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.straus.airports.AirportsApplication;
import com.straus.airports.datasource.AirportDataSource;
import com.straus.airports.datasource.RouteDataSource;
import com.straus.airports.dialog.WaitDialog;
import com.straus.airports.fragment.MapFragment;
import com.straus.airports.objects.Airport;
import com.straus.airports.objects.AirportDao;
import com.straus.airports.objects.Route;
import com.straus.airports.objects.RouteDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteFindTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private MapFragment mapFragment;
    private Airport origin;
    private Airport destination;
    private WaitDialog waitDialog;

    public RouteFindTask(Context context, MapFragment mapFragment, Airport origin, Airport destination) {
        this.context = context;
        this.mapFragment = mapFragment;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waitDialog = new WaitDialog(context);
        waitDialog.setCancelable(false);
        waitDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (RouteDataSource.getInstance().getDirectConnection(origin.getIATA(), destination.getIATA())) {
            directConnection();
        } else {
            tripWithConnections(RouteDataSource.getInstance().tt(origin.getIATA(), destination.getIATA()));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        waitDialog.dismiss();

    }

    private void directConnection() {
        AirportsApplication.getInstance().getMasterActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mapFragment.executePath();
            }
        });
    }

    private void tripWithConnections(HashMap<Integer, String> map) {
        List<String> IATAlist = new ArrayList<>();
        List<Airport> airports = new ArrayList<>();
        if (map == null) {
            AirportsApplication.getInstance().getMasterActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mapFragment.executeMultiplePath(null);
                }
            });
            return;
        }

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            IATAlist.add(entry.getValue());
        }

        Collections.reverse(IATAlist);

        for (String s : IATAlist) {
            try {
                airports.add(AirportDataSource.getInstance().queryBuilder().where(AirportDao.Properties.IATA.eq(s)).unique());
            } catch (Exception e) {
                airports = null;
                break;
            }
        }


        final List<Airport> finalAirports = airports;
        AirportsApplication.getInstance().getMasterActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mapFragment.executeMultiplePath(finalAirports);
            }
        });
    }
}
