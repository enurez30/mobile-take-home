package com.straus.airports.datasource;

import android.util.Log;

import com.straus.airports.datasource.daoHelper.DaoSessionHolder;
import com.straus.airports.objects.Airline;
import com.straus.airports.objects.Airport;
import com.straus.airports.objects.Route;
import com.straus.airports.objects.RouteDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteDataSource extends AbstractDataSource<Route, Long> {
    HashMap<Integer, String> map = new HashMap<>();
    int count = 0;
    String originIATA;
    private static RouteDataSource instance = null;
    List<Route> inspectionList = new ArrayList<>();
    List<Route> secondList = new ArrayList<>();

    public static RouteDataSource getInstance() {
        if (instance == null) {
            synchronized (RouteDataSource.class) {
                if (instance == null) {
                    instance = new RouteDataSource();
                }
            }
        }
        return instance;
    }

    private RouteDataSource() {
        super(DaoSessionHolder.getDaoSession().getRouteDao());
    }

    public boolean getDirectConnection(String originIATA, String destinationIATA) {
        return this.queryBuilder()
                .where(RouteDao.Properties.Origin.eq(originIATA))
                .where(RouteDao.Properties.Destination.eq(destinationIATA)).list().size() > 0;
    }

    public HashMap<Integer, String> tt(String originIATA, String destinationIATA) {
        map.clear();
        this.originIATA = originIATA;
        map.put(count, destinationIATA);
        inspectionList = this.queryBuilder().where(RouteDao.Properties.Destination.eq(destinationIATA)).list();

        for (int i = 0; i < inspectionList.size(); i++) {
            if (step(inspectionList.get(i).getOrigin())) {
                // exit
                count++;
                map.put(count, inspectionList.get(i).getDestination());
                return map;
            }
        }
        for (int i = 0; i < inspectionList.size(); i++) {
             secondList = this.queryBuilder().where(RouteDao.Properties.Destination.eq(inspectionList.get(i).getOrigin())).list();
            for (int j = 0; j < secondList.size(); j++) {
                if (step(secondList.get(j).getOrigin())) {
                    // exit
                    count++;
                    map.put(count, secondList.get(j).getDestination());
                    return map;
                }
            }
        }

        for (int i = 0; i <secondList.size() ; i++) {
            List<Route>list = this.queryBuilder().where(RouteDao.Properties.Destination.eq(secondList.get(i).getOrigin())).list();
            for (int j = 0; j < list.size(); j++) {
                if (step(list.get(j).getOrigin())) {
                    // exit
                    count++;
                    map.put(count, list.get(j).getDestination());
                    return map;
                }
            }
        }

        return null;
    }

    private boolean step(String IATA) {
        return IATA.equals(originIATA);
    }

}
