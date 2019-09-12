package com.farm.dinh.datasource;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.repository.IRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDataSource extends DataSource {
    public void getQuestionsList(int userId, final IRepository<Questions> listener) {
        getApiInterface().getQuestionsList(userId).enqueue(new Callback<APIResponse<Questions>>() {
            @Override
            public void onResponse(Call<APIResponse<Questions>> call, Response<APIResponse<Questions>> response) {
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
            public void onFailure(Call<APIResponse<Questions>> call, Throwable t) {
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void addAnswer(int userId, int questionId, String answer, final IRepository<APIResponse> listener) {
        getApiInterface().addAnswer(userId, questionId, answer).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<>(response.body()));
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
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
}
