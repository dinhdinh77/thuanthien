package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.data.model.Ward;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.ui.viewmodel.model.UpdateInfoState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserViewModel extends BaseViewModel<LoginRepository, UserInfo> {
    private MutableLiveData<UpdateInfoState> updateInfoState = new MutableLiveData<>();
    private MutableLiveData<List<City>> listAddress = new MutableLiveData<>();

    public UserViewModel(LoginRepository repository) {
        super(repository);
    }

    public MutableLiveData<UpdateInfoState> getUpdateInfoState() {
        return updateInfoState;
    }

    public MutableLiveData<List<City>> getListAddress() {
        return listAddress;
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

    public void getUserInfo() {
        getRepository().getUserInfo(new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getResult().setValue(new ViewResult<>(success.getData(), false));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage(), false));
            }
        });
    }

    public void updateUserInfo(String name, String district, String street, String ward, String city,
                               String area, String oldPassword, String newPassword, boolean isChangePass) {
        UpdateInfoState state = getUpdateInfoState().getValue();
        if (state != null && !state.isDataVaild()) return;
        getRepository().updateUserInfo(name, district, street, ward, city, area, oldPassword, newPassword, isChangePass, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getResult().setValue(new ViewResult<>(success.getData(), true));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage(), true));
            }
        });
    }

    public void logout() {
        getRepository().logout();
    }

    public boolean isAgency() {
        return getRepository().isAgency();
    }

    public void updateInfoDataChanged(String name, String street, City city, District district, Ward ward, String oldPass, String newPass, String newPassAgain, boolean isChangePass) {
        UpdateInfoState updateInfo = new UpdateInfoState();
        updateInfo.setChangePass(isChangePass);
        if (isUserNameError(name)) {
            updateInfo.setUsernameError(R.string.invalid_name);
        } else if (city == null) {
            updateInfo.setCityError(R.string.invalid_selected_city);
        } else if (district == null) {
            updateInfo.setDistrictError(R.string.invalid_selected_district);
        } else if (ward == null) {
            updateInfo.setWardError(R.string.invalid_selected_ward);
        } else if (TextUtils.isEmpty(street)) {
            updateInfo.setStreetError(R.string.invalid_street);
        }
        if (isChangePass) {
            if (isPasswordError(oldPass)) {
                updateInfo.setOldPasswordError(R.string.invalid_password);
            } else if (isNewPasswordError(newPass)) {
                updateInfo.setNewPasswordError(R.string.invalid_password);
            } else if (isNewPasswordNotEnough(newPass)) {
                updateInfo.setNewPasswordError(R.string.invalid_new_password_2);
            } else if (isNewPasswordError(newPassAgain)) {
                updateInfo.setNewPasswordAgainError(R.string.invalid_password);
            } else if (isNewPasswordNotEnough(newPassAgain)) {
                updateInfo.setNewPasswordAgainError(R.string.invalid_new_password_2);
            } else if (!isSame(newPass, newPassAgain)) {
                updateInfo.setNewPasswordError(R.string.invalid_new_password_3);
                updateInfo.setNewPasswordAgainError(R.string.invalid_new_password_3);
            }
        }
        updateInfoState.setValue(updateInfo);
    }

    private boolean isUserNameError(String username) {
        return TextUtils.isEmpty(username);
    }

    private boolean isPasswordError(String password) {
        return TextUtils.isEmpty(password);
    }

    private boolean isNewPasswordError(String password) {
        return TextUtils.isEmpty(password);
    }

    private boolean isNewPasswordNotEnough(String password) {
        return password != null && password.length() < 6;
    }

    private boolean isSame(String password, String passwordAgain) {
        return password != null && passwordAgain != null && password.compareTo(passwordAgain) == 0;
    }

}
