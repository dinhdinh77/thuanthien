package com.farm.dinh.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.text.TextUtils;

import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.R;
import com.farm.dinh.ui.login.LoggedInUserView;
import com.farm.dinh.ui.viewmodel.model.LoginFormState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

public class LoginViewModel extends BaseViewModel<LoginRepository, ViewResult<LoggedInUserView>> {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public LoginViewModel(LoginRepository repository) {
        super(repository);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        getRepository().login(username, password, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                getResult().setValue(new ViewResult<>(new LoggedInUserView(success.getData().getName())));
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

    // A placeholder username validation check
    private boolean isUserNameError(String username) {
        return TextUtils.isEmpty(username);
    }

    // A placeholder password validation check
    private boolean isPasswordError(String password) {
        return TextUtils.isEmpty(password);
    }
}
