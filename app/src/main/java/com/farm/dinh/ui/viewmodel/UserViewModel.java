package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.UpdateInfoState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends BaseViewModel<MainRepository, ViewResult<UserInfo>> {
    private MutableLiveData<UpdateInfoState> updateInfoState = new MutableLiveData<>();
    private MutableLiveData<ViewResult<UserInfo>> userUpdate = new MutableLiveData<>();

    public UserViewModel(MainRepository repository) {
        super(repository);
    }

    public MutableLiveData<UpdateInfoState> getUpdateInfoState() {
        return updateInfoState;
    }

    public MutableLiveData<ViewResult<UserInfo>> getUserUpdate() {
        return userUpdate;
    }

    public void getUserInfo() {
        getRepository().getUserInfo(new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

    public void updateUserInfo(String name, String oldPassword, String newPassword, boolean isChangePass) {
        UpdateInfoState state = getUpdateInfoState().getValue();
        ViewResult<UserInfo> currUser = getResult().getValue();
        if (state != null && !state.isDataVaild()) return;
        if (!isChangePass) {
            if (currUser != null && currUser.getSuccess() != null && isSame(currUser.getSuccess().getName(), name))
                return;
            oldPassword = null;
            newPassword = null;
        }
        getRepository().updateUserInfo(name, oldPassword, newPassword, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getUserUpdate().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getUserUpdate().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

    public void updateInfoDataChanged(String name, String oldPass, String newPass, String newPassAgain, boolean isChangePass) {
        UpdateInfoState updateInfo = new UpdateInfoState();
        if (isUserNameError(name)) {
            updateInfo.setUsernameError(R.string.invalid_name);
            updateInfoState.setValue(updateInfo);
        }
        if (isChangePass) {
            if (isPasswordError(oldPass)) {
                updateInfo.setOldPasswordError(R.string.invalid_password);
                updateInfoState.setValue(updateInfo);
            } else if (isNewPasswordError(newPass)) {
                updateInfo.setNewPasswordError(R.string.invalid_password);
                updateInfoState.setValue(updateInfo);
            } else if (isNewPasswordNotEnough(newPass)) {
                updateInfo.setNewPasswordError(R.string.invalid_new_password_2);
                updateInfoState.setValue(updateInfo);
            } else if (isNewPasswordError(newPassAgain)) {
                updateInfo.setNewPasswordAgainError(R.string.invalid_password);
                updateInfoState.setValue(updateInfo);
            } else if (isNewPasswordNotEnough(newPassAgain)) {
                updateInfo.setNewPasswordAgainError(R.string.invalid_new_password_2);
                updateInfoState.setValue(updateInfo);
            } else if (!isSame(newPass, newPassAgain)) {
                updateInfo.setNewPasswordError(R.string.invalid_new_password_3);
                updateInfo.setNewPasswordAgainError(R.string.invalid_new_password_3);
                updateInfoState.setValue(updateInfo);
            } else {
                updateInfo.setDataVaild(true);
                updateInfoState.setValue(updateInfo);
            }
        }
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
