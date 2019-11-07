package com.farm.dinh.repository;

import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.datasource.MainDataSource;
import com.farm.dinh.datasource.UserDataSource;
import com.farm.dinh.local.Pref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainRepository extends Repository {
    private static volatile MainRepository instance;
    private List<Farmer> farmerList;

    public static MainRepository getInstance() {
        if (instance == null) {
            instance = new MainRepository();
        }
        return instance;
    }

    public void getQuestionsList(IRepository<Questions> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(MainDataSource.class).getQuestionsList(currUserId, listener);
    }

    public void addAnswer(int questionId, String answer, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(MainDataSource.class).addAnswer(currUserId, questionId, answer, listener);
    }

    public void getOrderHistory(int page, IPagingRepository<List<Order>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(MainDataSource.class).getOrderHistory(currUserId, page, listener);
    }

    public void searchOrders(String searchKey, int page, IPagingRepository<List<Order>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(MainDataSource.class).searchOrders(currUserId, searchKey, page, listener);
    }

    public void getFarmersList(int page, IPagingRepository<List<FarmerInfo>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(UserDataSource.class).getFarmersList(currUserId, page, listener);
    }

    public void searchFarmers(String searchKey, int page, IPagingRepository<List<FarmerInfo>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource(UserDataSource.class).searchFarmers(currUserId, searchKey, page, listener);
    }

    public void getProductsList(IRepository<List<Product>> listener) {
        getDataSource(MainDataSource.class).getProductsList(listener);
        if (farmerList == null) {
            String json = Pref.getInstance().get(Pref.KEY_FARMERS, "");
            farmerList = new Gson().fromJson(json, new TypeToken<List<Farmer>>() {
            }.getType());
        }
    }

    public void createOrder(Order order, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        order.setAgencyId(currUserId);
        getDataSource(MainDataSource.class).createOrder(order, listener);
    }

    public void editOrder(Order order, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        order.setAgencyId(currUserId);
        getDataSource(MainDataSource.class).editOrder(order, listener);
    }

    public void getTreesByFarmer(int farmerId, IRepository<List<TreeInfo>> listener) {
        getDataSource(MainDataSource.class).getTreesByFarmer(farmerId, listener);
    }

    public void getTreesList(IRepository<List<Tree>> listener) {
        getDataSource(MainDataSource.class).getTreesList(listener);
    }

    public void addTree(int farmerId, int treeId, String age, int amount, IRepository<String> listener) {
        getDataSource(MainDataSource.class).addTree(farmerId, treeId, age, amount, listener);
    }

    public void editTree(int id, int treeId, String age, int amount, IRepository<String> listener) {
        getDataSource(MainDataSource.class).editTree(id, treeId, age, amount, listener);
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
