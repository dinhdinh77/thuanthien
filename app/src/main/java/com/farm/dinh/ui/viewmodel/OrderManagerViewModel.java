package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.model.Order;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.MainRepository;

import java.util.List;

public class OrderManagerViewModel extends PagingBaseViewModel<Order> {

    @Override
    protected void getListEmptyKeyword(int currPage, IPagingRepository<List<Order>> listener) {
        getRepository(MainRepository.class).getOrderHistory(currPage, listener);
    }

    @Override
    protected void getListWithKeyword(String keyword, int currPage, IPagingRepository<List<Order>> listener) {
        getRepository(MainRepository.class).searchOrders(keyword, currPage, listener);
    }
}
