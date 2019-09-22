package com.farm.dinh.repository;

import android.text.TextUtils;
import android.util.Pair;

import com.farm.dinh.datasource.LoginDataSource;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.local.Pref;

public class LoginRepository extends Repository<LoginDataSource> {
    private static volatile LoginRepository instance;
    private UserInfo user = null;
    private String currPhone;
    private String currPass;

    private LoginRepository(LoginDataSource dataSource) {
        super(dataSource);
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        getDataSource().logout();
        Pref.getInstance().clearPreferences();
    }

    private void setLoggedInUser(UserInfo user, String username, String password) {
        this.user = user;
        this.currPhone = username;
        this.currPass = password;
        Pref.getInstance().set(Pref.KEY_USER_ID, user.getId());
        Pref.getInstance().set(Pref.KEY_PHONE, username);
        Pref.getInstance().set(Pref.KEY_PASS, password);
    }

    public Pair<String, String> getPreviousUser() {
        currPhone = TextUtils.isEmpty(currPhone) ? Pref.getInstance().get(Pref.KEY_PHONE, "") : currPhone;
        currPass = TextUtils.isEmpty(currPass) ? Pref.getInstance().get(Pref.KEY_PASS, "") : currPass;
        return new Pair<>(currPhone, currPass);
    }

    public void login(final String username, final String password, final IRepository<UserInfo> listener) {
        getDataSource().login(username, password, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                setLoggedInUser(success.getData(), username, password);
                listener.onSuccess(success);
            }

            @Override
            public void onError(Result.Error error) {
                listener.onError(error);
            }
        });
    }

    public void getUserInfo(IRepository<UserInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getUserInfo(currUserId, listener);
    }

    public void updateUserInfo(String name, String oldPassword, String newPassword, boolean isChangePass, IRepository<UserInfo> listener) {
        if (!isChangePass && isLoggedIn()) {
            if (this.user.getName().equals(name)) return;
            oldPassword = null;
            newPassword = null;
        }
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().updateUserInfo(currUserId, name, oldPassword, newPassword, listener);
    }
}
