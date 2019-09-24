package com.farm.dinh.repository;

import android.text.TextUtils;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.datasource.MainDataSource;
import com.farm.dinh.local.Pref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainRepository extends Repository<MainDataSource> {
    private static volatile MainRepository instance;
    private List<Farmer> farmerList;
    private List<City> cityList;

    public MainRepository(MainDataSource dataSource) {
        super(dataSource);
    }

    public static MainRepository getInstance(MainDataSource dataSource) {
        if (instance == null) {
            instance = new MainRepository(dataSource);
        }
        return instance;
    }

    public void getQuestionsList(IRepository<Questions> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getQuestionsList(currUserId, listener);
    }

    public void addAnswer(int questionId, String answer, IRepository<APIResponse> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().addAnswer(currUserId, questionId, answer, listener);
    }

    public void getOrderHistory(IRepository<List<Order>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getOrderHistory(currUserId, listener);
    }

    public void getProductsList(IRepository<List<Product>> listener) {
        getDataSource().getProductsList(listener);
        if (farmerList == null) {
            String json = Pref.getInstance().get(Pref.KEY_FARMERS, "");
            farmerList = new Gson().fromJson(json, new TypeToken<List<Farmer>>() {
            }.getType());
        }
    }

    public void createOrder(String phone, String quantity, String productId, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().createOrder(currUserId, phone, quantity, productId, listener);
    }

    public void createUser(String phone, String name, String street, String ward, String district, String city, IRepository<FarmerInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().createUser(currUserId, phone, name, street, ward, district, city, listener);
    }

    public void getAddress(final IRepository<List<City>> listener) {
        if (cityList != null) {
            if (listener != null)
                listener.onSuccess(new Result.Success(cityList));
        }
        String json = Pref.getInstance().get(Pref.KEY_ADDRESS, "");
        if (TextUtils.isEmpty(json)) {
            getDataSource().getAddress(new IRepository<List<City>>() {
                @Override
                public void onSuccess(Result.Success<List<City>> success) {
                    String citys = new Gson().toJson(success.getData());
                    Pref.getInstance().set(Pref.KEY_ADDRESS, citys);
                    cityList = success.getData();
                    if (listener != null)
                        listener.onSuccess(new Result.Success(cityList));
                }

                @Override
                public void onError(Result.Error error) {
                    if (listener != null)
                        listener.onError(error);
                }
            });
        } else {
            cityList = new Gson().fromJson(json, new TypeToken<List<City>>() {
            }.getType());
            if (listener != null)
                listener.onSuccess(new Result.Success(cityList));
        }
    }

    public String getFarmerViaPhone(String phone) {
        if (farmerList == null || farmerList.size() == 0) return null;
        String result = null;
        for (Farmer farmer : farmerList) {
            if (farmer.getPhone().equalsIgnoreCase(phone)) {
                result = farmer.getName();
                break;
            }
        }
        return result;
    }
}
