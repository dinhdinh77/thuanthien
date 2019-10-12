package com.farm.dinh.repository;

import android.text.TextUtils;
import android.util.Pair;

import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.datasource.LoginDataSource;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.local.Pref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LoginRepository extends Repository<LoginDataSource> {
    private static volatile LoginRepository instance;
    private UserInfo user = null;
    private String currPhone;
    private String currPass;
    private List<City> cityList;

    private LoginRepository(LoginDataSource dataSource) {
        super(dataSource);
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        getDataSource().logout();
        Pref.getInstance().clearPreferences();
    }

    private void setLoggedInUser(UserInfo user, String username, String password) {
        this.user = user;
        this.currPhone = username;
        this.currPass = password;
        Pref.getInstance().set(Pref.KEY_USER_ID, user.getId());
        Pref.getInstance().set(Pref.KEY_PHONE, username);
        Pref.getInstance().set(Pref.KEY_PASS, password);
        String farmers = new Gson().toJson(user.getFarmers());
        Pref.getInstance().set(Pref.KEY_FARMERS, farmers);
    }

    public Pair<String, String> getPreviousUser() {
        currPhone = TextUtils.isEmpty(currPhone) ? Pref.getInstance().get(Pref.KEY_PHONE, "") : currPhone;
        currPass = TextUtils.isEmpty(currPass) ? Pref.getInstance().get(Pref.KEY_PASS, "") : currPass;
        return new Pair<>(currPhone, currPass);
    }

    public void login(final String username, final String password, final IRepository<UserInfo> listener) {
        getDataSource().login(username, password, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                setLoggedInUser(success.getData(), username, password);
                listener.onSuccess(success);
            }

            @Override
            public void onError(Result.Error error) {
                listener.onError(error);
            }
        });
    }

    public void getUserInfo(IRepository<UserInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getUserInfo(currUserId, listener);
    }

    public void updateUserInfo(String name, String district, String street, String ward, String city,
                               String area, String oldPassword, String newPassword, boolean isChangePass, IRepository<UserInfo> listener) {
        if (!isChangePass && isLoggedIn()) {
            if (this.user.getName().equals(name) && this.user.getCity().equals(city) && this.user.getDistrict().equals(district)
                    && this.user.getWard().equals(ward) && this.user.getStreet().equals(street)) return;
            oldPassword = null;
            newPassword = null;
        }
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().updateUserInfo(currUserId, name, district, street, ward, city, area, oldPassword, newPassword, listener);
    }

    public boolean isAgency(){
        return this.user == null || this.user.getIsAgency() == 0 ? false : true;
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

    public void createUser(String phone, String name, String street, String ward, String district, String city, String area, IRepository<FarmerInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().createUser(currUserId, phone, name, street, ward, district, city, area, listener);
    }

    public void editUser(int farmerId, String phone, String name, String street, String ward, String district, String city, String area, IRepository<FarmerInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().editUser(currUserId, farmerId, phone, name, street, ward, district, city, area, listener);
    }
}
