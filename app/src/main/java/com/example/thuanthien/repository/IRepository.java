package com.example.thuanthien.repository;

import com.example.thuanthien.data.Result;

public interface IRepository<T> {
    void onSuccess(Result.Success<T> success);

    void onError(Result.Error error);
}
