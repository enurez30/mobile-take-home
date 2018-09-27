package com.straus.airports.datasource.daoHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.straus.airports.AirportsApplication;
import com.straus.airports.objects.DaoMaster;
import com.straus.airports.objects.DaoSession;

public class DaoSessionHolder {

    private static DaoSession daoSession;

    private static DaoSession createDaoSession(Context context){
        SQLiteOpenHelper helper = new UpgradeHelper(context, "nba_teamTheScore");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        return daoMaster.newSession();
    }

    public static synchronized DaoSession getDaoSession(){
        if(daoSession == null){
            daoSession = createDaoSession(AirportsApplication.getInstance());
        }
        return daoSession;
    }

}
