package com.farm.dinh.datasource;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.repository.IRepository;

import java.util.List;

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

    public void getOrderHistory(int userId, final IRepository<List<Order>> listener) {
        getApiInterface().getOrderHistory(userId).enqueue(new Callback<APIResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Order>>> call, Response<APIResponse<List<Order>>> response) {
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
            public void onFailure(Call<APIResponse<List<Order>>> call, Throwable t) {
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getProductsList(final IRepository<List<Product>> listener) {
        getApiInterface().getProductsList().enqueue(new Callback<APIResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Product>>> call, Response<APIResponse<List<Product>>> response) {
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
            public void onFailure(Call<APIResponse<List<Product>>> call, Throwable t) {
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void createOrder(int agencyId, String phone, String quantity, String productId, final IRepository<String> listener) {
        getApiInterface().createOrder(agencyId, phone, quantity, productId).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
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
            public void onFailure(Call<APIResponse> call, Throwable t) {
                listener.onError(new Result.Error(new Exception(t)));
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
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void createUser(int userId, String phone, String name, String street, String ward, String district, String city, final IRepository<FarmerInfo> listener) {
        getApiInterface().createUser(userId, phone, name, street, ward, district, city).enqueue(new Callback<APIResponse<FarmerInfo>>() {
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
                listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }
}
