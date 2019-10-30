package com.farm.dinh.repository;

import androidx.collection.SimpleArrayMap;

import com.farm.dinh.datasource.UserDataSource;
import com.farm.dinh.datasource.DataSource;
import com.farm.dinh.datasource.AuthenticationDataSource;
import com.farm.dinh.datasource.MainDataSource;

public class Repository {
    private static SimpleArrayMap<Class, DataSource> mapDataSource = new SimpleArrayMap<>();

    static {
        mapDataSource.put(MainDataSource.class, new MainDataSource());
        mapDataSource.put(AuthenticationDataSource.class, new AuthenticationDataSource());
        mapDataSource.put(UserDataSource.class, new UserDataSource());
    }

    protected final static <D extends DataSource> D getDataSource(Class<D> clazz) {
        return (D) mapDataSource.get(clazz);
    }
}
