package com.farm.dinh.datasource;

import com.farm.dinh.data.Result;
import com.farm.dinh.remote.PagingResponse;
import com.farm.dinh.remote.RemoteService;
import com.farm.dinh.remote.SimpleResponse;
import com.farm.dinh.remote.StandardResponse;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.IRepository;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DataSource<S> {

    public abstract Class<S> getServiceType();

    public final S getRemoteService() {
        return RemoteService.getService().create(getServiceType());
    }

    protected  <T> Callback<StandardResponse<T>> getStandardCallBack(final IRepository<T> listener) {
        return new Callback<StandardResponse<T>>() {
            @Override
            public void onResponse(Call<StandardResponse<T>> call, Response<StandardResponse<T>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<>(response.body().getData()));
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<T>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        };
    }

    protected <T> Callback<PagingResponse<T>> getPagingCallBack(final IPagingRepository<T> listener) {
        return new Callback<PagingResponse<T>>() {
            @Override
            public void onResponse(Call<PagingResponse<T>> call, Response<PagingResponse<T>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<>(response.body().getData()), response.body().getTotalPage());
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<PagingResponse<T>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        };
    }

    protected Callback<SimpleResponse> getSimpleCallBack(final IRepository<String> listener) {
        return new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<String>(response.body().getMessage()));
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        };
    }
}