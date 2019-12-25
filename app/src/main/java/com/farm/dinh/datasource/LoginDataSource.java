package com.farm.dinh.datasource;


import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.repository.IRepository;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource extends DataSource {

    public void login(String phone, String password, final IRepository<UserInfo> listener) {
        getApiInterface().login(phone, password).enqueue(new Callback<APIResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<APIResponse<UserInfo>> call, Response<APIResponse<UserInfo>> response) {
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
            public void onFailure(Call<APIResponse<UserInfo>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getUserInfo(int userId, final IRepository<UserInfo> listener) {
        getApiInterface().getUserInfo(userId).enqueue(new Callback<APIResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<APIResponse<UserInfo>> call, Response<APIResponse<UserInfo>> response) {
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
            public void onFailure(Call<APIResponse<UserInfo>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void updateUserInfo(int userId, String name, String district, String street, String ward, String city, String area, String old_password, String new_password, final IRepository<UserInfo> listener) {
        getApiInterface().updateUserInfo(userId, name, district, street, ward, city, area, old_password, new_password).enqueue(new Callback<APIResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<APIResponse<UserInfo>> call, Response<APIResponse<UserInfo>> response) {
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
            public void onFailure(Call<APIResponse<UserInfo>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getAddress(final IRepository<List<City>> listener) {
        getApiInterface().getAddress().enqueue(new Callback<APIResponse<List<City>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<City>>> call, Response<APIResponse<List<City>>> response) {
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
            public void onFailure(Call<APIResponse<List<City>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void createUser(int userId, String phone, String name, String street, String ward, String district, String city, String area, final IRepository<FarmerInfo> listener) {
        getApiInterface().createUser(userId, phone, name, street, ward, district, city, area).enqueue(new Callback<APIResponse<FarmerInfo>>() {
            @Override
            public void onResponse(Call<APIResponse<FarmerInfo>> call, Response<APIResponse<FarmerInfo>> response) {
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
            public void onFailure(Call<APIResponse<FarmerInfo>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void editUser(int userId, int farmerId, String phone, String name, String street, String ward, String district, String city, String area, final IRepository<FarmerInfo> listener) {
        getApiInterface().editUser(userId, farmerId, phone, name, street, ward, district, city, area).enqueue(new Callback<APIResponse<FarmerInfo>>() {
            @Override
            public void onResponse(Call<APIResponse<FarmerInfo>> call, Response<APIResponse<FarmerInfo>> response) {
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
            public void onFailure(Call<APIResponse<FarmerInfo>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
