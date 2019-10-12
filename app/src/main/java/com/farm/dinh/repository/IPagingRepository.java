package com.farm.dinh.repository;

import com.farm.dinh.data.Result;

public interface IPagingRepository<T> {
    void onSuccess(Result.Success<T> success, int pageTotal);

    void onError(Result.Error error);
}
