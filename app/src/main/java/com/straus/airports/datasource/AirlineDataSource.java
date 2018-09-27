package com.straus.airports.datasource;

import com.straus.airports.datasource.daoHelper.DaoSessionHolder;
import com.straus.airports.objects.Airline;
import com.straus.airports.objects.Airport;

public class AirlineDataSource extends AbstractDataSource<Airline,Long>{

    private static AirlineDataSource instance = null;

    public static AirlineDataSource getInstance() {
        if (instance == null) {
            synchronized (AirlineDataSource.class) {
                if (instance == null) {
                    instance = new AirlineDataSource();
                }
            }
        }
        return instance;
    }

    private AirlineDataSource() {
        super(DaoSessionHolder.getDaoSession().getAirlineDao());
    }

}
