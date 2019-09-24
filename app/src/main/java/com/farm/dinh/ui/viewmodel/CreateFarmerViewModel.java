package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Ward;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.CreateFarmerState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class CreateFarmerViewModel extends BaseViewModel<MainRepository, FarmerInfo> {
    public CreateFarmerViewModel(MainRepository repository) {
        super(repository);
    }

    private MutableLiveData<List<City>> listAddress = new MutableLiveData<>();
    private MutableLiveData<CreateFarmerState> stateLiveData = new MutableLiveData<>();

    public MutableLiveData<List<City>> getListAddress() {
        return listAddress;
    }

    public MutableLiveData<CreateFarmerState> getStateLiveData() {
        return stateLiveData;
    }

    public void checkDataChange(String phone, String name, String street, City city, District district, Ward ward) {
        CreateFarmerState state = new CreateFarmerState();
        if (TextUtils.isEmpty(phone)) {
            state.setPhoneError(R.string.invalid_username);
        } else if (TextUtils.isEmpty(name)) {
            state.setNameError(R.string.invalid_name);
        } else if (city == null) {
            state.setCityError(R.string.invalid_selected_city);
        } else if (district == null) {
            state.setDistrictError(R.string.invalid_selected_district);
        } else if (ward == null) {
            state.setWardError(R.string.invalid_selected_ward);
        } else if (TextUtils.isEmpty(street)) {
            state.setStreetError(R.string.invalid_street);
        }
        stateLiveData.setValue(state);
    }

    public void createFarmer(String phone, String name, String street, City city, District district, Ward ward) {
        CreateFarmerState state = stateLiveData.getValue();
        if (state == null || !state.isDataVaild()) return;
        getRepository().createUser(phone, name, street, ward == null ? null : ward.getName(), district == null ?
                null : district.getName(), city == null ? null : city.getName(), new IRepository<FarmerInfo>() {
            @Override
            public void onSuccess(Result.Success<FarmerInfo> success) {
                getResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

    public void getAddress() {
        getRepository().getAddress(new IRepository<List<City>>() {
            @Override
            public void onSuccess(Result.Success<List<City>> success) {
                listAddress.setValue(success.getData());
            }

            @Override
            public void onError(Result.Error error) {
                listAddress.setValue(null);
            }
        });
    }
}
