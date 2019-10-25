package com.farm.dinh.datasource;

import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.remote.IMainService;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.IRepository;

import java.util.List;

public class MainDataSource extends DataSource<IMainService> {
    public void getQuestionsList(int userId, final IRepository<Questions> listener) {
        getRemoteService().getQuestionsList(userId).enqueue(getStandardCallBack(listener));
    }

    public void addAnswer(int userId, int questionId, String answer, final IRepository<String> listener) {
        getRemoteService().addAnswer(userId, questionId, answer).enqueue(getSimpleCallBack(listener));
    }

    public void getOrderHistory(int userId, int page, final IPagingRepository<List<Order>> listener) {
        getRemoteService().getOrderHistory(userId, page).enqueue(getPagingCallBack(listener));
    }

    public void searchOrders(int userId, String searchKey, int page, final IPagingRepository<List<Order>> listener) {
        getRemoteService().searchOrders(userId, searchKey, page).enqueue(getPagingCallBack(listener));
    }

    public void getFarmersList(int userId, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getRemoteService().getFarmersList(userId, page).enqueue(getPagingCallBack(listener));
    }

    public void searchFarmers(int userId, String searchKey, int page, final IPagingRepository<List<FarmerInfo>> listener) {
        getRemoteService().searchFarmers(userId, searchKey, page).enqueue(getPagingCallBack(listener));
    }

    public void getProductsList(final IRepository<List<Product>> listener) {
        getRemoteService().getProductsList().enqueue(getStandardCallBack(listener));
    }

    public void createOrder(Order order, final IRepository<String> listener) {
        getRemoteService().createOrder(order).enqueue(getSimpleCallBack(listener));
    }

    public void editOrder(Order order, final IRepository<String> listener) {
        getRemoteService().editOrder(order).enqueue(getSimpleCallBack(listener));
    }

    public void getTreesByFarmer(int farmerId, final IRepository<List<TreeInfo>> listener) {
        getRemoteService().getTreesByFarmer(farmerId).enqueue(getStandardCallBack(listener));
    }

    public void getTreesList(final IRepository<List<Tree>> listener) {
        getRemoteService().getTreesList().enqueue(getStandardCallBack(listener));
    }

    public void addTree(int farmerId, int treeId, String age, int amount, final IRepository<String> listener) {
        getRemoteService().addTree(farmerId, treeId, age, amount).enqueue(getSimpleCallBack(listener));
    }

    public void editTree(int id, int treeId, String age, int amount, final IRepository<String> listener) {
        getRemoteService().editTree(id, treeId, age, amount).enqueue(getSimpleCallBack(listener));
    }

    @Override
    public Class<IMainService> getServiceType() {
        return IMainService.class;
    }
}
