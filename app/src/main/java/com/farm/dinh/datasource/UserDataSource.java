package com.farm.dinh.datasource;

import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.remote.IUserService;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.IRepository;

import java.util.List;

public class UserDataSource extends DataSource<IUserService>{
    @Override
    public Class<IUserService> getServiceType() {
        return IUserService.class;
    }

    public void getAddress(final IRepository<List<City>> listener) {
        getRemoteService().getAddress().enqueue(getStandardCallBack(listener));
    }

    public void getUserInfo(int userId, final IRepository<UserInfo> listener) {
        getRemoteService().getUserInfo(userId).enqueue(getStandardCallBack(listener));
    }

    public void updateUserInfo(int userId, String name, String district, String street, String ward, String city, String area, String old_password, String new_password, final IRepository<UserInfo> listener) {
        getRemoteService().updateUserInfo(userId, name, district, street, ward, city, area, old_password, new_password).enqueue(getStandardCallBack(listener));
    }

    public void createUser(int userId, String phone, String name, String street, String ward, String district, String city, String area, final IRepository<FarmerInfo> listener) {
        getRemoteService().createUser(userId, phone, name, street, ward, district, city, area).enqueue(getStandardCallBack(listener));
    }

    public void editUser(int userId, int farmerId, String phone, String name, String street, String ward, String district, String city, String area, final IRepository<FarmerInfo> listener) {
        getRemoteService().editUser(userId, farmerId, phone, name, street, ward, district, city, area).enqueue(getStandardCallBack(listener));
    }

    public void getFarmersList(int userId, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getRemoteService().getFarmersList(userId, page).enqueue(getPagingCallBack(listener));
    }

    public void searchFarmers(int userId, String searchKey, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getRemoteService().searchFarmers(userId, searchKey, page).enqueue(getPagingCallBack(listener));
    }
}
