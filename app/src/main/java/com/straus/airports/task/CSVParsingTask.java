package com.straus.airports.task;

import android.os.AsyncTask;
import android.widget.Toast;

import com.straus.airports.activity.MasterActivity;
import com.straus.airports.datasource.AirlineDataSource;
import com.straus.airports.datasource.AirportDataSource;
import com.straus.airports.datasource.RouteDataSource;
import com.straus.airports.objects.Airline;
import com.straus.airports.objects.Airport;
import com.straus.airports.objects.Route;
import com.straus.airports.utils.AppPref;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CSVParsingTask extends AsyncTask<Void, Void, Void> {

    private MasterActivity masterActivity;

    public CSVParsingTask(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        executeAirlinesCSV();
        executeAirportsCSV();
        executeRoutesCSV();
        return null;
    }

    private void executeAirlinesCSV() {
        InputStreamReader is = null;
        List<Airline> list = new ArrayList<>();
        if (AirlineDataSource.getInstance().count() > 0) {
            AirlineDataSource.getInstance().deleteAll();
        }
        try {
            is = new InputStreamReader(masterActivity.getAssets().open("airlines.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            StringTokenizer st = null;
            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                Airline airline = new Airline();
                airline.setName(st.nextToken());
                airline.setTwoDigitZIPcode(st.nextToken());
                airline.setThreeDigitZIPcode(st.nextToken());
                airline.setCountry(st.nextToken());
                list.add(airline);
            }
            AirlineDataSource.getInstance().insertOrReplaceInTx(list);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(masterActivity, "Error parsing airlines -- moving forward", Toast.LENGTH_SHORT).show();
        }
    }

    private void executeAirportsCSV() {

        InputStreamReader is = null;
        List<Airport> list = new ArrayList<>();
        if (AirportDataSource.getInstance().count() > 0) {
            AirportDataSource.getInstance().deleteAll();
        }
        try {
            is = new InputStreamReader(masterActivity.getAssets().open("airports.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            StringTokenizer st = null;
            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                Airport airport = new Airport();
                airport.setName(st.nextToken());
                airport.setCity(st.nextToken());
                airport.setCountry(st.nextToken());
                airport.setIATA(st.nextToken());
                try {
                    airport.setLatitude(Double.valueOf(st.nextToken()));
                } catch (Exception e) {
                    airport.setLatitude(0.0);
                }
                try {
                    airport.setLongitude(Double.valueOf(st.nextToken()));
                } catch (Exception e) {
                    airport.setLongitude(0.0);
                }
                list.add(airport);
            }
            AirportDataSource.getInstance().insertOrReplaceInTx(list);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(masterActivity, "Error parsing airports -- moving forward", Toast.LENGTH_SHORT).show();
        }
    }

    private void executeRoutesCSV() {

        InputStreamReader is = null;
        List<Route> list = new ArrayList<>();
        if (RouteDataSource.getInstance().count() > 0) {
            RouteDataSource.getInstance().deleteAll();
        }
        try {
            is = new InputStreamReader(masterActivity.getAssets().open("routes.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            StringTokenizer st = null;
            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                Route route = new Route();
                route.setAirlineId(st.nextToken());
                route.setOrigin(st.nextToken());
                route.setDestination(st.nextToken());
                list.add(route);
            }
            RouteDataSource.getInstance().insertOrReplaceInTx(list);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(masterActivity, "Error parsing routes -- moving forward", Toast.LENGTH_SHORT).show();
        }
        AppPref.setFirstTime(false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        masterActivity.showMapFragment();
    }

}
