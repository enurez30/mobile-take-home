package com.straus.airports.datasource;

import com.straus.airports.datasource.daoHelper.DaoSessionHolder;
import com.straus.airports.objects.Airport;
import com.straus.airports.objects.AirportDao;

import java.util.List;

public class AirportDataSource extends AbstractDataSource<Airport, Long> {

    private static AirportDataSource instance = null;

    public static AirportDataSource getInstance() {
        if (instance == null) {
            synchronized (AirportDataSource.class) {
                if (instance == null) {
                    instance = new AirportDataSource();
                }
            }
        }
        return instance;
    }

    private AirportDataSource() {
        super(DaoSessionHolder.getDaoSession().getAirportDao());
    }

    public List<Airport> getAirportsList(String s) {
        if (s.length() <= 3) {
            return this.queryBuilder().where(AirportDao.Properties.IATA.like("%" + s + "%")).list();
        } else {
            return this.queryBuilder().where(AirportDao.Properties.City.like("%" + s + "%")).list();
        }

    }

}
