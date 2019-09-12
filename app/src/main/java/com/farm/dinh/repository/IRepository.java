package com.farm.dinh.repository;

import com.farm.dinh.data.Result;

public interface IRepository<T> {
    void onSuccess(Result.Success<T> success);

    void onError(Result.Error error);
}
