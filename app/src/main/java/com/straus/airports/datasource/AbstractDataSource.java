package com.straus.airports.datasource;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public abstract class AbstractDataSource<T,K> {

    private AbstractDao<T, K> mDao;

    AbstractDataSource(AbstractDao<T, K> dao){
        this.mDao = dao;
    }

    public Property[] getProperties() {
        return mDao.getProperties();
    }

    public T load(K key) {
        return mDao.load(key);
    }

    public List<T> loadAll() {
        return mDao.loadAll();
    }

    public void insertInTx(Iterable<T> entities) {
        mDao.insertInTx(entities);
    }

    public void insertOrReplaceInTx(Iterable<T> entities) {
        mDao.insertOrReplaceInTx(entities);
    }

    public long insert(T entity) {
        return mDao.insert(entity);
    }

    public long insertOrReplace(T entity) {
        return mDao.insertOrReplace(entity);
    }

    public void save(T entity) {
        mDao.save(entity);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }

    public void delete(T entity) {
        mDao.delete(entity);
    }

    public void deleteInTx(Iterable<T> entities) {
        mDao.deleteInTx(entities);
    }

    public void update(T entity) {
        mDao.update(entity);
    }

    public QueryBuilder<T> queryBuilder() {
        return mDao.queryBuilder();
    }

    public void updateInTx(Iterable<T> entities) {
        mDao.updateInTx(entities);
    }

    public long count() {
        return mDao.count();
    }

    public Database getDatabase() {
        return mDao.getDatabase();
    }

}
