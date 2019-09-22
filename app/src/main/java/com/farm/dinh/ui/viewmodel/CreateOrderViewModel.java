package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class CreateOrderViewModel extends BaseViewModel<MainRepository, List<Product>>{
    public CreateOrderViewModel(MainRepository repository) {
        super(repository);
    }

    public void getProductsList(){
        getRepository().getProductsList(new IRepository<List<Product>>() {
            @Override
            public void onSuccess(Result.Success<List<Product>> success) {
                getResult().setValue(new ViewResult(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }
}
