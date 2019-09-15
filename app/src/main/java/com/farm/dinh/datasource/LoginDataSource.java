package com.farm.dinh.datasource;


import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.repository.IRepository;

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
                listener.onError(new Result.Error(new Exception(t)));
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
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void updateUserInfo(int userId, String name, String old_password, String new_password, final IRepository<UserInfo> listener) {
        getApiInterface().updateUserInfo(userId, name, old_password, new_password).enqueue(new Callback<APIResponse<UserInfo>>() {
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
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
