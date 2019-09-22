package com.farm.dinh.repository;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.datasource.MainDataSource;
import com.farm.dinh.local.Pref;

import java.util.List;

public class MainRepository extends Repository<MainDataSource> {
    private static volatile MainRepository instance;

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

    public void getProductsList(IRepository<List<Product>> listener){
        getDataSource().getProductsList(listener);
    }
}
