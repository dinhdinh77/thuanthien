package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Ward;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.ui.viewmodel.model.CreateFarmerState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class CreateFarmerViewModel extends BaseViewModel<LoginRepository, FarmerInfo> {
    public CreateFarmerViewModel(LoginRepository repository) {
        super(repository);
    }

    private MutableLiveData<List<City>> listAddress = new MutableLiveData<>();
    private MutableLiveData<CreateFarmerState> stateLiveData = new MutableLiveData<>();
    private MutableLiveData<FarmerInfo> farmerInfoLiveData = new MutableLiveData<>();

    public MutableLiveData<List<City>> getListAddress() {
        return listAddress;
    }

    public MutableLiveData<CreateFarmerState> getStateLiveData() {
        return stateLiveData;
    }

    public MutableLiveData<FarmerInfo> getFarmerInfoLiveData() {
        return farmerInfoLiveData;
    }

    public void checkDataChange(String phone, String name, String street, City city, District district, Ward ward, String area) {
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
        } else if (TextUtils.isEmpty(area)) {
            state.setAreaError(R.string.invalid_area);
        }
        stateLiveData.setValue(state);
    }

    public void processFarmer(String phone, String name, String street, City city, District district, Ward ward, String area) {
        final FarmerInfo farmerInfo = getFarmerInfoLiveData().getValue();
        CreateFarmerState state = getStateLiveData().getValue();
        if (state == null || !state.isDataVaild()) return;
        IRepository<FarmerInfo> listener = new IRepository<FarmerInfo>() {
            @Override
            public void onSuccess(Result.Success<FarmerInfo> success) {
                getResult().setValue(new ViewResult<>(farmerInfo == null ? success.getData() : farmerInfo, farmerInfo != null));//for api editUser
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        };
        if (farmerInfo == null) {
            getRepository().createUser(phone, name, street, ward == null ? null : ward.getName(), district == null ?
                    null : district.getName(), city == null ? null : city.getName(), area, listener);
        } else {
            getRepository().editUser(farmerInfo.getId(), phone, name, street, ward == null ? null : ward.getName(),
                    district == null ? null : district.getName(), city == null ? null : city.getName(), area, listener);
        }
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
