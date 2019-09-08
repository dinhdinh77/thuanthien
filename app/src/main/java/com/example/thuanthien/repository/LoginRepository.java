package com.example.thuanthien.repository;

import com.example.thuanthien.datasource.LoginDataSource;
import com.example.thuanthien.data.Result;
import com.example.thuanthien.data.model.UserInfo;
import com.example.thuanthien.local.Pref;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository extends Repository<LoginDataSource> {
    private static volatile LoginRepository instance;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private UserInfo user = null;

    // private constructor : singleton access
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

    private void setLoggedInUser(UserInfo user) {
        this.user = user;
        Pref.getInstance().set(Pref.KEY_USER_ID, user.getId());
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String username, String password, final IRepository<UserInfo> listener) {
        // handle login
        getDataSource().login(username, password, new IRepository<UserInfo>() {
            @Override
            public void onSuccess(Result.Success<UserInfo> success) {
                setLoggedInUser(success.getData());
                listener.onSuccess(success);
            }

            @Override
            public void onError(Result.Error error) {
                listener.onError(error);
            }
        });
    }
}
