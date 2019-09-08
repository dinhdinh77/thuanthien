package com.example.thuanthien.repository;

import com.example.thuanthien.datasource.DataSource;

public abstract class Repository<D extends DataSource> {
    private D dataSource;

    public Repository(D dataSource) {
        this.dataSource = dataSource;
    }

    public D getDataSource() {
        return dataSource;
    }
}
