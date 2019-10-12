package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class OrderManagerViewModel extends PagingBaseViewModel<Order, MainRepository>{

    public OrderManagerViewModel(MainRepository repository) {
        super(repository);
    }

    @Override
    protected void getListEmptyKeyword(int currPage, IPagingRepository<List<Order>> listener) {
        getRepository().getOrderHistory(currPage, listener);
    }

    @Override
    protected void getListWithKeyword(String keyword, int currPage, IPagingRepository<List<Order>> listener) {
        getRepository().searchOrders(keyword, currPage, listener);
    }
}
