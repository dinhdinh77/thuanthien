package com.example.thuanthien.ui.viewmodel;

import android.text.TextUtils;

import com.example.thuanthien.R;
import com.example.thuanthien.data.Result;
import com.example.thuanthien.data.model.UserInfo;
import com.example.thuanthien.repository.IRepository;
import com.example.thuanthien.repository.MainRepository;
import com.example.thuanthien.ui.viewmodel.model.UpdateInfoState;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends BaseViewModel<MainRepository, ViewResult<UserInfo>> {
    private MutableLiveData<UpdateInfoState> updateInfoState = new MutableLiveData<>();

    public MutableLiveData<UpdateInfoState> getUpdateInfoState() {
        return updateInfoState;
    }

    public UserViewModel(MainRepository repository) {
        super(repository);
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

    public void updateUserInfo(String name, String old_password, String new_password) {
        getRepository().updateUserInfo(name, old_password, new_password, new IRepository<UserInfo>() {
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
            } else if (!isNewPasswordSame(newPass, newPassAgain)) {
                updateInfo.setNewPasswordError(R.string.invalid_new_password_3);
                updateInfo.setNewPasswordAgainError(R.string.invalid_new_password_3);
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

    private boolean isNewPasswordSame(String password, String passwordAgain) {
        return password != null && passwordAgain != null && password.equals(passwordAgain);
    }

}
