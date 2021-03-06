package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;
import android.util.Pair;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.ui.viewmodel.model.LoggedInUserView;
import com.farm.dinh.ui.viewmodel.model.LoginFormState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel<LoginRepository, LoggedInUserView> {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<Pair<String, String>> previousUser = new MutableLiveData<>();

    public LoginViewModel(LoginRepository repository) {
        super(repository);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<Pair<String, String>> getPreviousUser() {
        return previousUser;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        getRepository().login(username, password, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getResult().setValue(new ViewResult<>(new LoggedInUserView(success.getData().getName(), success.getData().getIsAgency())));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });

    }

    public void loginDataChanged(String username, String password) {
        if (isUserNameError(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (isPasswordError(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void getAutoFillUser(){
        getPreviousUser().setValue(getRepository().getPreviousUser());
    }

    // A placeholder username validation check
    private boolean isUserNameError(String username) {
        return TextUtils.isEmpty(username);
    }

    // A placeholder password validation check
    private boolean isPasswordError(String password) {
        return TextUtils.isEmpty(password);
    }
}
