package com.farm.dinh.datasource;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.IRepository;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

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
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
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
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getOrderHistory(int userId, int page, final IPagingRepository<List<Order>> listener) {
        getApiInterface().getOrderHistory(userId, page).enqueue(new Callback<APIResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Order>>> call, Response<APIResponse<List<Order>>> response) {
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
            public void onFailure(Call<APIResponse<List<Order>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void searchOrders(int userId, String searchKey, int page, final IPagingRepository<List<Order>> listener) {
        getApiInterface().searchOrders(userId, searchKey, page).enqueue(new Callback<APIResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Order>>> call, Response<APIResponse<List<Order>>> response) {
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
            public void onFailure(Call<APIResponse<List<Order>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getFarmersList(int userId, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getApiInterface().getFarmersList(userId, page).enqueue(new Callback<APIResponse<List<FarmerInfo>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<FarmerInfo>>> call, Response<APIResponse<List<FarmerInfo>>> response) {
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
            public void onFailure(Call<APIResponse<List<FarmerInfo>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void searchFarmers(int userId, String searchKey, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getApiInterface().searchFarmers(userId, searchKey, page).enqueue(new Callback<APIResponse<List<FarmerInfo>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<FarmerInfo>>> call, Response<APIResponse<List<FarmerInfo>>> response) {
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
            public void onFailure(Call<APIResponse<List<FarmerInfo>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
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
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void createOrder(Order order, final IRepository<String> listener) {
        getApiInterface().createOrder(order).enqueue(new Callback<APIResponse>() {
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
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void editOrder(Order order, final IRepository<String> listener) {
        getApiInterface().editOrder(order).enqueue(new Callback<APIResponse>() {
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
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getTreesByFarmer(int farmerId, final IRepository<List<TreeInfo>> listener) {
        getApiInterface().getTreesByFarmer(farmerId).enqueue(new Callback<APIResponse<List<TreeInfo>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<TreeInfo>>> call, Response<APIResponse<List<TreeInfo>>> response) {
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
            public void onFailure(Call<APIResponse<List<TreeInfo>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void getTreesList(final IRepository<List<Tree>> listener) {
        getApiInterface().getTreesList().enqueue(new Callback<APIResponse<List<Tree>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Tree>>> call, Response<APIResponse<List<Tree>>> response) {
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
            public void onFailure(Call<APIResponse<List<Tree>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void addTree(int farmerId, int treeId, String age, int amount, final IRepository<String> listener) {
        getApiInterface().addTree(farmerId, treeId, age, amount).enqueue(new Callback<APIResponse<List<TreeInfo>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<TreeInfo>>> call, Response<APIResponse<List<TreeInfo>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<>(response.body().getMessage()));
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<TreeInfo>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }

    public void editTree(int id, int treeId, String age, int amount, final IRepository<String> listener) {
        getApiInterface().editTree(id, treeId, age, amount).enqueue(new Callback<APIResponse<List<TreeInfo>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<TreeInfo>>> call, Response<APIResponse<List<TreeInfo>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listener.onSuccess(new Result.Success<>(response.body().getMessage()));
                    } else {
                        listener.onError(new Result.Error(new Exception(response.body().getMessage())));
                    }
                } else {
                    listener.onError(new Result.Error(new Exception(response.message())));
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<TreeInfo>>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    listener.onError(new Result.Error(new Exception("Lỗi mạng, vui lòng thử lại sau.", t)));
                } else listener.onError(new Result.Error(new Exception(t)));
            }
        });
    }
}
