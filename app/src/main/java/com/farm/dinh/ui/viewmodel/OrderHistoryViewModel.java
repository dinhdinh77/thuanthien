package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class OrderHistoryViewModel extends BaseViewModel<MainRepository, List<Order>> {
    public OrderHistoryViewModel(MainRepository repository) {
        super(repository);
    }

    public void getOrderHistory(){
        getRepository().getOrderHistory(new IRepository<List<Order>>() {
            @Override
            public void onSuccess(Result.Success<List<Order>> success) {
                getResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }
}
