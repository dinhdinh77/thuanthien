package com.farm.dinh.repository;

import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.datasource.MainDataSource;
import com.farm.dinh.local.Pref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainRepository extends Repository<MainDataSource> {
    private static volatile MainRepository instance;
    private List<Farmer> farmerList;

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

    public void addAnswer(int questionId, String answer, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().addAnswer(currUserId, questionId, answer, listener);
    }

    public void getOrderHistory(int page, IPagingRepository<List<Order>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getOrderHistory(currUserId, page, listener);
    }

    public void searchOrders(String searchKey, int page, IPagingRepository<List<Order>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().searchOrders(currUserId, searchKey, page, listener);
    }

    public void getFarmersList(int page, IPagingRepository<List<FarmerInfo>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getFarmersList(currUserId, page, listener);
    }

    public void searchFarmers(String searchKey, int page, IPagingRepository<List<FarmerInfo>> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().searchFarmers(currUserId, searchKey, page, listener);
    }

    public void getProductsList(IRepository<List<Product>> listener) {
        getDataSource().getProductsList(listener);
        if (farmerList == null) {
            String json = Pref.getInstance().get(Pref.KEY_FARMERS, "");
            farmerList = new Gson().fromJson(json, new TypeToken<List<Farmer>>() {
            }.getType());
        }
    }

    public void createOrder(Order order, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        order.setAgencyId(currUserId);
        getDataSource().createOrder(order, listener);
    }

    public void editOrder(Order order, IRepository<String> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        order.setAgencyId(currUserId);
        getDataSource().editOrder(order, listener);
    }

    public void getTreesByFarmer(int farmerId, IRepository<List<TreeInfo>> listener) {
        getDataSource().getTreesByFarmer(farmerId, listener);
    }

    public void getTreesList(IRepository<List<Tree>> listener) {
        getDataSource().getTreesList(listener);
    }

    public void addTree(int farmerId, int treeId, String age, int amount, IRepository<String> listener) {
        getDataSource().addTree(farmerId, treeId, age, amount, listener);
    }

    public void editTree(int id, int treeId, String age, int amount, IRepository<String> listener) {
        getDataSource().editTree(id, treeId, age, amount, listener);
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
