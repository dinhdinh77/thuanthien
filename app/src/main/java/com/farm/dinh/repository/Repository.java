package com.farm.dinh.repository;

import com.farm.dinh.datasource.DataSource;

public abstract class Repository<D extends DataSource> {
    private D dataSource;

    public Repository(D dataSource) {
        this.dataSource = dataSource;
    }

    public D getDataSource() {
        return dataSource;
    }
}
