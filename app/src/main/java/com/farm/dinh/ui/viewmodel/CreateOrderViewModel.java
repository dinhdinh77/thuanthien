package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.CreateOrderState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class CreateOrderViewModel extends BaseViewModel<MainRepository, List<Product>> {
    private MutableLiveData<CreateOrderState> liveDataViewState = new MutableLiveData<>();
    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Pair<Boolean, String>> liveDataResultCreateOrder = new MutableLiveData<>();

    public CreateOrderViewModel(MainRepository repository) {
        super(repository);
    }

    public MutableLiveData<CreateOrderState> getLiveDataViewState() {
        return liveDataViewState;
    }


    public MutableLiveData<Order> getOrderMutableLiveData() {
        return orderMutableLiveData;
    }

    public MutableLiveData<Pair<Boolean, String>> getLiveDataResultCreateOrder() {
        return liveDataResultCreateOrder;
    }

    public String getFarmerViaPhone(String phone) {
        return getRepository().getFarmerViaPhone(phone);
    }

    public void setOrderData(Order orderData) {
        if (orderData == null) orderData = new Order();
        if (!TextUtils.isEmpty(orderData.getPhone()) && !TextUtils.isEmpty(orderData.getName()))
            orderData.setName(getRepository().getFarmerViaPhone(orderData.getPhone()));
        this.orderMutableLiveData.setValue(orderData);
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

    public void processOrder(String phone, String name, List<Product> products) {
        CreateOrderState state = new CreateOrderState();
        if (TextUtils.isEmpty(phone)) {
            state.setPhoneError(R.string.invalid_username);
        }

        for (int idx = 0; idx < products.size(); idx++) {
            Product product = products.get(idx);
            if (product.getQuantity() < 1) {
                state.setQuantityIndexError(idx);
                break;
            }
        }

        if (!state.isDataVaild()) {
            liveDataViewState.setValue(state);
            return;
        } else {
            Order order = orderMutableLiveData.getValue();
            order.setName(name);
            order.setPhone(phone);
            order.setProducts(products);
            IRepository<String> listener = new IRepository<String>() {
                @Override
                public void onSuccess(Result.Success<String> success) {
                    liveDataResultCreateOrder.setValue(new Pair<>(true, success.getData()));
                }

                @Override
                public void onError(Result.Error error) {
                    liveDataResultCreateOrder.setValue(new Pair<>(false, error.getError().getMessage()));
                }
            };
            if (!TextUtils.isEmpty(order.getOrderId())) {
                getRepository().editOrder(order, listener);
            } else {
                getRepository().createOrder(order, listener);
            }
        }
    }
}
