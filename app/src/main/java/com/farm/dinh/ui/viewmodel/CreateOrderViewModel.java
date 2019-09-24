package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.CreateOrderState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class CreateOrderViewModel extends BaseViewModel<MainRepository, List<Product>> {
    private MutableLiveData<CreateOrderState> liveDataViewState = new MutableLiveData<>();
    private MutableLiveData<String> liveDataResultCreateOrder = new MutableLiveData<>();

    public CreateOrderViewModel(MainRepository repository) {
        super(repository);
    }

    public MutableLiveData<CreateOrderState> getLiveDataViewState() {
        return liveDataViewState;
    }

    public MutableLiveData<String> getLiveDataResultCreateOrder() {
        return liveDataResultCreateOrder;
    }

    public void getProductsList() {
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

    public void createOrderDataChange(String phone, String quantity, Product product) {
        CreateOrderState state = new CreateOrderState();
        if (TextUtils.isEmpty(phone)) {
            state.setPhoneError(R.string.invalid_username);
        } else {
            state.setName(getRepository().getFarmerViaPhone(phone));
        }
        if (TextUtils.isEmpty(quantity)) {
            state.setQuantityError(R.string.invalid_quantity);
        }
        if (product == null) {
            state.setProductError(R.string.invalid_product);
        }
        liveDataViewState.setValue(state);
    }

    public void createOrder(String phone, String quantity, Product product) {
        CreateOrderState state = liveDataViewState.getValue();
        if (state == null || !state.isDataVaild() || product == null) return;
        getRepository().createOrder(phone, quantity, product.getId(), new IRepository<String>() {
            @Override
            public void onSuccess(Result.Success<String> success) {
                liveDataResultCreateOrder.setValue(success.getData());
            }

            @Override
            public void onError(Result.Error error) {
                liveDataResultCreateOrder.setValue(error.getError().getMessage());
            }
        });
    }
}
